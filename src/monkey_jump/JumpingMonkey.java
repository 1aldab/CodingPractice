/*
 *  A monkey wants to get to the other side of a river. The monkey is initially 
 *  located on one bank of the river (position -1) and wants to get to the opposite 
 *  bank (position N). The monkey can jump any (integer) distance between 1 and D. 
 *  If D is less than or equal to N then the monkey cannot jump right across the river. 
 *  Luckily, there are many stones hidden under the water. The water level is constantly 
 *  decreasing, and soon some of the stones will be out of the water. The monkey can 
 *  jump to and from the stones, but only when the particular stone is already out of 
 *  the water. The stones in the river are described in array A consisting of N integers. 
 *  A[K] represents a time when the stone at position K will be out of the water 
 *  (A[K] = -1 means that there is no stone at position K). You can assume that no two 
 *  stones will surface simultaneously. The goal is to find the earliest time when the 
 *  monkey can get to the other side of the river. For example, consider integer D = 3 
 *  and the following array A consisting of N = 6 integers: 
 *  A[0] = 1 
 *  A[1] = -1 
 *  A[2] = 0 
 *  A[3] = 2 
 *  A[4] = 3 
 *  A[5] = 5 
 *  Initially, the monkey cannot jump across the river in a single jump. However, at 
 *  time 2, there will be three stones out of the water. Time 2 is the earliest moment 
 *  when the monkey can jump across the river (for example, by jumps of length 1, 2 and 3). 
 *  Write a function:
 *  	int solution(int A[], int D);
	that, given a zero-indexed array A consisting of N integers and integer D, returns 
	the minimum number of jumps by which the monkey can get to the other side of the river. 
	If the monkey cannot reach the other side of the river, the function should return −1.
 */
package monkey_jump;

import java.util.*;

public class JumpingMonkey {

	static int solution(int[] A, int D) {
		int dest = A.length;
		int timer = 0;
		int pos = -1;
		Queue<TimePos> pq = new PriorityQueue<>();
		pq.add(new TimePos(0,-1));
		while (pos < dest) {
			TimePos next = null;
			while (!pq.isEmpty() && next == null) {
				next = pq.remove();
				if (next.time == -1) next = null;
			}
			if (next == null) return -1;
			pos = next.pos;
			timer = next.time;
			if (pos + D >= dest) return timer;
			pq.clear();
			for (int jump = 1; jump <= D && pos + jump < dest; jump++)
				pq.add(new TimePos(A[pos + jump], pos + jump));
		}
		return -1;
	}
	
	static int solution2(int[] A, int D) {
		int N = A.length;
		int timer = 0;
		int pos = -1;
		TimePos[] tp = new TimePos[N];
		for (int i = 0; i < N; i++)
			tp[i] = new TimePos(A[i], i);
		Arrays.sort(tp);
		Set<Integer> available = new TreeSet<>();
		int i = 0;
		while (i < N && N - pos > D) {
			for (int p : available) {
				if (p > pos) {
					if (p - pos <= D)
						pos = p;
				} else available.remove(p); 
			}
			if (tp[i].time > -1 && tp[i].pos > pos) {
				if (tp[i].pos - pos <= D) {
					pos = tp[i].pos;
					timer = tp[i].time;
				} else {
					available.add(tp[i].pos);
				}
			}
			i++;
		}
		return (N - pos <= D) ? timer : -1;
	}
	
	static void test(int[] A, int D) {
		System.out.println("A = " + Arrays.toString(A) + ", D = " + D);
		System.out.println("Time to reach the other side: (" + solution(A, D) + "," + solution2(A, D) + ")");
		System.out.println("----------------------------------------");
	}
	
	public static void main(String[] args) {
		int[] A1 = {1, -1, 0, 2, 3, 5};
		int D1 = 3;
		test(A1, D1);
		
		int[] A2 = {1, -1, -1, -1, 3, 5};
		int D2 = 3;
		test(A2, D2);
		
		int[] A3 = {3, 4, 0, 2, 1, 5};
		int D3 = 1;
		test(A3, D3);
	}
}

class TimePos implements Comparable<TimePos>{
	int time;
	int pos;
	
	public TimePos(int t, int p) {
		time = t;
		pos = p;
	}
	
	public int compareTo(TimePos tp) {
		return this.time - tp.time;
	}
}
