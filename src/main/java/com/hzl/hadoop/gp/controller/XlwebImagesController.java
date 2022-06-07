package com.hzl.hadoop.gp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.gp.entity.XlwebImagesEntity;
import com.hzl.hadoop.gp.service.XlwebImagesService;
import com.hzl.hadoop.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


/**
 * 新浪微博爬虫-用户图片库
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-12-23 13:12:30
 */
@Slf4j
@RestController
@RequestMapping("gp/xlwebimages")
public class XlwebImagesController {
	@Autowired
	private XlwebImagesService xlwebImagesService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public ResponseEntity<PageInfo<XlwebImagesEntity>> list(XlwebImagesEntity params, @RequestParam int start, @RequestParam int pageSize) {
		PageInfo<XlwebImagesEntity> page = xlwebImagesService.queryPage(params, start, pageSize);

		return new ResponseEntity(page, HttpStatus.OK);
	}


	/**
	 * 信息
	 */
	@GetMapping("/info/{id}")
	public ResponseEntity<XlwebImagesEntity> info(@PathVariable("id") Long id) {
		XlwebImagesEntity xlwebImages = xlwebImagesService.getById(id);

		return new ResponseEntity(xlwebImages, HttpStatus.OK);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public ResponseEntity save(@RequestBody XlwebImagesEntity xlwebImages) {
		xlwebImagesService.save(xlwebImages);

		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 修改
	 */
	@PutMapping("/update")
	public ResponseEntity update(@RequestBody XlwebImagesEntity xlwebImages) {
		xlwebImagesService.updateById(xlwebImages);

		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/delete")
	public ResponseEntity delete(@RequestBody Long[] ids) {
		xlwebImagesService.removeByIds(Arrays.asList(ids));

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}


	/**
	 * <p>
	 * 通用文件下载，可以下载任何格式的文件
	 * </p>
	 *
	 * @author hzl 2020/01/05 2:36 PM
	 */
	@GetMapping(value = "/download/wximage", name = "图片下载")
	public void downFileResponse(@RequestParam(value = "uid", required = false) String uid, @RequestParam(value = "fileName", required = false) String fileName) {

		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("uid", uid);
		queryWrapper.gt("create_time", "2022-01-17 00:00:00");
		List<XlwebImagesEntity> allImages = xlwebImagesService.list(queryWrapper);

		String path="/Users/hzl/Desktop/xlimage/"+fileName+uid+"/";

		//1.如果文件不存在，则创建
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		allImages.forEach(a->{
			try {
				log.info("文件名称{}", fileName);
				//下载文件
				try (InputStream inputStream = HttpUtils.download(a.getOriginalImageUrl())) {
					//文件保存目录
					File file = new File(path+ a.getPid() + ".jpg");
					try (FileOutputStream fileOutputStream = new FileOutputStream(file);) {
						IOUtils.copy(inputStream, fileOutputStream);

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	/**
	 * 删除
	 */
	@GetMapping("/all")
	public ResponseEntity<Boolean> getAllImagePage(@RequestParam Long uid){

		return new ResponseEntity(xlwebImagesService.getAllImagePage(uid),HttpStatus.OK);
	}




}
