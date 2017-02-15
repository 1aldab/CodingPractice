package encircular;

import java.util.*;

enum Direction {
	NORTH, SOUTH, EAST, WEST
}

class Position {
	int x;
	int y;
	Direction d;

	public Position (int x, int y) {
		this.x = x;
		this.y = y;
		this.d = Direction.EAST;
	}
	
	public String toString() {
		String dir = "";
		switch (this.d) {
		case NORTH:
			dir = "N";
			break;
		case SOUTH:
			dir = "S";
			break;
		case EAST:
			dir = "E";
			break;
		case WEST:
			dir = "W";
			break;
		}
		return "(" + x + "," + y +")" + dir;
	}
	
	public Position copy () {
		Position copy = new Position (this.x, this.y);
		copy.d = this.d;
		return copy;
	}

	public void execute(char c) {
		switch (c) {
		case 'G':
			switch(this.d) {
			case NORTH:
				this.y++;
				break;
			case SOUTH:
				this.y--;
				break;
			case EAST:
				this.x++;
				break;
			case WEST:
				this.x--;
				break;
			}
			break;
		case 'L':
			switch (this.d) {
			case NORTH:
				this.d = Direction.WEST;
				break;
			case SOUTH:
				this.d = Direction.EAST;
				break;
			case EAST:
				this.d = Direction.NORTH;
				break;
			case WEST:
				this.d = Direction.SOUTH;
				break;
			}
			break;
		case 'R':
			switch (this.d) {
			case NORTH:
				this.d = Direction.EAST;
				break;
			case SOUTH:
				this.d = Direction.WEST;
				break;
			case EAST:
				this.d = Direction.SOUTH;
				break;
			case WEST:
				this.d = Direction.NORTH;
				break;
			}
			break;
		}
	}
	
	public int hashCode () {
		int p1 = 31;
		int p2 = 17;
		int p3 = 5;
		int z = 0;
		switch(this.d) {
			case NORTH:
				z = 1;
				break;
			case SOUTH:
				z = 2;
				break;
			case EAST:
				z = 3;
				break;
			case WEST:
				z = 4;
				break;
					
		}
		return this.x * p1 + this.y * p2 + z * p3;
	}
	
	public boolean equals (Object o) {
		if (o instanceof Position) {
			Position p = (Position) o;
			return (p.x == this.x && p.y == this.y && p.d == this.d);
		}
		return false;
	}
}

class GFG {
	
	static String bounded (String commands) {
		Set<Position> visited = new HashSet<>();
		Position p = new Position (0,0);
		visited.add(p);
		int maxRun = 100;
		int repeats = 0;
		for (int run = 0; run < maxRun; run++) {
			for (int c = 0; c < commands.length(); c++) {
				p.execute(commands.charAt(c));
				Position newPos = p.copy();
				if (visited.contains(newPos) && run > 0) {
					repeats++;
					if (repeats == visited.size()) {
						return "Circular";
					}
				} else {
					visited.add(newPos);
					repeats = 0;
				}
			}
		}
		return "Not Circular";
	}
	
	public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
		int numTests = Integer.parseInt(sc.nextLine());
		for (int i = 1; i <= numTests; i++) {
			String line = sc.nextLine();
			System.out.println(bounded(line));
		}
	}
}
