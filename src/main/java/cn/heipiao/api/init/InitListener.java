/**
 * 
 */
package cn.heipiao.api.init;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * servlet生命周期容器类
 * 
 * @author wzw
 * @date 2016年6月18日
 * @version 1.0
 */
@WebListener
public class InitListener implements ServletContextListener {
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//严禁删除此类的System.out.println(); 
		try {
			System.out.println("init resource start ");
			String confPath = System.getProperty("hp.config");
			System.out.println("hp.config:" + confPath);
			if(confPath == null || !new File(confPath).isDirectory() || !new File(confPath + "/heipiaoConfig.properties").isFile()){
				System.out.println("hp.config variable is null or is not directory  or heipiaoConfig.properties is not exists !!!");
				Runtime.getRuntime().exit(1);
			}
			System.out.println("init resource end");
		} catch (Exception e) {
			System.out.println(e);
			Runtime.getRuntime().exit(1);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO 是否考虑将数据库连接池和Redis连接池在这里清除
	}

}
