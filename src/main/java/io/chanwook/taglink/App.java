package io.chanwook.taglink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chanwook
 */
@SpringBootApplication
@Controller
public class App {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

    @RequestMapping("/check")
    @ResponseBody
    public String check() {
        return "OK!";
    }

}