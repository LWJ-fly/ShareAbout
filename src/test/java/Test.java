import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.Set;

public class Test {

   static String init (){
        return  "{" +
                    "\"name\": 10.123," +
                    "\"gender\": \"男\"" +
                "}";
    }

    public static void main(String[] args) {
        String str = init();
        JSONObject jsonObject = JSONObject.parseObject(str);
        say(jsonObject);
    }


    static void say(Map<String, Object> objectMap){
        Set<String> keys = objectMap.keySet();
        for (String key:keys){
            Object o = objectMap.get(key);
            if (o!=null)
                System.out.println(key+"\t\t\t\t"+o.getClass());
        }
    }
}
