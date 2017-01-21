package snp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logger Helper
 * <p>
 * <br/>개정이력<br/>
 * 2016-11-22 lempel@gmail.com EAIConnection 을 factory 를 통해서 생성하도록 변경<br/>
 *
 * @author lempel@gmail.com
 * @since 2016-11-22
 */
public class LoggerHelper {
    public static Logger getLogger() {
        Logger result = null;

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements.length > 2) {
            result = LoggerFactory.getLogger(elements[2].getClassName());
        }

        return result;
    }

    public static org.apache.log4j.Logger getLog4jLogger() {
        org.apache.log4j.Logger result = null;

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements.length > 2) {
            result = org.apache.log4j.Logger.getLogger(elements[2].getClassName());
        }

        return result;
    }
}
