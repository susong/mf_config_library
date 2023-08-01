package com.mf.config;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 锁
 */
class Locker {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    void lock() {
        lock.writeLock().lock();
    }

    void unlock() {
        lock.writeLock().unlock();
    }

    void lockRead() {
        lock.readLock().lock();
    }

    void unlockRead() {
        lock.readLock().unlock();
    }

    void lockWrite() {
        lock.writeLock().lock();
    }

    void unlockWrite() {
        lock.writeLock().unlock();
    }
}
