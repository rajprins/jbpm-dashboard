package org.jbpm.jsf.core.handler;

import java.util.Date;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.UpdateTaskStartActionListener;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "updateTaskStart",
    description = "Update the start date of a started task.",
    attributes = {
        @TldAttribute (
            name = "task",
            description = "The task instance to update.",
            required = true,
            deferredType = TaskInstance.class
        ),
        @TldAttribute (
            name = "startDate",
            description = "The start date to assign.  If not given, the current date will be used.",
            deferredType = Date.class
        )
    }
)
public final class UpdateTaskStartHandler extends AbstractHandler {
    private final TagAttribute taskTagAttribute;
    private final TagAttribute startDateTagAttribute;

    public UpdateTaskStartHandler(final TagConfig config) {
        super(config);
        taskTagAttribute = getRequiredAttribute("task");
        startDateTagAttribute = getAttribute("startDate");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new UpdateTaskStartActionListener(
            getValueExpression(taskTagAttribute, ctx, TaskInstance.class),
            getValueExpression(startDateTagAttribute, ctx, Date.class)
        );
    }
}
