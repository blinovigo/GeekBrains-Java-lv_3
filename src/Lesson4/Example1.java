package Lesson4;

public class Example1 {
    private final Object mon = new Object();
    private volatile char currentLetter = 'A';

    public static void main(String[] args) {
        Example1 e1 = new Example1();
        new Thread(() -> e1.printA()).start();
        new Thread(() -> e1.printB()).start();
        new Thread(() -> e1.printC()).start();
    }

    public void printA() {
        synchronized (mon) {
            for (int i = 0; i < 5; i++) {
                while (currentLetter != 'A') {
                    try {
                        mon.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("A");
                currentLetter = 'B';
                mon.notifyAll();
            }
        }
    }
    public void printB() {
        synchronized (mon) {
            for (int i = 0; i < 5; i++) {
                while (currentLetter != 'B') {
                    try {
                        mon.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("B");
                currentLetter = 'C';
                mon.notifyAll();
            }
        }
    }

    public void printC() {
        synchronized (mon) {
            for (int i = 0; i < 5; i++) {
                while (currentLetter != 'C') {
                    try {
                        mon.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("C");
                currentLetter = 'A';
                mon.notifyAll();
            }
        }
    }

}
