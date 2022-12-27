package com.hzl.hadoop.crawler;


import com.hzl.hadoop.constant.TycConstant;
import com.hzl.hadoop.crawler.vo.LoginInfoVO;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * description
 *
 * @author hzl 2022/12/13 5:06 PM
 */
public class LoginAuto {

	public static void main(String args[]){
		autoLoginSave();
	}


	public static void autoLoginSave() {

		//用户名，密码后面从数据库获取
		String userName = "";
		String password = "";

		LoginInfoVO LoginInfoVO = autoLogin(userName, password);

	}

	public static LoginInfoVO autoLogin(String userName, String password) {
		//需要谷歌浏览器驱动
		System.setProperty("webdriver.chrome.driver", "chromedriver");


		//用户信息返回体
		LoginInfoVO LoginInfoVO = new LoginInfoVO();

		WebDriver webDriver = new ChromeDriver();
		try {

			webDriver.manage().window().maximize();
			webDriver.manage().deleteAllCookies();
			// 与浏览器同步非常重要，必须等待浏览器加载完毕
			webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// 打开目标地址
			webDriver.get(TycConstant.TYC_URL);
			Thread.sleep(1000);

			//关闭红包弹框
			webDriver.findElement(By.xpath("//*[@id=\"J_Modal_Container\"]/div/div/div[2]/div/div[2]/div/div[1]/i")).click();

			//选择密码登陆
			webDriver.findElement(By.xpath("//*[@id=\"page-container\"]/div[1]/div/div[1]/div[2]/div/div[6]/span")).click();

			//选择非扫描方式登陆按钮
			webDriver.findElement(By.xpath("//*[@id=\"J_Modal_Container\"]/div/div/div[2]/div/div[2]/div/div/div[2]")).click();

			//选择密码登陆
			webDriver.findElement(By.xpath("//*[@id=\"J_Modal_Container\"]/div/div/div[2]/div/div[2]/div/div/div[3]/div[1]/div[2]")).click();


			webDriver.findElement(By.xpath("//*[@id=\"J_Modal_Container\"]/div/div/div[2]/div/div[2]/div/div/div[3]/div[2]/div[1]/input")).sendKeys(userName);
			webDriver.findElement(By.xpath("//*[@id=\"J_Modal_Container\"]/div/div/div[2]/div/div[2]/div/div/div[3]/div[2]/div[2]/input")).sendKeys(password);

			//我已阅读并同意
			webDriver.findElement(By.xpath("//*[@id=\"J_Modal_Container\"]/div/div/div[2]/div/div[2]/div/div/div[3]/div[3]/input")).click();

			//登陆按钮
			webDriver.findElement(By.xpath("//*[@id=\"J_Modal_Container\"]/div/div/div[2]/div/div[2]/div/div/div[3]/div[2]/button")).click();


			// 滑动,把滑块从左端移到右端
			Thread.sleep(5000);
			SlideVerifyBlock.common(webDriver);

			//获取认证信息
			Thread.sleep(5000);

			//存储了tyc-user-info
			String localStorage = ((JavascriptExecutor) webDriver).executeScript("return window.localStorage;") != null
					? ((JavascriptExecutor) webDriver).executeScript("return window.localStorage;").toString() : "";
			System.out.println("获取的localStorage数据" + localStorage);

			String cookies = ((JavascriptExecutor) webDriver).executeScript("return document.cookie;") != null
					? ((JavascriptExecutor) webDriver).executeScript("return document.cookie;").toString() : "";
			System.out.println("获取的cookie数据" + cookies);

			if (localStorage.contains("tyc-user-inf")) {
				LoginInfoVO.setCookies(cookies);
				LoginInfoVO.setLocalStorage(localStorage);
			} else {
				//没有获取到用户信息，说明登陆失败
				LoginInfoVO = null;
			}

			// 暂停五秒钟后关闭
			Thread.sleep(5000);
			webDriver.quit();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			webDriver.quit();
		}

		return LoginInfoVO;

	}

}
