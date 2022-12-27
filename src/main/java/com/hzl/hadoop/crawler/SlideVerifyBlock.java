package com.hzl.hadoop.crawler;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * description
 * 滑块验证码处理
 *
 * @author hzl 2022/12/13 8:46 PM
 */
public class SlideVerifyBlock {

	// 减少计算210,
	private static final int OFFSET = 210;

	// 滑块和左边轴都有一定的距离， 误差范围内都可以接受
	private static final int LEFT_GAP = 141;

	//加速度
	private static int a_speed = 150;

	public static void common(WebDriver driver) throws Exception {

		//验证码原图裁剪
		BufferedImage fullBg =cutImage(driver, "0.png");
		WebElement slider = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[2]/div[2]/div[2]"));
		slider.click();
		//验证码凹陷裁剪，等待5秒保证没有验证失败提示的文字
		Thread.sleep(5000);
		BufferedImage bg=cutImage(driver, 1 + ".png");

		int width = bg.getWidth(), height = bg.getHeight();

		//System.out.println("宽"+width+"-"+"高"+height);
		int[] tmp1 = new int[3], tmp2 = new int[3];
		int gap = 0;
		outer:
		for (int i = OFFSET; i < width; i++) {
			for (int j = 240; j < height; j++) {
				int rgb1 = fullBg.getRGB(i, j), rgb2 = bg.getRGB(i, j);
				tmp1[0] = (rgb1 >> 16) & 0xff;
				tmp1[1] = (rgb1 >> 8) & 0xff;
				tmp1[2] = rgb1 & 0xff;
				tmp2[0] = (rgb2 >> 16) & 0xff;
				tmp2[1] = (rgb2 >> 8) & 0xff;
				tmp2[2] = rgb2 & 0xff;
				//System.out.println("原图像素"+tmp1[0]+"-"+tmp1[1]+"-"+tmp1[2]+"凹陷像素"+tmp2[0]+"-"+tmp2[1]+"-"+tmp2[2]+"位置"+i);

				//像素点差距在90以上才结束
				if (Math.abs(tmp1[0] - tmp2[0]) < 60 && Math.abs(tmp1[1] - tmp2[1]) < 60 && Math.abs(tmp1[2] - tmp2[2]) < 60) {
					continue;
				} else {

					gap = i;
					//System.out.println("差距"+gap);
					break outer;
				}
			}
		}

		//System.out.println("缺口位置:" + gap);
		moveWay2(driver, slider, gap);


	}

	public static void generateFile(byte[] data, String fileDirector, String filename) throws Exception {
		FileOutputStream stream = new FileOutputStream(fileDirector + File.separator + filename);
		stream.write(data);
		stream.flush();
		stream.close();
	}

	public static void moveWay1(WebDriver driver, WebElement slider, int gap) {
		Actions actions = new Actions(driver);
		actions.clickAndHold(slider);
		actions.perform();
		List<Double> doubles = moveManualiy(driver, gap - LEFT_GAP+OFFSET);
		Double[] array = doubles.toArray(new Double[doubles.size()]);
		double res = 0;
		for (int i = 0; i < array.length; i++) {
			// 由于经常会出现 forbidden
			int intValue = new Double(array[i]).intValue();
			res += (array[i] - intValue);
			actions.moveByOffset(intValue, (i % 2) * 1);
			actions.perform();
		}
		actions.moveByOffset(new Double(res).intValue(), 0);
		actions.pause(200 + new Random().nextInt(300)).release(slider);
		actions.perform();
	}

	// 先过去，再倒回来
	public static void moveWay2(WebDriver driver, WebElement slider, int gap) {
		Actions actions = new Actions(driver);
		actions.clickAndHold(slider);
		//目前只适合天眼查
		actions.moveByOffset(  (gap +42- LEFT_GAP)/2, 0);
		actions.perform();
		actions.moveByOffset(10, 0);
		actions.perform();
		actions.moveByOffset(-10, 0);
		actions.perform();
		actions.pause(200 + new Random().nextInt(300)).release(slider);
		actions.perform();
	}


	public static List<Double> moveManualiy(WebDriver driver, double distance) {
		DecimalFormat df = new DecimalFormat("######0.00");
		// 先加速移动 然后匀速移动  1/2*a*t0^2  + at0 * t1 =  distance
		double current = 0;
		ArrayList<Double> list = new ArrayList<>();
		int a = 0, cnt = 0;
		while (current < distance) {
			if (cnt < 10) {
				a = a_speed;
			} else {
				a = 0;
			}
			if (a == a_speed) {
				list.add(Double.valueOf(df.format(0.5 * a * Math.pow((cnt + 1) * (0.1), 2) - current)));
				current = 0.5 * a * Math.pow((cnt + 1) * (0.1), 2);
			} else {
				if (distance - current < a_speed * 0.1) {
					break;
				} else {
					current = current + a_speed * 0.1;
					list.add(a_speed * 0.1);
				}
			}
			cnt++;
		}
		list.add(distance - current);
		return list;
	}

	//截图
	public static BufferedImage cutImage(WebDriver driver, String fileName) throws IOException {
		byte[] bgBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		InputStream inputStream = new ByteArrayInputStream(bgBytes);
		return captureElement(inputStream, fileName);

	}

	//截图方法
	public static BufferedImage captureElement(InputStream screenshot, String fileName) throws IOException {

		BufferedImage img = ImageIO.read(screenshot);
		//从元素左上角坐标开始，按照元素的高宽对img进行裁剪为符合需要的图片
		BufferedImage dest = img.getSubimage(1071, 424, 707, 569);
		//可以注释掉，只是测试方便观察添加的
		//ImageIO.write(dest, "png", new File("/Users/hzl/Desktop/中南工作/" + fileName));
		return dest;
	}
}
