package snp.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * Validation method bundle.
 * @author jy.choi@xenoimpact.com
 */
final public class V {
    /**
     * If target is null, then throws exception.
     * @param target
     * @param targetName For exception message.
     * @throws IllegalArgumentException
     */
    final static public void noNull(final Object target, final String targetName) throws IllegalArgumentException {
        if (targetName == null) throw new IllegalArgumentException(noNullMessage("targetName argument"));
        if (target == null) throw new IllegalArgumentException(noNullMessage(targetName));
    }

    /**
     * If target is empty, then throws exception.
     * @param target
     * @param targetName For exception message.
     * @throws IllegalArgumentException
     */
    final static public void noEmpty(final Object target, final String targetName) throws IllegalArgumentException {
        noNull(target, targetName);
        if (isEmpty(target)) throw new IllegalArgumentException(targetName + " must not be empty.");
    }

    /**
     * Returns empty.
     * @param target
     * @return
     */
    final static public boolean isEmpty(final Object target) {
        if (target == null) return true;

        if (CharSequence.class.isInstance(target)) return (((CharSequence) target).length() == 0);
        if (Collection.class.isInstance(target)) return ((Collection) target).isEmpty();
        if (Map.class.isInstance(target)) return ((Map) target).isEmpty();

        if (target.getClass().isArray()) {
            final int num = Array.getLength(target);

            if (num > 0) {
                for (int i = 0; i < num; i++) {
                    if (Array.get(target, i) != null) return false;
                }
            }

            return true;
        }

        return false;
    }

    /**
     * return !isEmpty(target)
     *
     * @param target target
     * @return !isEmpty(target)
     */
    final static public boolean isNotEmpty(final Object target) {
        return !isEmpty(target);
    }

    final static private String noNullMessage(final String targetName) throws IllegalArgumentException {
        if (targetName == null) throw new IllegalArgumentException(noNullMessage("targetName"));
        return targetName + " must not be null.";
    }

    private V() {
    } // Invisible constructor.
}
