package paint_interval;

import java.util.Arrays;

/* You are given a range [first, last], initially white. 
You need to paint it black. 
For this purpose you have a set of triples 
[(f, l, cost), ...] - where each triple means that you 
can paint range [f, l] for `cost` coins 
(limitations: cost is floating point >= 0, f, l, first, last are integers). 
Find minimum cost needed to paint the whole range 
[first, last] or return -1 if it's impossible 

Example:
[first, last] = [0, 5] and set of triples is
[[0, 5, 10], [0, 4, 1], [0, 2,5], [2, 5, 1]]
Clearly the answer is to take [0, 4, 1] and [2, 5, 1]
and the total cost will be 2. 

Another example:
[first, last] = [0, 5]
triples are [[1,4, 10], [2, 5, 6]]
answer is -1, because it's impossible to color whole range.
 */

public class Painter {

	static class Interval implements Comparable<Interval> {
		int beg;
		int end;
		int cost;

		public Interval(int beg, int end, int cost) {
			this.beg = beg;
			this.end = end;
			this.cost = cost;
		}

		public int compareTo(Interval i) {
			if (i.beg != this.beg) return this.beg - i.beg;
			else return this.end - i.end;
		}
		
		public String toString() {
			return "(" + this.beg + "," + this.end + "," + this.cost + ")";
		}
	}

	// intervals must be sorted before using this method
	static int minCost(int beg, int end, Interval[] intervals, int idx, int costSoFar) {
		if (beg >= end) return costSoFar;
		if (idx == intervals.length || beg < intervals[idx].beg) return Integer.MAX_VALUE;
		if (beg > intervals[idx].end) return minCost(beg, end, intervals, idx + 1, costSoFar);
		int costUsingIdx = minCost(intervals[idx].end, end, intervals, idx + 1, costSoFar + intervals[idx].cost);
		int costWithoutIdx = minCost(beg, end, intervals, idx + 1, costSoFar);
		return costUsingIdx < costWithoutIdx ? costUsingIdx : costWithoutIdx;
	}

	static void test(int beg, int end, Interval[] intervals) {
		int minCost = minCost(beg, end, intervals, 0, 0);
		System.out.println("min cost = " + minCost);
		System.out.println("---------------------------");
	}

	public static void main(String[] args) {
		Interval[] intervals1 = new Interval[] {new Interval(0,5,10), new Interval(0,4,1), new Interval(0,2,5), new Interval(2,5,1)};
		Arrays.sort(intervals1);
		test(0, 5, intervals1);
		
		Interval[] intervals2 = new Interval[] {new Interval(2, 5, 6), new Interval(1,4, 10)};
		Arrays.sort(intervals2);
		test(0, 5, intervals2);
	}
}
