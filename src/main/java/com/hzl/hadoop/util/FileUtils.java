package com.hzl.hadoop.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * description
 *
 * @author hzl 2023/06/09 1:37 PM
 */
public class FileUtils {

	public static List<File> getAllFile(File dirFile) {
		// 如果文件夹不存在或着不是文件夹，则返回 null
		if (Objects.isNull(dirFile) || !dirFile.exists() || dirFile.isFile()){
			return null;
		}

		File[] childrenFiles = dirFile.listFiles();
		if (Objects.isNull(childrenFiles) || childrenFiles.length == 0){
			return null;
		}


		List<File> files = new ArrayList<>();
		for (File childFile : childrenFiles) {
			// 如果是文件，直接添加到结果集合
			if (childFile.isFile()) {
				files.add(childFile);
			}
			//以下几行代码取消注释后可以将所有子文件夹里的文件也获取到列表里
//            else {
//                // 如果是文件夹，则将其内部文件添加进结果集合
//                List<File> cFiles = getAllFile(childFile);
//                if (Objects.isNull(cFiles) || cFiles.isEmpty()) continue;
//                files.addAll(cFiles);
//            }
		}
		return files;
	}



}
