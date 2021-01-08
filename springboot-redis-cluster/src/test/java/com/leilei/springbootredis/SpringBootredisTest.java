// package com.leilei.springbootredis;
//
// import com.alibaba.fastjson.JSON;
// import com.alibaba.fastjson.JSONObject;
// import com.leilei.SpringbootRedisApplication;
// import com.leilei.entity.User;
// import com.leilei.util.RedisUtil;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.cglib.beans.BeanMap;
// import org.springframework.data.redis.core.DefaultTypedTuple;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
// import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
// import org.springframework.transaction.annotation.Transactional;
//
// import java.io.Serializable;
// import java.time.LocalDateTime;
// import java.util.*;
//
// /**
//  * @author : leilei
//  * @date : 17:51 2020/2/17
//  * @desc :
//  */
// @SpringBootTest(classes = SpringbootRedisApplication.class)
// @RunWith(SpringJUnit4ClassRunner.class)
// public class SpringBootredisTest {
//
//   @Autowired
//   private RedisTemplate redisTemplate;
//   @Autowired
//   private RedisUtil redisUtil;
//
//   /**
//    * 锁测试
//    */
//   @Test
//   public void testLock() {
//     System.out.println(redisUtil.lock("myLock", 1000));
//   }
//
//
//   /**
//    * String
//    */
//   @Test
//   public void testString() {
//     redisUtil.set("a", 11111);
//     System.out.println(redisUtil.get("a"));
//     redisUtil.set("过期时间测试", 123, 50);
//     redisUtil.del("a");
//   }
//   /**
//    * String
//    */
//   @Test
//   public void testString2() {
//     redisUtil.set("user:1", JSON.toJSONString(new User("zs1","11",11)));
//     redisUtil.set("user:2", JSON.toJSONString(new User("zs2","11",11)));
//     redisUtil.set("user:3", JSON.toJSONString(new User("zs3","11",11)));
//   }
//

