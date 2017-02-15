package iptree;
/* I was asked in an interview: You are given a dump file of 
   IPv4 addresses. You are to find 4 most common occurring 
   subnets. Lets say an IP address if of type a.b.c.d you have 
   to find most common occurring four subnets of the form, 
   a.*.*.* 
   a.b.*.* 
   a.b.c.* 
   a.b.c.d 
   Here * matches anything.
 */

import java.util.*;

public class IPTree {

	public class Subnet {
		int count;
		Subnet[] subnets;

		public Subnet() {
			count = 0;
			subnets = new Subnet[256];
		}
	}

	Subnet root = new Subnet();

	public void insertIP(String ip) {
		String[] parts = ip.split("\\.");
		Subnet curr = root;
		for (int i = 0; i < 4; i++) {
			int id = Integer.parseInt(parts[i]);
			if (curr.subnets[id] == null) curr.subnets[id] = new Subnet();
			curr = curr.subnets[id];
			curr.count++;
		}
	}

	public void getMostCommonSubnets() {
		Queue<Subnet> q = new LinkedList<Subnet>();
		q.add(root);
		q.add(null);
		int maxFreq = -1;
		int common = -1;
		while(!q.isEmpty()) {
			Subnet curr = q.poll();
			if (curr != null) { 
				for (int i = 0; i < curr.subnets.length; i++) {
					Subnet s = curr.subnets[i];
					if (s != null) {
						if (s.count > maxFreq) {
							maxFreq = s.count;
							common = i;
						}
						q.add(s);
					}
				}
			} else {
				if (!q.isEmpty())
					q.add(null);
				System.out.println("Most common subnet = " + common
						+ " with freq = " + maxFreq);
				maxFreq = -1;
				common = -1;
			}
		}
	}

	public static void main(String[] args) {
		String[] ips = {"1.0.1.2", "2.3.1.0", "3.4.5.0", "4.0.1.3",
				"1.1.2.3", "2.3.2.0", "3.4.5.1", "4.0.1.3",
				"1.2.3.4", "2.3.3.0", "3.4.5.2", "4.0.1.3",
				"1.3.4.5", "2.3.4.0", "3.4.5.3", "0.1.2.3",
				"1.4.5.6", "2.3.5.0", "5.2.3.4", "6.1.2.3",
				"1.5.6.7", "7.1.2.3", "8.1.2.3", "9.1.2.3"};
		IPTree tree = new IPTree();
		for (String ip : ips) {
			tree.insertIP(ip);
		}
		tree.getMostCommonSubnets();
	}

}
