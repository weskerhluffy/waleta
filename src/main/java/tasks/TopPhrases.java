package tasks;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// XXX: https://github.com/stanfordnlp/CoreNLP/blob/master/src/edu/stanford/nlp/util/BinaryHeapPriorityQueue.java

class BinaryHeapPriorityQueue<E> extends AbstractSet<E> implements Iterator<E> {

	/**
	 * An {@code Entry} stores an object in the queue along with its current
	 * location (array position) and priority. uses ~ 8 (self) + 4 (key ptr) + 4
	 * (index) + 8 (priority) = 24 bytes?
	 */
	private static final class Entry<E> {
		public E key;
		public int index;
		public double priority;

		@Override
		public String toString() {
			return key + " at " + index + " (" + priority + ')';
		}
	}

	@Override
	public boolean hasNext() {
		return size() > 0;
	}

	@Override
	public E next() {
		if (size() == 0) {
			throw new NoSuchElementException("Empty PQ");
		}
		return removeFirst();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@code indexToEntry} maps linear array locations (not priorities) to heap
	 * entries.
	 */
	private final List<Entry<E>> indexToEntry;

	/**
	 * {@code keyToEntry} maps heap objects to their heap entries.
	 */
	Map<E, Entry<E>> keyToEntry;

	private Entry<E> parent(Entry<E> entry) {
		int index = entry.index;
		return (index > 0 ? getEntry((index - 1) / 2) : null);
	}

	private Entry<E> leftChild(Entry<E> entry) {
		int leftIndex = entry.index * 2 + 1;
		return (leftIndex < size() ? getEntry(leftIndex) : null);
	}

	private Entry<E> rightChild(Entry<E> entry) {
		int index = entry.index;
		int rightIndex = index * 2 + 2;
		return (rightIndex < size() ? getEntry(rightIndex) : null);
	}

	private int compare(Entry<E> entryA, Entry<E> entryB) {
		int result = compare(entryA.priority, entryB.priority);
		if (result != 0) {
			return result;
		}
		if ((entryA.key instanceof Comparable)
				&& (entryB.key instanceof Comparable)) {
			Comparable<E> key = (Comparable<E>) entryA.key;
			return key.compareTo(entryB.key);
		}
		return result;
	}

	private static int compare(double a, double b) {
		double diff = a - b;
		if (diff > 0.0) {
			return 1;
		}
		if (diff < 0.0) {
			return -1;
		}
		return 0;
	}

	/**
	 * Structural swap of two entries.
	 * 
	 */
	private void swap(Entry<E> entryA, Entry<E> entryB) {
		int indexA = entryA.index;
		int indexB = entryB.index;
		entryA.index = indexB;
		entryB.index = indexA;
		indexToEntry.set(indexA, entryB);
		indexToEntry.set(indexB, entryA);
	}

	/**
	 * Remove the last element of the heap (last in the index array).
	 */
	public void removeLastEntry() {
		Entry<E> entry = indexToEntry.remove(size() - 1);
		keyToEntry.remove(entry.key);
	}

	/**
	 * Get the entry by key (null if none).
	 */
	private Entry<E> getEntry(E key) {
		return keyToEntry.get(key);
	}

	/**
	 * Get entry by index, exception if none.
	 */
	private Entry<E> getEntry(int index) {
		Entry<E> entry = indexToEntry.get(index);
		return entry;
	}

	private Entry<E> makeEntry(E key) {
		Entry<E> entry = new Entry<>();
		entry.index = size();
		entry.key = key;
		entry.priority = Double.NEGATIVE_INFINITY;
		indexToEntry.add(entry);
		keyToEntry.put(key, entry);
		return entry;
	}

	/**
	 * iterative heapify up: move item o at index up until correctly placed
	 */
	private void heapifyUp(Entry<E> entry) {
		while (true) {
			if (entry.index == 0) {
				break;
			}
			Entry<E> parentEntry = parent(entry);
			if (compare(entry, parentEntry) <= 0) {
				break;
			}
			swap(entry, parentEntry);
		}
	}

