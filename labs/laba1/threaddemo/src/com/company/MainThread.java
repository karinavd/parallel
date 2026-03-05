package com.company;

class ThreadClass {
    private final Thread thread;
    private int id;
    private volatile boolean canStop = false;

    public ThreadClass(int id) {
        this.id = id;
        this.thread = new Thread(() -> this.calculator());
    }

    private void calculator() {
        long sum = 0;
        long count = 0;
        
        do {
            count++;
            sum += 2;
        } while (!canStop);
        
        System.out.println("Thread index: " + id + "; Sum: " + sum + "; Amount of elements: " + count);
    }

    public void stop() {
        canStop = true;
    }

    public void start() {
        thread.start();
    }
}