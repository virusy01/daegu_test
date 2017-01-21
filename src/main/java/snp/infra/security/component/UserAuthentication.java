package snp.infra.security.component;

import snp.infra.user.model.User;
import snp.util.V;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * <br/>개정이력<br/>
 */
public class UserAuthentication extends AbstractAuthenticationToken {
    public UserAuthentication(User user,
                              Collection<? extends GrantedAuthority> authorities) {
        super(authorities);

        V.noNull(user, "user");

        this.user = user;
    }

    final private User user;

    public User user() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
