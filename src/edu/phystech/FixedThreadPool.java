package edu.phystech;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class FixedThreadPool implements ThreadPool {
  private final Queue<Runnable> tasks = new LinkedList<>();
  private final int size;
  private final Thread[] threads;
  
  public FixedThreadPool(int size) {
    threads = new Thread[size];
    this.size = size;
  }
  
  @Override
  public void start() {
    for (int i = 0; i < size; i++) {
      threads[i] = getThread();
      threads[i].start();
    }
  }
  
  
  private Thread getThread() {
    return new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        try {
          Runnable task;
          synchronized (tasks) {
            while (tasks.isEmpty()) {
              tasks.wait();
            }
            task = tasks.poll();
          }
          if (task != null) {
            task.run();
          }
        } catch (Exception e) {
          Thread.currentThread().interrupt();
        }
      }});
  }
  
  @Override
  public void execute(Runnable runnable) {
    synchronized (tasks) {
      tasks.add(runnable);
      tasks.notifyAll();
    }
  }
  
  public void interrupt() {
    while (Arrays.stream(threads).anyMatch(Thread::isAlive)) {
      for (Thread t : threads) {
        t.interrupt();
      }
    }
  }
}
