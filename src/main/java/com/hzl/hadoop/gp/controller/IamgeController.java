package com.hzl.hadoop.gp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * description
 * 电子相册接口
 *
 * @author hzl 2022/06/07 2:51 PM
 */
@Controller
@RequestMapping("/electronic")
public class IamgeController {

	/**
	 * 删除
	 */
	@GetMapping("/images/{page}")
	public String getImagesHtml(@PathVariable(name = "page") Integer page) {

		return "index" + page;
	}

}