	/**
	 * On the assumption that leftChild(entry) and rightChild(entry) satisfy the
	 * heap property, make sure that the heap at entry satisfies this property
	 * by possibly percolating the element entry downwards. I've replaced the
	 * obvious recursive formulation with an iterative one to gain (marginal)
	 * speed
	 */
	private void heapifyDown(final Entry<E> entry) {
		Entry<E> bestEntry; // initialized below

		do {
			bestEntry = entry;

			Entry<E> leftEntry = leftChild(entry);
			if (leftEntry != null) {
				if (compare(bestEntry, leftEntry) < 0) {
					bestEntry = leftEntry;
				}
			}

			Entry<E> rightEntry = rightChild(entry);
			if (rightEntry != null) {
				if (compare(bestEntry, rightEntry) < 0) {
					bestEntry = rightEntry;
				}
			}

			if (bestEntry != entry) {
				// Swap min and current
				swap(bestEntry, entry);
				// at start of next loop, we set currentIndex to largestIndex
				// this indexation now holds current, so it is unchanged
			}
		} while (bestEntry != entry);
		// log.info("Done with heapify down");
		// verify();
	}

	private void heapify(Entry<E> entry) {
		heapifyUp(entry);
		heapifyDown(entry);
	}

	/**
	 * Finds the E with the highest priority, removes it, and returns it.
	 * 
	 * @return the E with highest priority
	 */
	public E removeFirst() {
		E first = getFirst();
		remove(first);
		return first;
	}