//
//
//   /**
//    * hash
//    */
//   @Test
//   public void testMap() {
//     HashMap<String, Object> objectObjectHashMap = new HashMap<>();
//     objectObjectHashMap.put("aaa", 1);
//     objectObjectHashMap.put("aaa", "aaa");
//     objectObjectHashMap.put("a", 1);
//     objectObjectHashMap.put("qweqwr", 1L);
//     redisUtil.hmset("Map测试", objectObjectHashMap);
//
//     Map<Object, Object> map = redisUtil.hmget("Map测试");
//     for (Map.Entry<Object, Object> objectObjectEntry : map.entrySet()) {
//       System.out.println(objectObjectEntry.getKey() + "-------" + objectObjectEntry.getValue());
//     }
//
//     Object hget = redisUtil.hget("Map测试", "a");
//     System.out.println(hget);
//
//     redisUtil.hdel("Map测试", "qweqwr");
//   }
//
//   /**
//    * hash
//    */
//   @Test
//   public void testMapSuper() {
//     HashMap<String, Object> map = new HashMap<>();
//     map.put("a", 1L);
//     map.put("b", 2D);
//     map.put("c", 123);
//     map.put("d", LocalDateTime.now());
//     redisUtil.hmset("leilei:1:map", map);
//     redisUtil.hmset("leilei:2:map", map);
//     redisUtil.hmset("leilei:3:map", map);
//     redisUtil.hmset("leilei:4:map", map);
//   }
//
//   @Test
//   public void testMap2Bean() {
//     Map<String, Object> map = new HashMap<>();
//     map.put("username", "王五");
//     map.put("password", "123");
//     map.put("age", 11);
//     redisUtil.hmset("Map2Bean", map);
//     Map<Object, Object> map2Bean = redisUtil.hmget("Map2Bean");
//     User user = mapToBean(map2Bean, new User());
//     System.out.println("Map2Bean" + user);
//   }
//
//   /**
//    * map转对象
//    *
//    * @param map
//    * @param bean
//    * @param <T>
//    * @return
//    */
//   public static <T> T mapToBean(Map<Object, Object> map, T bean) {
//     BeanMap beanMap = BeanMap.create(bean);
//     beanMap.putAll(map);
//     return bean;
//   }
// //-----------------set----------------
//
//   /**
//    * set
//    */
//   @Test
//   public void testSet() {
//     redisUtil.sSet("Set集合", 3,3,12, 1, 2, 5, 12, 666);
//   }
//
//   /**
//    * test
//    */
//   @Test
//   public void testSetBean() {
//
//     long l = redisUtil.sSet("魏将",
//         new User("许褚", "qq", 55),
//         new User("张辽", "dd", 62),
//         new User("典韦", "123", 49));
//
//     long l2 = redisUtil.sSet("蜀将",
//             new User("关羽", "qqa", 55),
//             new User("赵飞", "dda", 625),
//             new User("赵云", "123a", 495));
//     System.out.println(l);
//     System.out.println(l2);
//   }
//
//   /**
//    * 测试并集
//    */
//   @Test
//   public void unionALl() {
//     Set<Object> objects = redisUtil.unionAll("魏将", "蜀将");
//     objects.forEach(e-> System.out.println(e));
//   }
//
//   /**
//    * 测试交集
//    */
//   @Test
//   public void testInse() {
//
//     long l = redisUtil.sSet("三国人物传1",
//             new User("吕布", "qq", 55),
//             new User("典韦", "123", 49));
//
//     long l2 = redisUtil.sSet("三国人物传2",
//             new User("吕布", "qq", 55),
//             new User("赵云", "123a", 495));
//     System.out.println(l);
//     System.out.println(l2);
//     redisUtil.intersect("三国人物传2", "三国人物传1").forEach(e-> System.out.println(e));
//   }
//   /**
//    * test
//    */
//   @Test
//   public void testSet1() {
//     long size = redisUtil.sGetSetSize("Set集合");
//     System.out.println(size);
//   }
//
//   //------------zSet-----------------
//
//   /**
//    * test 添加
//    */
//   @Test
//   public void test() {
//     redisUtil.sZset("天梯排行榜", "张三", 2);
//     redisUtil.sZset("天梯排行榜", "逍遥子", 1);
//     redisUtil.sZset("天梯排行榜", "无崖子", 3);
//   }
//
//   /**
//    * test zset 批量添加
//    */
//   @Test
//   public void test1() {
//
//     DefaultTypedTuple<Object> a = new DefaultTypedTuple<>("张三丰大弟子", 3.2);
//     DefaultTypedTuple<Object> b = new DefaultTypedTuple<>("宋青书", 9.8);
//     DefaultTypedTuple<Object> c = new DefaultTypedTuple<>("时间刺客罗", 8.5);
//     Set<TypedTuple<Object>> defaultTypedTuples = new HashSet<>(Arrays.asList(a, b, c));
//     Long add = redisUtil.sZsetList("天梯排行榜", defaultTypedTuples);
//     System.out.println(add);
//   }
//
//   /**
//    * test 获取排名 （返回位于zset集合中的索引值!!!!!）
//    */
//   @Test
//   public void test2() {
//     Long aLong = redisUtil.zRank("天梯排行榜", "无崖子");
//     System.out.println(aLong);
//   }
//
//   /**
//    * test
//    */
//   @Test
//   public void test3() {
//     redisUtil.zIncrScore("天梯排行榜", "无崖子", -0.5);
//   }
//
//   /**
//    * test 删除 可填入多个value
//    */
//   @Test
//   public void test4() {
//     Long aLong = redisUtil.zRemove("天梯排行榜", "无崖子");
//     System.out.println(aLong);
//   }
//
//   /**
//    * test 获取score值
//    */
//   @Test
//   public void test5() {
//     Double aDouble = redisUtil.zScore("天梯排行榜", "逍遥子");
//     System.out.println(aDouble);
//   }
//
//   /**
//    * test 获取zset集合大小
//    */
//   @Test
//   public void test6() {
//     Long size = redisUtil.zSize("天梯排行榜");
//     System.out.println(size);
//   }
//
//   /**
//    * test 获取集合中的数据 正序倒序 获取指定范围数据
//    */
//   @Test
//   public void test7() {
//     //正序
//     Set<Object> set = redisUtil.zRange("天梯排行榜", 0, -1);
//     set.forEach(e -> System.out.println(e));
//     System.out.println("---------");
//     //倒序
//     Set<Object> set1 = redisUtil.reverseRange("天梯排行榜", 0, -1);
//     set1.forEach(e -> System.out.println(e));
//   }
//
//   /**
//    * test 根据索引范围 获取某zset集合中  value 和 score
//    */
//   @Test
//   public void test8() {
//     Set<TypedTuple<Object>> set = redisUtil.rangeWithScore("天梯排行榜", 0, -1);
//     for (TypedTuple<Object> objectTypedTuple : set) {
//       System.out.println(objectTypedTuple.getValue() + " == " + objectTypedTuple.getScore());
//     }
//   }
//
//   /**
//    * test 根据分数最大最小值获取set集合后再根据 索引以及显示个数返回全新set集合
//    */
//   @Test
//   public void test9() {
//
//     Set<Object> set = redisUtil.rangeByScore("天梯排行榜", 1, 10);
//     //[逍遥子, 张三, 张三丰大弟子, 时间刺客罗, 宋青书]
//     System.out.println(set);
//     Set<Object> set1 = redisUtil.rangeByScore("天梯排行榜", 1, 10, 2L, 2L);
//     //[张三丰大弟子, 时间刺客罗]
//     System.out.println(set1);
//   }
//
//
//   /**
//    * fastjson 部分使用
//    */
//   @Test
//   public void testFastJson() {
//     //String对象转Json字符串
//     String str = "a=1&b=2&c=e";
//
//     str = str.replace("=", "\":\"");
//     System.out.println(str);
//     str = str.replace("&", "\",\"");
//     System.out.println(str);
//     str = "{\"" + str + "\"}";
//     System.out.println(str);
//     System.out.println(JSON.parseObject(str).toJSONString());
//
//     //Java对象转json字符串
//     User aa = new User("aa", "123456", 15);
//     System.out.println(aa); //User(username=aa, password=123456, age=15)
//     String s = JSON.toJSONString(aa);
//     System.out.println(s);   //{"age":15,"password":"123456","username":"aa"}
//
//     //json字符串转java对象
//     String ss = "{\"age\":15,\"password\":\"123456\",\"username\":\"aa\"}";
//     User user = JSON.parseObject(ss, User.class);
//     System.out.println(user); //User(username=aa, password=123456, age=15)
//
//     //Java集合转json字符串
//     List<Serializable> serializables = Arrays.asList(new User("a", "a", 1), new User("b", "b", 2));
//     String jsonString = JSON.toJSONString(serializables);
//     System.out.println(
//         jsonString); //[{"age":1,"password":"a","username":"a"},{"age":2,"password":"b","username":"b"}]
//
//     //json字符串转java集合
//     String jsonlistString = "[{\"age\":1,\"password\":\"a\",\"username\":\"a\"},{\"age\":2,\"password\":\"b\",\"username\":\"b\"}]";
//     List<User> users = JSON.parseArray(jsonlistString, User.class);
//     users.forEach(e -> System.out
//         .println(e));  //User(username=a, password=a, age=1) User(username=b, password=b, age=2)
//
//     //map转Json字符串
//     HashMap<String, Object> objectObjectHashMap = new HashMap<>();
//     objectObjectHashMap.put("a", 1);
//     objectObjectHashMap.put("b", 2L);
//     objectObjectHashMap.put("c", 3D);
//     objectObjectHashMap.put("d", 3f);
//     objectObjectHashMap.put("f", LocalDateTime.now());
//     String mapjsonString = JSON.toJSONString(objectObjectHashMap);
//     System.out
//         .println(mapjsonString);  //{"a":1,"b":2,"c":3.0,"d":3.0,"f":"2020-02-18T16:40:01.203"}
//
//     //map转json对象
//     JSONObject mapjsonobj = new JSONObject(objectObjectHashMap);
//     System.out.println("map转json对象" + mapjsonobj);
//     //json字符串转Map
//     //需要将json字符串转为json对象   json对象再转为map
//     JSONObject jsonObject = JSONObject.parseObject(mapjsonString);
//     Map<String, Object> map = (Map<String, Object>) jsonObject;
//     for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
//       System.out.println(stringObjectEntry.getKey() + "-------" + stringObjectEntry.getValue());
//       //a-------1
//       //b-------2
//       //c-------3.0
//       //d-------3.0
//       //f-------2020-02-18T16:45:37.452
//     }
//
//
//   }
//
//   /**
//    * list
//    */
//   @Test
//   public void testList() {
//     redisUtil.lSet("LIst测试", 1);
//     redisUtil.lSet("LIst测试", 2);
//     redisUtil.lSet("LIst测试", "aaaa");
//     List<Object> list = redisUtil.lGet("LIst测试", 1, 2);
//     System.out.println(list);
//     System.out.println(redisUtil.lGetListSize("LIst测试"));
//     System.out.println(redisUtil.lGetIndex("LIst测试", 2));
//     List<Object> listo = new ArrayList<>();
//     listo.add(1);
//     listo.add(2);
//     User user = new User();
//     user.setUsername("aa");
//     user.setPassword("123");
//     user.setAge(12);
//     listo.add(JSON.toJSONString(user));
//     redisUtil.lSet("list与json测试", listo);
//     String s = (String) redisUtil.lGetIndex("list与json测试", 2);
//     User user1 = JSON.parseObject(s, User.class);
//     System.out.println(user1);
//   }
// }
