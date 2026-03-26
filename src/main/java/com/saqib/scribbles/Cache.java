package com.saqib.scribbles;

import java.util.Optional;

public interface Cache<K, V> {
	void store(K key, V value);
	Optional<V> retrieve(K key);
	void evict(K key);
	void clear();
	int size();
}
