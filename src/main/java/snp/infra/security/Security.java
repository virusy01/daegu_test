package snp.infra.security;

import snp.infra.security.component.UserAuthentication;
import snp.infra.security.model.DirectLogin;
import snp.infra.user.model.User;
import snp.util.LoggerHelper;
import snp.util.V;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Objects;

/**
 * <br/>개정이력<br/>
 */
final public class Security {
    private static final Logger L = LoggerHelper.getLogger();

    static public String userId() {
        final User user = user();
        return (user != null) ? user.userId() : null;
    }

    static public String userId(final Authentication authentication) {
        final User user = user(authentication);
        return (user != null) ? user.userId() : null;
    }

    static public User user()
            throws IllegalArgumentException {
        final UserAuthentication userAuthentication = userAuthentication();
        if (userAuthentication == null) return null;

        return userAuthentication.user();
    }

    static public User user(final Authentication authentication)
            throws IllegalArgumentException {
        final UserAuthentication userAuthentication = userAuthentication(authentication);
        if (userAuthentication == null) return null;

        return userAuthentication.user();
    }

    static public UserAuthentication userAuthentication()
            throws IllegalArgumentException {
        if (SecurityContextHolder.getContext() == null) {
            L.warn("Could not found security context.");
            return null;
        }

        return userAuthentication(SecurityContextHolder.getContext().getAuthentication());
    }

    static public UserAuthentication userAuthentication(
            final Authentication authentication)
            throws IllegalArgumentException {
        if ((authentication == null) ||
                !authentication.isAuthenticated() ||
                (authentication.getPrincipal() == null)) return null;

        if (!UserAuthentication.class.isInstance(authentication)) {
            L.warn("Authentication object is not an instance of {}. ({})",
                    UserAuthentication.class.getCanonicalName(),
                    Objects.toString(authentication));

            return null;
        }

        return (UserAuthentication) authentication;
    }

    static public UserAuthentication login(
            final String userId,
            final AuthenticationManager authenticationManager)
            throws IllegalArgumentException, AuthenticationException {
        return login(userId, null, authenticationManager);
    }

    static public UserAuthentication login(
            final String userId, final Object details,
            final AuthenticationManager authenticationManager)
            throws IllegalArgumentException, AuthenticationException {
        V.noNull(userId, "userId");

        return login(new DirectLogin(userId, details), authenticationManager);
    }

    static public UserAuthentication login(
            final Authentication request,
            final AuthenticationManager authenticationManager)
            throws IllegalArgumentException, AuthenticationException {
        V.noNull(request, "request");
        V.noNull(authenticationManager, "authenticationManager");

        // Authenticate.
        final Authentication authentication = authenticationManager.authenticate(request);

        return login(userAuthentication(authentication));
    }

    static public UserAuthentication login(
            final UserAuthentication authentication)
            throws IllegalArgumentException {
        if (authentication == null) {
            logout();
            return null;
        }

        if (!authentication.isAuthenticated()) {
            authentication.setAuthenticated(true);
        }

        // Save to context.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (L.isInfoEnabled()) {
            final User user = authentication.user();

            L.info("User({}, {}) was logged in. ({})",
                    user.userId(), user.userName(), detailsToString(authentication.getDetails()));
        }

        return authentication;
    }

    static public void logout()
            throws IllegalArgumentException {
        if (SecurityContextHolder.getContext() == null) return;
        if (SecurityContextHolder.getContext().getAuthentication() == null) return;

        final UserAuthentication userAuthentication = userAuthentication();

        SecurityContextHolder.getContext().setAuthentication(null);

        if (L.isInfoEnabled()) {
            if (userAuthentication != null) {
                final User user = userAuthentication.user();

                L.info("User({}, {}) was logged out. ({})",
                        user.userId(), user.userName(), detailsToString(userAuthentication.getDetails()));
            } else {
                L.info(
                        "User(Unknown) was logged out. ({})",
                        detailsToString(SecurityContextHolder.getContext().getAuthentication().getDetails())
                );
            }
        }
    }

    /**
     * For Authentication#getDetails().
     * @param details
     * @return
     */
    static private String detailsToString(final Object details) {
        if (WebAuthenticationDetails.class.isInstance(details)) {
            return ((WebAuthenticationDetails) details).getRemoteAddress();
        }

        return Objects.toString(details);
    }

    private Security() {
    } // Invisible constructor.
}
