package nerdeando.main;

import nerdeando.config.ConfigValuesSingleton;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Amigo Joao Falou: " + ConfigValuesSingleton.getInstance().getAmigoJoaoFalou();
    }

}
