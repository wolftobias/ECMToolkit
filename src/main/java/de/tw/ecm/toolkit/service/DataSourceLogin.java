package main.java.de.tw.ecm.toolkit.service;

import main.java.de.tw.ecm.toolkit.data.sources.DataSource;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class DataSourceLogin extends Service<Boolean> {

	private DataSource dataSource;
	private String userid;
	private String password;

	public DataSourceLogin(DataSource dataSource, String userid, String password) {
		this.dataSource = dataSource;
		this.userid = userid;
		this.password = password;
	}

	@Override
	protected Task<Boolean> createTask() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				return dataSource.login(userid, password);
			}
		};
	}

}
