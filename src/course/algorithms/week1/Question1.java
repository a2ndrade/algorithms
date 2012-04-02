package course.algorithms.week1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Download the text file here: http://spark-public.s3.amazonaws.com/algo1/programming_prob/IntegerArray.txt
 * 
 * The file contains all the 100,000 integers between 1 and 100,000 (including both) in some random order( no integer is
 * repeated).
 * 
 * Your task is to find the number of inversions in the file given (every row has a single integer between 1 and
 * 100,000). Assume your array is from 1 to 100,000 and ith row of the file gives you the ith entry of the array. Write
 * a program and run over the file given. The numeric answer should be written in the space. So if your answer is
 * 1198233847, then just type 1198233847 in the space provided without any space / commas / any other punctuation marks.
 * You can make upto 5 attempts, and we'll count the best one for grading. (We do not require you to submit your code,
 * so feel free to choose the programming language of your choice, just type the numeric answer in the following space)
 * 
 * @author rcxr
 */
public class Question1 {

  public static void main(String[] args) throws FileNotFoundException {
    int[] array = getArray();
    System.out.println(inversions(array, 0, array.length));
  }

  /**
   * Counting Inversions
   * Based on: Merge-Sort
   * Complexity: O(n log(n))
   * 
   * @param array
   * @param begin
   * @param end
   * @return the number of inversions in the given interval
   */
  private static long inversions(int[] array, int begin, int end) {
    // end condition
    if (end - begin < 2) {
      return 0;
    }
    int mid = (begin + end) / 2;
    // total inversions = left inversions + right inversions + split inversions
    return
        inversions(array, begin, mid) +
        inversions(array, mid, end) +
        splitInversions(array, begin, mid, end);
  }

  /**
   * The following method merges the given array intervals:
   *   Interval 1: begin-mid
   *   Interval 2: mid-end
   * It also counts for the number of split inversions in the two intervals.
   * @param array
   * @param begin
   * @param mid
   * @param end
   * @return the number of split inversions in the given interval
   */
  private static long splitInversions(int[] array, int begin, int mid, int end) {
    // array used for copying merged values
    int[] copy = new int[end - begin];
    // left interval pointer
    int i = begin;
    // right interval pointer
    int j = mid;
    // copy array pointer
    int k = 0;
    long splitInversions = 0;
    while (i < mid && j < end) {
      // copying the smaller element into the temporal array
      if (array[i] < array[j]) {
        copy[k++] = array[i++];
      } else {
        copy[k++] = array[j++];
        // if the smaller element was in the right interval we account for the
        // respective inversions
        splitInversions += mid - i;
      }
    }
    // remaining elements in the left interval are copied to the temporal array 
    while (i < mid) {
      copy[k++] = array[i++];
    }
    // remaining elements in the right interval are copied to the temporal array 
    while (j < end) {
      copy[k++] = array[j++];
    }
    // original array gets updated
    while (0 < k--) {
      array[begin + k] = copy[k];
    }
    return splitInversions;
  }

  private static int[] getArray() throws FileNotFoundException {
    Scanner in = new Scanner(new File("src/course/algorithms/week1/IntegerArray.txt"));
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
