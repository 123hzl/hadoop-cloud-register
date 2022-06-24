package com.hzl.hadoop.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.security.entity.SysUserRoleEntity;
import com.hzl.hadoop.security.dto.SysUserRoleDTO;
import com.hzl.hadoop.security.vo.SysUserRoleVO;
import java.util.List;
/**
 * 用户角色关系表
 * 
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-21 14:58:40
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleEntity> {

	List<SysUserRoleDTO> listPage(SysUserRoleVO params);
	
}
