package com.cjunn.setool.dao.fluent.builder.condition;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * @ClassName ConditionType
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 9:51
 * @Version
 */
public enum QlConditionType {
    EQ("=", ""),
    NE("<>", ""),
    LT("<", ""),
    LOE("<=", ""),
    GT(">", ""),
    GOE(">=", ""),
    IS_NULL("IS NULL", ""),
    IS_NOT_NULL("IS NOT NULL", ""),
    BETWEEN("BETWEEN", "") {
        public String join(String leftExpr, List<String> parameterPlaceHolder) {
            Validate.notEmpty(parameterPlaceHolder, "BETWEEN语句参数不能为空", new Object[0]);
            Validate.isTrue(parameterPlaceHolder.size() == 2, "BETWEEN必须且只能有两个参数, holders: %s", new Object[]{parameterPlaceHolder});
            return leftExpr + " BETWEEN " + (String)parameterPlaceHolder.get(0) + " AND " + (String)parameterPlaceHolder.get(1);
        }
    },
    IN("IN(", ")"),
    NOT_IN("NOT IN(", ")"),
    LIKE("LIKE", "");

    private String operatorPrefix;
    private String operatorSuffix;
    private QlConditionType(String operatorPrefix, String operatorSuffix) {
        this.operatorPrefix = operatorPrefix;
        this.operatorSuffix = operatorSuffix;
    }
    public String join(String leftExpr, List<String> parameterPlaceHolder) {
        return parameterPlaceHolder.isEmpty()?leftExpr + " " + this.operatorPrefix + this.operatorSuffix:leftExpr + " " + this.operatorPrefix + " " + Joiner.on(',').join(parameterPlaceHolder) + this.operatorSuffix;
    }
}
