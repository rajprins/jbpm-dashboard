package org.jbpm.jsf.identity.handler;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.identity.User;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.handler.AbstractHandler;
import org.jbpm.jsf.identity.action.LoadUserActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "loadUser",
    description = "Read a user from the database.",
    attributes = {
        @TldAttribute (
            name = "id",
            description = "The ID of the user to load.",
            required = true,
            deferredType = long.class
        ),
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the user should be stored.",
            required = true,
            deferredType = User.class
        )
    }
)
public final class LoadUserHandler extends AbstractHandler {
    private final TagAttribute idTagAttribute;
    private final TagAttribute targetTagAttribute;

    public LoadUserHandler(final TagConfig config) {
        super(config);
        idTagAttribute = getRequiredAttribute("id");
        targetTagAttribute = getRequiredAttribute("target");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new LoadUserActionListener(
            getValueExpression(idTagAttribute, ctx, Long.class),
            getValueExpression(targetTagAttribute, ctx, User.class)
        );
    }
}
