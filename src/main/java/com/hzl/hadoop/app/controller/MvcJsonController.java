package com.hzl.hadoop.app.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import com.hzl.cloud.config.mybatis.DataSource;
import com.hzl.hadoop.app.dataobject.ContractDO;
import com.hzl.hadoop.app.service.MybatisService;
import com.hzl.hadoop.app.service.RedisService;
import com.hzl.hadoop.app.vo.PaymentVO;
import com.hzl.hadoop.websocket.service.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

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

	@Autowired
	private WebSocket webSocket;

	@GetMapping(value = "/jsontest")
	public ResponseEntity<PaymentVO> jsonTest() {
		return new ResponseEntity<PaymentVO>(new PaymentVO(), HttpStatus.OK);
	}
	
	/**
	 * <p>
	 * master数据库读取
	 * </p>
	 * 
	 * @author hzl 2020/01/08 12:41 PM
	 */
	@GetMapping(value = "/query")
	public ResponseEntity<ContractDO> qeuery() {

		return new ResponseEntity<ContractDO>(mybatisService.select(), HttpStatus.OK);
	}

	/**
	 * <p>
	 * slave1数据库读取
	 * </p>
	 * 
	 * @author hzl 2020/01/08 12:42 PM
	 */
	@GetMapping(value = "/querysalve")
	@DataSource(name = "salve1")
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

		List<Long> returnResult=redisService.selectRedisListLong();
		System.out.println("返回结果"+ returnResult);
		return new ResponseEntity<>(returnResult, HttpStatus.OK);
	}

	@PutMapping(value = "/update")
	public void update(@RequestParam Date localDate){

		redisService.update(localDate);
	}

	@GetMapping(value = "/thread")
	public void thread(){
		int page=0;
		List<Future<Boolean>> futureList = new ArrayList<>();

		for(int i=0;i<=1000;i++){
			AsyncResult<Boolean> result= redisService.threadTest(i);
			futureList.add(result);
			int tem=page++;
			log.info("结果"+page);
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

	@GetMapping(value = "/sendwebsocket")
	public void weblogic(){

		webSocket.sendMessage("front:测试websocket消息");
	}

	@GetMapping(value = "/api/public/test")
	public void interceptorPublic(){
		System.out.println("springmvc拦截器不拦截该方法");
	}


	@GetMapping(value = "/api/test")
	public void interceptorPrivate(){
		System.out.println("springmvc拦截器拦截该方法");
	}
}
