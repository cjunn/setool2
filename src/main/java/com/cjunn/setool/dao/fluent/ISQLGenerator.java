package com.cjunn.setool.dao.fluent;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.core.model.JpaAnnoModeInfo;
import com.cjunn.setool.utils.Pair;
import com.google.common.base.Function;

/**
 * @ClassName ISQLGenerator
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/9 9:31
 * @Version
 */
public interface ISQLGenerator {
    <T extends BaseModel> String makeQueryByIdSql(JpaAnnoModeInfo modelInfo, Function<Pair<String,String>,String> placeholderFunc);
    <T extends BaseModel> String makeDeleteByIdSql(JpaAnnoModeInfo modelInfo, Function<Pair<String,String>,String> placeholderFunc);
    <T extends BaseModel> String makeSaveSql(int size, JpaAnnoModeInfo modelInfo, Function<Pair<String,String>,String> placeholderFunc);
    <T extends BaseModel> String makeUpdateSql(JpaAnnoModeInfo modelInfo, Function<Pair<String,String>,String> placeholderFunc);
}
