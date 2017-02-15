package lrucache;

import java.util.LinkedHashMap;

public class FixedSizeLinkedHashMap<K,V> extends LinkedHashMap<K,V>{

	private final int MAXCAPACITY;
	
	public FixedSizeLinkedHashMap(int maxCapacity) {
		this.MAXCAPACITY = maxCapacity;
	}
	
	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K,V> eldest) {
		return this.size() > this.MAXCAPACITY;
	}
	
}
