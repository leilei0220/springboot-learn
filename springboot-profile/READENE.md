###Springboot-profile 
指定启动环境 （启动时加载对应yml配置文件）
yml中 指定启动环境

###springboot 配置文件 加载优先级
优先级1：项目路径下的config文件夹配置文件
优先级2：项目路径下配置文件
优先级3：资源路径下的config文件夹配置文件
优先级4：资源路径下配置文件

### 加载外部配置文件
当然 springboot 还可以加载外部配置文件这样也能保证安全性
如果使用外部配置文件 命令为  java -jar xxx.jar --spring.config.location=配置文件路径 
