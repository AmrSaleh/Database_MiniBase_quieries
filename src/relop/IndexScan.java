package relop;

import global.RID;
import global.SearchKey;
import heap.HeapFile;
import index.BucketScan;
import index.HashIndex;

/**
 * Wrapper for bucket scan, an index access method.
 */
public class IndexScan extends Iterator {

	HeapFile heap_file;
	HashIndex hash_index;
	BucketScan bucket_scan;
	Schema schema;
	boolean isOpen;

	/**
	 * Constructs an index scan, given the hash index and schema.
	 */
	public IndexScan(Schema schema, HashIndex index, HeapFile file) {
		// throw new UnsupportedOperationException("Not implemented");
		heap_file = file;
		hash_index = index;
		bucket_scan = index.openScan();
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
		bucket_scan = hash_index.openScan();
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
		bucket_scan.close();
		isOpen = false;
	}

	/**
	 * Returns true if there are more tuples, false otherwise.
	 */
	public boolean hasNext() {
		// throw new UnsupportedOperationException("Not implemented");
		return (isOpen && bucket_scan.hasNext());
	}

	/**
	 * Gets the next tuple in the iteration.
	 * 
	 * @throws IllegalStateException
	 *             if no more tuples
	 */
	public Tuple getNext() {
		// throw new UnsupportedOperationException("Not implemented");
		RID rid = bucket_scan.getNext();
		byte[] returned_byte = heap_file.selectRecord(rid);
		return new Tuple(schema, returned_byte);
	}

	/**
	 * Gets the key of the last tuple returned.
	 */
	public SearchKey getLastKey() {
		// throw new UnsupportedOperationException("Not implemented");
		return bucket_scan.getLastKey();
	}

	/**
	 * Returns the hash value for the bucket containing the next tuple, or
	 * maximum number of buckets if none.
	 */
	public int getNextHash() {
		// throw new UnsupportedOperationException("Not implemented");
		return bucket_scan.getNextHash();
	}

} // public class IndexScan extends Iterator
