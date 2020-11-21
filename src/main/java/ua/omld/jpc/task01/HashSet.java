package ua.omld.jpc.task01;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/**
 * This class is a simple implementation of the <tt>Set</tt> interface,
 * backed by a hash table. It makes no guarantees as to the iteration order
 * of the set. This class permits the <tt>null</tt> element.
 *
 * <p><strong>This implementation is not tread-safe.</strong>
 *
 * <p>The iterators returned by this class's <tt>iterator</tt> method are
 * <i>not fail-fast</i>.
 *
 * @author Oleksii Kostetskyi
 */
public class HashSet<E> implements Set<E> {

	private static final int MAX_CAPACITY = 1 << 30;
	private static final int DEFAULT_CAPACITY = 1 << 4;

	private final Entry<E>[] buckets;
	private int size;

	/**
	 * Constructs a new, empty set; the hashtable has default initial capacity (16).
	 */
	public HashSet() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructs a new, empty set; the hashtable has given initial capacity.
	 *
	 * @param initialCapacity the initial capacity of the hashtable
	 * @throws IllegalArgumentException if the initial capacity is less then 1 and greater then 2^30.
	 */
	public HashSet(int initialCapacity) {
		if (initialCapacity < 1 || initialCapacity > MAX_CAPACITY) {
			throw new IllegalArgumentException("Capacity is out of bounds: " + initialCapacity);
		}
		size = 0;
		buckets = new Entry[initialCapacity];
	}

	/**
	 * Constructs a new set containing the elements in the specified collection.
	 * Hashtable capacity is of collection size.
	 *
	 * @param c the collection whose elements are to be placed into this set
	 */
	public HashSet(Collection<E> c) {
		this(c.size());
		addAll(c);
	}

