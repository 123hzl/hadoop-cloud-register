package com.hzl.hadoop.mq.rocketmq.provider.impl;

import com.hzl.hadoop.mq.rocketmq.channel.OutputChannel;
import com.hzl.hadoop.mq.rocketmq.provider.ProviderService;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.lang.ref.SoftReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * description
 * 提供者
 *
 * @author hzl 2020/03/25 4:32 PM
 */
@Service
public class ProviderServiceImpl implements ProviderService {

	@Autowired
	private OutputChannel outputChannel;



	@Override
	public void send(String msg) throws Exception {
		outputChannel.output1().send(MessageBuilder.withPayload(msg).build());
	}

	@Override
	public <T> void sendWithTags(T msg, String tag) throws Exception {
		Message message = MessageBuilder.createMessage(msg,
				new MessageHeaders(Stream.of(tag).collect(Collectors
						.toMap(str -> MessageConst.PROPERTY_TAGS, String::toString))));
		outputChannel.output1().send(message);
	}

	@Override
	public <T> void sendObject(T msg, String tag) throws Exception {
		Message message = MessageBuilder.withPayload(msg)
				.setHeader(MessageConst.PROPERTY_TAGS, tag)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
				.build();
		outputChannel.output1().send(message);
	}

	@Override
	public <T> void sendTransactionalMsg(T msg, int num) throws Exception {
		MessageBuilder builder = MessageBuilder.withPayload(msg)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
		builder.setHeader("test", String.valueOf(num));
		builder.setHeader(RocketMQHeaders.TAGS, "binder");
		Message message = builder.build();
		//outputChannel.output2().send(message);
		SoftReference sf = new SoftReference(builder);
	}
}
