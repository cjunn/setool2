package com.cjunn.setool.dao.mybatis.intercept;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.core.model.JpaAnnoModeInfo;
import com.cjunn.setool.utils.Pair;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

/**
 * @ClassName MysqlSQLGenerator
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/9 9:32
 * @Version
 */
public class MysqlSQLGenerator implements ISQLGenerator {
    private String makeInsertRecordString(final JpaAnnoModeInfo modelInfo, Function<Pair<String,String>,String> placeholderFunc) {
        Iterable<String> placeHolders =
                Iterables.transform(
                        modelInfo.getColumnNameFieldNamePairs(),
                        columnNameFiledName -> modelInfo.hasVersionField() && modelInfo.getVersionColumnName().equals(columnNameFiledName.getValue0()) ?
                                "0" :
                                placeholderFunc.apply(columnNameFiledName));
        return "(" + Joiner.on(',').join(placeHolders) + ")";
    }
    private String makeInsertHead(JpaAnnoModeInfo modelInfo) {
        StringBuilder builder=new StringBuilder();
        builder.append("INSERT INTO ");
        builder.append(modelInfo.getTableName());
        builder.append(" (");
        builder.append(Joiner.on(',').join(modelInfo.getAllColumnNames()));
        builder.append(") VALUES ");
        return builder.toString();
    }
    private String joinSameString(int joinAmount, CharSequence joinString, CharSequence splitter) {
        StringBuilder resultBuilder = new StringBuilder(joinString);
        for(int i = 1; i < joinAmount; ++i) {
            resultBuilder.append(splitter).append(joinString);
        }
        return resultBuilder.toString();
    }

    @Override
    public <T extends BaseModel> String makeQueryByIdSql(JpaAnnoModeInfo modelInfo, Function<Pair<String, String>, String> placeholderFunc) {
        StringBuilder builder=new StringBuilder();
        builder.append("SELECT ");
        builder.append(Joiner.on(',').join(modelInfo.getAllColumnNames()));
        builder.append(" FROM ");
        builder.append(modelInfo.getTableName());
        builder.append(" WHERE ");
        builder.append(modelInfo.getPkColumnName());
        builder.append(" = ");
        builder.append(placeholderFunc.apply(modelInfo.getPkColumnNameFieldNamePair()));
        return builder.toString();
    }

    @Override
    public <T extends BaseModel> String makeDeleteByIdSql(JpaAnnoModeInfo modelInfo, Function<Pair<String, String>, String> placeholderFunc) {
        //DELETE FROM 表名称 WHERE 列名称 = 值
        StringBuilder builder=new StringBuilder();
        builder.append("DELETE FROM ");
        builder.append(modelInfo.getTableName());
        builder.append(" WHERE ");
        builder.append(modelInfo.getPkColumnName());
        builder.append(" = ");
        builder.append(placeholderFunc.apply(modelInfo.getPkColumnNameFieldNamePair()));
        return builder.toString();
    }

    @Override
    public <T extends BaseModel> String makeSaveSql(int size, JpaAnnoModeInfo modelInfo,Function<Pair<String,String>,String> placeholderFunc) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(this.makeInsertHead(modelInfo));
        String insertRecordString = this.makeInsertRecordString(modelInfo,placeholderFunc);
        String insertBody = joinSameString(size, insertRecordString, ",");
        sqlBuilder.append(insertBody);
        return sqlBuilder.toString();
    }

    @Override
    public <T extends BaseModel> String makeUpdateSql(JpaAnnoModeInfo modelInfo, Function<Pair<String, String>, String> placeholderFunc) {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE ");
        sqlBuilder.append(modelInfo.getTableName()).append(" SET ");
        Iterable<String> tokens = Iterables.transform(
                modelInfo.getColumnNameFieldNamePairsWithoutPk(),
                columnNameFiledName ->
                        modelInfo.isVersionColumnName(columnNameFiledName.getValue0()) ?
                                modelInfo.getVersionColumnName() + " = " + modelInfo.getVersionColumnName() + " + 1" :
                                columnNameFiledName.getValue0().concat("=").concat(placeholderFunc.apply(columnNameFiledName))
        );
        Joiner.on(',').appendTo(sqlBuilder, tokens);
        sqlBuilder.append(" WHERE ").append(modelInfo.getPkColumnName()).append(" = "+ placeholderFunc.apply(modelInfo.getPkColumnNameFieldNamePair()));
        if (modelInfo.hasVersionField()) {
            sqlBuilder.append(" and ").append(modelInfo.getVersionColumnName()).append(" = " +placeholderFunc.apply(modelInfo.getVersionColumnNameFieldNamePair()));
        }
        return sqlBuilder.toString();
    }
}
