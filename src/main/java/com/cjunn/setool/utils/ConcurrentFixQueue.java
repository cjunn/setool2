package com.cjunn.setool.utils;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName FIFOQueue
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/8 10:13
 * @Version
 */
public class ConcurrentFixQueue<E> extends AbstractQueue<E> {
    private Queue<E> queue=new LinkedList<>();
    private final int fixSize;

    public long getLastOfferTime() {
        return lastOfferTime;
    }

    private long lastOfferTime=0;
    private ReentrantLock lock=new ReentrantLock();

    public ConcurrentFixQueue(int fixSize){
        this.fixSize=fixSize;
    }

    @Override
    public Iterator<E> iterator() {
        lock.lock();
        try{
            return  new LinkedList<E>(queue).iterator();
        }finally {
            lock.unlock();
        }
    }


    @Override
    public boolean offer(E e) {
        lock.lock();
        try{
            lastOfferTime=System.currentTimeMillis();
            return queue.offer(e);
        }finally {
            if(queue.size()>fixSize){
                queue.poll();
            }
            lock.unlock();
        }
    }



















    @Override
    public int size() {
        lock.lock();
        try{
            return queue.size();
        }finally {
            lock.unlock();
        }
    }



    @Override
    public E poll() {
        lock.lock();
        try{
            return queue.poll();
        }finally {
            lock.unlock();
        }
    }

    @Override
    public E peek() {
        lock.lock();
        try{
            return queue.peek();
        }finally {
            lock.unlock();
        }
    }
}
