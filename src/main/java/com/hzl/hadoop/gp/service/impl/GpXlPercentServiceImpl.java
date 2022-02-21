package com.hzl.hadoop.gp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.gp.convert.GpPercentConvert;
import com.hzl.hadoop.gp.entity.GpXlPercentEntity;
import com.hzl.hadoop.gp.mapper.GpXlPercentMapper;
import com.hzl.hadoop.gp.service.GpXlPercentService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("gpXlPercentService")
public class GpXlPercentServiceImpl extends ServiceImpl<GpXlPercentMapper, GpXlPercentEntity> implements GpXlPercentService {

	@Autowired
	GpXlPercentMapper mapper;

	@Override
	public PageInfo queryPage(GpXlPercentEntity params, int start, int pageSize) {
		QueryWrapper<GpXlPercentEntity> queryWrapper = new QueryWrapper(params);

		PageInfo<GpXlPercentEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

		return pageResult;
	}

	@Override
	public Boolean init(String gpcode) {

//
//		QueryWrapper queryWrapper = new QueryWrapper();
//		queryWrapper.eq("symbol", gpcode);
//		remove(queryWrapper);

		for (int i = 1; ; i++) {
			List<GpXlPercentEntity> gpXlPercentEntities = GpPercentConvert.parse(gpcode, String.valueOf(i));
			if (CollectionUtils.isEmpty(gpXlPercentEntities)) {
				break;
			} else {
				int size = gpXlPercentEntities.size();
				Boolean isEnd=false;

				QueryWrapper queryWrapper1 = new QueryWrapper(gpXlPercentEntities.get(size - 1));
				GpXlPercentEntity gpXlPercentEntity = getOne(queryWrapper1, false);
				if (gpXlPercentEntity == null) {
					//批量插入
					saveBatch(gpXlPercentEntities);
				} else {
					//不存在插入，存在直接break
					for (GpXlPercentEntity a : gpXlPercentEntities) {

						QueryWrapper queryWrapper2 = new QueryWrapper(a);
						GpXlPercentEntity gpXlPercentEntity2 = getOne(queryWrapper2, false);

						if (gpXlPercentEntity2 != null) {
							isEnd=true;
							break;
						} else {
							save(a);
						}

					}

					if(isEnd){
						break;
					}

				}

			}

		}

		return true;
	}

	@Override
	public Boolean remove(String gpcode) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("symbol", gpcode);
		return remove(queryWrapper);
	}


}