package snp.infra.security;

import snp.infra.security.component.UserAuthentication;
import snp.infra.security.model.Login;
import snp.infra.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <br/>개정이력<br/>
 */
@RestController
@RequestMapping("/api/auth")
public class AuthApi {
    private final Environment env;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthApi(Environment env, AuthenticationManager authenticationManager) {
        Assert.notNull(env, "Environment can't be null");
        Assert.notNull(authenticationManager, "AuthenticationManager can't be null");
        this.env = env;
        this.authenticationManager = authenticationManager;
    }

    /**
     * 현재 로그인된 사용자 객체.
     *
     * @return
     */
    @GetMapping(path = "/user")
    User authenticatedUser() {
        return Security.user();
    }

    /**
     * 시스템 로그인.
     *
     * @param userId 사용자 아이디.
     * @param password 비밀번호.
     * @param request
     * @return 로그인 성공시, 로그인한 사용자 객체.
     * @throws AuthenticationException 로그인 실패시, 예외 발생.
     */
    @PostMapping(path = "/login", params = {"userId", "password" })
    User login(@RequestParam("userId") final String userId,
               @RequestParam("password") final String password,
               final HttpServletRequest request) throws AuthenticationException {
        final UserAuthentication userAuthentication = Security.login(
                new Login(userId, password, new WebAuthenticationDetails(request)),
                authenticationManager
        );

        return userAuthentication.user();
    }

    /**
     * 강제 로그인. (PRODUCTION 모드에서 작동하지 않음)
     *
     * @param userId 사용자 아이디.
     * @param request
     * @return 로그인한 사용자 객체.
     */
    @GetMapping(params = {"userId" })
    User forceAuthenticate(@RequestParam("userId") final String userId,
                           final HttpServletRequest request) {
        if (env.acceptsProfiles("production")) {
            throw new AccessDeniedException("This API is not supported in production mode.");
        }

        return Security.login(
                userId,
                new WebAuthenticationDetails(request),
                authenticationManager
        ).user();
    }
}
