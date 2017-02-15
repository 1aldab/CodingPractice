package sumrange;

import java.util.Arrays;

public class SumRange {
	static int[] cumulate(int[] a) {
		int len = a.length;
		int[] c = new int[len + 1];
		for (int i = 0; i < len; i++) {
			c[i + 1] = c[i] + a[i];
		}
		return c;
	}

	static void findRanges2(int[] a, int lo, int hi) throws Exception {
		int[] c = cumulate(a);
		for (int end = 1; end < c.length; end++) {
			int[] begRange = binSearch(c, lo, hi, 0, end, end);
			if (begRange == null) continue;
			for (int beg = begRange[0]; beg <= begRange[1]; beg++) {
				System.out.println("(" + beg + "," + (end - 1) + ")");
			}
		}
	}

	static int[] binSearch(int[] c, int lb, int ub, int beg, int end, int up) throws Exception {
		if (beg == end) {
			int sum = c[up] - c[beg];
			if (sum >= lb && sum < ub)
				return new int[] {beg, end};
			else return null;
		}
		int lo = beg;
		int hi = end;
		while (lo < hi) {
			int mid = lo + (hi - lo) / 2;
			int sum = c[up] - c[mid];
			if (sum >= lb && sum <= ub) {
				int[] r0 = binSearch(c, lb, ub, beg, mid, up);
				int[] r1 = binSearch(c, lb, ub, mid+1, end, up);
				if (r0 != null && r1 != null) return new int[] {r0[0], r1[1]};
				return (r1 == null) ? r0 : r1;
			}
			if (sum < lb) hi = mid;
			else lo = mid + 1;
		}
		int sum = c[up] - c[lo];
		if (sum >= lb && sum < ub)
			return new int[] {lo, lo};
		else return null;
	}

	///////////////////////////////////////////////////////

	public static void findRanges(int[] a, int lo, int hi) {
		int len = a.length;
		int[] sum = new int[len];
		for (int i = len - 1; i >= 0; i--) {
			for (int j = len - 1; j >= i; j--) {
				sum[j] += a[i];
				if (sum[j] >= lo && sum[j] <= hi)
					System.out.println("(" + i + "," + j + ")");
			}
		}
	}

	public static void main(String[] args) throws Exception {
		int[] i = {0, 1, 2, 3, 4, 5, 6, 7};
		int[] a = {2, 5, 1, 4, 3, 6, 0, 7};
		int lo = 6;
		int hi = 9;
		System.out.println(Arrays.toString(i));
		System.out.println(Arrays.toString(a));
		System.out.println("Finding ranges with " + lo + " <= sum <= " + hi);
		System.out.println("-----------------------------------");
		findRanges(a, 6, 9);
		System.out.println("-----------------------------------");
		findRanges2(a, 6, 9);
	}
}
