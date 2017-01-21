package snp.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.Assert;

import java.util.Collections;

/**
 * TODO 설명 작성
 *
 * <br/>개정이력<br/>
 * 2016-11-22 kirious@xenoimpact.com # datasource 관련 설정 추가<br/>- mybatis parameter null일때 설정 추가<br/>- transaction manager의 모든 exception에서 rollback 처리하도록 설정<br/>
 * 2016-11-24 lempel@gmail.com code review<br/>
 *
 * @author kirious@xenoimpact.com
 * @since 2016-11-22
 */
@Aspect
@Configuration
public class TransactionConfig {
	
	private static final String TX_METHOD_NAME = "*";
	private static final int TX_METHOD_TIMEOUT = 3;
	private static final String AOP_POINTCUT_EXPRESSION = "@within(org.springframework.transaction.annotation.Transactional)";
	
	private final PlatformTransactionManager transactionManager;

    @Autowired
    public TransactionConfig(PlatformTransactionManager transactionManager) {
        Assert.notNull(transactionManager, "PlatformTransactionManager can't be null");
        this.transactionManager = transactionManager;
    }

    @Bean
    public TransactionInterceptor txAdvice() {
        MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        transactionAttribute.setName(TX_METHOD_NAME);
        transactionAttribute.setRollbackRules(
                Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        transactionAttribute.setTimeout(TX_METHOD_TIMEOUT);
        source.setTransactionAttribute(transactionAttribute);
        TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, source);
        return txAdvice;
    }
    
    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
	
}
