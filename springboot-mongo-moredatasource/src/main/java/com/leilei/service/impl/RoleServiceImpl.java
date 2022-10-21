package com.leilei.service.impl;

import com.leilei.config.AjaxResult;
import com.leilei.config.LeileiException;
import com.leilei.entity.two.Role;
import com.leilei.service.IRoleService;
import org.bson.BsonDateTime;
import org.bson.types.BSONTimestamp;
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
  @Transactional(transactionManager = "twoTransactionManager", rollbackFor = Exception.class)
  public AjaxResult insertRole(Role role) throws Exception{
    role.setCreatTime(LocalDateTime.now());
      twoMongoTemplate.insert(role);
      int a = 1 / 0;
      return AjaxResult.buildSuccess("role保存成功", null);
  }

  @Override
  public List<Role> findAll() {
    return twoMongoTemplate.findAll(Role.class);
  }
}
