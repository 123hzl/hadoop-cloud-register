package com.hzl.hadoop.gp.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzl.hadoop.gp.constant.GpCodeEnum;
import com.hzl.hadoop.gp.convert.NewsConvert;
import com.hzl.hadoop.gp.entity.GpInfoEntity;
import com.hzl.hadoop.gp.entity.GpNewsEntity;
import com.hzl.hadoop.gp.service.GpNewsService;
import com.hzl.hadoop.gp.service.XinLangNews;
import com.hzl.hadoop.gp.vo.XlNewsVO;
import com.hzl.hadoop.util.LocalDateFormate;
import com.hzl.hadoop.util.email.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.hzl.hadoop.gp.constant.GpUrlConstant.SINGLE_STOCK_NEW;

/**
 * description
 *
 * @author hzl 2021/12/14 2:32 PM
 */
@Slf4j
@Service
public class XinLangNewsImpl implements XinLangNews {

	@Autowired
	private GpNewsService gpNewsService;

	@Autowired
	private MailService mailService;

	@Override
	public boolean getTodayNews(GpInfoEntity gpInfoEntity) throws IOException {

		NewsConvert xinLangNews = new NewsConvert();
		List<XlNewsVO> xlNewsVOS= xinLangNews.getXlNews(gpInfoEntity.getGpCode());

		//过滤今天的新闻
		List<GpNewsEntity> entities=xlNewsVOS.stream()
				.filter(xlNewsVO -> xlNewsVO.getReleaseTime().isAfter(LocalDateFormate.getTodayInitTime()))
				.map(x-> GpNewsEntity.builder()
						.title(x.getTitle())
						.releaseTime(x.getReleaseTime())
						.url(x.getUrl())
						.source(x.getSource())
						.gpCode(gpInfoEntity.getGpCode())
						.build()).collect(Collectors.toList());

		entities.forEach(a->{
			Wrapper<GpNewsEntity> queryWrapper=new QueryWrapper<>(a);
			if(gpNewsService.getOne(queryWrapper,false)==null){
				//去重保存 TODO 优化查询将url生成短链接
				gpNewsService.save(a);
				//发送邮件消息
				mailService.sendSimpleMail(gpInfoEntity.getGpName()+"股票："+"有新动态发布", a.getReleaseTime()+"\n"+a.getTitle()+"\n"+a.getUrl()+"\n");
			}

		});
		return true;
	}





}
