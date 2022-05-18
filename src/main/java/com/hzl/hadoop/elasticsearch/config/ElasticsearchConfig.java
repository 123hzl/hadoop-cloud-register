package com.hzl.hadoop.elasticsearch.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Configuration;

import java.util.Iterator;

/**
 * description
 * Elasticsearch8.0
 *
 * @author hzl 2022/02/21 11:08 AM
 */
@Slf4j
@Configuration
public class ElasticsearchConfig {


	public RestClient restLowLevelClient() {
		// Create the low-level client
		RestClientBuilder httpClientBuilder = RestClient.builder(
				new HttpHost("localhost", 9200)
		);

		//设置请求头
		Header[] defaultHeaders = new Header[]{new BasicHeader("header", "value")};
		httpClientBuilder.setDefaultHeaders(defaultHeaders);

		//设置失败处理动作
		httpClientBuilder.setFailureListener(new RestClient.FailureListener() {
			@Override
			public void onFailure(Node node) {
				log.info("Elasticsearch请求失败");
			}
		});

		//设置用户信息认证
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

		httpClientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
			@Override
			public HttpAsyncClientBuilder customizeHttpClient(
					HttpAsyncClientBuilder httpClientBuilder) {
				httpClientBuilder.disableAuthCaching();
				return httpClientBuilder
						.setDefaultCredentialsProvider(credentialsProvider);
			}
		});

		credentialsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials("user", "test-user-password"));


		//设置节点选择器，用于在设置为客户端本身的节点中筛选客户端将发送请求的节点。这很有用，例如，在启用嗅探时，可以防止向专用的主节点发送请求。默认情况下，客户端向每个配置的节点发送请求。
		httpClientBuilder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);

		//客户端以轮询方式将每个请求发送到配置的节点之一。节点可以通过节点选择器进行筛选，该选择器需要在初始化客户端时提供。
		// 这在启用嗅探功能时非常有用，以防HTTP请求不应该命中专用主节点。对于每个请求，客户端将运行最终配置的节点选择器来筛选节点候选
		// ，然后从剩余的节点中选择列表中的下一个节点。TODO 待完善优化
		httpClientBuilder.setNodeSelector(new NodeSelector() {
			@Override
			public void select(Iterable<Node> nodes) {
				/*
				 * Prefer any node that belongs to rack_one. If none is around
				 * we will go to another rack till it's time to try and revive
				 * some of the nodes that belong to rack_one.
				 */
				boolean foundOne = false;
				for (Node node : nodes) {
					String rackId = node.getAttributes().get("rack_id").get(0);
					if ("rack_one".equals(rackId)) {
						foundOne = true;
						break;
					}
				}
				if (foundOne) {
					Iterator<Node> nodesIt = nodes.iterator();
					while (nodesIt.hasNext()) {
						Node node = nodesIt.next();
						String rackId = node.getAttributes().get("rack_id").get(0);
						if ("rack_one".equals(rackId) == false) {
							nodesIt.remove();
						}
					}
				}
			}
		});


		//设置一个回调函数，允许修改默认的请求配置(例如，请求超时，认证，或者任何org.apache.http.client.config.RequestConfig.Builder允许设置的内容)
		httpClientBuilder.setRequestConfigCallback(
				new RestClientBuilder.RequestConfigCallback() {
					@Override
					public RequestConfig.Builder customizeRequestConfig(
							RequestConfig.Builder requestConfigBuilder) {
						//连接超时和套接字超时
						return requestConfigBuilder.setConnectTimeout(5000).setSocketTimeout(10000);
					}
				});

		//默认情况下，Apache Http异步客户端启动一个调度程序线程，以及连接管理器使用的工作线程数量，与本地检测到的处理器数量相同(取决于Runtime.getRuntime(). availableprocessors()返回的数量)。线程数的修改方法如下:
		httpClientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
			@Override
			public HttpAsyncClientBuilder customizeHttpClient(
					HttpAsyncClientBuilder httpClientBuilder) {
				return httpClientBuilder.setDefaultIOReactorConfig(
						IOReactorConfig.custom()
								.setIoThreadCount(5)
								.build());
			}
		});

		//设置一个允许修改http客户端配置的回调函数(例如ssl加密通信，或者任何org.apache.http.impl.nio.client.HttpAsyncClientBuilder允许设置的内容)
		httpClientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
			@Override
			public HttpAsyncClientBuilder customizeHttpClient(
					HttpAsyncClientBuilder httpClientBuilder) {
				//设置代理，将请求代理转发到设置的地址，proxy为代理地址
				return httpClientBuilder.setProxy(
						new HttpHost("111.11.11.10", 9000, "http"));
			}
		});

		// Create the low-level client

		return httpClientBuilder.build();
	}


	public ElasticsearchClient elasticsearchClient() {

		// Create the transport with a Jackson mapper
		ElasticsearchTransport transport = new RestClientTransport(
				restLowLevelClient(), new JacksonJsonpMapper());

		// And create the API client
		ElasticsearchClient client = new ElasticsearchClient(transport);
		return client;
	}
}
