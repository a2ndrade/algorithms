package course.algorithms.week2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Download the text file here: http://spark-public.s3.amazonaws.com/algo1/programming_prob/QuickSort.txt
 * 
 * The file contains all of the integers between 1 and 10,000 (inclusive) in unsorted order (with no integer repeated).
 * The integer in the ith row of the file gives you the ith entry of an input array.
 * 
 * Your task is to compute the total number of comparisons used to sort the given input file by QuickSort. As you know,
 * the number of comparisons depends on which elements are chosen as pivots, so we'll ask you to explore three different
 * pivoting rules. You should not count comparisons one-by-one. Rather, when there is a recursive call on a subarray of
 * length m, you should simply add m-1 to your running total of comparisons. (This is because the pivot element will be
 * compared to each of the other m-1 elements in the subarray in this recursive call.) WARNING: The Partition subroutine
 * can be implemented in several different ways, and different implementations can give you differing numbers of
 * comparisons. For this problem, you should implement the Partition subroutine as it is described in the video lectures
 * (otherwise you might get the wrong answer).
 * 
 * 1st part: For the first part of the programming assignment, you should always use the first element of the array as
 * the pivot element.
 * 
 * 2nd part: Compute the number of comparisons (as in Problem 1), always using the final element of the given array as
 * the pivot element. Again, be sure to implement the Partition subroutine as it is described in the video lectures.
 * Recall from the lectures that, just before the main Partition subroutine, you should exchange the pivot element
 * (i.e., the last element) with the first element.
 * 
 * 3rd part: Compute the number of comparisons (as in Problem 1), using the "median-of-three" pivot rule. [This primary
 * motivation behind this rule is to do a little bit of extra work to get much better performance on input arrays that
 * are already sorted.] In more detail, you should choose the pivot as follows. Consider the first, middle, and final
 * elements of the given array. (If the array has odd length it should be clear what the "middle" element is; for an
 * array with even length 2k, use the kth element as the "middle" element. So for the array: 4 5 6 7, the "middle"
 * element is the second one ---- 5 and not 6!) Identify which of these three elements is the median (i.e., the one
 * whose value is in between the other two), and use this as your pivot. As discussed in the first and second parts of
 * this programming assignment, be sure to implement Partition as described in the video lectures (including exchanging
 * the pivot element with the first element just before the main Partition subroutine).
 * SUBTLE POINT: A careful analysis would keep track of the comparisons made in identifying the median of the three
 * elements. You should NOT do this. That is, as in the previous two problems, you should simply add m-1 to your running
 * total of comparisons every time you recurse on a subarray with length m.
 * 
 * @author rcxr
 */
public class Question1 {

  public static void main(String[] args) throws FileNotFoundException {
    int[] array;
    // 1st part: using the first element as the pivot
    array = getArray();
    System.out.println(quickSort(array, 0, array.length, new FirstElementPivotPicker()));
    // 2nd part: using the last element as the pivot
    array = getArray();
    System.out.println(quickSort(array, 0, array.length, new LastElementPivotPicker()));
    // 3rd part: using the median of three for determining the pivot
    array = getArray();
    System.out.println(quickSort(array, 0, array.length, new MedianOfThreePivotPicker()));
  }
  
  /**
   * Quick Sort
   * Complexity: O(n log(n))
   * Memory: O(1)
   * 
   * @param array
   * @param begin
   * @param end
   * @param picker
   *          contains the logic for determining what element is used as the pivot
   * @return the number of comparisons that were performed against the pivot, including those performed in recursive
   *         calls
   */
  private static int quickSort(int[] array, int begin, int end, PivotPicker picker) {
    // end condition
    if (end - begin < 2) {
      return 0;
    }
    // the picker chooses an element as the pivot and places it to the front
    picker.moveToFront(array, begin, end);
    // the array is partitioned using the first element as the pivot
    int pivotPosition = partition(array, begin, end);
    // the only remaining task is to sort the left and right intervals
    return end - begin - 1 + quickSort(array, begin, pivotPosition, picker) + quickSort(array, pivotPosition + 1, end, picker);
  }

  /**
   * Defines an strategy for determining the pivot in a Quick Sort procedure.
   */
  private interface PivotPicker {
    /**
     * Chooses a pivot and moves it to the front of array the interval.
     * @param array
     * @param begin
     * @param end
     */
    void moveToFront(int[] array, int begin, int end);
  }
  /**
   * {@link PivotPicker} that always chooses the first element as the pivot.
   */
  private static class FirstElementPivotPicker implements PivotPicker {
    @Override
    public void moveToFront(int[] array, int begin, int end) {
      // pivot is already the first element of the array interval
    }
  }
  /**
   * {@link PivotPicker} that always chooses the last element as the pivot.
   */
  private static class LastElementPivotPicker implements PivotPicker {
    @Override
    public void moveToFront(int[] array, int begin, int end) {
      // moving the last element to the front
      int temp = array[begin];
      array[begin] = array[end - 1];
      array[end - 1] = temp;
    }
  }
  
  /**
   * {@link PivotPicker} that uses the median of three strategy for determining the pivot.
   */
  private static class MedianOfThreePivotPicker implements PivotPicker {
    @Override
    public void moveToFront(int[] array, int begin, int end) {
      int median;
      int middle = (begin + end - 1) / 2;
      if (array[begin] < array[middle]) {
        if (array[middle] < array[end - 1]) {
          median = middle;
        } else if (array[begin] < array[end - 1]) {
          median = end - 1;
        } else {
          median = begin;
        }
      } else {
        if (array[end - 1] < array[middle]) {
          median = middle;
        } else if (array[end - 1] < array[begin]) {
          median = end -1;
        } else {
          median = begin;
        }
      }
      // moving the median element to the front
      int temp = array[begin];
      array[begin] = array[median];
      array[median] = temp;
    }
  }

  private static int partition(int[] array, int begin, int end) {
    int pivot = array[begin];
    int pivotPosition = begin;
    // sweeping the array interval to determine the pivot position
    for (int i = pivotPosition + 1; i < end; i++) {
      // as we sweep we also maintain the state of the traversed section
      // by moving elements smaller than the pivot to the front section
      if (array[i] < pivot) {
        int temp = array[pivotPosition + 1];
        array[pivotPosition + 1] = array[i];
        array[i] = temp;
        pivotPosition++;
      }
    }
    array[begin] = array[pivotPosition];
    array[pivotPosition] = pivot;
    return pivotPosition;
  }

  private static int[] getArray() throws FileNotFoundException {
    Scanner in = new Scanner(new File("src/course/algorithms/week2/QuickSort.txt"));
    try {
      int[] array = new int[10000];
      for (int i = 0; i < 10000; i++) {
        array[i] = in.nextInt();
      }
      return array;
    } finally {
      in.close();
    }
  }
}