	/**
	 * Returns the number of elements in this set (its cardinality).
	 *
	 * @return the number of elements in this set (its cardinality)
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns <tt>true</tt> if this set contains no elements.
	 *
	 * @return <tt>true</tt> if this set contains no elements
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns <tt>true</tt> if this set contains the specified element.
	 * More formally, returns <tt>true</tt> if and only if this set
	 * contains an element <tt>e</tt> such that <tt>(e.equals(o))</tt>.
	 *
	 * @param o element whose presence in this set is to be tested
	 * @return <tt>true</tt> if this set contains the specified element
	 */
	@Override
	public boolean contains(Object o) {
		int index = hashFunction(Objects.hashCode(o));
		Entry<E> current = buckets[index];

		while (current != null) {
			if (Objects.equals(current.value, o)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	/**
	 * Returns an iterator over the elements in this set.  The elements are
	 * returned in no particular order.
	 *
	 * @return an iterator over the elements in this set
	 */
	@Override
	public Iterator<E> iterator() {
		return new SimpleHashSetIterator();
	}

	/**
	 * Returns an array containing all of the elements in this set.
	 *
	 * <p>The returned array is "safe" in that no references to it
	 * are maintained by this set.
	 *
	 * <p>This method acts as bridge between array-based and collection-based
	 * APIs.
	 *
	 * @return an array containing all the elements in this set
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		Iterator<E> iterator = iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			array[i] = iterator.next();
		}
		return array;
	}

	/**
	 * Returns an array containing all of the elements in this set; the
	 * runtime type of the returned array is that of the specified array.
	 * If the set fits in the specified array, it is returned therein.
	 * Otherwise, a new array is allocated with the runtime type of the
	 * specified array and the size of this set.
	 *
	 * <p>If this set fits in the specified array with room to spare
	 * (i.e., the array has more elements than this set), the element in
	 * the array immediately following the end of the set is set to
	 * <tt>null</tt>.
	 *
	 * <p>Like the {@link #toArray()} method, this method acts as bridge between
	 * array-based and collection-based APIs.
	 *
	 * @param a the array into which the elements of this set are to be
	 *          stored, if it is big enough; otherwise, a new array of the same
	 *          runtime type is allocated for this purpose.
	 * @return an array containing all the elements in this set
	 * @throws ArrayStoreException  if the runtime type of the specified array
	 *                              is not a supertype of the runtime type of every element in this
	 *                              set
	 * @throws NullPointerException if the specified array is null
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		T[] r = a.length >= size ? a :
				(T[]) java.lang.reflect.Array
						.newInstance(a.getClass().getComponentType(), size);

		Iterator<E> iterator = iterator();
		int i;
		for (i = 0; iterator.hasNext(); i++) {
			r[i] = (T) iterator.next();
		}
		while (i < r.length) {
			r[i++] = null;
		}
		return r;
	}

	/**
	 * Adds the specified element to this set if it is not already present.
	 * If this set already contains the element, the call leaves the set
	 * unchanged and returns <tt>false</tt>.  In combination with the
	 * restriction on constructors, this ensures that sets never contain
	 * duplicate elements.
	 *
	 * @param e element to be added to this set
	 * @return <tt>true</tt> if this set did not already contain the specified
	 * element
	 */
	@Override
	public boolean add(E e) {
		int index = hashFunction(Objects.hashCode(e));
		Entry<E> current = buckets[index];

		while (current != null) {
			if (Objects.equals(current.value, e)) {
				return false;
			}
			current = current.next;
		}
		Entry<E> entry = new Entry<>();
		entry.value = e;
		entry.next = buckets[index];
		buckets[index] = entry;
		size++;
		return true;
	}

	/**
	 * Removes the specified element from this set if it is present.
	 * Returns <tt>true</tt> if this set contained the element.
	 *
	 * @param o object to be removed from this set, if present
	 * @return <tt>true</tt> if this set contained the specified element
	 */
	@Override
	public boolean remove(Object o) {
		int index = hashFunction(Objects.hashCode(o));
		Entry<E> current = buckets[index];
		Entry<E> previous = null;

		while (current != null) {
			if (Objects.equals(current.value, o)) {
				if (previous == null) {
					buckets[index] = current.next;
				} else {
					previous.next = current.next;
				}
				size--;
				return true;
			}
			previous = current;
			current = current.next;
		}

		return false;
	}

	/**
	 * Returns <tt>true</tt> if this set contains all of the elements of the
	 * specified collection.  If the specified collection is also a set, this
	 * method returns <tt>true</tt> if it is a <i>subset</i> of this set.
	 *
	 * @param c collection to be checked for containment in this set
	 * @return <tt>true</tt> if this set contains all of the elements of the
	 * specified collection
	 * @throws NullPointerException if the specified collection is null
	 * @see #contains(Object)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Adds all of the elements in the specified collection to this set if
	 * they're not already present.  If the specified collection is also a set,
	 * the <tt>addAll</tt> operation effectively modifies this set so that
	 * its value is the <i>union</i> of the two sets.  The behavior of this
	 * operation is undefined if the specified collection is modified while
	 * the operation is in progress.
	 *
	 * @param c collection containing elements to be added to this set
	 * @return <tt>true</tt> if this set changed as a result of the call
	 * @throws NullPointerException if the specified collection is null
	 * @see #add(Object)
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean modified = false;
		for (E e : c) {
			modified |= add(e);
		}
		return modified;
	}

	/**
	 * Retains only the elements in this set that are contained in the
	 * specified collection.  In other words, removes from this set all of its
	 * elements that are not contained in the specified collection.
	 * If the specified collection is also a set, this
	 * operation effectively modifies this set so that its value is the
	 * <i>intersection</i> of the two sets.
	 *
	 * @param c collection containing elements to be retained in this set
	 * @return <tt>true</tt> if this set changed as a result of the call
	 * @throws NullPointerException if the specified collection is null
	 * @see #remove(Object)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		boolean modified = false;
		Iterator<E> it = iterator();
		while (it.hasNext()) {
			if (!c.contains(it.next())) {
				it.remove();
				modified = true;
			}
		}
		return modified;
	}

	/**
	 * Removes from this set all of its elements that are contained in the
	 * specified collection.  If the specified collection is also a set,
	 * this operation effectively modifies this set so that its value is
	 * the <i>asymmetric set difference</i> of the two sets.
	 *
	 * @param c collection containing elements to be removed from this set
	 * @return <tt>true</tt> if this set changed as a result of the call
	 * @throws NullPointerException if the specified collection is null
	 * @see #remove(Object)
	 * @see #contains(Object)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		boolean modified = false;
		for (Object e : c) {
			modified |= remove(e);
		}
		return modified;
	}

	/**
	 * Removes all of the elements from this set.
	 * The set will be empty after this call returns.
	 */
	@Override
	public void clear() {
		Arrays.fill(buckets, null);
		size = 0;
	}

	/**
	 * Compares the specified object with this set for equality.  Returns
	 * <tt>true</tt> if the specified object is also a set, the two sets
	 * have the same size, and every member of the specified set is
	 * contained in this set.
	 *
	 * @param o object to be compared for equality with this set
	 * @return <tt>true</tt> if the specified object is equal to this set
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Set)) {
			return false;
		}
		Collection<E> c = (Collection<E>) o;
		if (c.size() != size()) {
			return false;
		}
		try {
			return containsAll(c);
		} catch (ClassCastException | NullPointerException unused) {
			return false;
		}
	}

	/**
	 * Returns the hash code value for this set.  The hash code of a set is
	 * defined to be the sum of the hash codes of the elements in the set.
	 *
	 * @return the hash code value for this set
	 * @see Object#equals(Object)
	 * @see Set#equals(Object)
	 */
	@Override
	public int hashCode() {
		int h = 0;
		Iterator<E> i = iterator();
		while (i.hasNext()) {
			E obj = i.next();
			if (obj != null) {
				h += obj.hashCode();
			}
		}
		return h;
	}

	private int hashFunction(int hashCode) {
		return (hashCode < 0 ? -hashCode : hashCode) % buckets.length;
	}

	private static class Entry<E> {
		E value;
		Entry<E> next;
	}

	private class SimpleHashSetIterator implements Iterator<E> {

		private int currentBucket;
		private int previousBucket;
		private Entry<E> currentEntry;
		private Entry<E> previousEntry;
		private boolean canRemove;

		SimpleHashSetIterator() {
			currentEntry = previousEntry = null;
			currentBucket = previousBucket = -1;
			canRemove = false;
		}

		@Override
		public boolean hasNext() {
			if (currentEntry != null && currentEntry.next != null) {
				return true;
			}

			for (int index = currentBucket + 1; index < buckets.length; index++) {
				if (buckets[index] != null) {
					return true;
				}
			}
			return false;
		}

		@Override
		public E next() {
			previousEntry = currentEntry;
			previousBucket = currentBucket;
			canRemove = true;

			if (currentEntry == null || currentEntry.next == null) {
				do {
					currentBucket++;
				} while (currentBucket < buckets.length && buckets[currentBucket] == null);

				if (currentBucket < buckets.length) {
					currentEntry = buckets[currentBucket];
				} else {
					throw new NoSuchElementException();
				}
			} else {
				currentEntry = currentEntry.next;
			}

			return currentEntry.value;
		}

		@Override
		public void remove() {
			if (!canRemove) {
				throw new IllegalStateException("First call next().");
			}
			if (previousEntry != null && previousEntry.next != null) {
				previousEntry.next = currentEntry.next;
			} else {
				buckets[currentBucket] = currentEntry.next;
			}
			currentEntry = previousEntry;
			currentBucket = previousBucket;
			size--;
			canRemove = false;
		}
	}
}
