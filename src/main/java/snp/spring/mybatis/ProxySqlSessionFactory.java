package snp.spring.mybatis;

import org.apache.ibatis.session.*;

import java.sql.Connection;

public class ProxySqlSessionFactory implements SqlSessionFactory {

    transient private SqlSessionFactory delegate;

    public SqlSession openSession() {
        return delegate.openSession();
    }

    public SqlSession openSession(boolean autoCommit) {
        return delegate.openSession(autoCommit);
    }

    public SqlSession openSession(Connection connection) {
        return delegate.openSession(connection);
    }

    public SqlSession openSession(TransactionIsolationLevel level) {
        return delegate.openSession(level);
    }

    public SqlSession openSession(ExecutorType execType) {
        return delegate.openSession(execType);
    }

    public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
        return delegate.openSession(execType, autoCommit);
    }

    public SqlSession openSession(ExecutorType execType,
                                  TransactionIsolationLevel level) {
        return delegate.openSession(execType, level);
    }

    public SqlSession openSession(ExecutorType execType, Connection connection) {
        return delegate.openSession(execType, connection);
    }

    public Configuration getConfiguration() {
        return delegate.getConfiguration();
    }

    synchronized public void setDelegate(SqlSessionFactory delegate) {
        this.delegate = delegate;
    }

}