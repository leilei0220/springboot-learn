package com.leilei.service.impl;

import com.leilei.entity.two.Role;
import com.leilei.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @author : leilei
 * @date : 16:18 2020/2/16
 * @desc :
 */
@Service
public class RoleServiceImpl implements IRoleService {

  @Autowired
  @Qualifier("twoMongo")
  private MongoTemplate twoMongoTemplate;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int insertRole(Role role) {
    role.setCreatTime(LocalDateTime.now());
    try {
      twoMongoTemplate.insert(role);
      int a = 1 / 0;
      return 1;
    } catch (Exception e) {
      e.printStackTrace();
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      return -1;
    }
  }

  @Override
  public List<Role> findAll() {
    return twoMongoTemplate.findAll(Role.class);
  }
}
