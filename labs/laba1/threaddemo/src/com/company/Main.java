package com.company;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
public class Main {
    public static void main(String[] args) {
        System.out.println("Enter the number of threads:");
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        StopThread stopper = new StopThread();

        for (int i = 1; i <= count; i++) {
            ThreadClass thread = new ThreadClass(i);
            stopper.addThread(thread);
            thread.start();
        }
        stopper.startStopper();
        scanner.close();
    }
}