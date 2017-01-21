package snp.infra.security.model;

import snp.util.V;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

/**
 * <br/>개정이력<br/>
 */
final public class DirectLogin extends AbstractAuthenticationToken {
    public DirectLogin(final String userId, final Object details) {
        super(Collections.emptyList());

        V.noEmpty(userId, "userId");

        this.userId = userId;
        setDetails(details);
    }

    final private String userId;

    @Override
    public String getName() {
        return userId;
    }

    @Override
    public Object getCredentials() {
        return userId;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
