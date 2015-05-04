package relop;

import global.RID;
import global.SearchKey;
import heap.HeapFile;
import index.HashIndex;
import index.HashScan;

/**
 * Wrapper for hash scan, an index access method.
 */
public class KeyScan extends Iterator {

	HeapFile heap_file;
	HashIndex hash_index;
	HashScan hash_scan;
	SearchKey key;
	Schema schema;
	boolean isOpen;

	/**
	 * Constructs an index scan, given the hash index and schema.
	 */
	public KeyScan(Schema schema, HashIndex index, SearchKey key, HeapFile file) {
		// throw new UnsupportedOperationException("Not implemented");
		heap_file = file;
		hash_index = index;
		hash_scan = index.openScan(key);
		this.key = key;
		this.schema = schema;
		setSchema(schema);
		isOpen = true;
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
		if (!isOpen)
			return;
		close();
		hash_scan = hash_index.openScan(key);
		isOpen = true;
	}

	/**
	 * Returns true if the iterator is open; false otherwise.
	 */
	public boolean isOpen() {
		// throw new UnsupportedOperationException("Not implemented");
		return isOpen;
	}

	/**
	 * Closes the iterator, releasing any resources (i.e. pinned pages).
	 */
	public void close() {
		// throw new UnsupportedOperationException("Not implemented");
		if (!isOpen)
			return;
		hash_scan.close();
		isOpen = false;
	}

	/**
	 * Returns true if there are more tuples, false otherwise.
	 */
	public boolean hasNext() {
		// throw new UnsupportedOperationException("Not implemented");
		return (isOpen && hash_scan.hasNext());
	}

	/**
	 * Gets the next tuple in the iteration.
	 * 
	 * @throws IllegalStateException
	 *             if no more tuples
	 */
	public Tuple getNext() {
		// throw new UnsupportedOperationException("Not implemented");
//		if (!hasNext()) {
//			throw new IllegalSelectorException();
//		}
		RID rid = hash_scan.getNext();
		byte[] returned_byte = heap_file.selectRecord(rid);
		return new Tuple(schema, returned_byte);
	}

} // public class KeyScan extends Iterator
