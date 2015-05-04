package relop;

import java.nio.channels.IllegalSelectorException;

/**
 * The simplest of all join algorithms: nested loops (see textbook, 3rd edition,
 * section 14.4.1, page 454).
 */
public class SimpleJoin extends Iterator {
	
	Iterator currentLeft, currentRight;
	Predicate[] currentPredicate;
	Tuple currentTuple;
	Tuple leftTuple;
	static boolean leftEnded;

	/**
	 * Constructs a join, given the left and right iterators and join predicates
	 * (relative to the combined schema).
	 */
	public SimpleJoin(Iterator left, Iterator right, Predicate... preds) {
// 		throw new UnsupportedOperationException("Not implemented");
        currentLeft = left;
        currentRight = right;
        Schema s = new Schema(left.getSchema().getCount() + right.getSchema().getCount());
        setSchema(s.join(left.getSchema(), right.getSchema()));
        currentPredicate = preds;
        currentTuple = null;
        leftEnded = true;
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
// 		throw new UnsupportedOperationException("Not implemented");
        currentLeft.restart();
        currentRight.restart();
	}

	/**
	 * Returns true if the iterator is open; false otherwise.
	 */
	public boolean isOpen() {
// 		throw new UnsupportedOperationException("Not implemented");
        return (currentLeft.isOpen() && currentRight.isOpen());
	}

	/**
	 * Closes the iterator, releasing any resources (i.e. pinned pages).
	 */
	public void close() {
// 		throw new UnsupportedOperationException("Not implemented");
        currentLeft.close();
        currentRight.close();
	}

	/**
	 * Returns true if there are more tuples, false otherwise.
	 */
	public boolean hasNext() {
// 		throw new UnsupportedOperationException("Not implemented");
        while(!leftEnded){
            while(currentRight.hasNext()){
                Tuple rightTuple = currentRight.getNext();    
                Tuple joined = Tuple.join(leftTuple, rightTuple, getSchema());
                if (currentPredicate == null) {
                    currentTuple = new Tuple(joined.schema, joined.getData());
				    return true;
    			}
    			boolean res = true;
    			for (int i = 0; i < currentPredicate.length; i++) {
    				res &= currentPredicate[i].evaluate(joined);
    			}
    			if (res) {
    			    currentTuple = new Tuple(joined.schema, joined.getData());
    				return true;
    			}
            }
            currentRight.restart();
            leftEnded = true;
        }
        if(currentLeft.hasNext()) {
            leftEnded = false;
            leftTuple = currentLeft.getNext();
            currentRight.restart();
            return (this.hasNext());
        } 
        return false;
        // currentLeft.restart();
	}

	/**
	 * Gets the next tuple in the iteration.
	 * 
	 * @throws IllegalStateException
	 *             if no more tuples
	 */
	public Tuple getNext() {
// 		throw new UnsupportedOperationException("Not implemented");
        if (currentTuple == null) {
			throw new IllegalSelectorException();
		}
		return currentTuple;
	}

} // public class SimpleJoin extends Iterator
