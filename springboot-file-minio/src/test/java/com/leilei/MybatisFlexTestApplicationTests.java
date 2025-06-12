package com.leilei;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.leilei.entity.table.AccountTableDef.ACCOUNT;


@SpringBootTest
@RunWith(SpringRunner.class)
class MybatisFlexTestApplicationTests {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    void contextLoads() {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(ACCOUNT.ALL_COLUMNS)
                .where(ACCOUNT.ID.le(18).and(ACCOUNT.USER_NAME.eq("leilei").or(ACCOUNT.AGE.eq(18))));
        Account account = accountMapper.selectOneByQuery(queryWrapper);
        System.out.println(account);
    }

    @Test
    void page() {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select();
        Page<Account> paginate = accountMapper.paginate(1, 2, queryWrapper);
        System.out.println(paginate);
    }

    @Test
    void insert() {
        Account account = new Account();
        account.setUserName("leilei");
        account.setAge(18);
        accountMapper.insert(account);
        System.out.println(account.getId());
    }

}