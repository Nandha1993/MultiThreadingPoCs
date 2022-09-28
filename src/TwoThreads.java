/**
 * When Two Threads in multi threaded environment
 * Imagine we need to operate both the threads alternatively (distribute exection evenly)
 */
public class  TwoThreads {
    int counter =1;
    static int n;

    public void printOddNumber(int n) {
        // synchronized block - for exclusive accessing
        synchronized (this) {
            while(counter < n) {

                while(counter %2 == 0) {
                    try {
                        //it instructs currently executing thread to wait until another thread
                        // enter monitor and notify
                        wait();
                    } catch(InterruptedException e) {
                        System.out.println(e);
                    }

                }
                System.out.print(counter + " ");
                counter++;
                // notify wakes up the first thread that called wait on the same object
                notify();
            }

        }

    }

    public void printEvenNumber(int n) {
        synchronized (this) {
            while(counter < n) {

                while(counter %2 == 1) {
                    try {
                        //it instructs currently executing thread to wait until another thread
                        // enter monitor and notify
                        wait();
                    } catch(InterruptedException e) {
                        System.out.println(e);
                    }

                }
                System.out.print(counter + " ");
                counter++;
                notify();
            }

        }
    }

    public static void main(String args[]) {

        TwoThreads twoThreads = new TwoThreads();
        n= 10;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                twoThreads.printEvenNumber(n);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                twoThreads.printOddNumber(n);
            }
        });
        t1.start();
        t2.start();
    }
}
