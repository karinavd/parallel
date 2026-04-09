package com.company;

class ThreadClass {
    private final Thread thread;
    private int id;
    private int step;
    private volatile boolean canStop = false;
private int delay;
    public ThreadClass(int id,int step) {
        this.id = id;
        this.step = step;
        this.thread = new Thread(() -> this.calculator());
    }
public void setDelay(int delay){
    this.delay = delay;
}
    private void calculator() {
        long sum = 0;
        long count = 0;
        
        do {
            count++;
            sum += step;
        } while (!canStop);
        
        System.out.println("Thread index: " + id + "; Sum: " + sum + "; Amount of elements: " + count+" Assigned delay:" + delay );
    }

    public void stop() {
        canStop = true;
    }

    public void start() {
        thread.start();
    }
}