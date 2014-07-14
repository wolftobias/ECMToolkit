package main.java.de.tw.ecm.toolkit.service;

import javafx.concurrent.Task;
import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.DataRow;
import main.java.de.tw.ecm.toolkit.data.Entity;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;
import main.java.de.tw.ecm.toolkit.data.writer.DataWriter;

public class CRUDService {

	public CRUDService() {
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
			return new Task<DataList>() {
				@Override
				protected DataList call() throws Exception {
					updateMessage("reading...");

					int counter = 0;
					DataList dataList = new DataList(entity);
					DataReader reader = dataSource.read(entity, query);

					try {
						int rowCount = reader.getRowCount(query);
						updateProgress(counter++, rowCount);

						while (reader.next()) {
							if (isCancelled())
								break;
							updateProgress(counter++, rowCount);
							dataList.add(reader.readRow());
						}

						return dataList;
					} finally {
						reader.close();
					}
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
			return new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					updateMessage("creating...");

					int counter = 0;
					int rowCount = list.size();
					updateProgress(counter++, rowCount);
					DataWriter writer = dataSource.create(entity, list);

					try {
						for (DataRow dataRow : list) {
							if (isCancelled())
								break;
							updateProgress(counter++, rowCount);
							writer.createRow(dataRow);
						}
						writer.close();
					} catch (Exception e) {
						dataSource.rollback();
						throw e;
					}
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
			return new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					int counter = 0;
					int rowCount = list.size();
					updateProgress(counter++, rowCount);
					DataWriter writer = dataSource.update(entity, list);

					try {
						for (DataRow dataRow : list) {
							if (isCancelled())
								break;
							updateProgress(counter++, rowCount);
							writer.updateRow(dataRow);
						}
						writer.close();
					} catch (Exception e) {
						dataSource.rollback();
						throw e;
					}

					return null;
				}
			};
		}
	}

	public static class DeleteService extends AbstractService<DataList> {

		private Entity entity;

		private DataList list;

		public DeleteService(Entity entity, DataList list) {
			this.entity = entity;
			this.list = list;
			this.start();
		}

		@Override
		protected Task<DataList> createTask() {
			return new Task<DataList>() {
				@Override
				protected DataList call() throws Exception {
					updateMessage("deleting...");
					int counter = 0;
					int rowCount = list.size();
					updateProgress(counter++, rowCount);
					DataWriter writer = dataSource.delete(entity, list);

					try {
						for (DataRow dataRow : list) {
							if (isCancelled())
								break;
							updateProgress(counter++, rowCount);
							writer.deleteRow(dataRow);
						}
						writer.close();
					} catch (Exception e) {
						dataSource.rollback();
						throw e;
					}

					return null;
				}
			};
		}
	}
}