	/**
	 * Finds the E with the highest priority and returns it, without modifying
	 * the queue.
	 * 
	 * @return the E with minimum key
	 */
	public E getFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return getEntry(0).key;
	}

	/** {@inheritDoc} */
	public double getPriority() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return getEntry(0).priority;
	}

	/**
	 * Searches for the object in the queue and returns it. May be useful if you
	 * can create a new object that is .equals() to an object in the queue but
	 * is not actually identical, or if you want to modify an object that is in
	 * the queue.
	 * 
	 * @return null if the object is not in the queue, otherwise returns the
	 *         object.
	 */
	public E getObject(E key) {
		if (!contains(key))
			return null;
		Entry<E> e = getEntry(key);
		return e.key;
	}

	/** {@inheritDoc} */
	public double getPriority(E key) {
		Entry<E> entry = getEntry(key);
		if (entry == null) {
			return Double.NEGATIVE_INFINITY;
		}
		return entry.priority;
	}

	/**
	 * Adds an object to the queue with the minimum priority
	 * (Double.NEGATIVE_INFINITY). If the object is already in the queue with
	 * worse priority, this does nothing. If the object is already present, with
	 * better priority, it will NOT cause an a decreasePriority.
	 * 
	 * @param key
	 *            an <code>E</code> value
	 * @return whether the key was present before
	 */
	@Override
	public boolean add(E key) {
		if (contains(key)) {
			return false;
		}
		makeEntry(key);
		return true;
	}

	/** {@inheritDoc} */
	public boolean add(E key, double priority) {
		// log.info("Adding " + key + " with priority " + priority);
		if (add(key)) {
			relaxPriority(key, priority);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object key) {
		E eKey = (E) key;
		Entry<E> entry = getEntry(eKey);
		if (entry == null) {
			return false;
		}
		removeEntry(entry);
		return true;
	}

	private void removeEntry(Entry<E> entry) {
		Entry<E> lastEntry = getLastEntry();
		if (entry != lastEntry) {
			swap(entry, lastEntry);
			removeLastEntry();
			heapify(lastEntry);
		} else {
			removeLastEntry();
		}
	}

	private Entry<E> getLastEntry() {
		return getEntry(size() - 1);
	}

	/**
	 * Promotes a key in the queue, adding it if it wasn't there already. If the
	 * specified priority is worse than the current priority, nothing happens.
	 * Faster than add if you don't care about whether the key is new.
	 * 
	 * @param key
	 *            an <code>Object</code> value
	 * @return whether the priority actually improved.
	 */
	public boolean relaxPriority(E key, double priority) {
		Entry<E> entry = getEntry(key);
		if (entry == null) {
			entry = makeEntry(key);
		}
		if (compare(priority, entry.priority) <= 0) {
			return false;
		}
		entry.priority = priority;
		heapifyUp(entry);
		return true;
	}

	/**
	 * Demotes a key in the queue, adding it if it wasn't there already. If the
	 * specified priority is better than the current priority, nothing happens.
	 * If you decrease the priority on a non-present key, it will get added, but
	 * at it's old implicit priority of Double.NEGATIVE_INFINITY.
	 * 
	 * @param key
	 *            an <code>Object</code> value
	 * @return whether the priority actually improved.
	 */
	public boolean decreasePriority(E key, double priority) {
		Entry<E> entry = getEntry(key);
		if (entry == null) {
			entry = makeEntry(key);
		}
		if (compare(priority, entry.priority) >= 0) {
			return false;
		}
		entry.priority = priority;
		heapifyDown(entry);
		return true;
	}

	/**
	 * Changes a priority, either up or down, adding the key it if it wasn't
	 * there already.
	 * 
	 * @param key
	 *            an <code>Object</code> value
	 * @return whether the priority actually changed.
	 */
	public boolean changePriority(E key, double priority) {
		Entry<E> entry = getEntry(key);
		if (entry == null) {
			entry = makeEntry(key);
		}
		if (compare(priority, entry.priority) == 0) {
			return false;
		}
		entry.priority = priority;
		heapify(entry);
		return true;
	}

	/**
	 * Checks if the queue is empty.
	 * 
	 * @return a <code>boolean</code> value
	 */
	@Override
	public boolean isEmpty() {
		return indexToEntry.isEmpty();
	}

	/**
	 * Get the number of elements in the queue.
	 * 
	 * @return queue size
	 */
	@Override
	public int size() {
		return indexToEntry.size();
	}

	/**
	 * Returns whether the queue contains the given key.
	 */
	@SuppressWarnings("SuspiciousMethodCalls")
	@Override
	public boolean contains(Object key) {
		return keyToEntry.containsKey(key);
	}

	/**
	 * Clears the queue.
	 */
	@Override
	public void clear() {
		indexToEntry.clear();
		keyToEntry.clear();
	}

	// private void verify() {
	// for (int i = 0; i < indexToEntry.size(); i++) {
	// if (i != 0) {
	// // check ordering
	// if (compare(getEntry(i), parent(getEntry(i))) < 0) {
	// log.info("Error in the ordering of the heap! ("+i+")");
	// System.exit(0);
	// }
	// }
	// // check placement
	// if (i != ((Entry)indexToEntry.get(i)).index)
	// log.info("Error in placement in the heap!");
	// }
	// }

	public BinaryHeapPriorityQueue() {
		keyToEntry = new HashMap<E, BinaryHeapPriorityQueue.Entry<E>>();
		indexToEntry = new ArrayList<>();
	}

	// @Override
	public List<E> toSortedList() {
		List<E> sortedList = new ArrayList<>(size());
		BinaryHeapPriorityQueue<E> queue = this.deepCopy();
		while (!queue.isEmpty()) {
			sortedList.add(queue.removeFirst());
		}
		return sortedList;
	}

	public BinaryHeapPriorityQueue<E> deepCopy() {
		BinaryHeapPriorityQueue<E> queue = new BinaryHeapPriorityQueue<>();
		for (Entry<E> entry : keyToEntry.values()) {
			queue.relaxPriority(entry.key, entry.priority);
		}
		return queue;
	}

	@Override
	public Iterator<E> iterator() {
		return Collections.unmodifiableCollection(toSortedList()).iterator();
	}

}

public class TopPhrases {

	public static List<String> findTopPhrases(String fileName,
			Integer numberTops) {
		List<String> topPhrases = new ArrayList<>();
		BinaryHeapPriorityQueue<String> phrasesPq = new BinaryHeapPriorityQueue();

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				String phrases[] = line.split("\\|", -1);
				System.out.println(phrases + " mierda " + phrases.length);
				for (String phrase : Arrays.asList(phrases)) {
					System.out.println(phrase);
					updatePhraseCardinality(phrasesPq, phrase);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Integer i = 0; i < numberTops; i++) {
			topPhrases.add(phrasesPq.removeFirst());
		}
		return topPhrases;
	}

	public static void updatePhraseCardinality(
			BinaryHeapPriorityQueue<String> phrasesPq, String phrase) {
		String phrase_stored;
		if ((phrase_stored = phrasesPq.getObject(phrase)) != null) {
			Integer currentCardinality = (int) phrasesPq.getPriority(phrase);
			currentCardinality++;
			phrasesPq.relaxPriority(phrase, currentCardinality);
		} else {
			phrasesPq.add(phrase, 1);
		}
	}
}
