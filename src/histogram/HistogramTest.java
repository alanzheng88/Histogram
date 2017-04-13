package histogram;

import java.util.Iterator;
import java.util.Random;

import junit.framework.TestCase;

/**
 * JUnit Test class for the Histogram class. 
 */
public class HistogramTest extends TestCase {
	
	/**
	 * Custom test function which validates the distribution counts found
	 * in the Histogram's Bars (the input Iterator) match the expected counts.
	 * @param given An iterator to the Bars of the Histogram.
	 * @param expectedCounts The expected counts for each bar.
	 */
	
	public void assertBarCountEqual(Iterator<Histogram.Bar> given, int[] expectedCounts) {
		assertNotNull(given);
		assertNotNull(expectedCounts);
		int index = 0;
		while (given.hasNext()) {
			Histogram.Bar bar = given.next();
			assertTrue(index < expectedCounts.length);
			assertNotNull(bar);
			assertEquals(bar.getCount(), expectedCounts[index]);
			index++;
		}
		// In case no asserts were checked above (empty set).
		assertTrue(true);
	}
	
	/**
	 * Custom test function which validates the ranges are correct on the bars.
	 * @param given An iterator to the Bars of the Histogram.
	 * @param expectedRange The expected size of the range (i.e., span) of each bar.
	 */
	public void assertBarsRange(Iterator<Histogram.Bar> given, int expectedRange) {
		assertNotNull(given);
		while (given.hasNext()) {
			Histogram.Bar bar = given.next();
			assertNotNull(bar);
			int rangeWidth = bar.getRangeMax() - bar.getRangeMin() + 1;
			assertEquals(rangeWidth, expectedRange);
		}
		// In case no asserts were checked above (empty set).
		assertTrue(true);
	}
	
	public void testRangeSize1() {
		Histogram hist;
		hist = new Histogram(new int[] {0,1,2,3,4,5}, 6);
		assertBarCountEqual(hist.iterator(), new int[]{1,1,1,1,1,1});
		assertBarsRange(hist.iterator(), 1);
		
		hist = new Histogram(new int[] {1,1,1,1,1}, 6);
		assertBarCountEqual(hist.iterator(), new int[]{0,5,0,0,0,0});
		assertBarsRange(hist.iterator(), 1);

		hist = new Histogram(new int[] {1,1,1,1,1}, 2);
		assertBarCountEqual(hist.iterator(), new int[]{0, 5});
		assertBarsRange(hist.iterator(), 1);

		hist = new Histogram(new int[] {0}, 5);
		assertBarCountEqual(hist.iterator(), new int[]{1, 0, 0, 0, 0});
		assertBarsRange(hist.iterator(), 1);
		
		hist = new Histogram(new int[] {3}, 5);
		assertBarCountEqual(hist.iterator(), new int[]{0, 0, 0, 1, 0});
		assertBarsRange(hist.iterator(), 1);
	}

	public void testRangeSize10() {
		Histogram hist;
		hist = new Histogram(new int[] {100}, 11);
		assertBarCountEqual(hist.iterator(), new int[]{0,0,0,0,0,0,0,0,0,0,1});
		assertBarsRange(hist.iterator(), 10);

		hist = new Histogram(new int[] {10, 20, 30, 40, 50, 60, 70, 80, 90, 100}, 11);
		assertBarCountEqual(hist.iterator(), new int[]{0,1,1,1,1,1,1,1,1,1,1});
		assertBarsRange(hist.iterator(), 10);
		
		hist = new Histogram(new int[] {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100}, 11);
		assertBarCountEqual(hist.iterator(), new int[]{1,1,1,1,1,1,1,1,1,1,1});
		assertBarsRange(hist.iterator(), 10);
	}
	
	public void testImperfectFit() {
		Histogram hist;
		hist = new Histogram(new int[] {0,1,2}, 2);
		assertBarCountEqual(hist.iterator(), new int[]{2, 1});
		assertBarsRange(hist.iterator(), 2);
		
		hist = new Histogram(new int[] {1,1,1,1,1}, 1);
		assertBarCountEqual(hist.iterator(), new int[]{5});
		assertBarsRange(hist.iterator(), 2);
	}
	
	public void testEmptyData() {
		Histogram hist;
		hist = new Histogram(new int[] {}, 1);
		assertBarCountEqual(hist.iterator(), new int[]{0, 0, 0, 0, 0});
		assertBarsRange(hist.iterator(), 1);
	}
	
	public void testIterable() {
		Histogram hist;
		hist = new Histogram(new int[] {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100}, 11);
		assertBarCountEqual(hist.iterator(), new int[]{1,1,1,1,1,1,1,1,1,1,1});
		int count = 0;
		for (Histogram.Bar bar : hist) {
			count++;
			assertEquals(bar.getCount(), 1);
		}
		assertEquals(count, 11);
	}
	
	public void testSortedArray() {
		int[] integerList = new int[25];
		Random random = new Random();
		for(int i = 0; i < integerList.length; i++) {
			integerList[i] = random.nextInt(10);
		}
		Histogram histogram = new Histogram(integerList, 10);
		Iterator<Integer> list = histogram.getIntegerListIterator();
		Integer last = 0;
		while(list.hasNext()) {
			int next = list.next();
			assertTrue("next int is less than last int", next >= last);
			last = next;
		}
	}
	
