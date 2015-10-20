package tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;     //Spring应用上下文环境

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		SpringContextUtil.applicationContext = arg0;
	}
	
	public static ApplicationContext getApplicationContext() {
	    return applicationContext;
	  }
	
	public static Object getBean(String name) throws BeansException {
	    return applicationContext.getBean(name);
	  }
	
}