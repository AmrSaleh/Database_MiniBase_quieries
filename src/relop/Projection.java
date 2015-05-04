package relop;

import java.nio.channels.IllegalSelectorException;

/**
 * The projection operator extracts columns from a relation; unlike in
 * relational algebra, this operator does NOT eliminate duplicate tuples.
 */
public class Projection extends Iterator {

	Iterator currentIterator;
	Integer[] projectionFields;
	Tuple currentTuple;
	Schema newSchema;
	/**
	 * Constructs a projection, given the underlying iterator and field numbers.
	 */
	public Projection(Iterator iter, Integer... fields) {
		// throw new UnsupportedOperationException("Not implemented");
		currentIterator = iter;
		projectionFields = fields;
		currentTuple = null;

		newSchema = new Schema(projectionFields.length);
		for (int i = 0; i < projectionFields.length; i++) {
			newSchema.initField(i, currentIterator.getSchema(), projectionFields[i]);
		}
		setSchema(newSchema);

	}

	/**
	 * Gives a one-line explaination of the iterator, repeats the call on any
	 * child iterators, and increases the indent depth along the way.
	 */
	public void explain(int depth) {
		throw new UnsupportedOperationException("Not implemented");
	}

	/**
	 * Restarts the iterator, i.e. as if it were just constructed.
	 */
	public void restart() {
		// throw new UnsupportedOperationException("Not implemented");
		currentIterator.restart();
	}

	/**
	 * Returns true if the iterator is open; false otherwise.
	 */
	public boolean isOpen() {
		// throw new UnsupportedOperationException("Not implemented");
		return currentIterator.isOpen();
	}

	/**
	 * Closes the iterator, releasing any resources (i.e. pinned pages).
	 */
	public void close() {
		// throw new UnsupportedOperationException("Not implemented");
		currentIterator.close();
	}

	/**
	 * Returns true if there are more tuples, false otherwise.
	 */
	public boolean hasNext() {
		// throw new UnsupportedOperationException("Not implemented");
		if (currentIterator.hasNext()) {
			return true;
		} else
			return false;
	}

	/**
	 * Gets the next tuple in the iteration.
	 * 
	 * @throws IllegalStateException
	 *             if no more tuples
	 */
	public Tuple getNext() {
		// throw new UnsupportedOperationException("Not implemented");

		if (hasNext() == false) {
			throw new IllegalSelectorException();
		}

		currentTuple = currentIterator.getNext();
// 		Schema s = new Schema(projectionFields.length);
		Object[] dataFields = new Object[projectionFields.length];

		for (int i = 0; i < projectionFields.length; i++) {
// 			s.initField(i, currentTuple.schema, projectionFields[i]);
			dataFields[i] = currentTuple.getField(projectionFields[i]);
		}

		// ==> schema [phone(0)]
		// ==> data [555(0) , 555(1) , elHaram (2)]

		// Tuple newTuple(s);
		Tuple newTuple = new Tuple(newSchema, dataFields);

		// for(int i = 0; i < projectionFields.length; i++)
		// newTuple.setField(i, currentTuple.getField(projectionFields[i]));

		return newTuple;
	}
} // public class Projection extends Iterator
