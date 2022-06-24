package com.hzl.hadoop.config.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * description
 *
 * @author hzl 2020/01/19 6:20 PM
 */
public class FastJson3RedisSerializer<T> extends FastJsonRedisSerializer<T> {

	static {
		ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
		ParserConfig.getGlobalInstance().addAccept("com.hzl.hadoop.app.entity");
	}

	private FastJsonConfig fastJsonConfig = new FastJsonConfig();
	private Class<T> type;

	public FastJson3RedisSerializer(Class<T> type) {
		super(type);
		this.type = type;
	}

	@Override
	public FastJsonConfig getFastJsonConfig() {
		return this.fastJsonConfig;
	}

	@Override
	public void setFastJsonConfig(FastJsonConfig fastJsonConfig) {
		this.fastJsonConfig = fastJsonConfig;
	}

	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		} else {
			try {
				return JSON.toJSONBytes(this.fastJsonConfig.getCharset(), t, this.fastJsonConfig.getSerializeConfig(), this.fastJsonConfig.getSerializeFilters(), this.fastJsonConfig.getDateFormat(), JSON.DEFAULT_GENERATE_FEATURE, this.fastJsonConfig.getSerializerFeatures());
			} catch (Exception var3) {
				throw new SerializationException("Could not serialize: " + var3.getMessage(), var3);
			}
		}
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes != null && bytes.length != 0) {
			try {
				T s = (T) JSON.parseObject(bytes, this.fastJsonConfig.getCharset(), this.type, this.fastJsonConfig.getParserConfig(), this.fastJsonConfig.getParseProcess(), JSON.DEFAULT_PARSER_FEATURE, this.fastJsonConfig.getFeatures());
				System.out.println("测试是否转换成功" + s.toString());
				return s;
			} catch (Exception var3) {
				throw new SerializationException("Could not deserialize: " + var3.getMessage(), var3);
			}
		} else {
			return null;
		}
	}


}
