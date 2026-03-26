package com.saqib.scribbles;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @param <K> Key
 * @param <V> Value
 * 
 * Although we are using a ConcurrentHashMap and a ConcurrentLinkedDeque to store the values
 * and the order of access respectively coordinating reads and updates in a concurrent environment
 * isn't possible except by using synchronized which is highly inefficient.
 * 
 * The solution: Caffeine.
 * 
 */

public class ConcurrentLRUCache<K, V> implements Cache<K, V> {
	
	private final int capacity;
	private final ConcurrentHashMap<K, TimestampValue<V>> store;
	private final ConcurrentLinkedDeque<K> accessOrder;
	private final AtomicInteger currentSize = new AtomicInteger(0);
	private final Duration ttl;
	
	private record TimestampValue<V>(V value, Instant timestamp, Duration ttl){
		boolean isExpired() {
			return timestamp.plus(ttl).isBefore(Instant.now());
		}
	}
	
	public ConcurrentLRUCache(int capacity, Duration ttl){
		this.capacity = capacity;
		this.store = new ConcurrentHashMap<K,TimestampValue<V>>(capacity);
		this.accessOrder = new ConcurrentLinkedDeque<K>();
		this.ttl = ttl;
	}
	

	@Override
	public void store(K key, V value) {
		if (currentSize.intValue() >= capacity) {
			evictLRU();
		}
		store.put(key, new TimestampValue(value, Instant.now(), ttl));
		recordAccess(key);
		currentSize.incrementAndGet();
	}

	private void evictLRU() {
		K lruKey = accessOrder.removeFirst();
		store.remove(lruKey);
		currentSize.decrementAndGet();
	}


	@Override
	public Optional<V> retrieve(K key) {
		TimestampValue<V> value = store.computeIfAbsent(key, k -> fetchFromDB(k));
		if (value != null && !value.isExpired()) {
			recordAccess(key);
			return Optional.of(value.value());
		}
		else {
			return Optional.empty();
		}
	}

    // LIMITATION: remove() + addLast() not atomic — duplicates possible under contention
    private void recordAccess(K key) {
        accessOrder.remove(key);  // O(n)
        accessOrder.addLast(key);
    }

    //placeholder
	private TimestampValue<V> fetchFromDB(K key) {
		return null;
	}


	@Override
	public void evict(K key) {
		store.remove(key);
		accessOrder.remove(key);
		currentSize.decrementAndGet();
	}

	@Override
	public void clear() {
		store.clear();
		accessOrder.clear();
		currentSize.set(0);
	}

	@Override
	public int size() {
		return currentSize.get();
	}

}
