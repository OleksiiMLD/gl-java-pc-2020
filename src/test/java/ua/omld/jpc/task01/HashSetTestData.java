package ua.omld.jpc.task01;

import java.util.Set;

/**
 * @author Oleksii Kostetskyi
 */
public class HashSetTestData {

	static final Integer i1 = 1;
	static final Integer i2 = -10;
	static final Integer i3 = 120;
	static final Integer i4 = 234_500;
	static final Integer i5 = 10_989_020;

	static final Set<Integer> set1;
	static final Set<Integer> set2;
	static final Set<Integer> setUnion;
	static final Set<Integer> setIntersection;
	static final Set<Integer> setSubtraction;

	static {
		set1 = new HashSet<>();
		set1.add(i1);
		set1.add(i2);
		set1.add(i3);

		set2 = new HashSet<>();
		set2.add(i3);
		set2.add(i4);
		set2.add(i5);

		setUnion = new HashSet<>();
		setUnion.add(i1);
		setUnion.add(i2);
		setUnion.add(i3);
		setUnion.add(i4);
		setUnion.add(i5);

		setIntersection = new HashSet<>();
		setIntersection.add(i3);

		setSubtraction = new HashSet<>();
		setSubtraction.add(i1);
		setSubtraction.add(i2);
	}

}
