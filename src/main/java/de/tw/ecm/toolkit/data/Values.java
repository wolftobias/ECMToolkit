package main.java.de.tw.ecm.toolkit.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Values<T> implements Iterable<T>, Cloneable {

	protected List<T> values = new ArrayList<T>();

	public void add(T value) {
		this.values.add(value);
	}

	public int size() {
		return this.values.size();
	}

	public T get(int i) {
		return this.values.get(i);
	}

	public T[] toArray() {
		return (T[]) this.values.toArray();
	}

	public void setValues(List<T> values) {
		this.values = values;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Values))
			return false;

		ListIterator<T> e1 = this.listIterator();
		ListIterator<?> e2 = ((Values<?>) o).listIterator();
		while (e1.hasNext() && e2.hasNext()) {
			Object o1 = e1.next();
			Object o2 = e2.next();
			if (!(o1 == null ? o2 == null : o1.equals(o2)))
				return false;
		}
		return !(e1.hasNext() || e2.hasNext());
	}

	@Override
	public String toString() {
		return values.toString();
	}

	@Override
	public int hashCode() {
		return values.hashCode();
	}

	@Override
	public Iterator<T> iterator() {
		return this.values.iterator();
	}

	public ListIterator<T> listIterator() {
		return this.values.listIterator();
	}

	public List<T> toList() {
		return new ArrayList<T>(this.values);
	}

	public ObservableList<T> toObservableList() {
		return FXCollections.observableList(this.toList());
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public boolean contains(Object object) {
		return this.values.contains(object);
	}
	
	@Override
	public void forEach(Consumer<? super T> action) {
		this.values.forEach(action);
	}
	
	@Override
	public Spliterator<T> spliterator() {
		return this.values.spliterator();
	}
}
