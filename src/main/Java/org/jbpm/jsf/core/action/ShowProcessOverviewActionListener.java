package org.jbpm.jsf.core.action;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;


public class ShowProcessOverviewActionListener implements JbpmActionListener {
    private final ValueExpression procsExpression;
    private final ValueExpression tasksExpression;
    private final ValueExpression jobsExpression;
    private final ValueExpression usersExpression;
    private final ValueExpression groupsExpression;
    
    public ShowProcessOverviewActionListener(
    		final ValueExpression procsExpression, 
    		final ValueExpression tasksExpression, 
    		final ValueExpression jobsExpression,
    		final ValueExpression usersExpression,
    		final ValueExpression groupsExpression
    ) {
        this.procsExpression  = procsExpression;
        this.tasksExpression  = tasksExpression;
        this.jobsExpression   = jobsExpression;
        this.usersExpression  = usersExpression;
        this.groupsExpression = groupsExpression;
    }

    public String getName() {
        return "showProcessOverview";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ELContext elContext = facesContext.getELContext();
            
            int procs  = context.getJbpmContext().getGraphSession().findAllProcessDefinitions().size();
            int tasks  = context.getJbpmContext().getTaskList().size();
            int jobs   = context.getJbpmContext().getSession().createQuery("from org.jbpm.job.Job").list().size();
            int users  = context.getJbpmContext().getSession().createQuery("from org.jbpm.identity.User").list().size();
            int groups = context.getJbpmContext().getSession().createQuery("from org.jbpm.identity.Group").list().size();
            
            /*
            System.out.println("Processes: " +procs +"\n" +
            				   "Tasks    : " +tasks +"\n" +
            		           "Jobs     : " +jobs +"\n" +
            		           "Users    : " +users +"\n" +
            		           "Groups   : " +groups);
            */
            
            procsExpression.setValue(elContext, procs);
            tasksExpression.setValue(elContext, tasks);
            jobsExpression.setValue(elContext, jobs);
            usersExpression.setValue(elContext, users);
            groupsExpression.setValue(elContext, groups);
            context.selectOutcome("success");
            
        } catch (Exception ex) {
            context.setError("Error retreiving process information", ex);
            return;
        }
    }
}
