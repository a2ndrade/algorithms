package course.algorithms;

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
  
  private static int[] getArray() throws FileNotFoundException {
    int[] array = new int[100000];
    Scanner in = new Scanner(new File("src/course/algorithms/IntegerArray.txt"));
    for (int i = 0; i < 100000; i++) {
      array[i] = in.nextInt();
    }
    return array;
  }

  private static long inversions(int[] array, int begin, int end) {
    if (end - begin < 2) {
      return 0;
    }
    int mid = (begin + end) / 2;
    return inversions(array, begin, mid) + inversions(array, mid, end) + splitInversions(array, begin, mid, end);
  }
  
  private static long splitInversions(int[] array, int begin, int mid, int end) {
    int[] copy = new int[end - begin];
    int i = begin;
    int j = mid;
    int k = 0;
    long splitInversions = 0;
    while (i < mid && j < end) {
      if (array[i] < array[j]) {
        copy[k++] = array[i++];
      } else {
        splitInversions += mid - i;
        copy[k++] = array[j++];
      }
    }
    while (i < mid) {
      copy[k++] = array[i++];
    }
    while (j < end) {
      copy[k++] = array[j++];
    }
    while (0 < k--) {
      array[begin + k] = copy[k];
    }
    return splitInversions;
  }
}
