package edu.phystech;

import java.util.*;

public class ScalableThreadPool implements ThreadPool {
  private final List<Thread> threads;
  private final Queue<Runnable> tasks = new LinkedList<>();
  private final int minSize;
  private final int maxSize;
  private volatile int tasksInWork = 0;
  private volatile int threadsSize = 0;
  private volatile boolean stopTasks = false;

  class Worker implements Runnable {
    @Override
    public void run() {
      refreshThreads();
      while (!stopTasks) {
        try {
          Runnable task;
          synchronized (tasks) {
            while (tasks.isEmpty() && !stopTasks && tasksInWork < threadsSize) {
              tasks.wait();
            }
            task = tasks.poll();
            tasks.notifyAll();
          }
          if (task != null) {
            atomicInc();
            task.run();
            atomicDec();
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }}
  }

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
      tasks.notifyAll();
    }
    if (threadsSize < maxSize && tasksInWork == threadsSize) {
      Thread thread = getThread();
      threads.add(thread);
      System.out.println("Thread " + (threadsSize-1) + " started");
      thread.start();
    }
  }

  private void atomicInc() {
    synchronized (tasks) {
      tasksInWork += 1;
      System.out.println("Tasks in work: " + tasksInWork);
      tasks.notifyAll();
    }
  }

  private void atomicDec() {
    synchronized (tasks) {
      tasksInWork -= 1;
      System.out.println("Tasks in work: " + tasksInWork);
      tasks.notifyAll();
    }
  }


  private void refreshThreads() {
    synchronized (threads) {
      threadsSize = threads.size();
      System.out.println("Threads count: " + threadsSize);
      threads.notifyAll();
    }
  }
  
  private Thread getThread() {
    return new Thread(new Worker());
  }


  public void interrupt() throws InterruptedException {
    while (threads.stream().anyMatch(Thread::isAlive)) {
      stopTasks = true;
      synchronized (tasks) {
        tasks.notifyAll();
      }
      for (Thread t : threads) {
        t.join();
      }
    }
    System.out.println("Threads stopped");
  }


}
