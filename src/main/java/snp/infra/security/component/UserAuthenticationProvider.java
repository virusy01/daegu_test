package snp.infra.security.component;

import snp.infra.security.model.DirectLogin;
import snp.infra.security.model.Login;
import snp.infra.user.UserRepo;
import snp.infra.user.model.User;
import snp.util.V;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * <br/>개정이력<br/>
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    Environment env;

    @Autowired
    UserRepo userRepo;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication == null) return null;

        if (Login.class.isAssignableFrom(authentication.getClass())) {
            final Login l = (Login) authentication;

            final User user = userRepo.find(l.getName());
            if (user == null) throw new UsernameNotFoundException(l.getName());

            // Validate password.
            if (V.isEmpty(l.getCredentials())) throw new BadCredentialsException("Password must not be empty.");
            final String password = userRepo.password(l.getName());

            if (!l.getCredentials().equals(password)) throw new BadCredentialsException("Wrong password.");

            //TODO authorities
            final UserAuthentication userAuthentication = new UserAuthentication(user, null);
            userAuthentication.setAuthenticated(true);

            return userAuthentication;
        } else if (DirectLogin.class.isAssignableFrom(authentication.getClass())) {
            final DirectLogin dl = (DirectLogin) authentication;

            final User user = userRepo.find(dl.getName());
            if (user == null) throw new UsernameNotFoundException(dl.getName());

            //TODO authorities
            final UserAuthentication userAuthentication = new UserAuthentication(user, null);
            userAuthentication.setAuthenticated(true);

            return userAuthentication;
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        if (authenticationClass == null) return false;

        return Login.class.isAssignableFrom(authenticationClass) ||
                DirectLogin.class.isAssignableFrom(authenticationClass);
    }
}
