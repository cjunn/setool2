package com.cjunn.setool.monitor.druid;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;

/**
 * @ClassName MonitorFilter
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/7 21:29
 * @Version
 */
public class ExecuteSqlMonitorFilter extends FilterEventAdapter {
    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean result) {
        super.statementExecuteAfter(statement, sql, result);
    }

    @Override
    protected void statementExecuteBatchAfter(StatementProxy statement, int[] result) {
        super.statementExecuteBatchAfter(statement, result);
    }
    @Override
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
        super.statementExecuteQueryAfter(statement, sql, resultSet);
    }

    @Override
    protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
        super.statementExecuteUpdateAfter(statement, sql, updateCount);
    }

    @Override
    protected void statement_executeErrorAfter(StatementProxy statement, String sql, Throwable error) {
        super.statement_executeErrorAfter(statement, sql, error);
    }
}