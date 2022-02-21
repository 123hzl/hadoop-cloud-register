package com.hzl.hadoop.gp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.gp.convert.BaiduImageConvert;
import com.hzl.hadoop.gp.entity.BaiduImagesEntity;
import com.hzl.hadoop.gp.mapper.BaiduImagesMapper;
import com.hzl.hadoop.gp.service.BaiduImagesService;
import com.hzl.hadoop.gp.vo.BaiduImageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.hzl.hadoop.gp.convert.BaiduImageConvert.parseImage;

@Slf4j
@Service("baiduImagesService")
public class BaiduImagesServiceImpl extends ServiceImpl<BaiduImagesMapper, BaiduImagesEntity> implements BaiduImagesService {

	@Autowired
	BaiduImagesMapper mapper;

	@Override
	public PageInfo queryPage(BaiduImagesEntity params, int start, int pageSize) {
		QueryWrapper<BaiduImagesEntity> queryWrapper = new QueryWrapper(params);

		PageInfo<BaiduImagesEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

		return pageResult;
	}


	/**
	 *
	 *
	 * @param searchName 搜索条件
	 * @param size 下载大小，需要乘以30
	 * @author hzl 2022-01-18 9:13 AM
	 * @return
	 */
	@Override
	public Boolean getImage(String searchName,int size) {
		int pn = 30;
		int rn = 30;

		String httpResult = BaiduImageConvert.httpGet(BaiduImageConvert.getParam(String.valueOf(pn), String.valueOf(rn), "", searchName), BaiduImageConvert.URL);
		BaiduImageVO baiduImageVO = parseImage(httpResult);

		//只获取3000张图片
		for (int i = 0; i <= size; i++) {
			String gsm = baiduImageVO.getGsm();
			if (baiduImageVO != null && StringUtils.hasLength(gsm)) {
				//插入数据库todo

				List<BaiduImagesEntity> entityList=baiduImageVO.getImages().stream()
						.map(a-> BaiduImagesEntity.builder().searchName(searchName).originalImageUrl(a).build()).filter(b->StringUtils.hasLength(b.getOriginalImageUrl()))
						.collect(Collectors.toList());
				log.info("测试{}",entityList.toString());

				saveBatch(entityList);
				//插入数据库结束
				pn = pn + rn;
				httpResult = BaiduImageConvert.httpGet(BaiduImageConvert.getParam(String.valueOf(pn), String.valueOf(rn), baiduImageVO.getGsm(), searchName), BaiduImageConvert.URL);
				baiduImageVO = parseImage(httpResult);

			} else {
				break;
			}
		}
		return true;
	}




}