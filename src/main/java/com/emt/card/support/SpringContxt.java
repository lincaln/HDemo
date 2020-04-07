package com.emt.card.support;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SpringContxt implements ApplicationContextAware {

	 private static ApplicationContext context;
	 
	 /* (non Javadoc)
	     * @Title: setApplicationContext
	     * @Description: spring获取bean工具类
	     * @param applicationContext
	     * @throws BeansException
	     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	     */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContxt.context = applicationContext;
	}

	// 传入线程中
    @SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
        return (T) SpringContxt.context.getBean(beanName);
    }
 
    // 国际化使用
    public static String getMessage(String key) {
        return context.getMessage(key, null, Locale.getDefault());
    }
 
    /// 获取当前环境
    public static String getActiveProfile() {
    	Environment environment=context.getEnvironment();
    	if(environment.getActiveProfiles().length>0)
            return environment.getActiveProfiles()[0];
    	else
    		return environment.getDefaultProfiles()[0];	
    }
}
