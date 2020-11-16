package ua.omld.jpc.task01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ua.omld.jpc.task01.HashSetTestData.i1;
import static ua.omld.jpc.task01.HashSetTestData.i2;
import static ua.omld.jpc.task01.HashSetTestData.i3;
import static ua.omld.jpc.task01.HashSetTestData.i4;
import static ua.omld.jpc.task01.HashSetTestData.i5;
import static ua.omld.jpc.task01.HashSetTestData.setIntersection;
import static ua.omld.jpc.task01.HashSetTestData.setSubtraction;
import static ua.omld.jpc.task01.HashSetTestData.setUnion;

/**
 * @author Oleksii Kostetskyi
 */
@DisplayName("A HashSet ")
class HashSetTest {

	/**
	 * For catching Exceptions there is used Java 1.7 style with anonymous classes, according to task.
	 */

	Set<Integer> set;

	@Nested
	@DisplayName(" when new")
	class WhenNew {

		@BeforeEach
		void createHash() {
			set = new HashSet<>();
		}

		@Test
		void sizeIsZero() {
			assertEquals(0, set.size(), "New Set size must be 0.");
		}

		@Test
		void setIsEmpty() {
			assertTrue(set.isEmpty(), "New set must be empty.");
		}

		@Test
		void addReturnsTrue() {
			assertTrue(set.add(i1));
		}

		@Test
		void addNullThrowsNullPointerException() {
			assertThrows(NullPointerException.class, new Executable() {
				@Override
				public void execute() throws Throwable {
					set.add(null);
				}
			}, "Nulls are not permitted.");
		}

		@Test
		void removeElementReturnsFalse() {
			assertFalse(set.remove(i1), "No elements in Set - nothing to remove.");
		}

		@Test
		void removeNullThrowsNullPointerException() {
			assertThrows(NullPointerException.class, new Executable() {
				@Override
				public void execute() throws Throwable {
					set.remove(null);
				}
			}, "Nulls are not permitted.");
		}

		@Test
		void iteratorDoesNotGiveNextElement() {
			Iterator<Integer> iterator = set.iterator();
			assertNotNull(iterator, "Iterator always present.");
			assertFalse(iterator.hasNext(), "Empty Set does not have next element.");
		}

		@Test
		void toArrayReturnsEmptyArray() {
			assertTrue(Arrays.deepEquals(new Integer[0], set.toArray()));
		}

		@Test
		void toArrayWithGivenArrayReturnsEmptiedArray() {
			assertTrue(Arrays.deepEquals(new Object[5], set.toArray(new Object[]{1, 2, 3, 4, 5})));
		}

		@Test
		void addAllReturnsTrue() {
			assertTrue(set.addAll(Arrays.asList(i1, i2, i3, i4, i5)), "All elements must be added.");
		}

		@Test
		void addAllWithNullThrowsNullPointerException() {
			assertThrows(NullPointerException.class, new Executable() {
				@Override
				public void execute() throws Throwable {
					set.addAll(Arrays.asList(i1, null));
				}
			}, "Nulls are not permitted.");
		}

		@Test
		void removeAllReturnsFalse() {
			assertFalse(set.removeAll(Arrays.asList(i1, i2, i3, i4, i5)), "There are no elements in new Set.");
		}

		@Test
		void removeAllWithNullThrowsNullPointerException() {
			assertThrows(NullPointerException.class, new Executable() {
				@Override
				public void execute() throws Throwable {
					set.removeAll(Arrays.asList(i1, null));
				}
			}, "Nulls are not permitted.");
		}
	}

	@Nested
	@DisplayName("when adding an element")
	class WhenAddingOne {

		@BeforeEach
		void createSetAndAddElement() {
			set = new HashSet<>();
			set.add(i1);
		}

		@Test
		void sizeIsOne() {
			assertEquals(1, set.size(), "Set size must be 1.");
		}

		@Test
		void setIsNotEmpty() {
			assertFalse(set.isEmpty(), "Set with elements can`t be empty.");
		}

		@Test
		void removeElementReturnsTrue() {
			assertTrue(set.remove(i1));
		}

		@Test
		void afterRemoveElementSetIsEmpty() {
			set.remove(i1);
			assertTrue(set.isEmpty(), "Set must be empty.");
		}

