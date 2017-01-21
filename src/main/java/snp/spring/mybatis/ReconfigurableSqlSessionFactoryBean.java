package snp.spring.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import snp.util.LoggerHelper;

import java.util.concurrent.locks.ReentrantLock;

public class ReconfigurableSqlSessionFactoryBean extends SqlSessionFactoryBean implements ConfigurationModifyListener {
    private static final Logger L = LoggerHelper.getLogger();

    private final ReentrantLock lock = new ReentrantLock();


    private final ProxySqlSessionFactory proxySqlSessionFactory = new ProxySqlSessionFactory();

    private final ConfigurationModifyObserver configurationModifyObserver = new ConfigurationModifyObserverTimerTask();


    public ReconfigurableSqlSessionFactoryBean() {
        super();
        configurationModifyObserver.addModifyListener(this);
    }

    /**
     * {@inheritDoc}
     */
    public SqlSessionFactory getObject() throws Exception {
        proxySqlSessionFactory.setDelegate(super.getObject());
        return proxySqlSessionFactory;
    }

    public void destroy() throws Exception {
        configurationModifyObserver.stop();
    }

    public void onConfigurationModify() {
        if (L.isInfoEnabled()) {
            L.info("Refreshing SqlSessionFactory.");
        }
        lock.lock();
        try {
            SqlSessionFactory sqlSessionFactory = super.buildSqlSessionFactory();
            proxySqlSessionFactory.setDelegate(sqlSessionFactory);
            configurationModifyObserver.setConfiguration(sqlSessionFactory.getConfiguration());
        } catch (Exception e) {
            if (L.isErrorEnabled()) {
                L.error("Error Occured while Refreshing SqlSessionFactory.", e);
            }
        } finally {
            lock.unlock();
        }
    }

    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        SqlSessionFactory sqlSessionFactory = super.getObject();
        proxySqlSessionFactory.setDelegate(sqlSessionFactory);
        configurationModifyObserver.setConfiguration(sqlSessionFactory.getConfiguration());
        configurationModifyObserver.start();
    }

}