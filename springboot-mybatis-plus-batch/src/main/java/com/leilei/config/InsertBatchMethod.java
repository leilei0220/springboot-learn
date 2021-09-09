package com.leilei.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
/**
 * @author lei
 * @create 2021-09-09 10:53
 * @desc 批量插入方法实现
 **/

@Log4j2
public class InsertBatchMethod extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        final String sql = "<script>insert into %s %s values %s</script>";
        final String fieldSql = prepareFieldSql(tableInfo);
        final String valueSql = prepareValuesSql(tableInfo);
        final String sqlResult = String.format(sql, tableInfo.getTableName(), fieldSql, valueSql);
        log.debug("batch-insert-sql:----->{}", sqlResult);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlResult, modelClass);
        // id 需和自定义Base接口中对应批量新增方法名一致
        return this.addInsertMappedStatement(mapperClass, modelClass, "insertBatch", sqlSource, new NoKeyGenerator(), null, null);
    }

    private String prepareFieldSql(TableInfo tableInfo) {
        StringBuilder fieldSql = new StringBuilder();
        fieldSql.append(tableInfo.getKeyColumn()).append(",");
        tableInfo.getFieldList().forEach(x -> fieldSql.append(x.getColumn()).append(","));
        fieldSql.delete(fieldSql.length() - 1, fieldSql.length());
        fieldSql.insert(0, "(");
        fieldSql.append(")");
        return fieldSql.toString();
    }

    /**
     * 进行批量SQL拼接
     * @param tableInfo
     * @return
     */
    private String prepareValuesSql(TableInfo tableInfo) {
        final StringBuilder valueSql = new StringBuilder();
        valueSql.append("<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" separator=\"),(\" close=\")\">");
        valueSql.append("#{item.").append(tableInfo.getKeyProperty()).append("},");
        tableInfo.getFieldList().forEach(x -> valueSql.append("#{item.").append(x.getProperty()).append("},"));
        valueSql.delete(valueSql.length() - 1, valueSql.length());
        valueSql.append("</foreach>");
        return valueSql.toString();
    }
}