package snp.spring.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;

interface ExcludeSqlSessionFactoryBean {

    public SqlSessionFactory getObject() throws Exception;

    public void afterPropertiesSet() throws Exception;

}
