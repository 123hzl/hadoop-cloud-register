package com.hzl.hadoop.gp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.gp.convert.CpConvert;
import com.hzl.hadoop.gp.entity.CpInfoEntity;
import com.hzl.hadoop.gp.mapper.CpInfoMapper;
import com.hzl.hadoop.gp.service.CpInfoService;
import com.hzl.hadoop.gp.vo.CpVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("cpInfoService")
public class CpInfoServiceImpl extends ServiceImpl<CpInfoMapper, CpInfoEntity> implements CpInfoService {

	@Autowired
	CpInfoMapper mapper;

	@Override
	public PageInfo queryPage(CpInfoEntity params, int start, int pageSize) {
		QueryWrapper<CpInfoEntity> queryWrapper = new QueryWrapper(params);

		PageInfo<CpInfoEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

		return pageResult;
	}

	@Override
	public Boolean init() {
		for(int i=1;;i++){
			String data = CpConvert.httpGet(CpConvert.DLT_TUL, i);
			String jsonArray = JSONObject.parseObject(data).getJSONObject("value").getString("list");
			List<CpVO> cpVOS = JSONArray.parseArray(jsonArray, CpVO.class);
			if(CollectionUtils.isEmpty(cpVOS)){
				break;
			}
			Boolean isEnd=false;
			for(CpVO a:cpVOS){
				log.info("结果{}", a.convert());
				CpInfoEntity cpInfoEntity=a.convert();
				QueryWrapper queryWrapper = new QueryWrapper();
				queryWrapper.eq("lottery_draw_num",cpInfoEntity.getLotteryDrawNum());
				CpInfoEntity cpInfoEntityisExist=mapper.selectOne(queryWrapper);
				if(cpInfoEntityisExist==null){
					mapper.insert(cpInfoEntity);
				}else{
					isEnd=true;
					break;

				}
			}

			if(isEnd){
				break;
			}

		}

		return true;
	}

}