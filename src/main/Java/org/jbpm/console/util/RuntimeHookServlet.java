package org.jbpm.console.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Servlet for adding JVM runtime hooks
 * 
 */
public class RuntimeHookServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() throws ServletException {
		Runtime.getRuntime().addShutdownHook(ShutdownHook.getInstance());
	}

}
