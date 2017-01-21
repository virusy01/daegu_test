package snp.infra.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <br/>개정이력<br/>
 */
@Controller
public class IndexController {
    @RequestMapping(value={"/", "/index", "/app/*", "/app/*/*", "/app/*/*/*"})
    public String index() {
        if (Security.user() == null){
            return "/login.html";
        }else {
            return "/main.html";
        }
    }
}
