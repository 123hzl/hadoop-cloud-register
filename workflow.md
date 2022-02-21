工作流前端文件目录：

libs:jquery和jsPlumb

需要添加一个监听表，然后节点和流程图关联这个监听表，并设置监听类为全局或者指定节点


读取workflow_char流程图数据，获取流程图结构，解析后并存储到start_node,gateway_node,approve_node,end_node中
存储节点关系，节点上的审批人和审批组，监听器等一系列配置。。。网管节点的下一个节点可以是多个，也可以是一个，所以表中下一个节点改成数组
存储多个节点的id