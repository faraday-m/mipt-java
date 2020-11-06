package edu.phystech;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ScalableThreadPool implements ThreadPool {
  private final List<Thread> threads;
  private final Queue<Runnable> tasks = new LinkedList<>();
  private final int minSize;
  private final int maxSize;
  private volatile int tasksInWork = 0;
  
  public ScalableThreadPool(int minSize, int maxSize) {
    threads = new ArrayList<>(minSize);
    this.minSize = minSize;
    this.maxSize = maxSize;
  }
  
  @Override
  public void start() {
    for (int i = 0; i < minSize; i++) {
      Thread thread = getThread();
      threads.add(thread);
      System.out.println("Thread " + i + " started");
      thread.start();
    }
  }
  
  @Override
  public void execute(Runnable runnable) {
    synchronized (tasks) {
      tasks.add(runnable);
      synchronized (threads) {
        if (threads.size() < maxSize) {
          Thread thread = getThread();
          threads.add(thread);
          System.out.println("Thread " + (threads.size()-1) + " started");
          thread.start();
        }
      }
      tasks.notifyAll();
    }
  }
  
  private Thread getThread() {
    return new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        try {
        Runnable task;
        synchronized (tasks) {
          while (tasks.isEmpty() && tasksInWork < minSize) {
            tasks.wait();
          }
          task = tasks.poll();
        }
        if (task != null) {
          tasksInWork += 1;
          System.out.println("Tasks in work: " + tasksInWork);
          task.run();
          tasksInWork -= 1;
          System.out.println("Tasks in work: " + tasksInWork);
      }
    } catch (Exception e) {
      Thread.currentThread().interrupt();
    }
    }});
  }
  
  public void interrupt() {
    while (threads.stream().anyMatch(Thread::isAlive)) {
      for (Thread t : threads) {
        t.interrupt();
      }
    }
  }
}
