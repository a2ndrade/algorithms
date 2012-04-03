package course.algorithms.week1;

/**
 * [Posted March 22nd] You are given as input an unsorted array of n distinct numbers, where n is a power of 2. Give an
 * algorithm that identifies the second-largest number in the array, and that uses at most n+log2(n)-2 comparisons.
 * 
 * @author rcxr
 */
public class Problem1 {
  
  public static void main(String[] args) {
    int[] array = getArray();
    // We get the largest element along with its trace (the second largest element should on the trace)
    // We discard the largest element by moving to the trace and look for the largest element that correspond to the second
    // largest element in the original array
    System.out.println(largestWithTrace(array, 0, array.length).trace.largest());
  }

  /**
   * Largest Number With Trace
   * Complexity: O(log(n))
   * 
   * @param array
   * @param begin
   * @param end
   * @return the largest number in the given array interval and all the numbers it was compared against
   */
  private static TraceNode largestWithTrace(int[] array, int begin, int end) {
    // End condition
    if (end - begin < 2) {
      return new TraceNode(array[begin]);
    }
    int mid = (begin + end) / 2;
    return TraceNode.compare(largestWithTrace(array, begin, mid), largestWithTrace(array, mid, end));
  }
  
  private static class TraceNode {
    private final int value;
    /**
     * The values the node value has been compared against.
     */
    private final TraceNode trace;

    public TraceNode(int value, TraceNode trace) {
      this.value = value;
      this.trace = trace;
    }
    
    public TraceNode(int value) {
      this(value, null);
    }
    
    /**
     * When two nodes are compared a whole new node gets created. The value for this new node correspond to the largest
     * among the two compared nodes's values. The next node points to the structure that holds all the elements that so
     * far have been compared against the value. It's important to mention that this trace is the value's original trace
     * plus the new number that was recently compared.
     * 
     * @param a
     * @param b
     * @return a new node holding the largest value and an updated trace
     */
    public static TraceNode compare(TraceNode a, TraceNode b) {
      if (a.value < b.value) {
        return new TraceNode(b.value, new TraceNode(a.value, b.trace));
      } else {
        return new TraceNode(a.value, new TraceNode(b.value, a.trace));
      }
    }
    
    /**
     * Compares the value against and all the values from the nodes in the trace and returns the largest
     * 
     * @return the largest element in the structure
     */
    public int largest() {
      if (null == trace) {
        return value;
      }
      int largest = trace.largest();
      if (value < largest) {
        return largest;
      }
      return value;
    }
  }

  private static int[] getArray() {
    return new int[] {1,2,3,4,5,6,7,8,9,0,11,111,1111,33,44,55};
  }
}
