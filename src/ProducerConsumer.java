import java.util.LinkedList;
import java.util.List;

/**
 *  The most classic  multi-process synchronization problem
 *      This is the common multi-threading problem to ensure
 *      consistent execution of process in multi-threading
 *      environment of fixed Buffer size
 *
 *  Explanation :
 *      Producer process inserts new process should ensure
 *      buffer's size limit not exceeds
 *      Consumer process consumes process should ensure
 *      buffer's size not empty
 *
 *      The idea is to implement this both process mutual exclusive
 *      using wait() and notify()
 */
public class ProducerConsumer {
    // list for producer and consumer process of buffer size -10
    LinkedList<Integer> list = new LinkedList<>();
    int capacity = 10;

    //Producer Process
    private void produce() throws InterruptedException{
        int val=0;
        synchronized (this) {
            while (true) {
                    //if buffer size exceeds
                while(list.size() == capacity)
                    wait(); // the current execution is in wait/blocked state , till the consumer consumes
                list.add(val);
                System.out.println("Producer "+val++);
                notify(); // after producer produce , it notifies consumer
            }
        }
    }
        //consumer process
    private void consume() throws InterruptedException{
        synchronized (this) {
            while (true) {
                //if list is empty, consumer has nothing to consume
                // and so it is pushed to wait state
                while(list.size() == 0)
                    wait();

                System.out.println("Consumer " + list.removeFirst());
                notify();
                Thread.sleep(1000);
            }
        }
    }

    public static void main(String args[]) throws InterruptedException{
        final ProducerConsumer pc = new ProducerConsumer();
        // execute producer process
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //execute consumer process
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }

}
