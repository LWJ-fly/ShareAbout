package online.wenmeng.service;

import online.wenmeng.utils.Config;
import online.wenmeng.utils.MyUtils;
import online.wenmeng.utils.QQLoginUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;


@Service
public class TestService {

    public Map<String,Object> login(HttpSession session, String code) {
        System.out.println(code);
        Map<String, Object> objectMap = QQLoginUtils.QQLoginCode(code);

        return objectMap;
    }




    void say(Map<String, Object> objectMap){
        Set<String> keys = objectMap.keySet();
        for (String key:keys){
            Object o = objectMap.get(key);
            if (o!=null)
                System.out.println(key+"\t\t\t\t"+o.getClass());
        }
    }

}
