package zeroblocks;

import java.util.*;

/* Imagine we have an image. We’ll represent this image as a simple 
 * 2D array where every pixel is a 1 or a 0. The image you get is known 
 * to have a single rectangle of 0s on a background of 1s. Write a 
 * function that takes in the image and returns the coordinates of the 
 * rectangle -- either top-left and bottom-right; or top-left, width, 
 * and height.
*/

public class ZeroBlockFinder {

	public static int[] findZeros(int[][] image) {
		int width = image[0].length;
		int height = image.length;
		int X, Y, W, H;
		X = Y = W = H = -1;
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++) {
				if (image[x][y] == 0) {
					X = x;
					Y = y;
					break;
				}
			}
			if (X != -1) break;
		}
		W = H = 1;
		while (X + H < height && image[X + H][Y] == 0) H++;
		while (Y + W < width && image[X][Y + W] == 0) W++;
		return new int[] {X, Y, W, H};
	}

	public static List<Tuple> findBlocks(int[][] image) {
		List<Tuple> results = new ArrayList<>();
		int width = image[0].length;
		int height = image.length;
		int X, Y, W, H, x, y; 
		x = y = 0;
		while (x < height && y < width) {
			X = Y = W = H = -1;
			for (; x < height; x++) {
				for (y = 0; y < width; y++) {
					if (image[x][y] == 0 && !covered(x, y, results)) {
						X = x;
						Y = y;
						break;
					}
				}
				if (X != -1) break;
			}
			if (X != -1) {
				W = H = 1;
				while (X + H < height && image[X + H][Y] == 0) H++;
				while (Y + W < width && image[X][Y + W] == 0) W++;
				results.add(new Tuple(X, Y, W, H));
			}
		}
		return results;
	}

	public static boolean covered(int x, int y, List<Tuple> list) {
		for (Tuple t : list) {
			if (x >= t.x && x <= t.x + t.h && y >= t.y && y <= t.y + t.w) return true;
		}
		return false;
	}

	public static void main(String[] args) {
		int[][] image = {  
				{1, 1, 1, 1, 1, 1, 1},
				{1, 1, 1, 1, 1, 1, 1},
				{1, 1, 1, 0, 0, 0, 1},
				{1, 0, 1, 0, 0, 0, 1},
				{1, 0, 1, 1, 1, 1, 1},
				{1, 0, 1, 0, 0, 1, 1},
				{1, 1, 1, 0, 0, 1, 1},
				{1, 1, 1, 1, 1, 1, 1},
		};

		List<Tuple> results = findBlocks(image);
		for (Tuple t : results)
			System.out.println(t);

	}
}

class Tuple {
	int x;
	int y;
	int w;
	int h;

	public Tuple(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public String toString() {
		return "" + x + "," + y + "," + w + "," + h;
	}
}

