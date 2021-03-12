package com.cjunn.setool.monitor.druid;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.*;
import com.alibaba.druid.sql.SQLUtils;
import com.cjunn.setool.aop.handler.TakeTimeLogHandler;
import com.cjunn.setool.core.monitor.IUserMonitorGetter;
import com.cjunn.setool.core.monitor.UserMonitor;
import com.cjunn.setool.core.monitor.UserMonitorFactory;
import com.cjunn.setool.core.monitor.sql.SQLInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MonitorFilter
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/7 21:29
 * @Version
 */
public class ExecuteSqlMonitorFilter extends FilterEventAdapter implements IUserMonitorGetter<SQLInfo> {
    public static final Logger      LOGGER                      =   LoggerFactory.getLogger(ExecuteSqlMonitorFilter.class);
    private UserMonitor<SQLInfo>    monitor                     =   UserMonitorFactory.getUserMonitor(ExecuteSqlMonitorFilter.class);
    private SQLUtils.FormatOption   statementSqlFormatOption    =   new SQLUtils.FormatOption(false, false);
    private String transaId(StatementProxy statement){
        TransactionInfo transactionInfo = statement.getConnectionProxy().getTransactionInfo();
        if(transactionInfo==null){
            return "";
        }
        return String.valueOf(transactionInfo.getId());
    }
    private String conneId(StatementProxy statement){
        return String.valueOf(statement.getConnectionProxy().getId());
    }
    private String stmtId(StatementProxy statement) {
        StringBuffer buf = new StringBuffer();
        if (statement instanceof CallableStatementProxy) {
            buf.append("cstmt-");
        } else if (statement instanceof PreparedStatementProxy) {
            buf.append("pstmt-");
        } else {
            buf.append("stmt-");
        }
        buf.append(statement.getId());
        return buf.toString();
    }
    private String formatSQL(StatementProxy statement,String sql){
        int parametersSize = statement.getParametersSize();
        if(parametersSize==0){
            return sql;
        }
        List<Object> parameters = new ArrayList<Object>(parametersSize);
        for (int i = 0; i < parametersSize; ++i) {
            JdbcParameter jdbcParam = statement.getParameter(i);
            parameters.add(jdbcParam != null
                    ? jdbcParam.getValue()
                    : null);
        }
        String dbType = statement.getConnectionProxy().getDirectDataSource().getDbType();
        String formattedSql = SQLUtils.format(sql, dbType, parameters, this.statementSqlFormatOption);
        return formattedSql;
    }
    private void monitorSQL(StatementProxy statement, String sql) {
        long lastExecuteStartNano = statement.getLastExecuteStartNano();
        statement.setLastExecuteTimeNano();
        long lastExecuteTimeNano = statement.getLastExecuteTimeNano();
        SQLInfo of = SQLInfo.of(
                conneId(statement),
                stmtId(statement),
                transaId(statement),
                formatSQL(statement, sql),
                statement.getLastExecuteType().toString(),
                lastExecuteTimeNano,
                lastExecuteStartNano);
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug(of.toString());
        }
        monitor.addMonitorInfo(of);
    }

    @Override
    public void preparedStatement_addBatch(FilterChain chain, PreparedStatementProxy statement) throws SQLException {
        super.preparedStatement_addBatch(chain, statement);
    }

    @Override
    protected void statementExecuteBefore(StatementProxy statement, String sql) {
        statement.setLastExecuteStartNano();
    }

    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean result) {
        monitorSQL(statement,sql);
    }

    @Override
    protected void statementExecuteBatchBefore(StatementProxy statement) {
        statement.setLastExecuteStartNano();
    }
    @Override
    protected void statementExecuteBatchAfter(StatementProxy statement, int[] result) {
        String sql=statement instanceof PreparedStatementProxy? ((PreparedStatementProxy) statement).getSql():statement.getBatchSql();
        monitorSQL(statement,sql);
    }

    @Override
    protected void statementExecuteQueryBefore(StatementProxy statement, String sql) {
        statement.setLastExecuteStartNano();
    }

    @Override
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
        monitorSQL(statement,sql);
    }

    @Override
    protected void statementExecuteUpdateBefore(StatementProxy statement, String sql) {
        statement.setLastExecuteStartNano();
    }

    @Override
    protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
        monitorSQL(statement,sql);
    }

    @Override
    protected void statement_executeErrorAfter(StatementProxy statement, String sql, Throwable error) {
        monitorSQL(statement,sql);
    }

    @Override
    public UserMonitor<SQLInfo> getUserMonitor() {
        return monitor;
    }
}