		@Test
		void setContainsElement() {
			assertTrue(set.contains(i1), "Set must contain " + i1);
		}

		@Test
		void containsAllReturnsFalse() {
			assertFalse(set.containsAll(Arrays.asList(i2, i3, i4)));
		}


		@Test
		void afterClearSetIsEmpty() {
			set.clear();
			assertTrue(set.isEmpty(), "After clear Set must be empty.");
		}

		@Test
		void addSameElementReturnsFalse() {
			assertFalse(set.add(i1), "Same element must not be added.");
		}

		@Test
		void toArrayWithGivenWrongTypeArrayThrowsArrayStoreException() {
			assertThrows(ArrayStoreException.class, new Executable() {
				@Override
				public void execute() throws Throwable {
					set.toArray(new String[]{"1", "2", "3", "4", "5"});
				}
			}, "Wrong array type not permitted.");
		}
	}

	@Nested
	@DisplayName("when adding few elements")
	class WhenAddingFive {

		@BeforeEach
		void createSetAndAddFiveElements() {
			set = new HashSet<>();
			set.add(i1);
			set.add(i2);
			set.add(i3);
			set.add(i4);
			set.add(i5);
		}

		@Test
		void sizeIsFive() {
			assertEquals(5, set.size(), "Set size must be 5.");
		}

		@Test
		void removeTwoElementsSizeIsThree() {
			set.remove(i2);
			set.remove(i4);
			assertEquals(3, set.size(), "Set size must be 3.");
		}

		@Test
		void containsAddedElements() {
			assertTrue(set.contains(i1), "Set must contain " + i1);
			assertTrue(set.contains(i2), "Set must contain " + i2);
			assertTrue(set.contains(i3), "Set must contain " + i3);
			assertTrue(set.contains(i4), "Set must contain " + i4);
			assertTrue(set.contains(i5), "Set must contain " + i5);
		}

		@Test
		void iteratorReturnsFiveElements() {
			Iterator<Integer> iterator = set.iterator();
			for (int i = 0; i < 5; i++) {
				assertTrue(iterator.hasNext());
				assertNotNull(iterator.next());
			}
			assertFalse(iterator.hasNext());
		}

		@Test
		void removeOnIteratorWithoutNextThrowsIllegalStateException() {
			final Iterator<Integer> iterator = set.iterator();
			assertThrows(IllegalStateException.class, new Executable() {
				@Override
				public void execute() throws Throwable {
					iterator.remove();
				}
			}, "Remove() without next() not permitted.");
		}

		@Test
		void doubleRemoveOnIteratorThrowsIllegalStateException() {
			final Iterator<Integer> iterator = set.iterator();
			iterator.next();
			iterator.remove();
			assertThrows(IllegalStateException.class, new Executable() {
				@Override
				public void execute() throws Throwable {
					iterator.remove();
				}
			}, "Double Remove() not permitted.");
		}

		@Test
		void containsAllWithSubSetReturnsTrue() {
			assertTrue(set.containsAll(Arrays.asList(i2, i3, i4)), "Set must contain its sub-set.");
		}

		@Test
		void withNullElementContainsAllThrowsNullPointerException() {
			assertThrows(NullPointerException.class, new Executable() {
				@Override
				public void execute() throws Throwable {
					set.containsAll(Arrays.asList(i2, null, i4));
				}
			}, "Nulls are not permitted.");
		}
	}

	@Nested
	@DisplayName("basic math operations")
	class MathOperationsWithTwoSets {

		Set<Integer> setA;
		Set<Integer> setB;

		@BeforeEach
		void setUp() {
			setA = new HashSet<>(HashSetTestData.set1);
			setB = new HashSet<>(HashSetTestData.set2);
		}

		@Test
		void whenAddAllGetUnion() {
			assertTrue(setA.addAll(setB));
			assertEquals(setUnion, setA, "Union must contain all elements.");
		}

		@Test
		void whenRetainAllGetIntersection() {
			assertTrue(setA.retainAll(setB));
			assertEquals(setIntersection, setA, "Intersection must contain only common elements.");
		}

		@Test
		void whenRemoveAllGetSubtraction() {
			assertTrue(setA.removeAll(setB));
			assertEquals(setSubtraction, setA, "Subtraction must contain only different elements.");
		}
	}
}