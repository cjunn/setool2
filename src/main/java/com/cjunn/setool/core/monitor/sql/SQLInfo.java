package com.cjunn.setool.core.monitor.sql;

/**
 * @ClassName SQLInfo
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/8 11:09
 * @Version
 */
public class SQLInfo {
    private String connectionId;
    private String statementId;
    private String transactionId;
    private String executeSqlText;

    @Override
    public String toString() {
        return "SQLInfo{" +
                "connectionId='" + connectionId + '\'' +
                ", statementId='" + statementId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", executeSqlText='" + executeSqlText + '\'' +
                ", executeType='" + executeType + '\'' +
                ", executeTimeNano=" + executeTimeNano +
                ", executeStartNano=" + executeStartNano +
                '}';
    }

    private String executeType;
    private Long executeTimeNano;
    private Long executeStartNano;



    public static SQLInfo of(String connectionId,
                             String statementId,
                             String transactionId,
                             String executeSqlText,
                             String executeType,
                             Long executeTimeNano,
                             Long executeStartNano){
        return new SQLInfo(connectionId,
                            statementId,
                            transactionId,
                            executeSqlText,
                            executeType,
                            executeTimeNano,
                            executeStartNano);
    }

    public SQLInfo(
            String connectionId,
            String statementId,
            String transactionId,
            String executeSqlText,
            String executeType,
            Long executeTimeNano,
            Long executeStartNano) {
        this.connectionId = connectionId;
        this.statementId = statementId;
        this.transactionId = transactionId;
        this.executeSqlText = executeSqlText;
        this.executeTimeNano = executeTimeNano;
        this.executeStartNano = executeStartNano;
        this.executeType=executeType;
    }

    public String getExecuteType() {
        return executeType;
    }

    public void setExecuteType(String executeType) {
        this.executeType = executeType;
    }


    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getExecuteSqlText() {
        return executeSqlText;
    }

    public void setExecuteSqlText(String executeSqlText) {
        this.executeSqlText = executeSqlText;
    }

    public Long getExecuteTimeNano() {
        return executeTimeNano;
    }

    public void setExecuteTimeNano(Long executeTimeNano) {
        this.executeTimeNano = executeTimeNano;
    }

    public Long getExecuteStartNano() {
        return executeStartNano;
    }

    public void setExecuteStartNano(Long executeStartNano) {
        this.executeStartNano = executeStartNano;
    }
}
