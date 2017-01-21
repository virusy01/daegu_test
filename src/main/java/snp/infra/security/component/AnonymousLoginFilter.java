package snp.infra.security.component;

import snp.infra.security.Security;
import snp.util.LoggerHelper;
import snp.util.V;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <br/>개정이력<br/>
 */
public class AnonymousLoginFilter implements Filter {
    private static final Logger L = LoggerHelper.getLogger();

    public AnonymousLoginFilter(String defaultSystemId,
                                String defaultUserId,
                                AuthenticationManager authenticationManager) {
        super();

        V.noNull(authenticationManager, "authenticationManager");

        this.defaultSystemId = defaultSystemId;
        this.defaultUserId = defaultUserId;
        this.authenticationManager = authenticationManager;
    }

    final private String defaultSystemId;
    final private String defaultUserId;
    final private AuthenticationManager authenticationManager;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (Security.user() == null) {
            L.info("Anonymous authentication was found. Inject user({}) automatically.", defaultUserId);

            Security.login(
                    defaultUserId,
                    (request instanceof HttpServletRequest) ? new WebAuthenticationDetails((HttpServletRequest) request) : null,
                    authenticationManager
            );
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
