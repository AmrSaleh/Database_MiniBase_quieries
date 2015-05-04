package relop;

import global.RID;
import heap.HeapFile;
import heap.HeapScan;

/**
 * Wrapper for heap file scan, the most basic access method. This "iterator"
 * version takes schema into consideration and generates real tuples.
 */
public class FileScan extends Iterator {
	HeapFile heap_file;
	HeapScan heap_scan;
	RID rid;
	Schema schema;
	boolean isOpen;

	/**
	 * Constructs a file scan, given the schema and heap file.
	 */
	public FileScan(Schema schema, HeapFile file) {
		heap_file = file;
		heap_scan = file.openScan();
		this.schema = schema;
		setSchema(schema);
		rid = new RID();
		isOpen = true;
	}

	/**
	 * Gives a one-line explanation of the iterator, repeats the call on any
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
		heap_scan = heap_file.openScan();
		isOpen = true;
		rid = new RID();
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
		heap_scan.close();
		isOpen = false;
	}

	/**
	 * Returns true if there are more tuples, false otherwise.
	 */
	public boolean hasNext() {
		// throw new UnsupportedOperationException("Not implemented");
		return (isOpen && heap_scan.hasNext());
	}

	/**
	 * Gets the next tuple in the iteration.
	 * 
	 * @throws IllegalStateException
	 *             if no more tuples
	 */
	public Tuple getNext() {
		// throw new UnsupportedOperationException("Not implemented");
		byte[] returned_byte = heap_scan.getNext(rid);
		return new Tuple(schema, returned_byte);
	}

	/**
	 * Gets the RID of the last tuple returned.
	 */
	public RID getLastRID() {
		// throw new UnsupportedOperationException("Not implemented");
		RID last_rid = new RID();
		last_rid.copyRID(rid);
		return last_rid;
	}
	

} // public class FileScan extends Iterator
