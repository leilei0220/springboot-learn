### springboot 整合rabbitmq
整合多种模式  测试结果与心得详见 测试类

anonymous 匿名队列使用

common 为多个模式公共所需 我这里为 一个对象实体


easy 包下 为简单模式 

work 包下 为工作模式   多个消费者会轮流获取到队列消息 例如 两个消费者 生产者发送十条消息 则 每个消费者会消费五次

confirm 包下 为rabbitmq 消费发送者确认模式 配合消费者端的手动应答，确保消息被成功发送以及消费

direct 包下 路由模式  队列绑定到direct交换机再指定路由键 ，生产者发送消息时指定交换机路由键 ，则会被对应队列监听到

fanout包下 发布订阅模式 队列绑定到fanout交换机 未指定路由键名, 生产者发送消息时指定交换机 路由键指定为空或指定为 "" 则会被所有订阅到交换机的队列监听到

topic 包下 为主题模式  队列绑定到topic模式交换机 在指定路由键 例如（top.#）或(top.*)
    #  * 区别
    * 仅仅会匹配路由键的一个词 如果生产者发送消息到路由键 例如 leilei.one / leilei.two 则会被对应绑定的队列监听到
    # 则可以匹配路由键的多个词 如果生产者发送消息到路由键 例如 leilei.one / leilei.one.xxl / leilei.one.xxl.eq  只要是以leilei 路由键为前缀的，无论多少个次都会被监听到

ttl包下 为 rabbitmq 的ttl模式 （设置消息存活时间）

ttl-dead 包下 为 ttl + 死信队列模式 （可做类似定时任务以及延迟处理场景）

queue 队列默认为持久化状态
    
发送消息 convertSendAndReceive   convertAndSend 区别
  使用 convertAndSend 方法时的结果：输出时没有顺序，不需要等待，直接运行
  使用 convertSendAndReceive 方法时的结果：输出有顺序 只有确定消费者接收到消息，才会发送下一条信息，每条消息之间会有等待间隔时间
  
确保消息成功发送 confirm 模式 如果失败则可以按照自己逻辑处理保存到数据库失败发送表 可后续做补偿
确保消息成功消费 ack 手动应答  失败时按业务 选择从新投递或者丢弃
  
