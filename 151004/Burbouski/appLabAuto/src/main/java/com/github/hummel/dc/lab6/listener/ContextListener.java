package com.github.hummel.dc.lab6.listener;

import com.github.hummel.dc.lab6.ConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.stereotype.Component;

@Component
@WebListener
public class ContextListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ConnectionPool.getInstance().closeConnections();
	}
}