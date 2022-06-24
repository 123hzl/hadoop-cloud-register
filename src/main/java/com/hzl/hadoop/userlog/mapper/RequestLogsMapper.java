package com.hzl.hadoop.userlog.mapper;

import com.hzl.hadoop.userlog.dto.RequestLogsDTO;
import com.hzl.hadoop.userlog.vo.RequestLogsVO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.userlog.entity.RequestLogsEntity;

import java.util.List;


/**
 * 请求日志
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-11-19 16:18:12
 */
@Mapper
public interface RequestLogsMapper extends BaseMapper<RequestLogsEntity> {
	List<RequestLogsDTO> listPage(RequestLogsVO params);
}
