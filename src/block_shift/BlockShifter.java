package block_shift;

import java.util.*;

public class BlockShifter {
	
	/* Below is the initial algorithm that came to my mind.*/
	public static void rotate(int[] nums, int k) {
		int n = nums.length;
		int gcd = gcd(n, k);
		for (int j = 0; j < gcd; j++) {
			int prev = nums[j];
			int pidx = j;
			for (int i = 0; i < n / gcd; i++) {
				int nidx = (pidx + k) % n;
				int temp = nums[nidx];
				nums[nidx] = prev;
				prev = temp;
				pidx = nidx;
			}
		}
	}
	
	public static int gcd(int n, int k) {
		if (k == 0) return 0;
		for (int i = k; i > 1; i--) {
			if (k % i == 0 && n % i == 0) return i;
		}
		return 1;
	}
	
	/* Below is a more elegant approach */
	public static void rotateFaster(int[] a, int k) {
		int n = a.length;
		k = k % n;
		if (k == 0) return;
		reverse(a, 0, n - 1);
		reverse(a, 0 , k - 1);
		reverse(a, k, n - 1);
	} 
	
	public static void reverse(int[] a, int i, int j) {
		int n = j - i + 1;
		for (int k = 0; k < n / 2; k++) {
			int temp = a[i + k];
			a[i + k] = a[j - k];
			a[j - k] = temp;
		}
	}

	/* Test function for the faster method */
	public static void test(int[] a, int k) {
		System.out.print(Arrays.toString(a) + " -- " + k + " --> ");
		rotateFaster(a, k);
		System.out.println(Arrays.toString(a));
	}

	public static void main(String[] args) {
		int[] a;
		a = new int[] {1,2,3,4,5,6};
		test(a, 2);
		a = new int[] {1,2};
		test(a, 3);
		a = new int[] {1,2,3,4,5,6,7};
		test(a, 2);
		a = new int[] {1,2,3,4,5,6,7,8,9,10,11,12};
		test(a, 4);
	}

}
