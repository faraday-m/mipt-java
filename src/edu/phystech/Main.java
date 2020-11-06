package edu.phystech;

import static java.lang.Thread.sleep;

public class Main {
  public static void fixedThreadpoolTest() {
    FixedThreadPool ftp = new FixedThreadPool(10);
    ftp.start();
    for (int i = 0; i < 20; i++) {
      int finalI = i;
      ftp.execute(() -> {
        try {
          System.out.println("Task " + finalI);
          sleep(1000);
          System.out.println("Wake up");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }
    try {
      sleep(5000);
      System.out.println("Joining...");
      ftp.interrupt();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static void scalableThreadpoolTest() {
    ScalableThreadPool stp = new ScalableThreadPool(5, 10);
    stp.start();
    for (int i = 0; i < 20; i++) {
      int finalI = i;
      stp.execute(() -> {
        try {
          System.out.println("Task " + finalI);
          sleep(1000);
          System.out.println("Wake up");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }
    try {
      sleep(5000);
      System.out.println("Joining...");
      stp.interrupt();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static void main(String[] args) {
    scalableThreadpoolTest();
    fixedThreadpoolTest();
  }
}
