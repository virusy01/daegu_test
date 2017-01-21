package snp.infra.helper;

import java.util.Map;

/**
 * <br/>개정이력<br/>
 * 2016-11-03 jy.choi@xenoimpact.com 서버 개발 표준 샘플. (pr2)<br/>
 *
 * @author jy.choi@xenoimpact.com
 */
final public class RequestParams {
    final static private ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    final static public Map<String, Object> map() {
        return threadLocal.get();
    }

    final static public void map(Map<String, Object> map) {
        threadLocal.set(map);
    }
}
