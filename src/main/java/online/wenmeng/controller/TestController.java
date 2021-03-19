package online.wenmeng.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import online.wenmeng.service.TestService;

import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("login")
    public Map<String,Object> login(HttpSession session,@RequestParam("code") String code){
        return testService.login(session,code);
    }


}
