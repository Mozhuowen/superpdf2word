package tools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import monitor.StatMonitorRunnable;

import org.springframework.web.context.ContextLoaderListener;

public class Loadonstart implements ServletContextListener
{
	@Override  
    public void contextInitialized(ServletContextEvent event) {  
		try
		{
			new Thread(new StatMonitorRunnable(event.getServletContext())).start();
		}catch (Exception e) {
			LogUtil.e(this, e.toString());
		}
			
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}