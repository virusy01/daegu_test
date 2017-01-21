package snp.infra.security.model;

import snp.util.V;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

/**
 * 정규 로그인 요청 모델.
 *
 */
public class Login extends AbstractAuthenticationToken {
    public Login(final String userId, final String password, final Object details) {
        super(Collections.emptyList());

        V.noEmpty(userId, "userId");

        this.userId = userId;
        this.password = password;
        setDetails(details);
    }

    final private String userId;
    final private String password;

    @Override
    public String getName() {
        return userId;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public Object getCredentials() {
        return password;
    }
}
