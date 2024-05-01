package com.github.hummel.dc.lab6.listener;

import com.github.hummel.dc.lab6.bean.container.Cart;
import com.github.hummel.dc.lab6.bean.container.Page;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.stereotype.Component;

import static com.github.hummel.dc.lab6.utils.Constants.*;

@Component
@WebListener
public class SessionListener implements HttpSessionListener {
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		var httpSession = se.getSession();
		httpSession.setAttribute(USER, null);
		httpSession.setAttribute(AUTHOR_PAGING_PARAMS, new Page(DEFAULT_START_PAGE, DEFAULT_PAGE_SIZE));
		httpSession.setAttribute(BOOK_PAGING_PARAMS, new Page(DEFAULT_START_PAGE, DEFAULT_PAGE_SIZE));
		httpSession.setAttribute(ORDER_PAGING_PARAMS, new Page(DEFAULT_START_PAGE, DEFAULT_PAGE_SIZE));
		httpSession.setAttribute(CART, new Cart());
	}
}