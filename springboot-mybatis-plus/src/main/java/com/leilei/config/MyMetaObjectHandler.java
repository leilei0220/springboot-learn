package com.leilei.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", Long.class, System.currentTimeMillis()); // 起始版本 3.3.0(推荐使用)NAPSHOT`)
        this.strictInsertFill(metaObject, "createId", Integer.class, 1111); // 起始版本 3.3.0(推荐使用)NAPSHOT`)
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "modifyTime", Long.class, System.currentTimeMillis()); // 起始版本 3.3.0(推荐使用)
        this.strictUpdateFill(metaObject, "modifyId", Integer.class, 2222); // 起始版本 3.3.0(推荐使用)

    }
}