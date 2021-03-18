package online.wenmeng.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import online.wenmeng.exception.LoginException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 狼芒
 * @Date: 2021/2/14 23:14
 * @Descrintion: QQ登录的工具类，仅用于QQ登录相关调用，提高可移植性
 * @version: 1.0
 */
public class QQLoginUtils {

    /**
     * @param code 用户登录时QQ官网发出的登录令牌
     * @return 以Json串的形式返回用户的简略信息
     */
    public static Map<String, Object> getUserInfo(String code){
        return QQLoginCode(code);
    }

    /**
     * @Method: 从官方文档中开始，按照官方文档从code开始访问
     * @param code Authorization Code；即 授权代码
     * @return 返回从官方文档获取的用户信息
     */
    public static Map<String,Object> QQLoginCode(String code){
        try {
            Map<String,Object> map = new HashMap<>();
            //2. 通过Authorization Code获取Access Token 即通过授权代码获取访问令牌
            String parames = "grant_type=authorization_code&client_id=101865958&client_secret=c17e056b8636f2d844146053bc37846e&redirect_uri=http://www.wenmeng.online/QQLogin&code="+code;

            //调用访问网站操作，获取返回的访问令牌代码
            String access_Token_Results = httpsRequest.httpsRequest("https://graph.qq.com/oauth2.0/token", "GET", parames);

            //如果代码中返回"callback"，则证明获取失败
            if(access_Token_Results.contains("callback")) {
                throw new LoginException();
            }

            //4.获取用户OpenID , 返回的数据为callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
            String access_token = getOneParameter(access_Token_Results,"access_token");
            parames = "access_token="+access_token;
            String openID_Results = httpsRequest.httpsRequest("https://graph.qq.com/oauth2.0/me", "GET", parames);

            //5. 调用OpenAPI接口,以get_user_info接口为例
            String openid = getOneProperty(openID_Results, "openid");
            parames = "oauth_consumer_key=101865958&access_token="+access_token + "&openid="+openid;
            //调用访问网站操作，获取用户信息，返回为json串
            String get_user_info = httpsRequest.httpsRequest("https://graph.qq.com/user/get_user_info", "GET", parames);
            map = JSON.parseObject(get_user_info,Map.class);
            map.put(Config.OPENID,openid);
            return map;
        } catch (Exception e) {
            return MyUtils.getNewMap(Config.ERROR,null,"code Wrong",code);
        }
    }
    /**
     * @Method: java获取request中的参数、java解析URL问号后的参数
     * @param UrlParameter  浏览器地址中的参数
     * @param keyWord 需要解析的参数
     * @return
     */
    public static String getOneParameter(String UrlParameter,String keyWord) {
        final String charset = "utf-8";
        try {
            UrlParameter = URLDecoder.decode(UrlParameter, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (UrlParameter.indexOf('?') != -1) {//标准浏览器地址解析
            final String contents = UrlParameter.substring(UrlParameter.indexOf('?') + 1);
            String[] keyValues = contents.split("&");
            String key , value;
            for (int i = 0; i < keyValues.length; i++) {
                key = keyValues[i].substring(0, keyValues[i].indexOf("="));
                value = keyValues[i].substring(keyValues[i].indexOf("=") + 1);
                if (key.equals(keyWord)) {
                    if (value != null || !"".equals(value.trim())) {
                        return value;
                    }
                }
            }
        }else {//如果只是传过来的后缀
            String[] keyValues = UrlParameter.split("&");
            String key , value;
            for (int i = 0; i < keyValues.length; i++) {
                key = keyValues[i].substring(0, keyValues[i].indexOf("="));
                value = keyValues[i].substring(keyValues[i].indexOf("=") + 1);
                if (key.equals(keyWord)) {
                    if (value != null || !"".equals(value.trim())) {
                        return value;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @Method: 解析简单json串，获取相应的属性
     * @param jsonStr 需要解析的字符串
     * @param targetStr 目标属性
     * @return 需要获取目标属性的值
     */
    public static String getOneProperty(String jsonStr , String targetStr){
        //有些返回值带有（） 需要去掉
        if(jsonStr.contains("(")){
            jsonStr = jsonStr.substring(jsonStr.indexOf('(') + 1);
            if (jsonStr.contains(")")){
                jsonStr = jsonStr.substring(0,jsonStr.indexOf(')'));
            }
        }
        //将String字符转为Json对象
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        return jsonObject.getString(targetStr);
    }
}
