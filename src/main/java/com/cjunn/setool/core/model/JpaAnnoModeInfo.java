package com.cjunn.setool.core.model;

import com.cjunn.setool.dao.exception.NoIdException;
import com.cjunn.setool.dao.exception.NoTableException;
import com.cjunn.setool.utils.Pair;
import com.cjunn.setool.utils.Predicates2;
import com.google.common.base.*;
import com.google.common.collect.*;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JpaAnnoModeInfo {
    private static final Logger logger = LoggerFactory.getLogger(JpaAnnoModeInfo.class);
    private String pkColumnName;
    private String pkFieldName;
    private Iterable<String> allColumnNames;
    private Iterable<String> allFieldNames;
    private Iterable<String> allColumnNamesWithoutPk;
    private Iterable<String> allFieldNamesWithoutPk;
    private String tableName;
    private String entityName;
    private Pair<String, String> tableNameEntityNamePair;
    private Pair<String, String> pkColumnNameFieldNamePair;

    public Pair<String, String> getVersionColumnNameFieldNamePair() {
        return versionColumnNameFieldNamePair;
    }



    private Pair<String, String> versionColumnNameFieldNamePair;



    private Iterable<Pair<String, String>> columnNameFieldNamePairs;

    public Iterable<Pair<String, String>> getColumnNameFieldNamePairsWithoutPk() {
        return columnNameFieldNamePairsWithoutPk;
    }



    private Iterable<Pair<String, String>> columnNameFieldNamePairsWithoutPk;
    private ImmutableBiMap<String, String> allColumnNameFieldNameMap;



    private Class<?> modelClazz;
    private final Function<Field, String> fieldToGetterNameTransformer;
    private final Function<Pair<String, String>, String> toColumnNamesTransformer;
    private final Function<Pair<String, String>, String> toFieldNamesTransformer;

    private JpaAnnoModeInfo() {
        this.fieldToGetterNameTransformer = (Field columnUnAnnotatedField)->"get" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, columnUnAnnotatedField.getName());
        this.toColumnNamesTransformer = (Pair<String, String> columnNameFieldNamePair)->(String)columnNameFieldNamePair.getValue0();
        this.toFieldNamesTransformer = (Pair<String, String> columnNameFieldNamePair)->(String)columnNameFieldNamePair.getValue1();
    }
    private JpaAnnoModeInfo(Class<?> modelClazz) {
        this.fieldToGetterNameTransformer = (Field columnUnAnnotatedField)->"get" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, columnUnAnnotatedField.getName());
        this.toColumnNamesTransformer = (Pair<String, String> columnNameFieldNamePair)->(String)columnNameFieldNamePair.getValue0();
        this.toFieldNamesTransformer = (Pair<String, String> columnNameFieldNamePair)->(String)columnNameFieldNamePair.getValue1();
        this.modelClazz = modelClazz;
        this.tableNameEntityNamePair = this.loadTableNameEntityNamePairFromModelClazz(modelClazz);
        this.tableName = this.tableNameEntityNamePair.getValue0();
        this.entityName = this.tableNameEntityNamePair.getValue1();
        this.pkColumnNameFieldNamePair = this.loadPkColumnNameFieldNamePairFromModelClazz(modelClazz);
        this.pkColumnName = this.pkColumnNameFieldNamePair.getValue0();
        this.pkFieldName = this.pkColumnNameFieldNamePair.getValue1();
        this.versionColumnNameFieldNamePair = this.loadVersionColumnNameFieldNamePairFromModelClazz(modelClazz);
        this.columnNameFieldNamePairsWithoutPk = this.loadColumnNameFieldNamePairsWithoutPk(modelClazz);
        this.allColumnNamesWithoutPk = Iterables.transform(this.columnNameFieldNamePairsWithoutPk, this.toColumnNamesTransformer);
        this.allFieldNamesWithoutPk = Iterables.transform(this.columnNameFieldNamePairsWithoutPk, this.toFieldNamesTransformer);
        this.columnNameFieldNamePairs = this.loadColumnNameFieldNamePairs();
        this.allColumnNames = Iterables.transform(this.columnNameFieldNamePairs, this.toColumnNamesTransformer);
        this.allFieldNames = Iterables.transform(this.columnNameFieldNamePairs, this.toFieldNamesTransformer);
        this.allColumnNameFieldNameMap = this.loadAllColumnNameFieldNameMap();
    }
    public static JpaAnnoModeInfo of(Class<?> modelClazz) {
        return new JpaAnnoModeInfo(modelClazz);
    }
    public JpaAnnoModeInfo excludeAnyFields(final Iterable<String> fieldNames) {
        JpaAnnoModeInfo copy = new JpaAnnoModeInfo();
        copy.modelClazz = this.modelClazz;
        copy.tableNameEntityNamePair = this.tableNameEntityNamePair;
        copy.tableName = this.tableName;
        copy.entityName = this.entityName;
        copy.pkColumnNameFieldNamePair = this.pkColumnNameFieldNamePair;
        copy.pkColumnName = this.pkColumnName;
        copy.pkFieldName = this.pkFieldName;
        copy.versionColumnNameFieldNamePair = this.versionColumnNameFieldNamePair;
        copy.columnNameFieldNamePairsWithoutPk = Iterables.filter(this.columnNameFieldNamePairsWithoutPk, input -> {
            String fieldName = input.getValue1();
            return JpaAnnoModeInfo.this.hasVersionField() && fieldName.equals(JpaAnnoModeInfo.this.getVersionFieldName())?true:!Iterables.contains(fieldNames, fieldName);
        });
        copy.allColumnNamesWithoutPk = Iterables.transform(copy.columnNameFieldNamePairsWithoutPk, this.toColumnNamesTransformer);
        copy.allFieldNamesWithoutPk = Iterables.transform(copy.columnNameFieldNamePairsWithoutPk, this.toFieldNamesTransformer);
        copy.columnNameFieldNamePairs = copy.loadColumnNameFieldNamePairs();
        copy.allColumnNames = Iterables.transform(copy.columnNameFieldNamePairs, this.toColumnNamesTransformer);
        copy.allFieldNames = Iterables.transform(copy.columnNameFieldNamePairs, this.toFieldNamesTransformer);
        copy.allColumnNameFieldNameMap = copy.loadAllColumnNameFieldNameMap();
        return copy;
    }

    public String toString() {
        return (new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)).append("pkColumnName", this.pkColumnName).append("pkFieldName", this.pkFieldName).append("allColumnNames", this.allColumnNames).append("allFieldNames", this.allFieldNames).append("allColumnNamesWithoutPk", this.allColumnNamesWithoutPk).append("allFieldNamesWithoutPk", this.allFieldNamesWithoutPk).append("tableName", this.tableName).append("entityName", this.entityName).append("tableNameEntityNamePair", this.tableNameEntityNamePair).append("pkColumnNameFieldNamePair", this.pkColumnNameFieldNamePair).append("columnNameFieldNamePairs", this.columnNameFieldNamePairs).append("columnNameFieldNamePairsWithoutPk", this.columnNameFieldNamePairsWithoutPk).append("allColumnNameFieldNameMap", this.allColumnNameFieldNameMap).append("modelClazz", this.modelClazz).append("fieldToGetterNameTransformer", this.fieldToGetterNameTransformer).append("toColumnNamesTransformer", this.toColumnNamesTransformer).append("toFieldNamesTransformer", this.toFieldNamesTransformer).toString();
    }


    private ImmutableBiMap<String, String> loadAllColumnNameFieldNameMap() {
        com.google.common.collect.ImmutableBiMap.Builder<String, String> allColumnNameFieldNameMapBuilder = ImmutableBiMap.builder();
        Iterator<Pair<String, String>> iterator = this.columnNameFieldNamePairs.iterator();
        while(iterator.hasNext()) {
            Pair<String, String> columnNameFieldNamePair = iterator.next();
            allColumnNameFieldNameMapBuilder.put(Maps.immutableEntry(columnNameFieldNamePair.getValue0(), columnNameFieldNamePair.getValue1()));
        }
        return allColumnNameFieldNameMapBuilder.build();
    }
    private ImmutableList<Pair<String, String>> loadColumnNameFieldNamePairs() {
        ImmutableList.Builder<Pair<String, String>> columnNameFieldNameMap = ImmutableList.builder();
        columnNameFieldNameMap.add(Pair.with(this.pkColumnName, this.pkFieldName));
        columnNameFieldNameMap.addAll(this.columnNameFieldNamePairsWithoutPk);
        return columnNameFieldNameMap.build();
    }
    private Iterable<Field> loadColumnAnnotatedFieldsWithoutPk(Class<?> modelClazz) {
        final ArrayList<Method> methods = Lists.newArrayList(modelClazz.getMethods());
        return ImmutableList.copyOf(Iterables.filter(FieldUtils.getAllFieldsList(modelClazz), Predicates.or(Predicates.and(Predicates.not(Predicates2.isAnnotationPresent(Id.class)), Predicates2.isAnnotationPresent(Column.class)), Predicates.compose(new Predicate<String>() {
            public boolean apply( String getterName) {
                return Iterables.any(methods, Predicates.and(Predicates.and(Predicates.not(Predicates2.isAnnotationPresent(Id.class)), Predicates2.isAnnotationPresent(Column.class)), Predicates.compose(Predicates.equalTo(getterName), new Function<Method, String>() {
                    public String apply(Method modelMethod) {
                        return modelMethod.getName();
                    }
                })));
            }
        }, this.fieldToGetterNameTransformer))));
    }
    private ImmutableList<Pair<String, String>> loadColumnNameFieldNamePairsWithoutPk(Class<?> modelClazz) {
        return ImmutableList.copyOf(Iterables.transform(this.loadColumnAnnotatedFieldsWithoutPk(modelClazz), new Function<Field, Pair<String, String>>() {
            public Pair<String, String> apply(Field columnAnnotatedField) {
                return JpaAnnoModeInfo.this.loadColumnNameFieldNamePair(columnAnnotatedField);
            }
        }));
    }

    private Pair<String, String> loadTableNameEntityNamePairFromModelClazz(Class<?> modelClazz) {
        if (modelClazz.isAnnotationPresent(Table.class)) {
            String tableName = modelClazz.getAnnotation(Table.class).name();
            String entityName = ClassUtils.getShortClassName(modelClazz);
            return Pair.with(tableName, entityName);
        } else {
            throw new NoTableException("Model: "+ClassUtils.getSimpleName(modelClazz)+" 没有未被 javax.persistence.Table 注解， 可能不符合预期。");
        }
    }

    private Pair<String, String> loadPkColumnNameFieldNamePairFromModelClazz(Class<?> modelClazz) {
        List<Field> fieldsList = FieldUtils.getAllFieldsList(modelClazz);
        ArrayList<Method> methods = Lists.newArrayList(modelClazz.getMethods());
        Optional<Field> idFieldOptional = Iterables.tryFind(fieldsList, Predicates.or(Predicates2.isAnnotationPresent(Id.class), this.hisGetterAnnotatedBy(Id.class, methods)));
        if (idFieldOptional.isPresent()) {
            Field idField = idFieldOptional.get();
            return this.loadColumnNameFieldNamePair(idField);
        } else {
            throw new NoIdException("Model: "+ClassUtils.getSimpleName(modelClazz)+" 没有javax.persistence.Id 字段信息");
        }
    }

    private Pair<String, String> loadVersionColumnNameFieldNamePairFromModelClazz(Class<?> modelClazz) {
        List<Field> fieldsList = FieldUtils.getAllFieldsList(modelClazz);
        ArrayList<Method> methods = Lists.newArrayList(modelClazz.getMethods());
        Optional<Field> versionFieldOptional = Iterables.tryFind(fieldsList, Predicates.or(Predicates2.isAnnotationPresent(Version.class), this.hisGetterAnnotatedBy(Version.class, methods)));
        if (versionFieldOptional.isPresent()) {
            Field versionField = versionFieldOptional.get();
            return this.loadColumnNameFieldNamePair(versionField);
        } else {
            return null;
        }
    }

    private Pair<String, String> loadColumnNameFieldNamePair(Field field) {
        String fieldName = field.getName();
        if (field.isAnnotationPresent(Column.class)) {
            String columnName = ((Column)field.getAnnotation(Column.class)).name();
            return Pair.with(columnName, fieldName);
        } else {
            return Pair.with(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, fieldName), fieldName);
        }
    }

    private Predicate<Field> hisGetterAnnotatedBy(final Class<? extends Annotation> annotationClass, final Iterable<Method> methods) {
        return Predicates.compose(new Predicate<String>() {
            public boolean apply(String getterName) {
                return Iterables.any(methods, Predicates.and(Predicates2.isAnnotationPresent(annotationClass), Predicates.compose(Predicates.equalTo(getterName), new Function<Method, String>() {
                    public String apply(Method modelMethod) {
                        return modelMethod.getName();
                    }
                })));
            }
        }, this.fieldToGetterNameTransformer);
    }

    public Pair<String, String> getPkColumnNameFieldNamePair() {
        return this.pkColumnNameFieldNamePair;
    }

    public String getPkColumnName() {
        return this.pkColumnName;
    }

    public String getPkFieldName() {
        return this.pkFieldName;
    }

    public Iterable<String> getAllColumnNames() {
        return this.allColumnNames;
    }

    public Iterable<String> getAllFieldNames() {
        return this.allFieldNames;
    }

    public Iterable<String> getAllColumnNamesWithoutPk() {
        return this.allColumnNamesWithoutPk;
    }

    public Iterable<String> getAllFieldNamesWithoutPk() {
        return this.allFieldNamesWithoutPk;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public String getFieldNameByColumnName(String columnName) {
        return (String)this.allColumnNameFieldNameMap.get(columnName);
    }

    public String getColumnNameByFieldName(String fieldName) {
        return (String)this.allColumnNameFieldNameMap.inverse().get(fieldName);
    }


    public Iterable<Pair<String, String>> getColumnNameFieldNamePairs() {
        return columnNameFieldNamePairs;
    }

    public boolean hasVersionField() {
        return this.versionColumnNameFieldNamePair != null;
    }

    public boolean isVersionColumnName(String columnName) {
        return this.hasVersionField() && StringUtils.equals(columnName, this.getVersionColumnName());
    }

    public boolean isVersionFieldName(String columnName) {
        return this.hasVersionField() && StringUtils.equals(columnName, this.getVersionFieldName());
    }
    public Class<?> getModelClazz() {
        return modelClazz;
    }

    public void setModelClazz(Class<?> modelClazz) {
        this.modelClazz = modelClazz;
    }
    public String getVersionFieldName() {
        return (String)this.versionColumnNameFieldNamePair.getValue1();
    }

    public String getVersionColumnName() {
        return (String)this.versionColumnNameFieldNamePair.getValue0();
    }

    public Optional<Field> getPkFieldOpt() {
        Field pkField = this.getPkField();
        return pkField != null ? Optional.of(pkField) : Optional.absent();
    }

    public Field getPkField() {
        return StringUtils.isNotEmpty(this.pkFieldName) ? FieldUtils.getField(this.modelClazz, this.pkFieldName, true) : null;
    }
}
