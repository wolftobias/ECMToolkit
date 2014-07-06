package main.java.de.tw.ecm.toolkit.service;

import javafx.concurrent.Task;
import main.java.de.tw.ecm.toolkit.auth.User;

public class LoginService extends AbstractService<Void> {

	private User user;

	public LoginService(User user) {
		this.user = user;
		this.start();
	}

	@Override
	protected Task<Void> createTask() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				updateMessage("login...");
				dataSource.login(user);
				return null;
			}
		};
	}

}
