package com.hzl.hadoop.mq.rabbitmq.banding;

import com.hzl.hadoop.mq.rabbitmq.constant.DirectConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * description
 * 声明绑定规则，队列和交换机通过路由键进行绑定
 *
 * @author hzl 2021/11/09 2:10 PM
 */
@Component
public class BindingInit {

	@Autowired
	Queue directHouseQueue;

	@Autowired
	DirectExchange directHouseExchange;


	@Bean
	public Binding topicProvinceBinding() {
		return BindingBuilder.bind(directHouseQueue).to(directHouseExchange).with(DirectConstant.HOUSE_ROUTINT_KEY);
	}
}
