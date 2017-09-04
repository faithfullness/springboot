package cn.joojee.wxqh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class SpringRedisConfig {

	@Value("${redis.host}")
	private String host;

	@Value("${redis.port}")
	private Integer port;

	@Value("${redis.password}")
	private String password;

	@Value("${redis.timeout}")
	private Integer timeout;

	@Value("${redis.maxIdle}")
	private Integer maxIdle;

	@Value("${redis.minIdel}")
	private Integer minIdel;

	@Value("${redis.maxtotal}")
	private Integer maxTotal;

	@Value("${redis.maxwaitmillis}")
	private Long maxWaitMillis;

	@Value("${redis.maxWait}")
	private Long maxWait;


	@Bean
	public JedisPoolConfig getJedisPoolConfig() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		// 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		poolConfig.setBlockWhenExhausted(true);
		// 是否启用pool的jmx管理功能, 默认true
		poolConfig.setJmxEnabled(true);
		// 最大空闲连接数, 默认8个 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
		poolConfig.setMaxIdle(this.maxIdle);
		// 最小空闲数
		poolConfig.setMinIdle(this.minIdel);
		// 最大连接数, 默认8个
		poolConfig.setMaxTotal(this.maxTotal);
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		poolConfig.setMaxWaitMillis(this.maxWaitMillis);
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		poolConfig.setTestOnBorrow(true);
		return poolConfig;
	}

	@Bean(name = "jedisPool")
	public JedisPool getJedisPool(JedisPoolConfig jedisPoolConfig) {
		return new JedisPool(jedisPoolConfig,this.host,this.port,this.timeout,this.password);
	}
}
