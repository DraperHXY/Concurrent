import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author draper_hxy
 */
public class LruCache<K, V> {

    private final int maxCapacity;
    private ConcurrentHashMap<K, V> cacheMap;
    private ConcurrentLinkedQueue<K> keys;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock writeLock = readWriteLock.writeLock();
    private Lock readLock = readWriteLock.readLock();

    private ScheduledExecutorService scheduledExecutorService;

    public LruCache(int maxCapacity) {
        if (maxCapacity < 0) {
            throw new IllegalArgumentException();
        }
        this.maxCapacity = maxCapacity;
        cacheMap = new ConcurrentHashMap<>(maxCapacity);
        keys = new ConcurrentLinkedQueue<>();
        scheduledExecutorService = Executors.newScheduledThreadPool(3);
    }

    public V put(K key, V value) {
        writeLock.lock();
        try {
            // 1. 是否存在于当前缓存
            if (cacheMap.contains(key)) {
                moveToTailOfQueue(key);
                cacheMap.put(key, value);
                return value;
            }
            // 2. 是否超出缓存容量，超出的话移除队列头部元素以及对应的缓存
            if (cacheMap.size() == maxCapacity) {
                removeOldestKey();
            }
            // 3. key 不存在与当前缓存。将 key 添加到队列的尾部并且缓存 key 及对应的元素
            keys.add(key);
            cacheMap.put(key, value);
            return value;
        } finally {
            writeLock.unlock();
        }
    }

    public V get(K key) {
        readLock.lock();
        try {
            if (cacheMap.contains(key)) {
                moveToTailOfQueue(key);
                return cacheMap.get(key);
            } else {
                return null;
            }
        } finally {
            readLock.unlock();
        }
    }

    public V remove(K key) {
        writeLock.lock();
        try {
            if (cacheMap.contains(key)) {
                keys.remove(key);
                return cacheMap.remove(key);
            }
            return null;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 将元素添加到队列的尾部（put / get 的时候执行）
     */
    private void moveToTailOfQueue(K key) {
        keys.remove(key);
        keys.add(key);
    }

    /**
     * 移除队列头部的元素以及对应的缓存（缓存容量已满的时候执行）
     */
    private void removeOldestKey() {
        K oldestKey = keys.poll();
        if (oldestKey != null) {
            cacheMap.remove(oldestKey);
        }
    }

    private void removeAfterExpireTime(final K key, long expireTime) {
        scheduledExecutorService.schedule(() -> {
            cacheMap.remove(key);
            keys.remove(key);
        }, expireTime, TimeUnit.MILLISECONDS);
    }

}
