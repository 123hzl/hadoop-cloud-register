package com.hzl.hadoop.config.redis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * description
 *
 * @author hzl 2021/11/18 4:55 PM
 */
@ToString
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.redis1")
public class RedisConnectProperties {

	/**
	 * Database index used by the connection factory.
	 */
	private int database = 0;

	/**
	 * Connection URL. Overrides host, port, and password. User is ignored. Example:
	 * redis://user:password@example.com:6379
	 */
	private String url;

	/**
	 * Redis server host.
	 */
	private String host = "localhost";

	/**
	 * Login username of the redis server.
	 */
	private String username;

	/**
	 * Login password of the redis server.
	 */
	private String password;

	/**
	 * Redis server port.
	 */
	private int port = 6379;

	/**
	 * Whether to enable SSL support.
	 */
	private boolean ssl;

	/**
	 * Read timeout.
	 */
	private Duration timeout;

	/**
	 * Connection timeout.
	 */
	private Duration connectTimeout;

	/**
	 * Client name to be set on connections with CLIENT SETNAME.
	 */
	private String clientName;

	/**
	 * Type of client to use. By default, auto-detected according to the classpath.
	 */
	private RedisProperties.ClientType clientType;

	private RedisProperties.Sentinel sentinel;

	private RedisProperties.Cluster cluster;

	private final RedisProperties.Jedis jedis = new RedisProperties.Jedis();

	private final RedisProperties.Lettuce lettuce = new RedisProperties.Lettuce();
}
