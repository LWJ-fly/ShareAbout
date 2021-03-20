package online.wenmeng.service;

import com.alibaba.fastjson.JSONObject;
import online.wenmeng.utils.httpsRequest;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;


@Service
public class TestService {

    private String URL = "https://wenmeng.online/api/changeUserInfo/123456/";

    public Map<String,Object> login(HttpSession session, String code) {

        System.out.println("code:"+code);
        String userInfo = httpsRequest.httpsRequest(URL +  code, "GET", null);
        JSONObject jsonObject = JSONObject.parseObject(userInfo);

        System.out.println(jsonObject);
        return jsonObject;
    }




    void say(JSONObject jsonObject){
        JSONObject data = jsonObject.getJSONObject("data");
        Set<String> keys = data.keySet();
        for (String key:keys){
            Object o = data.get(key);
            if (o!=null)
                System.out.println(key+"\t\t\t\t"+o.getClass());
        }
    }
}
