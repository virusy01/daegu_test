package snp.config;

import snp.infra.helper.RequestParams;
import snp.util.V;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <br/>개정이력<br/>
 */
@Configuration
public class AopConfig {
    @Bean
    BeanNameAutoProxyCreator apiAutoProxy() {
        final BeanNameAutoProxyCreator apiAutoProxy = new BeanNameAutoProxyCreator();
        apiAutoProxy.setBeanNames("*Api", "*Controller");
        apiAutoProxy.setInterceptorNames("__before_api_method__");
        return apiAutoProxy;
    }

    @Bean(name = "__before_api_method__")
    MethodBeforeAdvice beforeApiMethod() throws IllegalArgumentException {
        return (method, args, target) -> {
            final Map<String, Object> params = new ParamMap<>();

            final Parameter[] parameters = method.getParameters();
            final int numParameters = parameters.length;
            String paramName;

            for (int paramIndex = 0; paramIndex < numParameters; ++paramIndex) {
                if (args[paramIndex] == null) continue;

                if (parameters[paramIndex].isAnnotationPresent(RequestParam.class)) {
                    paramName = parameters[paramIndex].getAnnotation(RequestParam.class).name();

                    if (V.isEmpty(paramName))
                        paramName = parameters[paramIndex].getAnnotation(RequestParam.class).value();

                    if (V.isEmpty(paramName)) {
                        throw new IllegalArgumentException("@RequestParam#name is required in " + method.getDeclaringClass().getCanonicalName() + "#" + method.getName() + ".");
                    }

                    params.put(paramName, args[paramIndex]);
                }
            }

            RequestParams.map(params);
        };
    }

    private static final String BEFORE_REPO_METHOD = "__before_repo_method__";

    @Bean
    BeanNameAutoProxyCreator beforeRepoProxy() {
        final BeanNameAutoProxyCreator repoAutoProxy = new BeanNameAutoProxyCreator();
        repoAutoProxy.setBeanNames("*Repo", "*Repository");
        repoAutoProxy.setInterceptorNames(BEFORE_REPO_METHOD);
        return repoAutoProxy;
    }

    @Bean(name = BEFORE_REPO_METHOD)
    MethodBeforeAdvice beforeRepoMethod() {
        return (method, args, target) -> {
            Map<String, Object> map = RequestParams.map();

            if (map instanceof ParamMap) {
                ((ParamMap) map).unlock();
            }
        };
    }

    private static final String AFTER_REPO_METHOD = "__after_repo_method__";

    @Bean
    BeanNameAutoProxyCreator afterRepoProxy() {
        final BeanNameAutoProxyCreator repoAutoProxy = new BeanNameAutoProxyCreator();
        repoAutoProxy.setBeanNames("*Repo", "*Repository");
        repoAutoProxy.setInterceptorNames(AFTER_REPO_METHOD);
        return repoAutoProxy;
    }

    @Bean(name = AFTER_REPO_METHOD)
    AfterReturningAdvice afterRepoMethod() {
        return (returnValue, method, args, target) -> {
            Map<String, Object> map = RequestParams.map();

            if (map instanceof ParamMap) {
                ((ParamMap) map).lock();
            }
        };
    }

    private static class ParamMap<K, V> extends HashMap<K, V> {
        AtomicBoolean lock = new AtomicBoolean(true);

        private void lock() {
            lock.set(true);
        }

        private void unlock() {
            lock.set(false);
        }

        @Override
        public V get(Object key) {
            if (lock.get()) {
                throw new IllegalStateException("Do not use get. Add parameters to service.");
            } else if (super.containsKey(key)) {
                return super.get(key);
            }

            return null;
        }
    }
}
