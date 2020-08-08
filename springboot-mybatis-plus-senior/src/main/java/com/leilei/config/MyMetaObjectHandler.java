package com.leilei.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author lei
 * @version 1.0
 * @date 2020/8/8 11:24
 * @desc
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //判断是否有set属性
        if (metaObject.hasSetter("createId")) {
            //判断是否有值 如果没设置值 则为null
            Object createId = getFieldValByName("createId", metaObject);
            if (createId == null) {
                log.info("创建人需要维护 且值为空 需要填充 ");
                //开发中 创建者 修改者ID 通过Aop 或者权限框架获取当前操作用户 ，用用户ID进行填充即可
                this.strictInsertFill(metaObject, "createId", Long.class, 666L);

            }
        }
        if (metaObject.hasSetter("createTime")) {
            Object createTime = getFieldValByName("createTime", metaObject);
            if (createTime == null) {
                log.info("创建时间需要维护 且值为空 需要填充 ");
                this.strictInsertFill(metaObject, "createTime", Long.class, System.currentTimeMillis());
            }
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter("modifyId")) {
            Object modifyId = getFieldValByName("modifyId", metaObject);
            if (modifyId == null) {
                log.info("修改人需要维护 且值为空 需要填充 ");
                this.strictUpdateFill(metaObject, "modifyId", Long.class, 666L);
            }
        }
        if (metaObject.hasSetter("modifyTime")) {
            Object modifyTime = getFieldValByName("modifyTime", metaObject);
            if (modifyTime == null) {
                log.info("修改时间需要维护 且值为空 需要填充 ");
                this.strictUpdateFill(metaObject, "modifyTime", Long.class, System.currentTimeMillis());
            }
        }

    }
}