package com.leilei.service.impl;

import cn.hutool.core.date.DateUtil;
import com.leilei.config.AjaxResult;
import com.leilei.config.LeileiException;
import com.leilei.entity.one.User;
import com.leilei.entity.one.vo.CountUser;
import com.leilei.entity.one.vo.MonthByUser;
import com.leilei.entity.one.vo.UserVo;
import com.leilei.service.IUserService;
import org.bson.BsonTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators.Cond;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * @author : leilei
 * @date : 16:17 2020/2/16
 * @desc :
 */
@Service
public class UserServiceImpl implements IUserService {

  @Autowired
  @Qualifier("oneMongo")
  private MongoTemplate oneMongoTemplate;

  @Override
  @Transactional(transactionManager = "oneTransactionManager", rollbackFor = Exception.class)
  public AjaxResult insertUser(User user) throws Exception {
    user.setCreatTime(LocalDateTime.now());

    try {
      oneMongoTemplate.insert(user);
      int a = 1 / 0;
      return AjaxResult.buildSuccess("操作成功", null);
    } catch (Exception e) {
      e.printStackTrace();
      throw new LeileiException("出现异常,当前保存User 事务会回滚");
    }

  }

  @Override
  public List<User> findAll() {
    try {
      List<User> all = oneMongoTemplate.findAll(User.class);
      return all;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 统计所有用户年龄总和
   *
   * @return
   */
  @Override
  public Integer countUserAge() {
    Aggregation aggregation = newAggregation(group().sum("$age").as("ageSum"));
    AggregationResults<UserVo> user = oneMongoTemplate.aggregate(aggregation, "user", UserVo.class);
    if (user != null) {
      List<UserVo> mappedResults = user.getMappedResults();
      if (mappedResults != null) {
        UserVo userVo = mappedResults.get(0);
        return userVo.getAgeSum();
      }
      return null;
    }
    return null;
  }

  /**
   * 根据月份统计玩家注册数
   *
   * @return
   */
  @Override
  public MonthByUser countUserByMonth() {
    List<AggregationOperation> operations = new ArrayList<AggregationOperation>();
    operations.add(Aggregation.project().andExpression("substr(creatTime,5,2)").as("creatTime"));
    operations.add(Aggregation.group("creatTime").count().as("total"));
    Aggregation aggregation = Aggregation.newAggregation(operations);
    AggregationResults<UserVo> aggregate =
        oneMongoTemplate.aggregate(aggregation, "user", UserVo.class);
    aggregate.getMappedResults().forEach(e -> System.out.println(e));
    if (aggregate != null) {
      MonthByUser monthByUser = new MonthByUser();

      aggregate.getMappedResults().forEach(e -> {
        if (e.getId() == 1) {
          monthByUser.setJanuary(e.getTotal());
        }
        if (e.getId() == 2) {
          monthByUser.setFebruary(e.getTotal());
        }
        if (e.getId() == 3) {
          monthByUser.setMarch(e.getTotal());
        }
        if (e.getId() == 4) {
          monthByUser.setApril(e.getTotal());
        }
        if (e.getId() == 5) {
          monthByUser.setMay(e.getTotal());
        }
        if (e.getId() == 6) {
          monthByUser.setJune(e.getTotal());
        }
        if (e.getId() == 7) {
          monthByUser.setJuly(e.getTotal());
        }
        if (e.getId() == 8) {
          monthByUser.setAugust(e.getTotal());
        }
        if (e.getId() == 9) {
          monthByUser.setSeptember(e.getTotal());
        }
        if (e.getId() == 10) {
          monthByUser.setOctober(e.getTotal());
        }
        if (e.getId() == 11) {
          monthByUser.setNovember(e.getTotal());
        }
        if (e.getId() == 12) {
          monthByUser.setDecember(e.getTotal());
        }
      });
      return monthByUser;
    }
    return MonthByUser.builder()
        .February(0).January(0).March(0)
        .April(0).May(0).June(0).July(0)
        .August(0).September(0).October(0)
        .November(0).December(0).build();
  }

  /***
   * 今日 本周 本月 本季 本年 总  （玩家年龄之和）
   * @return
   */
  @Override
  public CountUser countUser() {
    /** 条件 */
    Criteria criDay =
        Criteria.where("creatTime")
            .andOperator(
                Criteria.where("creatTime").gte(DateUtil.beginOfDay(DateUtil.date())),
                Criteria.where("creatTime").lte(DateUtil.endOfDay(DateUtil.date())));
    Criteria criWeek =
        Criteria.where("creatTime")
            .andOperator(
                Criteria.where("creatTime").gte(DateUtil.beginOfWeek(DateUtil.date())),
                Criteria.where("creatTime").lte(DateUtil.endOfWeek(DateUtil.date())));

    Criteria criMonth =
        Criteria.where("creatTime")
            .andOperator(
                Criteria.where("creatTime").gte(DateUtil.beginOfMonth(DateUtil.date())),
                Criteria.where("creatTime").lte(DateUtil.endOfMonth(DateUtil.date())));
    Criteria criQuarter =
        Criteria.where("creatTime")
            .andOperator(
                Criteria.where("creatTime").gte(DateUtil.beginOfQuarter(DateUtil.date())),
                Criteria.where("creatTime").lte(DateUtil.endOfQuarter(DateUtil.date())));
    Criteria criYear =
        Criteria.where("creatTime")
            .andOperator(
                Criteria.where("creatTime").gte(DateUtil.beginOfYear(DateUtil.date())),
                Criteria.where("creatTime").lte(DateUtil.endOfYear(DateUtil.date())));

    /** 逻辑判断 */
    Cond condDay = ConditionalOperators.when(criDay).thenValueOf("$age").otherwise(0);
    ConditionalOperators.Cond condWeek = ConditionalOperators.when(criWeek).thenValueOf("$age")
        .otherwise(0);
    Cond condMonth = ConditionalOperators.when(criMonth).thenValueOf("$age").otherwise(0);
    Cond condQuarter = ConditionalOperators.when(criQuarter).thenValueOf("$age").otherwise(0);
    Cond condyear = ConditionalOperators.when(criYear).thenValueOf("$age").otherwise(0);

    /** 分组 查询*/
    Aggregation agg =
        Aggregation.newAggregation(
            Aggregation.match(Criteria.where("sex").is("男")),
            Aggregation.group()
                .sum(condDay)
                .as("dayCount")
                .sum(condWeek)
                .as("weekCount")
                .sum(condMonth)
                .as("monthCount")
                .sum(condQuarter)
                .as("quarterCount")
                .sum(condyear)
                .as("yearCount")
                .sum("$age")
                .as("totalCount"),
            Aggregation.skip(0),
            Aggregation.limit(1));
    AggregationResults<CountUser> aggregate =
        oneMongoTemplate.aggregate(agg, "user", CountUser.class);
    if (aggregate != null) {
      if (aggregate.getMappedResults().size() > 0) {
        return aggregate.getMappedResults().get(0);
      }
      return CountUser.builder()
          .dayCount(0)
          .weekCount(0)
          .monthCount(0)
          .quarterCount(0)
          .yearCount(0)
          .totalCount(0)
          .build();
    }
    return CountUser.builder()
        .dayCount(0)
        .weekCount(0)
        .monthCount(0)
        .quarterCount(0)
        .yearCount(0)
        .totalCount(0)
        .build();
  }


}
