package com.hzl.hadoop.gp.repository.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.exception.CommonException;
import com.hzl.hadoop.gp.constant.GpUrlConstant;
import com.hzl.hadoop.gp.mapper.GpVolumeMapper;
import com.hzl.hadoop.gp.mapper.GpYlMapper;
import com.hzl.hadoop.gp.mapper.GpZxMapper;
import com.hzl.hadoop.gp.repository.GpRepository;
import com.hzl.hadoop.gp.vo.GpVO;
import com.hzl.hadoop.gp.vo.VolumeVO;
import com.hzl.hadoop.gp.vo.YlVO;
import com.hzl.hadoop.gp.vo.ZXVO;
import com.hzl.hadoop.util.JsonUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author hzl 2020/10/31 5:17 PM
 */
@Component
public class GpRepositoryImpl implements GpRepository  {

	GpYlMapper gpYlMapper;
	GpZxMapper gpZxMapper;
	GpVolumeMapper gpVolumeMapper;

	public GpRepositoryImpl(GpYlMapper gpYlMapper, GpZxMapper gpZxMapper, GpVolumeMapper gpVolumeMapper) {
		this.gpYlMapper = gpYlMapper;
		this.gpZxMapper = gpZxMapper;
		this.gpVolumeMapper = gpVolumeMapper;
	}

	@Override
	public int insert(YlVO ylVO) {
		int i = gpYlMapper.insert(ylVO);
		if (i <= 0) {
			throw new CommonException("插入伊利股票信息失败" + ylVO.toString());
		}
		return i;
	}

	@Override
	public int insert(ZXVO zxvo) {
		int i = gpZxMapper.insert(zxvo);
		if (i <= 0) {
			throw new CommonException("插入伊利股票信息失败" + zxvo.toString());
		}
		return i;
	}

	@Override
	public List<GpVO> selectMaxPriceVolatility(String gpCode,LocalDate date) {
		List<GpVO> gpVOS = new ArrayList<>();
		if (GpUrlConstant.GP_CODE_YL.equals(gpCode)) {
			gpVOS = JsonUtils.cloneObjectList(gpYlMapper.selectMaxPriceVolatility(date), GpVO.class);
		} else if (GpUrlConstant.GP_CODE_ZX.equals(gpCode)) {
			gpVOS = JsonUtils.cloneObjectList(gpZxMapper.selectMaxPriceVolatility(gpCode,date), GpVO.class);
		} else {
			gpVOS = JsonUtils.cloneObjectList(gpZxMapper.selectMaxPriceVolatility(gpCode,date), GpVO.class);
		}

		return gpVOS;
	}

	@Override
	public List<GpVO> selectMinPriceVolatility(String gpCode,LocalDate date) {
		List<GpVO> gpVOS;
		if (GpUrlConstant.GP_CODE_YL.equals(gpCode)) {
			gpVOS = JsonUtils.cloneObjectList((gpYlMapper.selectMinPriceVolatility(date)), GpVO.class);
		} else if (GpUrlConstant.GP_CODE_ZX.equals(gpCode)) {
			gpVOS = JsonUtils.cloneObjectList((gpZxMapper.selectMinPriceVolatility(gpCode,date)), GpVO.class);

		} else {
			gpVOS = JsonUtils.cloneObjectList((gpZxMapper.selectMinPriceVolatility(gpCode,date)), GpVO.class);
		}
		return gpVOS;
	}

	@Override
	public List<GpVO> selectCurrentPriceAll(String gpCode) {
		List<GpVO> gpVOS = new ArrayList<>();

		if (GpUrlConstant.GP_CODE_YL.equals(gpCode)) {
			gpVOS = gpYlMapper.selectCurrentPriceAll();
		} else if (GpUrlConstant.GP_CODE_ZX.equals(gpCode)) {
			gpVOS = gpZxMapper.selectCurrentPriceAll();

		}
		return gpVOS;
	}

	@Override
	public List<VolumeVO> queryVolume(VolumeVO volumeVO) {
		if (GpUrlConstant.GP_CODE_YL.equals(volumeVO.getGpCode())) {
			return gpVolumeMapper.queryVolumeYl(volumeVO);
		}
		return gpVolumeMapper.queryVolume(volumeVO);
	}

	@Override
	public List<VolumeVO> queryVolumeByDate(VolumeVO volumeVO) {
		if (GpUrlConstant.GP_CODE_YL.equals(volumeVO.getGpCode())) {
			return gpVolumeMapper.queryVolumeByDateYl(volumeVO);
		}
		return gpVolumeMapper.queryVolumeByDate(volumeVO);
	}

	@Override
	public PageInfo<VolumeVO> queryVolumePage(VolumeVO volumeVO) {
		PageInfo<VolumeVO> pageResult = PageHelper.startPage(1, 10).doSelectPageInfo(() -> gpVolumeMapper.select(volumeVO));

		return pageResult;
	}

	@Override
	public GpVO selectEndPriceByDate(String gpCode, LocalDate localDate) {
		if (GpUrlConstant.GP_CODE_YL.equals(gpCode)) {
			return gpYlMapper.selectEndPriceByDate(gpCode,localDate);
		}
		return gpZxMapper.selectEndPriceByDate(gpCode,localDate);
	}


}
