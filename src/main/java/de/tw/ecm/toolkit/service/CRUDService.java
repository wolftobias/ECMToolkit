package main.java.de.tw.ecm.toolkit.service;

import javafx.concurrent.Task;
import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.Entity;

public class CRUDService {

	public CRUDService() {
	}

	public static class ReadListService extends AbstractService<DataList> {

		private Entity entity;

		private String query;

		public ReadListService(Entity entity, String query) {
			this.entity = entity;
			this.query = query;
			this.start();
		}

		@Override
		protected Task<DataList> createTask() {
			return new Task() {
				@Override
				protected Object call() throws Exception {
					updateMessage("reading...");
					return dataSource.readList(entity, query);
				}
			};
		}
	}
	
	public static class ReadService extends AbstractService<DataList> {

		private Entity entity;

		private String query;

		public ReadService(Entity entity, String query) {
			this.entity = entity;
			this.query = query;
			this.start();
		}

		@Override
		protected Task<DataList> createTask() {
			return new Task() {
				@Override
				protected Object call() throws Exception {
					updateMessage("reading...");
					return dataSource.read(entity, query);
				}
			};
		}
	}
	
	public static class CreateService extends AbstractService<Void> {

		private Entity entity;

		private DataList list;

		public CreateService(Entity entity, DataList list) {
			this.entity = entity;
			this.list = list;
			this.start();
		}

		@Override
		protected Task<Void> createTask() {
			return new Task() {
				@Override
				protected Object call() throws Exception {
					updateMessage("creating...");
					dataSource.create(entity, list);
					return null;
				}
			};
		}
	}	

	public static class UpdateService extends AbstractService<Void> {

		private Entity entity;

		private DataList list;

		public UpdateService(Entity entity, DataList list) {
			this.entity = entity;
			this.list = list;
			this.start();
		}

		@Override
		protected Task<Void> createTask() {
			return new Task() {
				@Override
				protected Object call() throws Exception {
					updateMessage("reading...");
					dataSource.update(entity, list);
					return null;
				}
			};
		}
	}	

	public static class DeleteListService extends AbstractService<DataList> {

		private Entity entity;

		private DataList list;

		public DeleteListService(Entity entity, DataList list) {
			this.entity = entity;
			this.list = list;
			this.start();
		}

		@Override
		protected Task<DataList> createTask() {
			return new Task() {
				@Override
				protected Object call() throws Exception {
					updateMessage("deleting...");
					dataSource.delete(entity, list);
					return null;
				}
			};
		}
	}	

	public static class DeleteService extends AbstractService<Void> {

		private Entity entity;

		private String sql;

		public DeleteService(Entity entity, String sql) {
			this.entity = entity;
			this.sql = sql;
			this.start();
		}

		@Override
		protected Task<Void> createTask() {
			return new Task() {
				@Override
				protected Object call() throws Exception {
					updateMessage("deleting...");
					dataSource.delete(entity, sql);
					return null;
				}
			};
		}
	}	

}
