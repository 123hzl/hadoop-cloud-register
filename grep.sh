#!/usr/bin/env bash
#日志查询脚本
basedir=$1
search=$2
echo "目录${basedir}"
cd ${basedir}
for log in $(ls)
do
   echo "开始搜索${log}"
   #-C显示上下包括当前行的上下5行，-B显示包括当前行的前5行，-A显示当前行包括后5行 search需要查询的内容，log需要查询的文件
   grep -C 10  "${search}" ${log}
   echo "${log}搜索结束"
done