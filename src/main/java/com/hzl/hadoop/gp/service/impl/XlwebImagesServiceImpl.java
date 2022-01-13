package com.hzl.hadoop.gp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.gp.entity.XlwebImagesEntity;
import com.hzl.hadoop.gp.mapper.XlwebImagesMapper;
import com.hzl.hadoop.gp.service.XlwebImagesService;
import com.hzl.hadoop.gp.vo.XlwebImagesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hzl.hadoop.gp.convert.ImageConvert.getImageInfosAll;


@Service("xlwebImagesService")
public class XlwebImagesServiceImpl extends ServiceImpl<XlwebImagesMapper, XlwebImagesEntity> implements XlwebImagesService {

	@Autowired
	XlwebImagesMapper mapper;

	@Override
	public PageInfo queryPage(XlwebImagesEntity params, int start, int pageSize) {
		QueryWrapper<XlwebImagesEntity> queryWrapper = new QueryWrapper(params);

		PageInfo<XlwebImagesEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

		return pageResult;
	}

	@Override
	public Boolean getAllImagePage(Long uid) {
		List<XlwebImagesVO> list = getImageInfosAll(String.valueOf(uid));
		Map<String, Object> map = new HashMap<>(1);
		map.put("uid", uid);
		mapper.deleteByMap(map);
		//新增一个文件下载表，所有下载的文件都在该表记录
		list.forEach(a -> {
			saveBatch(a.convert());
		});
		return null;
	}

}