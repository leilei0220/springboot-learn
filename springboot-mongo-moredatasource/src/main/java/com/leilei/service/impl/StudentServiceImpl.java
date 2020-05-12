package com.leilei.service.impl;

import com.leilei.entity.one.User;
import com.leilei.entity.three.Student;
import com.leilei.service.IStudentService;
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

  /**
   * 说明  当前 学习深度不够 目前只会回滚 当前事务管理器下 数据源中的数据 这里仅仅只会回滚 threeMongoTemplate
   * @param student
   * @return
   */
  @Override
  @Transactional(value = "ThreeFactoryTransactionManager", rollbackFor = Exception.class)
  public int insertStudent(Student student) {
    LocalDateTime now = LocalDateTime.now();
    student.setCreatTime(now);
    try {
      threeMongoTemplate.insert(student);
      oneMongoTemplate.insert(
          User.builder()
              .id(student.getId())
              .userName(student.getStudentName())
              .age(student.getAge())
              .creatTime(now)
              .sex(new User().getSex())
              .build());
      int a = 1 / 0;
      return 1;
    } catch (Exception e) {
      e.printStackTrace();
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      return -1;
    }
  }
}
