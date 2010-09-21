package org.jbpm.console.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;


public class BootstrapListener implements ServletContextListener {
   private JbpmConfiguration jbpmConfiguration;
   private static final Log log = LogFactory.getLog(BootstrapListener.class);

   public void contextInitialized(ServletContextEvent servletContextEvent) {
      String jbpmCfgResource = servletContextEvent.getServletContext().getInitParameter("jbpm.configuration.resource");
      jbpmConfiguration = JbpmConfiguration.getInstance(jbpmCfgResource);

      JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
      try {
         log.info("Bootstrap Hibernate session");
         jbpmContext.getGraphSession();
      }
      catch (RuntimeException e) {
         jbpmContext.setRollbackOnly();

         throw e;
      }
      finally {
         jbpmContext.close();
      }
   }

   public void contextDestroyed(ServletContextEvent servletContextEvent) {

   }
}
