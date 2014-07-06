package main.java.de.tw.ecm.toolkit.service;

import java.io.File;
import java.util.List;

import javafx.concurrent.Task;
import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.DataRow;
import main.java.de.tw.ecm.toolkit.data.Entity;
import main.java.de.tw.ecm.toolkit.data.reader.CSVDataReader;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;
import main.java.de.tw.ecm.toolkit.data.writer.CSVDataWriter;
import main.java.de.tw.ecm.toolkit.data.writer.DataWriter;

public class FileService {

	public FileService() {
	}

	public static class ReadService extends AbstractService<DataList> {

		private File file;

		private Entity entity;

		public ReadService(File file, Entity entity) {
			this.file = file;
			this.entity = entity;
			this.start();
		}

		@Override
		protected Task<DataList> createTask() {
			return new Task() {
				@Override
				protected Object call() throws Exception {
					updateMessage("reading file...");
					DataList dataList = null;
					DataReader reader = null;

					try {
						reader = new CSVDataReader(file, entity);
						dataList = new DataList(entity);
						while (reader.next()) {
							dataList.add(reader.readRow());
						}
					} finally {
						close(reader);
					}

					return dataList;
				}
			};
		}

		protected void close(DataReader reader) throws Exception {
			if (reader != null)
				reader.close();
		}
	}

	public static class WriteService extends AbstractService<Void> {

		private File file;

		private Entity entity;

		private DataList list;

		public WriteService(File file, Entity entity, DataList list) {
			this.file = file;
			this.entity = entity;
			this.list = list;
			this.start();
		}

		@Override
		protected Task<Void> createTask() {
			return new Task() {
				@Override
				protected Object call() throws Exception {
					CSVDataWriter writer = null;
					updateMessage("writing file...");
					List<String> headers = list.getHeaderCaptions();

					if (headers.isEmpty())
						headers = list.getHeaderNames();
					try {
						writer = new CSVDataWriter(file);
						writer.writeHeader(headers);

						for (int i = 0; i < list.size(); i++) {
							DataRow dataRow = list.get(i);
							writer.writeRow(dataRow);
						}
					} finally {
						close(writer);
					}

					return null;
				}
			};
		}

		protected void close(DataWriter writer) throws Exception {
			if (writer != null)
				writer.close();
		}
	}
}
