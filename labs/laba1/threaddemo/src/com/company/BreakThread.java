package com.company;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
class StopThread {
    private static class ThreadItem {
        final ThreadClass thread;
        final int delay;
        ThreadItem(ThreadClass thread, int delay) {
            this.thread = thread;
            this.delay = delay;
        }
    }

    private final List<ThreadItem> threadsList = new ArrayList<>();
    public void addThread(ThreadClass thread) {
        threadsList.add(new ThreadItem(thread,  new Random().nextInt(9000) + 1000));
    }

    public void startStopper() {
        new Thread(() -> {
            threadsList.sort(Comparator.comparingInt(x -> x.delay));
            
            int time = 0;
            for (ThreadItem item : threadsList) {
                try {
                    Thread.sleep(item.delay - time);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Thread has stopped!");
                }
                
                time = item.delay;
                item.thread.stop();
            }
        }).start();
    }
}