package main.java.de.tw.ecm.toolkit.service;

import javafx.concurrent.Task;
import main.java.de.tw.ecm.toolkit.data.Attribute;
import main.java.de.tw.ecm.toolkit.data.Attributes;
import main.java.de.tw.ecm.toolkit.data.Entities;
import main.java.de.tw.ecm.toolkit.data.Entity;

public class EntityService extends AbstractService<Void> {

	public EntityService() {
		this.start();
	}

	@Override
	protected Task<Void> createTask() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				updateMessage("loading entites...");

				Entities nativeEntities = dataSource.getEntities();
				Entities entities = selectedRepository.getEntities();

				for (Entity nativeEntity : nativeEntities) {
					String nativeId = nativeEntity.getId();
					Entity entity = entities.getById(nativeId);
					if (entity != null) {
						nativeEntity.setCaption(entity.getCaption());
						mergeAttributes(entity.getAttributes(),
								nativeEntity.getAttributes());
					}
				}

				selectedRepository.setEntities(nativeEntities);
				return null;
			}
		};
	}

	private void mergeAttributes(Attributes attributes,
			Attributes nativeAttributes) {
		for (Attribute nativeAttribute : nativeAttributes) {
			String nativeName = nativeAttribute.getName();
			Attribute attribute = attributes.getByName(nativeName);

			if (attribute != null)
				this.mergeAttribute(attribute, nativeAttribute);
		}
	}

	private void mergeAttribute(Attribute attribute, Attribute nativeAttribute) {
		nativeAttribute.setCaption(attribute.getCaption());
	}

}
