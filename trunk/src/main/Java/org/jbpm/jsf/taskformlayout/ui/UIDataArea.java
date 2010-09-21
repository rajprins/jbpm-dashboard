package org.jbpm.jsf.taskformlayout.ui;

public final class UIDataArea extends UIDataSection {

	private static final long serialVersionUID  = 1L;
	public static final String COMPONENT_TYPE   = "jbpm4jsf.tfl.DataArea";
    public static final String COMPONENT_FAMILY = "jbpm4jsf.tfl";
    public static final String RENDERER_TYPE    = null;

    public UIDataArea() {
        setRendererType(RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }
}
