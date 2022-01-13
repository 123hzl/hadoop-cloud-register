package com.hzl.hadoop.gp.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description
 *
 * @author hzl 2021/12/24 2:32 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XlwebFriendsVO {

	//用户id
	private Long id;
	//城市
	private String location;

	//昵称
	private String name;

	//好友数量
	private Integer friends_count;
}
