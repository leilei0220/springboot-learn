此项目为单机fastdfs文件系统 上传下载 删除demo
项目依赖中的fastdfs-client-java  中央仓库是找不到的
请到https://github.com/happyfish100/fastdfs-client-java下载并install
###所用软件： 
      centos	7.x
      libfatscommon	FastDFS分离出的一些公用函数包
      FastDFS	FastDFS本体
      fastdfs-nginx-module	FastDFS和nginx的关联模块
      nginx	nginx1.15.4
      
  ######编译环境
  yum install git gcc gcc-c++ make automake autoconf libtool pcre pcre-devel zlib zlib-devel openssl-devel wget vim -y
  步奏：
  mkdir /home/dfs #创建数据存储目录
  cd /usr/local/src #切换到安装目录准备下载安装包
  
  ######安装libfatscommon
  git clone https://github.com/happyfish100/libfastcommon.git --depth 1
  cd libfastcommon/
  ./make.sh && ./make.sh install #编译安装
  ######安装FastDFS
  cd ../ #返回上一级目录
  git clone https://github.com/happyfish100/fastdfs.git --depth 1
  cd fastdfs/
  ./make.sh && ./make.sh install #编译安装
  cp /etc/fdfs/tracker.conf.sample /etc/fdfs/tracker.conf
  cp /etc/fdfs/storage.conf.sample /etc/fdfs/storage.conf
  cp /etc/fdfs/client.conf.sample /etc/fdfs/client.conf #客户端文件，测试用
  cp /usr/local/src/fastdfs/conf/http.conf /etc/fdfs/ #供nginx访问使用
  cp /usr/local/src/fastdfs/conf/mime.types /etc/fdfs/ #供nginx访问使用
  
 ###### 安装fastdfs-nginx-module
  cd ../ #返回上一级目录
  git clone https://github.com/happyfish100/fastdfs-nginx-module.git --depth 1
  cp /usr/local/src/fastdfs-nginx-module/src/mod_fastdfs.conf /etc/fdfs
  
  ######安装nginx
  wget http://nginx.org/download/nginx-1.15.4.tar.gz #下载nginx压缩包
  tar -zxvf nginx-1.15.4.tar.gz #解压
  cd nginx-1.15.4/
  #####添加fastdfs-nginx-module模块
  ./configure --add-module=/usr/local/src/fastdfs-nginx-module/src/ 
  make && make install #编译安装
  
  ###单机部署
  tracker配置
  #####服务器ip为 192.168.52.1
  #####我建议用ftp下载下来这些文件 本地修改
  vim /etc/fdfs/tracker.conf
  #####需要修改的内容如下
  port=22122  # tracker服务器端口（默认22122,一般不修改）
  base_path=/home/dfs  # 存储日志和数据的根目录
  storage配置
  vim /etc/fdfs/storage.conf
  #####需要修改的内容如下
  port=23000  # storage服务端口（默认23000,一般不修改）
  base_path=/home/dfs  # 数据和日志文件存储根目录
  store_path0=/home/dfs  # 第一个存储目录
  tracker_server=192.168.52.1:22122  # tracker服务器IP和端口
  http.server_port=8888  # http访问文件的端口(默认8888,看情况修改,和nginx中保持一致)
  client测试
  vim /etc/fdfs/client.conf
  #####需要修改的内容如下
  base_path=/home/dfs
  tracker_server=192.168.52.1:22122    #tracker服务器IP和端口
  #####保存后测试,返回ID表示成功 如：group1/M00/00/00/xx.tar.gz
  fdfs_upload_file /etc/fdfs/client.conf /usr/local/src/nginx-1.15.4.tar.gz
  配置nginx访问
  vim /etc/fdfs/mod_fastdfs.conf
 #####需要修改的内容如下
  tracker_server=192.168.52.1:22122  #tracker服务器IP和端口
  url_have_group_name=true
  store_path0=/home/dfs
 #####配置nginx.config
  vim /usr/local/nginx/conf/nginx.conf
  #####添加如下配置
  server {
      listen       8888;    ## 该端口为storage.conf中的http.server_port相同
      server_name  localhost;
      location ~/group[0-9]/ {
          ngx_fastdfs_module;
      }
      error_page   500 502 503 504  /50x.html;
      location = /50x.html {
      root   html;
      }
  }
  #####测试下载，用外部浏览器访问刚才已传过的nginx安装包,引用返回的ID
  http://192.168.52.1:8888/group1/M00/00/00/wKgAQ1pysxmAaqhAAA76tz-dVgg.tar.gz
  #####弹出下载单机部署全部跑通
  
  ####client测试
  vim /etc/fdfs/client.conf
  ###需要修改的内容如下
  base_path=/home/moe/dfs
  tracker_server=192.168.52.2:22122  # 服务器1
  tracker_server=192.168.52.3:22122  # 服务器2
  tracker_server=192.168.52.4:22122  # 服务器3
  ####保存后测试,返回ID表示成功 如：group1/M00/00/00/xx.tar.gz
  fdfs_upload_file /etc/fdfs/client.conf /usr/local/src/nginx-1.15.4.tar.gz
  
  ###启动
  防火墙
  #####不关闭防火墙的话无法使用  或开放对应端口
  systemctl stop firewalld.service #关闭
  systemctl restart firewalld.service #重启
  ######开启80端口
  firewall-cmd --zone=public --add-port=80/tcp --permanent
  ######tracker
  /etc/init.d/fdfs_trackerd start #启动tracker服务
  /etc/init.d/fdfs_trackerd restart #重启动tracker服务
  /etc/init.d/fdfs_trackerd stop #停止tracker服务
  chkconfig fdfs_trackerd on #自启动tracker服务
  ######storage
  /etc/init.d/fdfs_storaged start #启动storage服务
  /etc/init.d/fdfs_storaged restart #重动storage服务
  /etc/init.d/fdfs_storaged stop #停止动storage服务
  chkconfig fdfs_storaged on #自启动storage服务
  ######nginx
  /usr/local/nginx/sbin/nginx #启动nginx
  /usr/local/nginx/sbin/nginx -s reload #重启nginx
  /usr/local/nginx/sbin/nginx -s stop #停止nginx
  
  ###### 上述安装步奏均来自于github上官方教程  地址：https://github.com/happyfish100/fastdfs/wiki
  
 #####client测试 跑通后 想要在springboot项目使用请看下，，，，，，，
 
##新建springboot工程
#### yml配置：
    server.port=80
    # 文件最大size
    spring.servlet.multipart.max-file-size=2MB
    # FastDFS服务器地址
    app.base.url=http://192.168.86.134:8888/ 
#### 新建 fastdfs client配置文件 例如本项目中的 fdfs_client.conf

#### 拷贝我的fastdfs上传下载删除工具类 fastDfsUtils
####编写controller层 
####启动项目  访问localhost  来到上传首页
#### 上传成功后可的到 fastdfs服务器+端口+组名+文件名  http://192.168.86.134:8888/group1/M00/00/00/wKhWhl4W5QeAZl_JAACiX9cjJqM192.jpg
#### 删除页面没有编写 采用 postman方式即可 传入参数  /group1/M00/00/00/wKhWhl4W5QeAZl_JAACiX9cjJqM192.jpg  当成功后可能会请求上次图片路径 还是会成功
#### 请清除缓存！！！！ 
  
  