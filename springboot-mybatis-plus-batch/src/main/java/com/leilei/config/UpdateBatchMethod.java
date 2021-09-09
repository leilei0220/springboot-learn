package com.leilei.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;


/**
 * @author lei
 * @create 2021-09-09 10:53
 * @desc 批量更新方法实现，条件为主键，选择性更新
 **/
@Log4j2
public class UpdateBatchMethod extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        // 进行批量SQL拼接
        String sql = "<script>\n<foreach collection=\"list\" item=\"item\" separator=\";\">\nupdate %s %s where %s=#{%s} %s\n</foreach>\n</script>";
        String additional = tableInfo.isWithVersion() ? tableInfo.getVersionFieldInfo().getVersionOli("item", "item.") : ""
                + tableInfo.getLogicDeleteSql(true, true);
        String setSql = sqlSet(tableInfo.isLogicDelete(), false, tableInfo, false, "item", "item.");
        String sqlResult = String.format(sql, tableInfo.getTableName(), setSql, tableInfo.getKeyColumn(), "item." + tableInfo.getKeyProperty(), additional);
        log.debug("batch-update-sql:----->{}", sqlResult);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlResult, modelClass);
        // id 需和自定义Base接口中对应批量修改方法名一致
        return this.addUpdateMappedStatement(mapperClass, modelClass, "updateBatch", sqlSource);
    }
}