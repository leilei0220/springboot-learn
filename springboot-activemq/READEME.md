##springboot 整合 activemq
简单使用
common 为公共使用 
easy --activemq简单使用 点对点模式
topic activemq topic模式使用 注意此需要在yml中添加配置 pub-sub-domain: true
virtual 虚拟主题模式 要求比较严格
  其topic命名必须以 Virtual.xxx格式
  JmsListenerContainerFactory 中 setPubSubDomain(false); 
  消费者 destination 则必须以Consumer.xxx.Virtual.xx 