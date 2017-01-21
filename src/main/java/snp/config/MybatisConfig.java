package snp.config;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import snp.spring.mybatis.ReconfigurableSqlSessionFactoryBean;

import javax.sql.DataSource;
import java.util.stream.Stream;

/**
 * <br/>개정이력<br/>
 */
@Configuration
@EnableTransactionManagement
@MapperScan(
        //TODO set mapper packages.
        basePackages = {"snp.infra.**.*", "snp.app.**.*", "snp.example.**.*" },
        sqlSessionTemplateRef = "sqlSessionTemplate_default"
)
public class MybatisConfig {

    @Bean(name = "transactionManager")
    PlatformTransactionManager transactionManager(@Qualifier("dataSource_default") final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "dataSource_default")
    @ConfigurationProperties(prefix = "spring.datasource.default")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sqlSessionFactory_default")
    SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource_default") final DataSource dataSource) throws Exception {
        //final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        /**
         * refresh xml mapper factory
         * 2016-11-15 @kirious
         */
        final ReconfigurableSqlSessionFactoryBean sqlSessionFactory = new ReconfigurableSqlSessionFactoryBean();

        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/mapper/mybatis.xml"));
        sqlSessionFactory.setMapperLocations(
                Stream.of(
                        new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/infra/**/*.xml"),
                        new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/app/**/*.xml"),
                        new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/example/**/*.xml")
                ).flatMap(Stream::of).toArray(Resource[]::new)
        );

        SqlSessionFactory object = sqlSessionFactory.getObject();
        return object;
    }

    @Bean(name = "sqlSessionTemplate_default")
    SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory_default") final SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.SIMPLE);
    }
}
