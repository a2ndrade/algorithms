package course.algorithms;

import java.util.Random;

/**
 * [Posted March 22nd] You are a given a unimodal array of n distinct elements, meaning that its entries are in
 * increasing order up until its maximum element, after which its elements are in decreasing order. Give an algorithm to
 * compute the maximum element that runs in O(log n) time.
 * 
 * @author rcxr
 */
public class Problem2 {

	public static void main(String[] args) {
    int[] array = getArray();
    System.out.println(ternarySearch(array, 0, array.length));
	}

	/**
	 * Ternary Search
	 * Based in: http://en.wikipedia.org/wiki/Ternary_search
	 * Complexity: O(log(n))
	 * 
	 * @param array
	 * @param begin
	 * @param end
	 * @return the maximum value in the given array interval
	 */
	private static int ternarySearch(int[] array, int begin, int end) {
	  if (end - begin < 2) {
	    return array[begin];
	  }
    int oneThird = (end + begin + begin) / 3;
    int twoThirds = (end + end + begin) / 3;
    if (array[oneThird] < array[twoThirds]) {
      return ternarySearch(array, oneThird + 1, end);
    } else {
      return ternarySearch(array, begin, twoThirds);
    }
  }

  private static int[] getArray() {
    int size = 25251;
    int[] array = new int[size];
    int i = 0;
    int inflection = new Random().nextInt(size);
    while (i <= inflection) {
      array[i] = i++;
    }
    while (i < size) {
      array[i] = -i++;
    }
    return array;
  }
}
