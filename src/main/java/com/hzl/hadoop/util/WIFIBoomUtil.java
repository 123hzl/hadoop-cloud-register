package com.hzl.hadoop.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description
 * wifi破解，适用mac
 * networksetup -setairportnetwork en0 11 i23434
 *
 * @author hzl 2022/01/24 11:32 AM
 */
@Slf4j
public class WIFIBoomUtil {


	public static File logFile = new File("/Users/hzl/Desktop/run.log");
	public static String wifiName = "2323-WLAN";
	public static String algorithm = null;

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// 最大密码数量
	public static int maxPass = 99999999;

	// 初始密码数量
	public static int startPass = 0;


	public static void main(String[] args) throws IOException, InterruptedException {


		String password = "13434";

		String cmd = String.format("networksetup -setairportnetwork en0 %s %s", wifiName, password);
		List<String> exec = execute(cmd);


		if (exec.contains(String.format("Could not find network %s.", wifiName))) {
			//continue;
		}
		if (exec.size() == 0 && ping(cmd)) {
			log.info(sdf.format(new Date()) + "," + "连接成功 wifi名称:" + wifiName + ",密码:" + password + System.getProperty("line.separator"));

		}


	}

	/**
	 * ping 校验
	 */
	private static boolean ping(String wifiCmd) throws IOException, InterruptedException {
		/**
		 * 应连接wifi需要一定的时间所以睡眠3秒
		 */
		Thread.sleep(1000 * 3);
		boolean pinged = false;
		String cmd = "ping www.baidu.com";
		Process process = Runtime.getRuntime().exec(cmd);
		BufferedReader bReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
		String s = bReader.readLine();
		if (s != null && s.length() > 0) {
			pinged = true;
		}
		process.getInputStream().close();
		bReader.close();

		return pinged;
	}


	/**
	 * 执行cmd 命令
	 *
	 * @param cmd
	 * @return
	 * @throws IOException
	 */
	public static List<String> execute(String cmd) throws IOException {
		Process process = null;
		BufferedReader bReader = null;
		List<String> result = new ArrayList<String>();
		try {
			process = Runtime.getRuntime().exec(cmd);
			bReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
			String line = null;
			while ((line = bReader.readLine()) != null) {
				result.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			process.getInputStream().close();
			bReader.close();

		}
		return result;
	}


	/**
	 * 获得密码
	 *
	 * @param passStr
	 * @return
	 */
	public static String getPass(Integer passStr) {
		String str = String.format("%08d", passStr);
		return str;
	}


}
