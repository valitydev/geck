package dev.vality.geck.common.util;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public final class ObjectCache<T> {
    private T cached;
    private final ReadWriteLock updateRWLock = new ReentrantReadWriteLock();
    private final Lock updateRLock = updateRWLock.readLock();
    private final Lock updateWLock = updateRWLock.writeLock();
    private final ReadWriteLock chdRWLock = new ReentrantReadWriteLock();
    private final Lock chdRLock = chdRWLock.readLock();
    private final Lock chdWLock = chdRWLock.writeLock();
    private final Supplier<T> cacheCreator;
    private final long updateDelay;
    private volatile long lastUpdateTime;

    public ObjectCache(Supplier<T> cacheCreator) {
        this(cacheCreator, -1);
    }

    /**
     * @param updateDelay use -1 to disable delay, use 0 to always update, greater than 0 to set fixed delay
     */
    public ObjectCache(Supplier<T> cacheCreator, long updateDelay) {
        Objects.requireNonNull(cacheCreator);
        this.cacheCreator = cacheCreator;
        this.updateDelay = updateDelay;
    }

    public T getObject() {
        long currTime = System.currentTimeMillis();
        if (isUpdateRequired(currTime)) {
            if (updateWLock.tryLock()) {
                try {
                    return isUpdateRequired(currTime) ? updateObjectInternal(currTime) : getObjInternal();
                } finally {
                    updateWLock.unlock();
                }
            } else {
                return isNotInitialized() ? getUpdatedObjInternal() : getObjInternal();
            }
        } else {
            return getObjInternal();
        }
    }

    private boolean isUpdateRequired(long currTime) {
        return isOutdated(currTime) || isNotInitialized();
    }

    private T updateObject() {
        return cacheCreator.get();
    }

    private T updateObjectInternal(long currTime) {
        T newObject = updateObject();
        if (newObject == null) {
            newObject = getObjInternal();
        } else {
            setObjInternal(newObject);
        }
        lastUpdateTime = currTime;
        return newObject;
    }

    private T getUpdatedObjInternal() {
        try {
            updateRLock.lock();
            return getObjInternal();
        } finally {
            updateRLock.unlock();
        }
    }

    private T getObjInternal() {
        try {
            chdRLock.lock();
            return cached;
        } finally {
            chdRLock.unlock();
        }
    }

    private void setObjInternal(T obj) {
        try {
            chdWLock.lock();
            cached = obj;
        } finally {
            chdWLock.unlock();
        }
    }

    private boolean isOutdated(long currTime) {
        if (updateDelay < 0) {
            return false;
        } else {
            return ((currTime - lastUpdateTime) >= updateDelay);
        }
    }

    private boolean isNotInitialized() {
        return getObjInternal() == null;
    }

}