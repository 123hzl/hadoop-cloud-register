# 基于官方的OpenJDK 8镜像作为基础镜像
FROM java:8

# 设置Kafka版本
ARG KAFKA_VERSION=3.2.0

# 设置Kafka安装路径
ARG KAFKA_HOME=/opt/kafka

# 下载并安装Kafka
RUN wget -q https://archive.apache.org/dist/kafka/${KAFKA_VERSION}/kafka_2.13-${KAFKA_VERSION}.tgz && \
    tar -xzf kafka_2.13-${KAFKA_VERSION}.tgz -C /opt && \
    rm kafka_2.13-${KAFKA_VERSION}.tgz

# 设置Kafka环境变量
ENV PATH=${KAFKA_HOME}/bin:$PATH

# 设置Kafka工作目录
WORKDIR ${KAFKA_HOME}

# 暴露Kafka监听端口
EXPOSE 9092

# 启动Kafka
CMD ["kafka-server-start.sh", "config/server.properties"]

#docker build --pull -t registry.cn-hangzhou.aliyuncs.com/hzl-hadoop/hzl:kafka3.2.0 .
#docker push registry.cn-hangzhou.aliyuncs.com/hzl-hadoop/hzl:kafka3.2.0