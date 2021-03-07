package com.cjunn.setool.dao.executor;

import com.cjunn.setool.core.model.BaseModel;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BatchExecutor<T extends BaseModel> {

    int save(Iterable<T> ite);

    int save(Iterable<T> ite, String... args);

    int saveExcludeFirstRecordNullFields(Iterable<T> ite);

    int update(Iterable<T> ite);

    int update(Iterable<T> ite, String... args);

    int updateExcludeFirstRecordNullFields(Iterable<T> ite);
}