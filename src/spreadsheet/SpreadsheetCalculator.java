package spreadsheet;

/* A spreadsheet consists of two-dimensional array of cells labeled A1, A2, etc.
 * Rows are identified using letters, columns by numbers. Each cell contains either
 * an integer or an expression. Expressions contain integers, cell references, and 
 * operators '+', '-', '*', '/' with the usual rules of evaluation - note that the
 * input is RPN and should be evaluated in stack order.
 * 
 * Write a program to read a spreadsheet from 'stdin', evaluate the values of all
 * the cells, and write the output to 'stdout'.
 * 
 * The spreadsheet input is defined as follows:
 * 		Line 1: two integers, defining the width and height of the spreadsheet (n,m)
 * 		n * m lines each containing an expression which is the value of the 
 * 		corresponding cell (cells enumerated in the order A1, A1, A<n>, B1, ...)
 * 
 * Your program must output its data in the same format, but each cell should be 
 * reduced to a single floating-point value. For example, we would expect the 
 * following to produce the indicated output:
 * 
 * INPUT:
	 	3 2
		A2
		4 5 *
		A1
		A1 B2 / 2 +
		3
		39 B1 B2 * /
 *				
 *  OUTPUT:
   		20.00000
		20.00000
		8.66667
		3.00000
		1.50000
 * 
 * Assumptions
 * 		Your program should detect cyclic dependencies in the input data, report these
 * 		in sensible manner, and exit with a non-zero exit code.
 * 		All numbers in the input are positive integers, but internal calculations and
 * 		output should be in floating point.
 * 		You can assume that there are no more than 26 rows (A-Z) in the spreadsheet;
 * 		however, there can be any number of columns (1-n).	 
 * */

import java.util.*;

public class SpreadsheetCalculator {
    
    static Map<String, String> sheet = new TreeMap<>();
    
    private static boolean solve(String key) {
        Stack<String> stack = new Stack<>();			// to keep track of dependencies
        Map<String, String> tracker = new HashMap<>();  // to track circular dependencies
        Set<String> evaluated = new HashSet<String>();  // to track ready to evaluate addresses
        stack.push(key);
        while (!stack.isEmpty()) {
            String address = stack.peek();
            boolean ready = true;
            Set<String> deps = findDeps(address);
            for (String dep : deps) {
            	if (!evaluated.contains(dep)) {
            		ready = false;
            		break;
            	}
            }
            if (ready) {
                evaluate(address);
                evaluated.add(address);
                stack.pop();
            } else {
                for (String dep : deps) {
                    if (tracker.containsKey(dep)) return false;
                    tracker.put(dep, address);
                    stack.push(dep);
                }
            }
        }
        return true;
    }
    
    private static void evaluate(String key) {
        String exp = sheet.get(key);
	 	Stack<Double> s = new Stack<Double> ();
		Scanner sc = new Scanner(exp);
		String addressPattern = "[A-Z][0-9]+";
		while (sc.hasNext()) {
			if (sc.hasNextInt())
				s.push(sc.nextInt() * 1.0);
			else if (sc.hasNextDouble())
				s.push(sc.nextDouble());
			else if (sc.hasNext(addressPattern))
				s.push(Double.parseDouble(sheet.get(sc.next(addressPattern))));
			else {
				String op = sc.next();
				double num2 = s.pop();
				double num1 = s.pop();
				if (op.equals("+")) s.push(num1 + num2);
				else if (op.equals("-")) s.push(num1 - num2);
				else if (op.equals("*")) s.push(num1 * num2);
				else s.push(num1 / num2);
			}
		}
		sheet.put(key, String.format( "%.5f", s.pop()));
		sc.close();
    }
    
    private static void readSheet() {
        Scanner sc = new Scanner(System.in);
        String[] firstLine = sc.nextLine().split(" ");
        int n = Integer.parseInt(firstLine[0]); // num columns
        int m = Integer.parseInt(firstLine[1]); // num rows
        for(int line = 0; line < n * m; line++) {
            String key = "" + (char) (line / n + 'A') + ((line % n) + 1);
            String cell = sc.nextLine();
            sheet.put(key, cell);
        }
        sc.close();
    }
    
    private static Set<String> findDeps(String key) {
        Set<String> deps = new HashSet<>();
        String exp = sheet.get(key);
        Scanner sc = new Scanner(exp);
        String addressPattern = "[A-Z][0-9]+";
        while (sc.hasNext())
            if (sc.hasNext(addressPattern)) deps.add(sc.next(addressPattern));
            else sc.next();
        sc.close();
        return deps;
    }
    
    public static void main(String args[] ) throws Exception {
        readSheet();
        for(String key : sheet.keySet()) {
            if (!solve(key)) {
                System.out.println("Error: Circular dependency!");
                return;
            }
            System.out.println(sheet.get(key));
        }
    }

}