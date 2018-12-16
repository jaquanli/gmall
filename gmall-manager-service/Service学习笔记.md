###Redis配置位置
1、将maven依赖放在service-util的pom中，因为只有service层面才会用到Redis  
2、Redis连接和配置类放在service-util，  
3、对应的连接服务的配置地址和其他选项配置在需要地址的项目中

####关于redis中获取以及存储数据使用的**KEY**名称规则：
命名规则公式：<br/>Object:id:field  
解释:  
**Object**：当前模块/当前对象/当前表的简称  
**id**:当前要查询的id，比如对象的id或者说是查询的条件  
**field**：查询查询出要存储的结果，比如查询整个user对象，field就是user，
如果查询的是id，file就是id
####Redis缓存击穿：
解释：就是Redis产生异常，无法正常工作，从而全部的并发压力都堆积在DB层面，导致DB服务器宕机  
**解决**：限制DB的访问量  
分析思路：
具体方式：设置Redis分布式缓存锁，