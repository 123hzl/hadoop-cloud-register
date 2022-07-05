#资料https://www.jianshu.com/p/168fbb97b447
#基于java8镜像
FROM java:8

#复制本地jar到容器的根目录，并重命名为app.jar
ADD hadoop.jar /register.jar
RUN echo 'Asia/Shanghai' >/etc/timezone
#修改jar创建时间
RUN bash -c 'touch /register.jar'
#EXPOSE 8888
#容器启动时第一个运行的命令
ENTRYPOINT exec java -jar -Xms128m -Xmx300m -Dspring.profiles.active=local /register.jar
#运行 docker build --pull -t registry.cn-hangzhou.aliyuncs.com/hzl-hadoop/hzl:register-0.0.02 .
#docker login -u 17602153276 -p 1qaz2wsx registry.cn-hangzhou.aliyuncs.com
#docker push registry.cn-hangzhou.aliyuncs.com/hzl-hadoop/hzl:register-0.0.02
#docker pull registry.cn-hangzhou.aliyuncs.com/hzl-hadoop/hzl:register-0.0.02
#docker run -d -p 8888:8888 registry.cn-hangzhou.aliyuncs.com/hzl-hadoop/hzl:register-0.0.02