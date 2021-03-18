package online.wenmeng.exception;

import online.wenmeng.utils.Config;
import online.wenmeng.utils.MyUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: 狼芒
 * @Date: 2021/2/28 12:41
 * @Descrintion: 异常的处理器
 * @version: 1.0
 */
public class ExceptionResolver implements HandlerExceptionResolver {
    @ResponseBody
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        e.printStackTrace();
        //创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        modelAndView.addObject(Config.STATE,Config.ERROR);
        modelAndView.addObject(Config.ACTION,Config.ERROR);
        modelAndView.addObject(Config.DATA,null);
        modelAndView.addObject(Config.MSG,e);
        if (e instanceof java.lang.reflect.UndeclaredThrowableException){
            e = (Exception) e.getCause();
        }
        if (e instanceof java.lang.reflect.InvocationTargetException){
            e = (Exception) e.getCause();
        }
        if (e instanceof RuntimeException){
            e = (Exception) e.getCause();
        }


        //获取到异常对象
        if (e instanceof LoginException){
            modelAndView.addObject(Config.ACTION,Config.LOGIN);
            modelAndView.addObject(Config.MSG,"当前用户未登录");
        }else if (e instanceof SecretErrorMaxException){
            modelAndView.addObject(Config.ACTION,Config.ERROR);
            modelAndView.addObject(Config.MSG,"Key error upper limit, please try again tomorrow");
        }else if (e instanceof SecretErrorException){
            String ipAddr = MyUtils.getIpAddr(httpServletRequest);
            if (Config.secretErrorMap.containsKey(ipAddr)){
                Config.secretErrorMap.put(ipAddr,Config.secretErrorMap.get(ipAddr)+1);
            }else {
                Config.secretErrorMap.put(ipAddr,1);
            }
            modelAndView.addObject(Config.MSG,"密钥错误");
        }else {
            modelAndView.addObject(Config.MSG,e.getMessage());
        }
        return modelAndView;
    }
}
