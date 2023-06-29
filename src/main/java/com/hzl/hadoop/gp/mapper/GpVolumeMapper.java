package com.hzl.hadoop.gp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.Page;
import com.hzl.hadoop.config.mybatis.BaseMapperUtil;
import com.hzl.hadoop.gp.vo.VolumeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * description
 *
 * @author hzl 2020/11/06 4:16 PM
 */
@Mapper
public interface GpVolumeMapper extends BaseMapperUtil<VolumeVO> {

	List<VolumeVO> queryVolume(VolumeVO volumeVO);

	List<VolumeVO> queryVolumeYl(VolumeVO volumeVO);

	Page<VolumeVO> queryVolumeYlPage(VolumeVO volumeVO);

	List<VolumeVO> queryVolumeByDate(VolumeVO volumeVO);

	List<VolumeVO> queryVolumeByDateYl(VolumeVO volumeVO);

	List<VolumeVO> queryHightToLow(VolumeVO volumeVO);

	List<VolumeVO> queryLowToHight(VolumeVO volumeVO);



}
