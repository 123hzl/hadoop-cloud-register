package com.hzl.hadoop.interfacemanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.interfacemanager.dto.InterfaceManageDTO;
import com.hzl.hadoop.interfacemanager.entity.InterfaceManageEntity;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.interfacemanager.vo.InterfaceManageVO;


import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-11-16 14:05:48
 */
public interface InterfaceManageService extends IService<InterfaceManageEntity> {

	PageInfo<InterfaceManageDTO> queryPage(InterfaceManageVO params, int start, int pageSize);

	List<InterfaceManageEntity> selectUrls(InterfaceManageVO interfaceManageVO);
}

