package com.saqib.scribbles;

import java.time.Duration;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 
 * @param <K>
 * @param <V>
 * 
 * Using a LinkedHashMap and a ttl. Can't handle concurrent use. 
 * We could synchronize on the methods but that's inefficient.
 * 
 * Solution: use Caffeine.
 * 
 */

public class LRUCacheTTL<K, V> implements Cache<K, V> {
	
	record TimestampedValue<V>(V value, Instant timestamp, Duration ttl) {
		boolean isExpired() {
			return timestamp.plus(ttl).isBefore(Instant.now());
		}
	}
	
	private final int capacity;
	private final LinkedHashMap<K,TimestampedValue<V>> store;
	private final Duration ttl;
	
	public LRUCacheTTL(int capacity, Duration ttl) {
		this.capacity = capacity;
		this.ttl = ttl;
		this.store = new LinkedHashMap<>(capacity, 0.75f, true) {
			protected boolean removeEldestEntry(Map.Entry<K, TimestampedValue<V>> eldest) {
				return size() > capacity;
			}
		};
	}

	@Override
	public void store(K key, V value) {
		store.put(key, new TimestampedValue<V>(value, Instant.now(), ttl));
	}

	@Override
	public Optional<V> retrieve(K key) {
		TimestampedValue<V> entry = store.get(key);
		
		if (entry != null && !entry.isExpired()) {
			return Optional.ofNullable(entry.value());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public void evict(K key) {
		store.remove(key);
	}

	@Override
	public void clear() {
		store.clear();
	}

	@Override
	public int size() {
		return store.size();
	}

}
