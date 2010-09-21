package org.jbpm.jsf.core.handler;

import java.util.List;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.ListTasksForProcessActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "listTasksForProcess",
    description = "Read a list of task instances from a process definition.",
    attributes = {
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the task instance list should be stored.",
            required = true,
            deferredType = List.class
        ),
        @TldAttribute (
            name = "process",
            description = "The process whose task instances are to be read.",
            required = true,
            deferredType = ProcessDefinition.class
        )
    }
)
public final class ListTasksForProcessHandler extends AbstractHandler {
    private final TagAttribute targetTagAttribute;
    private final TagAttribute processInstanceTagAttribute;

    public ListTasksForProcessHandler(final TagConfig config) {
        super(config);
        targetTagAttribute = getRequiredAttribute("target");
        processInstanceTagAttribute = getRequiredAttribute("process");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new ListTasksForProcessActionListener(
            getValueExpression(processInstanceTagAttribute, ctx, ProcessDefinition.class),
            getValueExpression(targetTagAttribute, ctx, List.class)
        );
    }
}
