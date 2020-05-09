##springboot 项目中 支持 http 和 https 双协议
1.从阿里云下载ssl证书 tomcat版本 （可选择免费的）

2.下载完成后解压 包含 xxx.pfx 和一个txt文件，我们要做的就是将 .pfx文件拷贝到 咱们的resource目录

3.yml文件中 配置证书路径 以及https 端口

4.启动类中添加bean 支持https 以及http

5.到目前为止是可以通过http 协议和 Https协议进行访问了，但是会遇到问题 在使用https 协议时 所有的 post 
请求全部会变为get 导致出现一些功能性的问题

6.配置 fifter过滤器 将http 请求转发到https请求上来 重定向类型：307 解决 post请求变为get 的问题 