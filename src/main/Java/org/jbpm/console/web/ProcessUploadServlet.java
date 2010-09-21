package org.jbpm.console.web;

import java.io.IOException;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;

@SuppressWarnings("deprecation")
public class ProcessUploadServlet extends HttpServlet {

   private static final long serialVersionUID = 1L;

   private JbpmConfiguration jbpmConfiguration;

   public void init() throws ServletException {
      String jbpmCfgResource = getServletContext().getInitParameter("jbpm.configuration.resource");
      jbpmConfiguration = JbpmConfiguration.getInstance(jbpmCfgResource);
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      log.debug("Handling status request");
      response.getWriter().println("Process upload module is operational");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      log.debug("Handling upload request");
      response.getWriter().println(handleRequest(request));
   }


   private String handleRequest(HttpServletRequest request) {
      // check if request is multipart content
      if (!FileUpload.isMultipartContent(request)) {
         log.debug("Not a multipart request");
         return "Not a multipart request";
      }

      try {
         DiskFileUpload fileUpload = new DiskFileUpload();
         List<?> itemList = fileUpload.parseRequest(request);
         log.debug("Upload from GPD");
         if (itemList.isEmpty()) {
            log.debug("No process file in the request");
            return "No process file in the request";
         }
         FileItem fileItem = (FileItem) itemList.get(0);
         if (fileItem.getContentType().indexOf("application/x-zip-compressed") == -1) {
            log.debug("Not a process archive");
            return "Not a process archive";
         }
         try {
            log.debug("Deploying process from archive " + fileItem.getName());
            ProcessDefinition processDefinition = parseProcessArchive(fileItem);
            deployProcessDefinition(processDefinition);
            return "Deployed process " + processDefinition.getName() + " successfully";
         }
         catch (IOException e) {
            log.debug("Failed to read process archive", e);
            return "IOException";
         }
      }
      catch (FileUploadException e) {
         log.debug("Failed to parse HTTP request", e);
         return "FileUploadException";
      }
   }

   private ProcessDefinition parseProcessArchive(FileItem fileItem) throws IOException {
      ZipInputStream processStream = new ZipInputStream(fileItem.getInputStream());
      try {
         ProcessDefinition processDefinition = ProcessDefinition.parseParZipInputStream(processStream);
         log.debug("Created process " + processDefinition.getName());
         return processDefinition;
      }
      finally {
         processStream.close();
      }
   }

   private void deployProcessDefinition(ProcessDefinition processDefinition) {
      JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
      try {
         jbpmContext.deployProcessDefinition(processDefinition);
         log.debug("Deployed process " + processDefinition.getName() + " successfully");
      }
      catch (RuntimeException e) {
         jbpmContext.setRollbackOnly();
         log.error("Failed to deploy process " + processDefinition.getName(), e);
         throw e;
      }
      finally {
         jbpmContext.close();
      }
   }

   private static final Log log = LogFactory.getLog(ProcessUploadServlet.class);
}
