package org.jbpm.jsf.core.handler;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.ShowProcessOverviewActionListener;
import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
*
*/
@TldTag (
   name = "showProcessOverview",
   description = "Shows an overview of deployed and running process instances.",
   attributes = {
       @TldAttribute (
               name = "procs",
               description = "An EL expression into which the number of running/started processes should be stored.",
               required = true,
               deferredType = int.class
       ),
       @TldAttribute (
               name = "tasks",
               description = "An EL expression into which the number of running/started tasks should be stored.",
               required = false,
               deferredType = int.class
       ),
       @TldAttribute (
               name = "jobs",
               description = "An EL expression into which the number of running/started jobs should be stored.",
               required = false,
               deferredType = int.class
       ),
       @TldAttribute (
               name = "users",
               description = "An EL expression into which the number of defined users should be stored.",
               required = false,
               deferredType = int.class
       ),
       @TldAttribute (
               name = "groups",
               description = "An EL expression into which the number of defined groups should be stored.",
               required = false,
               deferredType = int.class
       )       
   }
)
public final class ShowProcessOverviewHandler extends AbstractHandler {
    private final TagAttribute procsTagAttribute;
    private final TagAttribute tasksTagAttribute;
    private final TagAttribute jobsTagAttribute;
    private final TagAttribute usersTagAttribute;
    private final TagAttribute groupsTagAttribute;
    
    public ShowProcessOverviewHandler(final TagConfig config) {
        super(config);
        procsTagAttribute  = getRequiredAttribute("procs");
        tasksTagAttribute  = getRequiredAttribute("tasks");
        jobsTagAttribute   = getRequiredAttribute("jobs");
        usersTagAttribute  = getRequiredAttribute("users");
        groupsTagAttribute = getRequiredAttribute("groups");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new ShowProcessOverviewActionListener(
            getValueExpression(procsTagAttribute, ctx, int.class),
            getValueExpression(tasksTagAttribute, ctx, int.class),
            getValueExpression(jobsTagAttribute, ctx, int.class),
            getValueExpression(usersTagAttribute, ctx, int.class),
            getValueExpression(groupsTagAttribute, ctx, int.class)
        );
    }

}