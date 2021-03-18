package online.wenmeng.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 狼芒
 * @Date: 2021/2/14 23:09
 * @Descrintion: static静态资源
 * @version: 1.0
 */
public class Config {


    //出现错误，很有可能是未知错误
    public static String ERROR = "error";
    //请求数据成功
    public static String SUCCESS = "success";
    //重试
    public static String RETRY = "retry";
    //用户的登录页面
    public static String LOGIN = "login";
    //跳转到主页
    public static String INDEX = "index";


    /**
     *  数据库相关属性
     */
    public static String QQPERMISSION = "qq";//QQ的权限
    public static String ALIPAYPERMISSION = "Alipay";//Alipay支付接口的权限
    public static String APIPERMISSION = "api";//Alipay支付接口的权限
    public static long shortTime = 1;//短期登录时间
    public static long longTime = 30;//短期登录时间


    /**
     * 返回参数定义
     */
    public static String STATE = "state";//请求的状态    success error
    public static String ACTION = "action";//后台希望的的响应动作 null index login
    public static String MSG = "msg";//附加的消息 null
    public static String DATA = "data";//数据内容 null


    /**
     * 后台使用参数
     */
    //用户保存在session中的openID
    public static String OPENID = "openid";
    //用户登录时保存在session中的登录的QQ信息
    public static String userInfoInRun = "userInfoInRun";
    //发送邮件次数限制
    public static Map<Integer,Integer> sendEmailTimesMap = new HashMap<>();
    //限制发邮件的次数
    public static int LimitSendEmailTimes = 3;
    //限制用户调用接口次数——感觉并不必要，因为会影响使用_密钥错误次数过多暂停服务，明天重启
    public static Map<String,Integer> secretErrorMap = new HashMap<>();

    public static int secretErrorTimes = 100;
}
