package lrucache;

import java.util.*;

class Node<K> {
	K key;
	Node<K> next;
	Node<K> prev;
	
	public Node(K key) {
		this.key = key;
	}
}

enum Colour {
	RED, GREEN, BLUE, BROWN, BLACK, PURPLE, ORANGE, YELLOW, WHITE;
}

public class LruCacheDLL<K> {
	final int MAXCAPACITY;
	Map<K,Node<K>> map;
	Node<K> head, tail;
	
	public LruCacheDLL(int capacity) {
		this.MAXCAPACITY = capacity;
		map = new HashMap<>();
		head = null;
		tail = null;
	}
	
	public void printCache() {
		System.out.print("< ");
		Node<K> curr = head;
		while (curr != null) {
			System.out.print(curr.key + " ");
			curr = curr.next;
		}
		System.out.println(">");
	}
	
	public void add(K key) {
		remove(key);
		if (map.size() == MAXCAPACITY && tail != null)
			remove(tail.key);
		Node<K> newNode = new Node<>(key);
		map.put(key, newNode);
		setHead(newNode);
		if (map.size() == 1) tail = newNode;
	}
	
	private void remove(K key) {
		if (map.containsKey(key)) {
			Node<K> node = map.get(key);
			if (node != head) node.prev.next = node.next;
			else head = node.next;
			if (node != tail) node.next.prev = node.prev;
			else tail = node.prev;
			map.remove(key);
		}
	}
	
	private void setHead(Node<K> node) {
		if (head != null) head.prev = node;
		node.next = head;
		head = node;
	}
	
	public static void main(String[] args) {
		LruCacheDLL<Colour> lru = new LruCacheDLL<>(3);
		lru.remove(Colour.BROWN);
		lru.printCache();
		lru.add(Colour.BLACK);
		lru.printCache();
		lru.add(Colour.RED);
		lru.printCache();
		lru.add(Colour.BLUE);
		lru.printCache();
		lru.add(Colour.ORANGE);
		lru.printCache();
		lru.remove(Colour.PURPLE);
		lru.printCache();
		lru.remove(Colour.BLUE);
		lru.printCache();
		lru.add(Colour.YELLOW);
		lru.printCache();
	}
}
