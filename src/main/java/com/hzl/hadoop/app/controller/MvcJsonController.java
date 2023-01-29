package com.hzl.hadoop.app.controller;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.hzl.hadoop.app.dataobject.ContractDO;
import com.hzl.hadoop.app.service.MybatisService;
import com.hzl.hadoop.app.service.RedisService;
import com.hzl.hadoop.app.vo.PaymentVO;
import com.hzl.hadoop.executor.SingleExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * description
 * WebMvcConfig中配置的消息转换器测试
 *
 * @author hzl 2020/01/03 10:24 PM
 */
@Slf4j
@RestController
public class MvcJsonController {
	@Autowired
	MybatisService mybatisService;

	@Autowired
	RedisService redisService;



	//源头
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	@Qualifier("redisTemplate1")
	private RedisTemplate<String, Object> redisTemplate1;

	@GetMapping(value = "/jsontest")
	public ResponseEntity<PaymentVO> jsonTest() {
		return new ResponseEntity<PaymentVO>(new PaymentVO(), HttpStatus.OK);
	}

	/**
	 * <p>
	 * master数据库读取
	 * </p>
	 * http://localhost:8888/query/pinyin?zw=李白
	 *
	 * @author hzl 2020/01/08 12:41 PM
	 */
	@GetMapping(value = "/query")
	public ResponseEntity<ContractDO> qeuery() {
		String pinyin = PinyinUtil.getPinyin("你好", " ");

		return new ResponseEntity<ContractDO>(mybatisService.select(), HttpStatus.OK);
	}

	/**
	 * <p>
	 * master数据库读取
	 * </p>
	 *
	 * @author hzl 2020/01/08 12:41 PM
	 */
	@GetMapping(value = "/query/pinyin")
	public ResponseEntity<String> qeueryPinyin(@RequestParam String zw) {

		String pinyin = PinyinUtil.getPinyin(zw, " ");

		return new ResponseEntity<String>(pinyin, HttpStatus.OK);
	}

	/**
	 * <p>
	 * slave1数据库读取
	 * </p>
	 *
	 * @author hzl 2020/01/08 12:42 PM
	 */
	@GetMapping(value = "/querysalve")
	public ResponseEntity<ContractDO> qeuerySlave() {

		return new ResponseEntity<ContractDO>(mybatisService.select(), HttpStatus.OK);
	}

	/**
	 * <p>
	 * master数据库读取,先从redis中读取
	 * </p>
	 *
	 * @author hzl 2020/01/08 12:41 PM
	 */
	@GetMapping(value = "/queryredis")
	public ResponseEntity<ContractDO> qeueryRedis() {

		return new ResponseEntity<ContractDO>(redisService.selectRedis(), HttpStatus.OK);
	}

	@GetMapping(value = "/updateRedis")
	public ResponseEntity<List<Long>> updateRedis() {

		List<Long> returnResult = redisService.selectRedisListLong();
		System.out.println("返回结果" + returnResult);
		return new ResponseEntity<>(returnResult, HttpStatus.OK);
	}

	@PutMapping(value = "/update")
	public void update(@RequestParam Date localDate) {

		redisService.update(localDate);
	}

	@GetMapping(value = "/thread")
	public void thread() {
		int page = 0;
		List<Future<Boolean>> futureList = new ArrayList<>();

		for (int i = 0; i <= 1000; i++) {
			AsyncResult<Boolean> result = redisService.threadTest(i);
			futureList.add(result);
			int tem = page++;
			log.info("结果" + page);
		}
		//等待循环中的业务操作完成，因为线程是异步的，如果此时直接结束方法，其实业务还在后台处理中
		while (true) {
			if (null != futureList) {
				boolean isAllDone = true;
				for (Future future : futureList) {
					if (null == future || !future.isDone()) {
						isAllDone = false;
					}
				}
				if (isAllDone) {
					log.info("结束循环结束");
					break;
				}
			}
		}

	}


	@GetMapping(value = "/api/public/test")
	public void interceptorPublic() {
		System.out.println("springmvc拦截器不拦截该方法");
	}


	@GetMapping(value = "/api/test")
	public void interceptorPrivate() {
		System.out.println("springmvc拦截器拦截该方法");
	}


	/**
	 * redis迁移工具，需要将redistemple的序列化换成，new StringRedisSerializer()（需要根据具体项目进行调试）
	 *
	 * @param null
	 * @author hzl 2021-11-19 2:57 PM
	 * @return
	 */
	@GetMapping(value = "/redis/migration")
	public ResponseEntity redisMigration() {
		//读取数据,获取所有key

		Set<String> allKey = redisTemplate.keys("dlink_oct.**");
		//log.info("所有key:{}",allKey);
		log.info("所有key数量:{}", allKey.size());
		List<CompletableFuture> completableFutures = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(allKey)) {
			log.info("线程池是否为空开始:{}",SingleExecutor.getInstance().isShutdown());
			allKey.forEach(key -> {
				CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {

					DataType dataType = redisTemplate.type(key);
					putValue(dataType, key);

				}, SingleExecutor.getInstance());

				completableFutures.add(completableFuture);

			});
		}
		CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();
		//因为不需要多次初始化，所有用完关闭线程池
		SingleExecutor.getInstance().shutdown();
		log.info("线程池是否为空结束:{}",SingleExecutor.getInstance().isShutdown());

		return new ResponseEntity(HttpStatus.OK);
	}

	private void putValue(DataType dataType, String key) {
		switch (dataType) {
			case STRING:

				if (!redisTemplate1.hasKey(key)) {

					//获取值
					String value = redisTemplate.opsForValue().get(key).toString();
					//获取过期时间
					Long exporeTime = redisTemplate.getExpire(key, TimeUnit.SECONDS);
					log.info("过期时间string：{}----{}",exporeTime,key);
					//插入数据
					redisTemplate1.opsForValue().set(key, value);
					//设置过期时间
					if(exporeTime.longValue()!=-1){
						//设置过期时间为-1会将key删除
						redisTemplate1.expire(key, exporeTime, TimeUnit.SECONDS);
					}

				}
				break;
			case SET:

				if (!redisTemplate1.hasKey(key)) {

					Set<Object> set = redisTemplate.opsForSet().members(key);
					log.info("set的key{}---{}",key,set.toString());
					//获取过期时间
					Long exporeTimeSet = redisTemplate.getExpire(key, TimeUnit.SECONDS);
					//插入数据
					log.info("过期时间set：{}",exporeTimeSet);
					//插入数据
					redisTemplate1.opsForSet().add(key, set.toArray());
					//设置过期时间
					if(exporeTimeSet.longValue()!=-1){
						redisTemplate1.expire(key, exporeTimeSet, TimeUnit.SECONDS);
					}
				}
				break;
			case LIST:
				log.info("未知类型" + dataType.code());
				break;
			case ZSET:
				log.info("未知类型" + dataType.code());
				break;
			case HASH:
				log.info("未知类型" + dataType.code());
				break;
			default:
				break;
		}


	}

}
