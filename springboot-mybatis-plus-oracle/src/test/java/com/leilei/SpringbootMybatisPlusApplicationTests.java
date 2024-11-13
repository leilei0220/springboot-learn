package com.leilei;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leilei.entity.Permission;
import com.leilei.entity.RealEseate;
import com.leilei.entity.Role;
import com.leilei.service.IPermissionService;
import com.leilei.service.IRealEseateService;
import com.leilei.service.IRoleService;
import com.leilei.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class SpringbootMybatisPlusApplicationTests {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @Autowired
    private IRealEseateService realEseateService;

    @Autowired
    private IPermissionService permissionService;


    @Test
    void contextLoads() {
        userService.list().forEach(e -> System.out.println(e));
        roleService.list().forEach(e -> System.out.println(e));
    }

    /**
     * 单表测试分页
     */
    @Test
    public void testPage() {
        Page<RealEseate> realEseatePage = realEseateService.page(new Page<RealEseate>(1, 5), new QueryWrapper<RealEseate>().orderByDesc("build_time"));
        realEseatePage.getRecords().forEach(e -> System.out.println(e));
        System.out.println(realEseatePage.getTotal());

    }

    /**
     * 连表查询分页
     */
    @Test
    public void tesJoinTablePage() {
        IPage iPage = realEseateService.selectJoinTablePage(1, 5, null);
        iPage.getRecords().forEach(e -> System.out.println(e));
    }

    /**
     * 测试Mybatis-plus QueryWrapper 查询构造
     */
    @Test
    public void testWrapper() {
        /**
         * 比较 大于小于 等于 between 排序.....
         */
        //SELECT id,permission_name,permission_sn FROM permission WHERE (permission_name = ?)
        System.out.println(permissionService.getOne(new QueryWrapper<Permission>().eq("permission_name", "支付管理")));

        //SELECT id,permission_name,permission_sn FROM permission WHERE (id > ? AND id < ? AND permission_sn = ?)
        System.out.println(permissionService.getOne(new QueryWrapper<Permission>()
                .gt("id", 1)
                .lt("id", 3)
                .eq("permission_sn", "pro")));

        //SELECT id,project_name,address,house_type,area,build_time,user_id FROM real_eseate ORDER BY build_time DESC
        realEseateService.list(new QueryWrapper<RealEseate>().orderByDesc("build_time")).forEach(e -> System.out.println(e));

        //SELECT id,project_name,address,house_type,area,build_time,user_id FROM real_eseate WHERE (id BETWEEN ? AND ?) LIMIT ?,?
        Page<RealEseate> realEseatePage = realEseateService.page(new Page<RealEseate>(1, 3),
                                                                 new QueryWrapper<RealEseate>().between("id", 1, 5));

    }

    /**
     * 批量操作
     */
    @Test
    public void testBatch() {
        /**
         * 新增
         */
        //==>  Preparing: INSERT INTO role ( role_name ) VALUES ( ? )
        //==> Parameters: 驱蚊器翁(String)
        //==> Parameters: 俺问问去(String)
        roleService.saveBatch(Arrays.asList(new Role("驱蚊器翁"), new Role("俺问问去")));
        /**
         *批量修改或者插入  有Id 则修改 无则新增  可传Wrapper
         */
        roleService.saveOrUpdateBatch(Arrays.asList(new Role(3L, "璀璨钻石"), new Role(4L, "尊贵铂金")));
    }

}