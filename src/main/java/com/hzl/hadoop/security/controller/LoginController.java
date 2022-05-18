package com.hzl.hadoop.security.controller;

import com.hzl.hadoop.gp.service.GpNoticeService;
import com.hzl.hadoop.gp.service.GpService;
import com.hzl.hadoop.gp.vo.MaxMinHtmlVO;
import com.hzl.hadoop.gp.vo.VolumeVO;
import com.hzl.hadoop.security.service.MyUserDetailsService;
import com.hzl.hadoop.security.vo.SysUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * description
 * 伊利股票
 *
 * @author hzl 2020/10/31 5:20 PM
 */
@Slf4j
@Controller
public class LoginController {

	@Autowired
	MyUserDetailsService myUserDetailsService;
	/**
	 * <p>
	 * 登陆页面
	 * </p>
	 *
	 * @author hzl 2020/01/08 12:41 PM
	 */
	@GetMapping(value = "/loginPage")
	public String getMinMaxInfo() {
		return "login_page";
	}



	/**
	 * <p>
	 * 注册页面
	 * </p>
	 *
	 * @author hzl 2020/01/08 12:41 PM
	 */
	@PostMapping(value = "/register")
	public ResponseEntity<Boolean> register(@RequestBody SysUserVO sysUserVO) {

		return new ResponseEntity(myUserDetailsService.register(sysUserVO), HttpStatus.OK);
	}


	/**
	 * <p>
	 * 获取当前登陆人信息 todo
	 * </p>
	 *
	 * @author hzl 2020/01/08 12:41 PM
	 */
	@PostMapping(value = "/userinfo")
	public ResponseEntity<Boolean> getCurrentUserInfo(@RequestBody SysUserVO sysUserVO) {

		return new ResponseEntity(myUserDetailsService.register(sysUserVO), HttpStatus.OK);
	}



	/**
	 * <p>
	 * 获取token todo
	 * </p>
	 *
	 * @author hzl 2020/01/08 12:41 PM
	 */
	@PostMapping(value = "/token")
	public ResponseEntity<Boolean> getToken(@RequestBody SysUserVO sysUserVO) {

		return new ResponseEntity(myUserDetailsService.register(sysUserVO), HttpStatus.OK);
	}
}
