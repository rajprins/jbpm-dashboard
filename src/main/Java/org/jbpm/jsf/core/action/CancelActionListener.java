package org.jbpm.jsf.core.action;

import java.util.Collection;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 *
 */
public final class CancelActionListener implements JbpmActionListener {

    private final ValueExpression valueExpression;

    public CancelActionListener(final ValueExpression valueExpression) {
        this.valueExpression = valueExpression;
    }

    public String getName() {
        return "cancel";
    }

    @SuppressWarnings ({"unchecked"})
    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object value = valueExpression.getValue(elContext);
            if (value == null) {
                context.setError("Cancel failed", "The value is null");
                return;
            }
            if (value instanceof TaskInstance) {
                ((TaskInstance)value).setSignalling(false);
                ((TaskInstance)value).cancel();
                context.addSuccessMessage("Task instance cancelled");
            } else if (value instanceof Token) {
                final Token token = ((Token) value);
                token.end();
                for (TaskInstance task : (Collection<TaskInstance>) token.getProcessInstance().getTaskMgmtInstance().getUnfinishedTasks(token)) {
                    task.cancel();
                }
                context.addSuccessMessage("Token ended");
            } else if (value instanceof ProcessInstance) {
                final ProcessInstance processInstance = ((ProcessInstance) value);
                processInstance.end();
                for (TaskInstance task : (Collection<TaskInstance>) processInstance.getTaskMgmtInstance().getUnfinishedTasks(processInstance.getRootToken())) {
                    task.setSignalling(false);
                    task.cancel();
                }
                context.addSuccessMessage("Process instance cancelled");
            } else {
                context.setError("Cancel failed", "The value is not a recognized type");
                return;
            }
            context.getJbpmContext().getSession().flush();
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Cancel failed", ex);
            return;
        }
    }
}
