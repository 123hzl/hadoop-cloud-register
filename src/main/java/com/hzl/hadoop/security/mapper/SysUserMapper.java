package com.hzl.hadoop.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.security.entity.SysUser;
import com.hzl.hadoop.security.dto.SysUserDTO;
import com.hzl.hadoop.security.vo.SysUserVO;

import java.util.List;

/**
 * 用户信息表
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-21 11:01:56
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

	List<SysUserDTO> listPage(SysUserVO params);

}
