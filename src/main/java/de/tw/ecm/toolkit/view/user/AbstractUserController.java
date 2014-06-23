package main.java.de.tw.ecm.toolkit.view.user;

import main.java.de.tw.ecm.toolkit.view.AbstractController;

public abstract class AbstractUserController extends AbstractController {

	protected String caption;

	public String getCaption() {
		return this.resources.getString("view.title");
	}
}
