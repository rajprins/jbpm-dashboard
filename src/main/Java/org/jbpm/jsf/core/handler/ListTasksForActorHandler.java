package org.jbpm.jsf.core.handler;

import java.util.List;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.action.ListTasksForActorActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "listTasksForActor",
    description = "Read a list of task instances assigned to an actor ID.",
    attributes = {
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the task instance list should be stored.",
            required = true,
            deferredType = List.class
        ),
        @TldAttribute (
            name = "actorId",
            description = "The actor ID whose task instances are to be read.",
            required = true
        )
    }
)
public final class ListTasksForActorHandler extends AbstractHandler {
    private final TagAttribute targetTagAttribute;
    private final TagAttribute actorIdTagAttribute;

    public ListTasksForActorHandler(final TagConfig config) {
        super(config);
        targetTagAttribute = getRequiredAttribute("target");
        actorIdTagAttribute = getRequiredAttribute("actorId");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new ListTasksForActorActionListener(
            getValueExpression(actorIdTagAttribute, ctx, String.class),
            getValueExpression(targetTagAttribute, ctx, List.class)
        );
    }
}
