package com.cjunn.setool.dao.executor.mysql;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.core.model.HandlerType;
import com.cjunn.setool.core.model.JpaAnnoModeInfo;
import com.cjunn.setool.dao.IdMaker;
import com.cjunn.setool.dao.exception.OptimisticLockingException;
import com.cjunn.setool.dao.executor.AbstractBatchExecutor;
import com.cjunn.setool.utils.Predicates2;
import com.cjunn.setool.utils.Reflections;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MySqlBatchExecutor<T extends BaseModel> extends AbstractBatchExecutor<T> {
    public static final Logger LOGGER = LoggerFactory.getLogger(MySqlBatchExecutor.class);
    public static final int BATCH_LIMIT = 1000;

    @Override
    public int save(Iterable<T> source) {
        doFieldHandler(source, HandlerType.INSERT);
        return MySqlBatchExecutor.ExecuteAction.SAVE.invoke(MySqlBatchExecutor.ExecuteParams.of(this, source));
    }

    @Override
    public int save(Iterable<T> source, String... excludeFieldNames) {
        doFieldHandler(source, HandlerType.INSERT);
        return this.execute(MySqlBatchExecutor.ExecuteAction.SAVE, source, excludeFieldNames);
    }

    @Override
    public int saveExcludeFirstRecordNullFields(Iterable<T> source) {
        doFieldHandler(source, HandlerType.INSERT);
        return this.executeExcludeFirstRecordNullFields(MySqlBatchExecutor.ExecuteAction.SAVE, source);
    }

    @Override
    public int update(Iterable<T> source) {
        doFieldHandler(source, HandlerType.UPDATE);
        return MySqlBatchExecutor.ExecuteAction.UPDATE.invoke(MySqlBatchExecutor.ExecuteParams.of(this, source));
    }

    @Override
    public int update(Iterable<T> source, String... excludeFieldNames) {
        doFieldHandler(source, HandlerType.UPDATE);
        return this.execute(MySqlBatchExecutor.ExecuteAction.UPDATE, source, excludeFieldNames);
    }

    @Override
    public int updateExcludeFirstRecordNullFields(Iterable<T> source) {
        doFieldHandler(source, HandlerType.UPDATE);
        return this.executeExcludeFirstRecordNullFields(MySqlBatchExecutor.ExecuteAction.UPDATE, source);
    }


    private int execute(MySqlBatchExecutor.ExecuteAction action, Iterable<T> source, String[] excludeFieldNames) {
        JpaAnnoModeInfo jpaAnnoModeInfo = this.getModelInfo().excludeAnyFields(Lists.newArrayList(excludeFieldNames));
        LOGGER.debug("execute {}, exclude fields {}", action, excludeFieldNames);
        return action.invoke(MySqlBatchExecutor.ExecuteParams.of(this, source, jpaAnnoModeInfo));
    }

    private int executeExcludeFirstRecordNullFields(MySqlBatchExecutor.ExecuteAction action, Iterable<T> source) {
        T firstRecord = (T) Iterables.getFirst(source, (Object)null);
        if (firstRecord != null) {
            Iterable<String> modelNullFieldNames = this.getModelNullFieldNames(firstRecord);
            LOGGER.debug("execute {}, exclude fields {}, those fields is null in first record.", action, modelNullFieldNames);
            JpaAnnoModeInfo excludeNullFieldModelInfo = this.getModelInfo().excludeAnyFields(modelNullFieldNames);
            return action.invoke(MySqlBatchExecutor.ExecuteParams.of(this, source, excludeNullFieldModelInfo));
        } else {
            return 0;
        }
    }

    private Iterable<String> getModelNullFieldNames(final T record) {
        return Iterables.filter(this.getModelInfo().getAllFieldNames(), fieldName -> Reflections.getFieldValue(record, fieldName) == null);
    }

    private static <T extends BaseModel> void plusVersionIfRequired(MySqlBatchExecutor.ExecuteParams<T> executeParams, MySqlBatchExecutor.ExecuteAction action) {
        JpaAnnoModeInfo modelInfo = executeParams.modelInfo;
        Iterable<T> source = executeParams.source;
        if (modelInfo.hasVersionField()) {
            Iterator<T> iterator = source.iterator();
            while(iterator.hasNext()) {
                T record =iterator.next();
                if (action == MySqlBatchExecutor.ExecuteAction.SAVE) {
                    Reflections.setFieldValue(record, modelInfo.getVersionFieldName(), 0L);
                } else if (action == MySqlBatchExecutor.ExecuteAction.UPDATE) {
                    Long version = (Long)Reflections.getFieldValue(record, modelInfo.getVersionFieldName());
                    Reflections.setFieldValue(record, modelInfo.getVersionFieldName(), version + 1L);
                }
            }
        }

    }

    private  enum ExecuteAction {
        SAVE {
            <T extends BaseModel> String makeSql(Iterable<T> records, JpaAnnoModeInfo modelInfo) {
                MySqlBatchExecutor.LOGGER.debug("invoke make save sql.");
                int recordCount = Iterables.size(records);
                StringBuilder sqlBuilder = new StringBuilder();
                String insertHead = this.makeInsertHead(modelInfo);
                sqlBuilder.append(insertHead);
                String insertRecordString = this.makeInsertRecordString(modelInfo);
                String insertBody = joinSameString(recordCount, insertRecordString, ",");
                sqlBuilder.append(insertBody);
                return sqlBuilder.toString();
            }
        },
        SAVE_OR_UPDATE {
            <T extends BaseModel> String makeSql(Iterable<T> records, JpaAnnoModeInfo modelInfo) {
                String updateBody = this.makeUpdateBody(modelInfo.getAllColumnNamesWithoutPk());
                return SAVE.makeSql(records, modelInfo) + updateBody;
            }
        },
        UPDATE {
            <T extends BaseModel> String makeSql(Iterable<T> records, final JpaAnnoModeInfo modelInfo) {
                StringBuilder sqlBuilder = new StringBuilder("UPDATE ");
                sqlBuilder.append(modelInfo.getTableName()).append(" SET ");
                Iterable<String> tokens = Iterables.transform(
                        modelInfo.getAllColumnNamesWithoutPk(),
                        colName -> modelInfo.isVersionColumnName(colName) ? modelInfo.getVersionColumnName() + "=" + modelInfo.getVersionColumnName() + "+1" : colName.concat("=?")
                );
                Joiner.on(',').appendTo(sqlBuilder, tokens);
                sqlBuilder.append(" WHERE ").append(modelInfo.getPkColumnName()).append(" = ?");
                if (modelInfo.hasVersionField()) {
                    sqlBuilder.append(" and ").append(modelInfo.getVersionColumnName()).append(" = ?");
                }

                return sqlBuilder.toString();
            }

            public <T extends BaseModel> int invoke(MySqlBatchExecutor.ExecuteParams<T> executeParams) {
                if (Iterables.isEmpty(executeParams.getModelInfo().getAllFieldNames())) {
                    return 0;
                } else {
                    Iterable<T> source = executeParams.source;
                    final JpaAnnoModeInfo modelInfo = executeParams.modelInfo;
                    this.checkNoneNullVersionWhenRequired(executeParams, source);
                    String sql = this.makeSql(source, modelInfo);
                    Iterable<Object[]> argArys = Iterables.transform(source, record -> {
                        Iterable<String> setFieldNames = ExecuteAction.getAllFieldNameWithoutPkAndVersion(modelInfo);
                        Iterable<Object> setFieldValues = Iterables.transform(
                                setFieldNames,
                                fieldName -> fieldName != null ? Reflections.getFieldValue(record, fieldName) : null
                        );
                        List<Object> args = Lists.newArrayList(setFieldValues);
                        Object pkValue = Reflections.getFieldValue(record, modelInfo.getPkFieldName());
                        args.add(pkValue);
                        if (modelInfo.hasVersionField()) {
                            args.add(Reflections.getFieldValue(record, modelInfo.getVersionFieldName()));
                        }
                        return Iterables.toArray(args, Object.class);
                    });
                    ArrayList<Object[]> batchArgs = Lists.newArrayList(argArys);
                    if(LOGGER.isDebugEnabled()){
                        //TODO
                    }
                    int[] ints = executeParams.jdbcTemplate.batchUpdate(sql, batchArgs);
                    int updatedCount = 0;
                    for(int i = 0; i < ints.length; ++i) {
                        int count = ints[i];
                        updatedCount += count;
                    }
                    if (updatedCount != Iterables.size(source)) {
                        throw new OptimisticLockingException();
                    } else {
                        MySqlBatchExecutor.plusVersionIfRequired(executeParams, this);
                        return updatedCount;
                    }
                }
            }

            private <T extends BaseModel> void checkNoneNullVersionWhenRequired(MySqlBatchExecutor.ExecuteParams<T> executeParams, Iterable<T> source) {
                if (executeParams.modelInfo.hasVersionField()) {
                    Predicate<Object> versionIsNull = Predicates2.propertyPredicate(executeParams.modelInfo.getVersionFieldName(), Predicates.isNull());
                    if (Iterables.any(source, versionIsNull)) {
                        throw new OptimisticLockingException("未找到对应的乐观锁版本数据，无法完成数据更新。");
                    }
                }

            }
        };

        private ExecuteAction() {
        }

        abstract <T extends BaseModel> String makeSql(Iterable<T> ite, JpaAnnoModeInfo jpaAnnoModeInfo);

        protected String makeInsertHead(JpaAnnoModeInfo modelInfo) {
            return "INSERT INTO " + modelInfo.getTableName() + " (" + Joiner.on(',').join(modelInfo.getAllColumnNames()) + ") VALUES ";
        }

        public <T extends BaseModel> int invoke(MySqlBatchExecutor.ExecuteParams<T> executeParams) {
            if (Iterables.isEmpty(executeParams.getModelInfo().getAllFieldNames())) {
                return 0;
            } else {
                int rsCount = 0;
                int prevBatchCount = 0;
                String sql = null;
                Iterable<T> source = executeParams.source;
                JpaAnnoModeInfo modelInfo = executeParams.modelInfo;
                Iterable<List<T>> pages = Iterables.partition(source, BATCH_LIMIT);
                Function<T, Iterable<Object>> recordToUpdateArgsTransformer = this.recordToUpdateArgsTransformer(executeParams);
                Iterator<List<T>> iterator = pages.iterator();

                while(iterator.hasNext()) {
                    List<T> page = iterator.next();
                    if (MySqlBatchExecutor.LOGGER.isDebugEnabled()) {
                        MySqlBatchExecutor.LOGGER.debug("executing batch, this page size is {}", page.size());
                    }

                    if (page.size() != prevBatchCount) {
                        sql = this.makeSql(page, modelInfo);
                        prevBatchCount = page.size();
                    }

                    Object[] updateArgArray = this.transformSourceToUpdateArgArray(page, recordToUpdateArgsTransformer);
                    rsCount += executeParams.jdbcTemplate.update(sql, updateArgArray);
                    MySqlBatchExecutor.plusVersionIfRequired(executeParams, this);
                }

                return rsCount;
            }
        }

        private <T extends BaseModel> Function<T, Iterable<Object>> recordToUpdateArgsTransformer(final MySqlBatchExecutor.ExecuteParams executeParams) {
            return record -> {
                if (record == null) {
                    return null;
                } else {
                    List<Object> recordUpdateArgs = Lists.newArrayList();
                    JpaAnnoModeInfo modelInfo = executeParams.modelInfo;
                    Object pkValue = Reflections.getFieldValue(record, modelInfo.getPkFieldName());
                    if (pkValue == null) {
                        Object id = IdMaker.get(executeParams.modelClass);
                        Reflections.setFieldValue(record,modelInfo.getPkFieldName(),id);
                        recordUpdateArgs.add(id);
                    } else {
                        recordUpdateArgs.add(pkValue);
                    }
                    Iterable<String> otherFields = ExecuteAction.getAllFieldNameWithoutPkAndVersion(modelInfo);
                    Iterable<Object> otherFieldValues = Iterables.transform(otherFields, fieldName -> fieldName != null ? Reflections.getFieldValue(record, fieldName) : null);
                    Iterables.addAll(recordUpdateArgs, otherFieldValues);
                    return recordUpdateArgs;
                }
            };
        }

        private <T extends BaseModel> Object[] transformSourceToUpdateArgArray(Iterable<T> source, Function<T, Iterable<Object>> recordToUpdateArgsTransformer) {
            return Iterables.toArray(Iterables.concat(Iterables.transform(source, recordToUpdateArgsTransformer)), Object.class);
        }

        protected String makeInsertRecordString(final JpaAnnoModeInfo modelInfo) {
            Iterable<String> placeHolders = Iterables.transform(
                    modelInfo.getAllColumnNames(),
                    columnName -> modelInfo.hasVersionField() && modelInfo.getVersionColumnName().equals(columnName) ? "0" : "?"
            );
            return "(" + Joiner.on(',').join(placeHolders) + ")";
        }

        protected String makeUpdateBody(Iterable<String> allColumnNamesWithoutPk) {
            StringBuilder updateBody = new StringBuilder(" ON DUPLICATE KEY UPDATE ");
            Iterable<String> updateFieldTokens = Iterables.transform(
                    allColumnNamesWithoutPk,
                    columnName -> columnName.concat("=VALUES(").concat(columnName).concat(")")
            );
            Joiner.on(',').appendTo(updateBody, updateFieldTokens);
            return updateBody.toString();
        }

        protected static String joinSameString(int joinAmount, CharSequence joinString, CharSequence splitter) {
            StringBuilder resultBuilder = new StringBuilder(joinString);
            for(int i = 1; i < joinAmount; ++i) {
                resultBuilder.append(splitter).append(joinString);
            }
            return resultBuilder.toString();
        }

        private static Iterable<String> getAllFieldNameWithoutPkAndVersion(JpaAnnoModeInfo modelInfo) {
            Iterable<String> allFieldNamesWithoutPk = modelInfo.getAllFieldNamesWithoutPk();
            return modelInfo.hasVersionField()
                 ? Iterables.filter(
                    allFieldNamesWithoutPk,
                    Predicates.not(Predicates.equalTo(modelInfo.getVersionFieldName())))
                 : allFieldNamesWithoutPk;
        }
    }

    protected static class ExecuteParams<T extends BaseModel> {
        private final Iterable<T> source;
        private final Class<T> modelClass;
        private final JdbcTemplate jdbcTemplate;
        private JpaAnnoModeInfo modelInfo;

        ExecuteParams(AbstractBatchExecutor<T> executor, Iterable<T> source) {
            this.modelInfo = executor.getModelInfo();
            this.modelClass = executor.getModelClz();
            this.jdbcTemplate = executor.getJdbcTemplate();
            this.source = source;
        }

        ExecuteParams(AbstractBatchExecutor<T> executor, Iterable<T> source, JpaAnnoModeInfo modelInfo) {
            this(executor, source);
            this.modelInfo = modelInfo;
        }

        JpaAnnoModeInfo getModelInfo() {
            return this.modelInfo;
        }

        public static <T extends BaseModel> MySqlBatchExecutor.ExecuteParams<T> of(AbstractBatchExecutor<T> executor, Iterable<T> source) {
            return new MySqlBatchExecutor.ExecuteParams(executor, source);
        }

        public static <T extends BaseModel> MySqlBatchExecutor.ExecuteParams<T> of(AbstractBatchExecutor<T> executor, Iterable<T> source, JpaAnnoModeInfo modelInfo) {
            return new MySqlBatchExecutor.ExecuteParams(executor, source, modelInfo);
        }
    }
}
