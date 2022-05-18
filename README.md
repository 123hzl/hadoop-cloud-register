#计划安排，集成spring cloud aliababa ，hadoop，前端vue
登陆记录，异常ip检测。
集成 email（完成，后期集成数据库）

日志配置logback实现了slf4j，指定日志目录，分片，日志查询脚本grep.sh（完成)

集成mvc完成，WebMvcConfigurationSupport完成，json处理完成，日期格式处理完成

mvc拦截器，记录所有外部请求，拦截功能已经实现，记录所有请求功能待开发
com.hzl.hadoop.config.mvc.MvcHandlerInterceptor
主动调用其他系统的接口，需有手动记录到请求记录表


集成webflux 待开发


集成mybatis，druid数据库连接池(是否可以动态修改连接数)，多数据源(读写分离,主从数据库同步的延迟),

    分页:https://github.com/pagehelper/Mybatis-PageHelper(完成)
    集成jpa（完成，集成了mybatis工具包后基本不会用）
    tk:完成
    mybatis:完成
    druid:完成
    mybatis工具包：https://github.com/abel533/Mapper（完成(封装了jpa）
    多数据源：完成 数据源注解@DataSource必须在mapper注入前执行，事务回滚测试完成
    读写分离：参考：https://www.cnblogs.com/cjsblog/p/9712457.html，https://mp.weixin.qq.com/s/uivql_NMhWSnjknL9o5M3A（完成）
        通过hadoop.openMulti配置来开启和关闭多数据，如果不配置默认开启单数据源
    执行sql脚本：groovy或者后期根据mybatis开发一个，或者不开发
    mybatis一级（默认开启，和sqlsession的生命周期一致）关闭，二级缓存设置(不用)
        缓存：热点数据存放缓存，不经常修改的数据，用户权限，字典数据，配置信息
    mysql:集群和主从，分库分表（待整理文档，做成docker镜像）
    执行存储过程实例（待开发)，
    批处理sql开发包括插入和更新(待开发)
    mybaits拦截器开发（待开发）
    mybatis开发执行sql字符串 完成（com.hzl.hadoop.app.controller.ExcelExportController）
    mybatis存储日期类型自动将时间缩短，更新2019-09-01会变成2019-08-31（已经修复）\
    String类型转换成Date时区显示有问题（bug）方法：localStringToDate
        
集成条形码二维码，模版引擎组件 https://github.com/Nepxion/Zxing(完成)

多语言功能开发
    
开发权限，aop用于自定义注解功能实现，方法代理(学习资料：https://github.com/Nepxion/Matrix)(待深入学习和完善)

    注解执行时机：启动，调用，结束（bean的生命周期）
    针对spring的生命周期，开发对应的实现类例子，供后期复制调用（完成）
    
    
集成easyexcel,文件位置com.hzl.hadoop.file.excel.ExcelDone，demo待完善
    支持多语言，支持指定字端导出
    导出任意sql查询的结果：com.hzl.hadoop.app.controller.ExcelExportController
    
增加设计模式样例:com.hzl.hadoop.design

增加io流操作工具类(后期实际运用中完善)

docker-compose日志大小日志（完成:参考mysql/master/docker-compose.yml）https://www.west.cn/docs/51325.html

添加spring事务式例（待开发）

集成分布式事务和分布式锁，缓存：(数据库相关完成后需要开始了)
    
    参考： 
    集成  redis: 
            redistemplate工具类（写了今本的，其他的开发中待完善）
            jackson配置完成，fastjson配置待阿里bug修复后期，gson配置不要了
            cache注解实例（待完成），
            redistemplate测试类（待完成），
            redis分布式锁（开发中）
                集成Redisson实现分布式操作（https://github.com/redisson/redisson）
                https://gitee.com/ztp/redisson-spring-boot-starter(选用这个jar)
                第一步：读取配置文件生成Config对象
            整理redis主从和集群文档（抓紧）
    集成kafka，rocketmq
    集成：zookeeper
    集成 spring cache
    集成 缓存监控
    
    分布式锁的使用场景，判断数据是否被多个用户共享。
    
集成支付功能 （https://github.com/Javen205/IJPay，https://gitee.com/javen205/IJPay，https://github.com/easy-pay/spring-boot-easy-pay）
    
    在支付的和FreeMarkerZxingController条形码基础上开发报销单业务逻辑，结合消息中间件，异步处理报销单不是直接插入数据库
    用户提交报销单后直接发送到消息中间件并提示用户提交成功，后续处理由消息中间件分发给服务异步处理，减少并发和对数据库造车的压力
    https://www.cnblogs.com/lizm166/p/11023373.html
    支付成功(显示商家正在备货)后异步通知出货系统（消息队列）
    
开发多线程工具类：线程池

开发工作流

集成定时器，队列只消费一次


集成springsecurity,开启https用nginx，开发一套完善的权限控制组件(使用简单，性能优越)


集成dubbo，rpc,netty(nio)

限制访问流量服务器和应用都需要

开发websocket（完成）

增加json处理工具类（完成-后期根据项目实际情况完善）

文件功能file服务，开发上传文件接口，开发http请求下载文件的功能(完成，HttpFileDownloadTest)
     
     添加文件压缩解压操作，大文件网络传输有问题

开发启动脚本start.sh idea配置(-Xms0m -Xmx512m -Dspring.profiles.active=local)

集成gitlib ci/cd,自动构建docker镜像，k8部署，参考资料：https://gitee.com/itmuch/docker-book（已经下载到本地）

spring开发多线程 ，异步线程Async使用后，事务注解仍然有效（完成）

springboot应用监控-包括发布成功后发送邮件，服务停止后发送邮件

算法工具类：com.hzl.hadoop.util.AlgorithmUtils

根据redis开发在线登陆人数


新增谷歌文本对比工具类：
https://github.com/google/diff-match-patch
位置：com.hzl.hadoop.util.textCompare.TextCompare
1：集成 Elasticsearch（docker安装）

     中文分词软件：https://github.com/ysc/word（分析文本关键字，问答系统中文分析）
    
   
   
-- 文件加密解密

-- 添加了印章图片生成工具类（可以用，在具体应用中调整参数)，改章不具有法律效应，哎废了用来造假倒是可以
     
     com.hzl.hadoop.util.seal.SealUtil
     //生成签章图片
     https://github.com/localhost02/SealUtil
     //pdf插入电子签章图片，并添加证书具有法律效应（证书要钱，个人不愿意出钱，公司一般和e签宝合作。想赚钱真难）
     https://blog.csdn.net/do_bset_yourself/article/details/78171897



----前端最后做
以ant design pro为基础开发
    文档：https://www.yuque.com/ant-design/course
    项目：https://pro.ant.design/index-cn
    
#增加数学函数用于数据分析，主要是针对集合操作

#java性能监控,压力测试jmeter 

# 增加tomcat配置包括并发请求线程数量，增加underdrow替换tomcat方案，

-----产品简介性

--- 添加网络设备，蓝牙设备获取

--添加操作记录功能，记录对方ip，操作时间，地区，耗时，执行方法，操作，通过aop实现增强

---监控均价的异常波动，监控均价和最高均价，历史平均均价的对比，（分为正负两种情况）

