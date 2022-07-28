package com.hzl.hadoop.security.config;

import com.hzl.hadoop.security.service.impl.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * description
 * 参考：https://www.jianshu.com/p/defa75b65a46
 *
 * @author hzl 2021/09/09 4:44 PM
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	//指定密码加密策略
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Resource(
			name = "dataSource"
	)
	private DataSource dataSource;

	/**
	 * 记住我功能的token存取器配置
	 *
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		return tokenRepository;
	}

	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		// 1允许任何域名使用
		corsConfiguration.addAllowedOriginPattern("*");
		// 2允许任何头
		corsConfiguration.addAllowedHeader("*");
		// 3允许任何方法（post、get等）
		corsConfiguration.addAllowedMethod("*");

		corsConfiguration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}

	@Autowired
	@Qualifier("userDetailsServiceImpl")
	UserDetailsService userDetailsService;


	//注入我们自己的AuthenticationProvider
	@Autowired
	@Qualifier("myAuthenticationProvider")
	private AuthenticationProvider provider;

	@Autowired
	private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler myAuthenticationFailHander;
	@Autowired
	private LogoutSuccessHandler customLogoutSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				//配置跨域 关闭csrf防护
				.cors().configurationSource(corsConfigurationSource()).and()
				.csrf().disable()
				.headers().frameOptions().disable()
				.and();
		http
				//登录处理
				.formLogin() //表单方式，或httpBasic
				//.loginPage("/loginPage")
				.loginProcessingUrl("/api/login/account1")
				.successHandler(myAuthenticationSuccessHandler)
				.failureHandler(myAuthenticationFailHander)
				//成功登陆后跳转页面
				//.defaultSuccessUrl("/query/pinyin?zw=%E9%BB%84%E5%BF%A0%E4%BA%AE")
				//.failureUrl("/loginError")
				.permitAll()
				.and();
		http.rememberMe()
				.rememberMeParameter("remember-me").userDetailsService(userDetailsService)
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(60);
		http.logout()   //退出登录相关配置
				.logoutUrl("/api/login/outLogin")   //自定义退出登录页面
				.logoutSuccessHandler(customLogoutSuccessHandler) //退出成功后要做的操作（如记录日志），和logoutSuccessUrl互斥
				//.logoutSuccessUrl("/index") //退出成功后跳转的页面
				.deleteCookies("JSESSIONID");    //退出时要删除的Cookies的名字

		http
				.authorizeRequests() // 授权配置
				//无需权限访问
				.antMatchers("/js/**","/img/**","/css/**", "/error404", "/register", "/druid/**","/redis/migration","/favicon.ico","/workflow/**","/upload","/gp/**","/electronic/**","/dianziImages2/**","/ics").permitAll()
				//必须经过认证以后才能访问
				.anyRequest().access("@roleOauthService.hasPermission(request,authentication)");


		//.antMatchers("/user/**").hasRole("USER")
		//其他接口需要登录后才能访问
		//.anyRequest().authenticated();

	}

	//账号密码认证
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);
	}


}
