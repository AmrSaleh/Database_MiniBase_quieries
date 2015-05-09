package relop;

import java.nio.channels.IllegalSelectorException;

/**
 * The selection operator specifies which tuples to retain under a condition; in
 * Minibase, this condition is simply a set of independent predicates logically
 * connected by AND operators.
 */
public class Selection extends Iterator {

	Predicate[] currentPredicate;
	Iterator currentIterator;
	Tuple currentTuple;

	// Might need isOpen
	// is iter == null

	/**
	 * Constructs a selection, given the underlying iterator and predicates.
	 */
	public Selection(Iterator iter, Predicate... preds) {
		// throw new UnsupportedOperationException("Not implemented");
		currentPredicate = preds;
		currentIterator = iter;
		setSchema(iter.getSchema());
		currentTuple = null;
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
		// Might need to check that the iterator is open
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
		// Might need to check that the iterator is open
		currentIterator.close();
	}

	/**
	 * Returns true if there are more tuples, false otherwise.
	 */
	public boolean hasNext() {
		// throw new UnsupportedOperationException("Not implemented");

		// Check if the get next executed alone without the hasNext

		while (currentIterator.hasNext()) {
			currentTuple = currentIterator.getNext();
//			currentTuple.print();
			if (currentPredicate == null) {
				return true;
			}
			boolean res = true;
			for (int i = 0; i < currentPredicate.length; i++) {
				res &= currentPredicate[i].evaluate(currentTuple);
			}
			if (res) {
				return true;
			}
		}
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
		if (currentTuple == null) {
			throw new IllegalSelectorException();
		}
		return currentTuple;
	}

} // public class Selection extends Iterator
