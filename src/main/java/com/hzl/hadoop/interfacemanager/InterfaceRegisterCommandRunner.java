package com.hzl.hadoop.interfacemanager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzl.hadoop.interfacemanager.annotation.Permission;
import com.hzl.hadoop.interfacemanager.entity.InterfaceManageEntity;
import com.hzl.hadoop.interfacemanager.service.InterfaceManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description
 * 应用启动后，当所有bean加载完成，读取系统中的所有接口，插入到内部接口管理表
 *
 * @author hzl 2021/11/16 1:56 PM
 */
@Slf4j
@Component
public class InterfaceRegisterCommandRunner implements CommandLineRunner {

	@Autowired
	private RequestMappingHandlerMapping mapping;

	//获取服务名
	@Value("${spring.application.name:hadoop}")
	private String appName;

	@Autowired
	private InterfaceManageService interfaceManageService;

	public List<InterfaceManageEntity> getUrl() {

		// 获取url与类和方法的对应信息
		Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

		List<InterfaceManageEntity> list = new ArrayList<>();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
			//InterfaceManageEntity
			InterfaceManageEntity interfaceManageEntity = new InterfaceManageEntity();
			RequestMappingInfo info = m.getKey();
			HandlerMethod method = m.getValue();

			//设置服务名称
			interfaceManageEntity.setServiceName(appName);


			PatternsRequestCondition p = info.getPatternsCondition();
			for (String url : p.getPatterns()) {
				//设置路径
				interfaceManageEntity.setUrl(url);
			}


			//接口描述:@GetMapping(value = "/export/excel" ,name = "通用下载接口") 该接口中的name
			interfaceManageEntity.setDescription(info.getName());

			//获取请求类型
			RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();

			if (CollectionUtils.isEmpty(methodsCondition.getMethods())) {
				interfaceManageEntity.setMethod("none");
			} else {
				for (RequestMethod requestMethod : methodsCondition.getMethods()) {
					//设置请求类型
					interfaceManageEntity.setMethod(requestMethod.name().toLowerCase());
				}
			}

			//设置code
			generateCode(method.getMethod(), interfaceManageEntity);
			//设置是否需要登陆
			annoationDetail(method.getMethod(), interfaceManageEntity);

			list.add(interfaceManageEntity);
		}
		return list;
	}

	//获取注解详细信息
	public void annoationDetail(Method method, InterfaceManageEntity interfaceManageEntity) {

		if (method.getAnnotation(Permission.class) != null) {
			//获取方法上注解
			interfaceManageEntity.setIsLogin(method.getAnnotation(Permission.class).isLogin());
			interfaceManageEntity.setTenantId(method.getAnnotation(Permission.class).tenantId());
		} else if (method.getDeclaringClass().getAnnotation(Permission.class) != null) {
			//获取类上注解
			interfaceManageEntity.setIsLogin(method.getDeclaringClass().getAnnotation(Permission.class).isLogin());
			interfaceManageEntity.setTenantId(method.getDeclaringClass().getAnnotation(Permission.class).tenantId());
		} else {
			//类上和方法上都没有注解的情况
			interfaceManageEntity.setIsLogin(false);
			interfaceManageEntity.setTenantId(1L);
		}

	}

	//生成iam_perimission的code,服务名+类名+方法名+操作
	public void generateCode(Method method, InterfaceManageEntity InterfaceManageEntity) {
		String className = method.getDeclaringClass().getSimpleName();
		String methodName = method.getName();
		StringBuilder code = new StringBuilder();
		code.append(appName).append(".").append(className)
				.append(".").append(methodName).append(".").append(InterfaceManageEntity.getMethod());
		InterfaceManageEntity.setCode(code.toString().toLowerCase());
	}

	//保存@permission的信息到数据库

	@Override
	public void run(String... args) {
	/*临时注视	//插入前先删除所有
		QueryWrapper<InterfaceManageEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("service_name", appName);
		interfaceManageService.remove(queryWrapper);
		//log.info("测试获取url2" + this.getUrl().toString());
		//插入数据库，后面可以换成消息中间件，发送给指定服务插入
		interfaceManageService.saveBatch(this.getUrl());*/
	}
}
