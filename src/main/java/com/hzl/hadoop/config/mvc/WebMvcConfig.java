package com.hzl.hadoop.config.mvc;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.hzl.hadoop.constant.DataConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 * 和WebMvcConfigurerAdapter对比
 * 启动该配置需要在启动类上添加@EnableWebMvc注解
 * 参考资料：https://www.cnblogs.com/hellxz/p/8735602.html
 * https://blog.csdn.net/u010246789/article/details/52539576
 *
 * @author hzl 2020/01/03 11:25 AM
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	/**
	 * <p>
	 * 配置拦截器
	 * </p>
	 *
	 * @author hzl 2020/01/03 11:33 AM
	 */
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		//配置拦截器，并配置拦截规则
		registry.addInterceptor(new MvcHandlerInterceptor()).addPathPatterns("/api/**").excludePathPatterns("/api/public/**");
	}

	/**
	 * <p>
	 * 配置跨域路径映射
	 * https://blog.csdn.net/nlcexiyue/article/details/111273691
	 * </p>
	 *
	 * @author hzl 2020/01/03 11:34 AM
	 */
	@Override
	protected void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOriginPatterns("*")
				.allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
				.maxAge(3600)
				.allowCredentials(true);
	}

	/**
	 * <p>
	 * 配置格式化器
	 * springmvc 接受http请求会把请求参数自动绑定映射到controller的请求参数上
	 * spring没有默认配置将字符串转换为日期类型，
	 * </p>
	 * 对请求参数的格式化处理，可以在该方法中配置
	 *
	 * @author hzl 2020/01/03 11:34 AM
	 */
	@Override
	protected void addFormatters(FormatterRegistry registry) {
		super.addFormatters(registry);
		registry.addConverter(new LocaldateConvert());
		registry.addConverter(new LocaldateConvert.StringToLocaldatetimeConvert());
		registry.addConverter(new LocaldateConvert.StringToDateConvert());
		super.addFormatters(registry);
	}

	/**
	 * <p>
	 * 配置视图控制器映射
	 * </p>
	 *
	 * @author hzl 2020/01/03 12:37 PM
	 */
	@Override
	protected void addViewControllers(ViewControllerRegistry registry) {
	}

	/**
	 * <p>
	 * 自定义消息转换器,针对springmvc
	 * </p>
	 *
	 * @author hzl 2020/01/03 3:02 PM
	 */
	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		//1.需要定义一个convert转换消息的对象;
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		//2.添加fastJson的配置信息，比如：是否要格式化返回的json数据;
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
				//数值字段如果为null,输出为0,而非null
				SerializerFeature.WriteNullNumberAsZero,
				//List字段如果为null,输出为[],而非null
				SerializerFeature.WriteNullListAsEmpty,
				//字符类型字段如果为null,输出为"",而非null
				SerializerFeature.WriteNullStringAsEmpty,
				//Boolean字段如果为null,输出为falseJ,而非null
				SerializerFeature.WriteNullBooleanAsFalse,
				//消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
				SerializerFeature.DisableCircularReferenceDetect,
				//是否输出值为null的字段,默认为false。设置后为null的字段会输出
				SerializerFeature.WriteMapNullValue
				//对斜杠’/’进行转义
				//SerializerFeature.WriteSlashAsSpecial
				//将对象转为array输出
				//SerializerFeature.BeanToArray
				//SerializerFeature.WriteDateUseDateFormat
		);

		//全局指定日期格式,如果需要特殊处理，在字段上配置@JSONField(format = "yyyy-MM-dd")
		fastJsonConfig.setDateFormat(DataConstant.DATETIME);

		//3处理中文乱码问题
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		//4.在convert中添加配置信息.
		fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

		//5.将convert添加到converters当中.
		converters.add(fastJsonHttpMessageConverter);
	}

	/**
	 *
	 * 配置静态资源加载 例如css,js
	 * 不然freemarket映射的html无法加载代码中的css
	 * @author hzl 2022-06-07 3:06 PM
	 * @return
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		//为电子相册配置 http://localhost:8888/electronic/images
		registry.addResourceHandler("/css/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/dianziImages1/css/");
		registry.addResourceHandler("/img/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/dianziImages1/img/");

		super.addResourceHandlers(registry);
	}

}
