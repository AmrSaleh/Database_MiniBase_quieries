package relop;

import java.nio.channels.IllegalSelectorException;

/**
 * The projection operator extracts columns from a relation; unlike in
 * relational algebra, this operator does NOT eliminate duplicate tuples.
 */
public class Projection extends Iterator {
	
	Iterator currentIterator;
	Integer[] currentField;
	Tuple currentTuple;
	Schema currentSchema;

	/**
	 * Constructs a projection, given the underlying iterator and field numbers.
	 */
	public Projection(Iterator iter, Integer... fields) {
//		throw new UnsupportedOperationException("Not implemented");
		currentIterator = iter;
		currentField = fields;
		currentTuple = null;
		currentSchema = new Schema(currentField.length);
		setSchema(currentSchema);
		for(int i = 0; i < currentField.length; i++){
			currentSchema.initField(i, iter.getSchema(), currentField[i]);
		}
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
//		throw new UnsupportedOperationException("Not implemented");
		currentIterator.restart();
	}

	/**
	 * Returns true if the iterator is open; false otherwise.
	 */
	public boolean isOpen() {
//		throw new UnsupportedOperationException("Not implemented");
		return currentIterator.isOpen();
	}

	/**
	 * Closes the iterator, releasing any resources (i.e. pinned pages).
	 */
	public void close() {
//		throw new UnsupportedOperationException("Not implemented");
		currentIterator.close();
	}

	/**
	 * Returns true if there are more tuples, false otherwise.
	 */
	public boolean hasNext() {
//		throw new UnsupportedOperationException("Not implemented");
		if(currentIterator.hasNext()){
			currentTuple = currentIterator.getNext();
			Tuple t = new Tuple(currentSchema);
		    for(int i = 0; i < currentField.length; i++){
		        t.setField(i, currentTuple.getField(currentField[i]));
		    }
			currentTuple = t;
			return true;
		}else
			return false;
	}

	/**
	 * Gets the next tuple in the iteration.
	 * 
	 * @throws IllegalStateException
	 *             if no more tuples
	 */
	public Tuple getNext() {
//		throw new UnsupportedOperationException("Not implemented");
		if (currentTuple == null) {
			throw new IllegalSelectorException();
		}
		return currentTuple;
	}

} // public class Projection extends Iterator
