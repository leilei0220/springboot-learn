package com.leilei.service.impl;

import com.leilei.config.AjaxResult;
import com.leilei.config.LeileiException;
import com.leilei.entity.one.User;
import com.leilei.entity.three.Student;
import com.leilei.entity.two.Role;
import com.leilei.service.IStudentService;
import org.bson.BsonTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : leilei
 * @date : 16:17 2020/2/16
 * @desc :
 */
@Service
public class StudentServiceImpl implements IStudentService {

  @Autowired
  @Qualifier("threeMongo")
  private MongoTemplate threeMongoTemplate;

  @Autowired
  @Qualifier("oneMongo")
  private MongoTemplate oneMongoTemplate;
  @Autowired
  @Qualifier("twoMongo")
  private MongoTemplate twoMongoTemplate;

  /**
   * 说明  当前 学习深度不够 目前只会回滚 当前事务管理器下 数据源中的数据 这里仅仅只会回滚 threeMongoTemplate
   * 2020/05/18 已完成多数据源同一事务管理器配置
   * @param student
   * @return
   */
  @Override
  @Transactional(transactionManager = "chainedTransactionManager", rollbackFor = Exception.class)
  public AjaxResult insertStudent(Student student) throws Exception {
    student.setCreatTime(LocalDateTime.now());
    try {
      threeMongoTemplate.insert(student);
      oneMongoTemplate.insert(
          User.builder()
              .id(student.getId())
              .userName(student.getStudentName())
              .age(student.getAge())
              .creatTime(LocalDateTime.now())
              .build());
      twoMongoTemplate.insert(Role.builder().id(22L).age(111).roleName("asda").build());
//      int a = 1 / 0;
      return AjaxResult.buildSuccess("多数据源插入操作成功", null);
    } catch (Exception e) {
      e.printStackTrace();
      throw new LeileiException("多数据源操作失败，查看对应库数据是否回滚");
    }
  }
}
