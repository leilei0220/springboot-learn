SpringBoot 多环境配置文件选择 
SpringBoot 多环境启动篇  
编译打包其对应Yml  
打包命令 idea 进入项目目录下 使用Terminal终端   mvn clean install -P test  此命令就会打包test环境
打包后 在target classes 下可查看打包环境
启动时直接java -jar 执行就ok