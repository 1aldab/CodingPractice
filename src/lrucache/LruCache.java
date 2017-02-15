package lrucache;

import java.util.LinkedHashMap;

enum Color {
	RED, GREEN, BLUE, ORANGE, PURPLE, BROWN, YELLOW, WHITE, BLACK;
}

public class LruCache<K> {
	
	LinkedHashMap<K,K> cache;
	
	public LruCache(int maxCapacity) {
		this.cache = new FixedSizeLinkedHashMap<K,K>(maxCapacity);
	}
	
	public void printCache() {
		System.out.print("Cache content: ");
		for (java.util.Map.Entry<K,K> e : cache.entrySet()) {
			System.out.print("<" + e.getKey() + "> ");
		}
		System.out.println();
	}

	public void put(K key) {
		if (cache.containsKey(key)) cache.remove(key);
		cache.put(key, key);
		printCache();
	}
	
	public K get(K key) {
		if (cache.containsKey(key)) return cache.remove(key);
		return null;
	}
	
	public static void main(String[] args) {
		LruCache<Color> lru = new LruCache<>(3);
		lru.put(Color.RED);
		lru.put(Color.GREEN);
		lru.put(Color.BLUE);
		lru.put(Color.PURPLE);
		lru.put(Color.BLUE);
		Color c = lru.get(Color.RED);
		System.out.println(c == null ? "null" : c);
		c = lru.get(Color.PURPLE);
		System.out.println(c == null ? "null" : c);
		lru.printCache();
	}
}
