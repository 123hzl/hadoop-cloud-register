package com.hzl.hadoop.file.controller;

import com.hzl.hadoop.util.ExcelParseToMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * description
 * 文件下载
 *
 * @author hzl 2020/01/03 12:36 PM
 */
@Controller
@Slf4j
public class FileDownloadController {
	/**
	 * <p>
	 * 通用文件下载，有点问题暂时无法解决
	 * </p>
	 *
	 * @author hzl 2020/01/05 2:36 PM
	 */
	@GetMapping(value = "/download")
	public ResponseEntity<InputStreamResource> downFile(@RequestParam String fileName) throws IOException {
		log.info("文件名称" + fileName);
		String filePath = "/Users/hzl/Desktop/" + fileName;
		FileSystemResource file = new FileSystemResource(filePath);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
		headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(file.getInputStream()));
	}

	/**
	 * <p>
	 * 通用文件下载，可以下载任何格式的文件
	 * </p>
	 *
	 * @author hzl 2020/01/05 2:36 PM
	 */
	@GetMapping(value = "/download/file")
	public void downFileResponse(@RequestParam String fileName, HttpServletResponse response) {
		try {
			log.info("文件名称" + fileName);
			String filePath = "/Users/hzl/Desktop/" + fileName;
			FileSystemResource file = new FileSystemResource(filePath);
			try (InputStream inputStream = file.getInputStream()) {
				response.setContentType("application/x-msdownload;charset=utf-8");
				response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
				try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream())) {
					IOUtils.copy(inputStream, bufferedOutputStream);
				}
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}


	}


	/**
	 * <p>
	 * 通用文件上传
	 * </p>
	 *
	 * @author hzl 2020/01/06 11:00 AM
	 */

	@PostMapping(value = "/upload")
	public ResponseEntity<Map<String, List<Map<String, String>>>> uploadFile(MultipartFile file) {
		Map<String, List<Map<String, String>>> result = null;
		try {
			result = ExcelParseToMapUtil.parseExcelFile(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(result);
	}
}
