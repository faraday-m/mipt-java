package edu.phystech;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class FixedThreadPool implements ThreadPool {
  private final Queue<Runnable> tasks = new LinkedList<>();
  private final int size;
  private final Thread[] threads;
  private volatile boolean stopTasks = false;

  class Worker implements Runnable {
    @Override
    public void run() {
      while (!stopTasks) {
        try {
          Runnable task;
          synchronized (tasks) {
            while (tasks.isEmpty() && !stopTasks) {
              tasks.wait();
            }
            task = tasks.poll();
          }
          if (task != null) {
            task.run();
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
  
  public FixedThreadPool(int size) {
    threads = new Thread[size];
    this.size = size;
  }
  
  @Override
  public void start() {
    for (int i = 0; i < size; i++) {
      initThread(i);
    }
  }
  
  
  private void initThread(int i) {
    Thread thread = new Thread(new Worker());
    threads[i] = thread;
    thread.start();
  }
  
  @Override
  public void execute(Runnable runnable) {
    synchronized (tasks) {
      tasks.add(runnable);
      tasks.notifyAll();
    }
  }
  
  public void interrupt() throws InterruptedException {
    while (Arrays.stream(threads).anyMatch(Thread::isAlive)) {
      stopTasks = true;
      synchronized (tasks) {
        tasks.notifyAll();
      }
      for (int i = 0; i < size; i++) {
        threads[i].join();
      }
    }
    System.out.println("Threads stopped");
  }
}
