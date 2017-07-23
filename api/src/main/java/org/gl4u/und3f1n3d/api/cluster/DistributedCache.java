package org.gl4u.und3f1n3d.api.cluster;

public interface DistributedCache<K,V> {

    V get(K key);

    void put(K key, V value);

    void remove(K key);

}
