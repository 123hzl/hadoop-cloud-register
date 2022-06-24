package com.hzl.hadoop.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.security.entity.SysRoleEntity;
import com.hzl.hadoop.security.dto.SysRoleDTO;
import com.hzl.hadoop.security.vo.SysRoleVO;
import java.util.List;
/**
 * 用户角色表
 * 
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-21 14:58:40
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {

	List<SysRoleDTO> listPage(SysRoleVO params);
	
}
