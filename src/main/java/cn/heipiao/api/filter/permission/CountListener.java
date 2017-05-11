package cn.heipiao.api.filter.permission;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountListener implements HttpSessionListener {
	private static int count = 0;
	private static final Logger logger = LoggerFactory
			.getLogger(CountListener.class);
	public void sessionCreated(HttpSessionEvent arg0) {
		logger.debug("sessionCreated");
		count++;
		HttpSession session = arg0.getSession();
		ServletContext sctx = session.getServletContext();
		sctx.setAttribute("count", count);

	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		logger.debug("sessionDestroyed");
		count--;
		HttpSession session = arg0.getSession();
		ServletContext sctx = session.getServletContext();
		sctx.setAttribute("count", count);
	}

}