	public void testSomeLargeNumbers() {
		Histogram hist;
		hist = new Histogram(new int[] {500005,1,1,1,1,1,1,1,1,1,1,1}, 11);
		assertBarCountEqual(hist.iterator(), new int[]{11,0,0,0,0,0,0,0,0,0,1});
		assertBarsRange(hist.iterator(), 45456);
		
		hist = new Histogram(new int[] {500005,1,1,1,1,1,1,1,1,1,1}, 11);
		assertBarCountEqual(hist.iterator(), new int[]{10,0,0,0,0,0,0,0,0,0,1});
		assertBarsRange(hist.iterator(), 45456);
		
		hist = new Histogram(new int[] {123456,1,1,1,1,1,1,1,1,1,1}, 5);
		assertBarCountEqual(hist.iterator(), new int[]{10,0,0,0,1});
		assertBarsRange(hist.iterator(), 24692);
	}
	
	public void testAllNumberSameData() {
		Histogram hist;
		hist = new Histogram(new int[] {0,0,0,0,0,0,0,0}, 1);
		assertBarCountEqual(hist.iterator(), new int[]{8});
		assertBarsRange(hist.iterator(), 1);
		
		hist = new Histogram(new int[] {0,0,0,0,0,0,0,0,0,0,0}, 5);
		assertBarCountEqual(hist.iterator(), new int[]{11, 0, 0, 0, 0});
		assertBarsRange(hist.iterator(), 1);
		
		hist = new Histogram(new int[] {10,10,10,10,10,10,10,10,10,10}, 5);
		assertBarCountEqual(hist.iterator(), new int[]{0, 0, 0, 10, 0});
		assertBarsRange(hist.iterator(), 3);
	}
	
	
	public void testFailCreate() {
		@SuppressWarnings("unused")
		Histogram hist;
		try {
			hist = new Histogram(null, 3);
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(true);
		}
		
		try {
			hist = new Histogram(new int[] {2}, -1);
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	public void testNotNullIntegerListIterator() {
		Histogram hist = new Histogram(new int[] {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100}, 11);
		assertNotNull(hist.getIntegerListIterator());
	}
	
	public void testUnmodifiableList() {
		Histogram hist = new Histogram(new int[] {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100}, 11);
		Iterator<Histogram.Bar> list = hist.iterator();
		while(list.hasNext()) {
			try {
				list.next();
				list.remove();
				fail();
			} catch(UnsupportedOperationException e) {
				assertTrue(true);
			}
		}
	}
	
	public void testNegativeIntegerList() {
		@SuppressWarnings("unused")
		Histogram hist;
		try {
			hist = new Histogram(new int[] {0, -1, 20, 30, 40, 50, 60, 70, 80, 90, 100}, 11);
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	public void testDescendingNumbers() {
		Histogram hist;
		hist = new Histogram(new int[] {10,9,8,7,6,5,4,3,2,1}, 11);
		assertBarCountEqual(hist.iterator(), new int[]{0,1,1,1,1,1,1,1,1,1,1});
		assertBarsRange(hist.iterator(), 1);
		
		hist = new Histogram(new int[] {11,9,8,7,6,5,4,3,2,1}, 11);
		assertBarCountEqual(hist.iterator(), new int[]{1,2,2,2,2,1,0,0,0,0,0});
		assertBarsRange(hist.iterator(), 2);
		
		hist = new Histogram(new int[] {10,8,6,4,2,0}, 12);
		assertBarCountEqual(hist.iterator(), new int[]{1,0,1,0,1,0,1,0,1,0,1,0});
		assertBarsRange(hist.iterator(), 1);
	}
	
	public void testSameValuesButDifferentNumberBars() {
		Histogram hist;
		hist = new Histogram(new int[] {0,999,1998,2997,3996,4995}, 6);
		assertBarCountEqual(hist.iterator(), new int[]{1,1,1,1,1,1});
		assertBarsRange(hist.iterator(), 833);
		
		hist = new Histogram(new int[] {0,999,1998,2997,3996,4995}, 1);
		assertBarCountEqual(hist.iterator(), new int[]{6});
		assertBarsRange(hist.iterator(), 4996);
		
		hist = new Histogram(new int[] {0,999,1998,2997,3996,4995}, 5);
		assertBarCountEqual(hist.iterator(), new int[]{2,1,1,1,1});
		assertBarsRange(hist.iterator(), 1000);
		
		hist = new Histogram(new int[] {0,999,1998,2997,3996,4995}, 10);
		assertBarCountEqual(hist.iterator(), new int[]{1,1,0,1,0,1,0,1,0,1});
		assertBarsRange(hist.iterator(), 500);
		
		hist = new Histogram(new int[] {0,999,1998,2997,3996,4995}, 11);
		assertBarCountEqual(hist.iterator(), new int[]{1,0,1,0,1,0,1,0,1,0,1});
		assertBarsRange(hist.iterator(), 455);
	}
	
	public void testEqualValues() {
		Histogram hist;
		hist = new Histogram(new int[] {1,1,1,1,1,1}, 1);
		assertBarCountEqual(hist.iterator(), new int[]{6});
		assertBarsRange(hist.iterator(), 2);

		hist = new Histogram(new int[] {2,2,2,2,2,2}, 1);
		assertBarCountEqual(hist.iterator(), new int[]{6});
		assertBarsRange(hist.iterator(), 3);

		hist = new Histogram(new int[] {100,100,100,100,100}, 1);
		assertBarCountEqual(hist.iterator(), new int[]{5});
		assertBarsRange(hist.iterator(), 101);

		hist = new Histogram(new int[] {100,100,100,100,100}, 2);
		assertBarCountEqual(hist.iterator(), new int[]{0,5});
		assertBarsRange(hist.iterator(), 51);
	}

}
