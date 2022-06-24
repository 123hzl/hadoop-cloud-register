package com.hzl.hadoop.userlog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.userlog.dto.RequestLogsDTO;
import com.hzl.hadoop.userlog.entity.RequestLogsEntity;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.userlog.vo.RequestLogsVO;


import java.util.Map;

/**
 * 请求日志
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-11-19 16:18:12
 */
public interface RequestLogsService extends IService<RequestLogsEntity> {

	PageInfo<RequestLogsDTO> queryPage(RequestLogsVO params, Integer current, Integer pageSize);
}

