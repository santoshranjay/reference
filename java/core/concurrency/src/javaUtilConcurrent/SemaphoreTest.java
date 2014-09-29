/*
 * A semaphore is a protected variable (or abstract data type) and constitutes the classic method for restricting access to shared resources (e.g. storage)
 * in a multi-processing environment.In other words, where a synchronized block allows only one entity to access a shared resource, a semaphore allows 'n' 
 * entities to access the resource. As an example, imagine going to the bank. Imagine too that there are few tellers, so that the line waiting to make a 
 * bank transaction is rather long. Think of the number of available tellers (that is, tellers who are currently not handling a customer) as the semaphore 
 * count. When the count is zero, no one new can engage with a teller. When a teller becomes available, the count increases, and another customer in the bank
 *  line can approach the teller. After the teller is engaged with a customer, the count is reduced. If no one is waiting in the bank line, the count remains
 *  above zero. Typically in a bank, the people waiting in a queue are handled in a first-in first-out (FIFO) manner. However, not all queues proceed in this 
 *  fair, equitable way. For instance, sometimes people jump ahead in the line for various reasons.
 *  Before going any further, it's helpful to define three synchronization-related terms: lock, permit, and block.
 *   A lock is a construct for controlling access to a shared resource by multiple threads. Commonly, a lock provides exclusive access to a shared resource 
 *   -- only one thread at a time can acquire the lock and all access to the shared resource requires that the lock be acquired first.
 *   A permit is like a ticket or token to a lock that is associated with a semaphore. A semaphore maintains a set of permits typically for a restricted pool 
 *   of resources. A thread must acquire a permit from the semaphore before it can obtain a resource from the pool. When the thread finishes with the resource,
 *   it returns it back to the pool. The permit is then returned to the semaphore, allowing another thread to acquire that resource. A block signifies that a 
 *   thread is waiting for a required lock or permit before it can access a shared resource. 
 *   With those definitions in mind, let's look at the new Semaphore class, the class in J2SE 5.0 that offers support for semaphores. 
 *   
 *   You create the class with one of the following two constructors: 

   Semaphore(int permits) 
   Semaphore(int permits, boolean fair)

Unless specified, the created Semaphore has a "non-fair" setting. This means that FIFO behavior is not guaranteed. To prevent starvation 
(a thread never getting a resource), it is good practice to always create fair semaphores. However, there are throughput advantages if you ignore fairness. 

To acquire a permit from a semaphore, you call one of the following methods: 

   acquire() 
   acquire(int permits) 

The acquire() signature of the method gets a single permit. The acquire(int permits) signature of the method gets the requested number of permits. 
The methods block until a permit is available, or until the waiting thread is interrupted. If the thread is interrupted, an InterruptedException is thrown.
The next two methods also acquire permits, but their threads are dormant and uninterruptible until the required number of permits are available. 
If a thread is interrupted while waiting, it is discovered after the resource becomes available. 

   acquireUninterruptibly() 
   acquireUninterruptibly(int permits) 

The tryAcquire methods do not block. If the required number of permits are available, the methods return true. If the permits are not available, 
the methods return false. All permits must be available for true to be returned. The fairness setting is ignored with these untimed tryAcquire methods. 

   tryAcquire() 
   tryAcquire(int permits) 

The timed versions work similarly to the untimed version, but wait for the specified TimeUnit before returning. If interrupted while waiting, 
the methods throw an InterruptedException. 

  
   tryAcquire(long timeout, TimeUnit unit) 
   tryAcquire(int permits, long timeout, TimeUnit unit) 

The following line shows how to wait 30 seconds for one permit: 

   boolean acq = tryAcquire(30, TimeUnit.SECONDS) 

Now let's look at an example that uses the Semaphore class. 
The following program, SemaphoreTest, simulates a service provided by an online auction house. 
The service allows customers to sample multiple items available for purchase, and displays an average of 
the prices of these items. However due to licensing limitations, only two threads can access the service at
 a time. To simulate the price sampling and averaging, the SemaphoreTest program waits 50 milliseconds, 
 and then returns a random number from 0-to-100. If the service is concurrently accessed after the limit of 
 two threads is reached, a second pricing scheme is used. This latter scheme doesn't have the licensing 
 restriction, but returns a less-reliable price (in this example, $20). 

Here is the SemaphoreTest program: 

   import java.util.concurrent.*;
   import java.util.*;   
   
   public class SemaphoreTest {
     private static final int LOOP_COUNT = 100;
     private static final int MAX_AVAILABLE = 2;
     private final static Semaphore semaphore =
       new Semaphore(MAX_AVAILABLE, true);   
   
     private static class Pricer {
       private static final Random random = new Random();
       public static int getGoodPrice() {
         int price = random.nextInt(100);
         try {
           Thread.sleep(50);
         } catch (InterruptedException ex) {
           ex.printStackTrace();
         }
         return price;
       }
       public static int getBadPrice() {
         return 20;
       }
     }
   
     public static void main(String args[]) {
       for (int i=0; i<LOOP_COUNT; i++) {
         final int count = i;
         new Thread() {
           public void run() {
             int price;
             if (semaphore.tryAcquire()) {
               try {
                 price = Pricer.getGoodPrice();
               } finally {
                 semaphore.release();
               }
             } else {
               price = Pricer.getBadPrice();
             }
             System.out.println(count + ": " + price);
           }
         }.start();
       }
     }
   }

Depending on the speed of your machine, you might need to modify the 50 millisecond delay to get reasonable results. "Reasonable results" means that you get
 some random values generated, and some $20 values. 

One sample run follows. Your results might differ. 

2: 20
3: 20
4: 20
5: 20
...

25: 20
26: 20
27: 20
0: 0
1: 4
30: 20
31: 20
32: 20
33: 20
...

43: 20
44: 20
28: 84
29: 6
48: 20
49: 20
50: 20
...

85: 20
86: 20
87: 20
88: 20
89: 20
46: 41
47: 55
93: 20
94: 20
95: 20
96: 20
97: 20
98: 20
99: 20
91: 4
92: 64

Notice how the results for pass 0 and 1 don't appear until after many other results. You then get more 20s before seeing more "good" results.
 Each call to the average pricing service takes time, therefore the interspersed results. 

By going through a semaphore, the program effectively controls access to the limited resource. 

Semaphores can also be used for controlling access to other pooled resources, such as connection pools.
 With a connection pool, there might be several open sockets. After passing through a semaphore lock, different threads would get access to the open sockets.
 

 */
package javaUtilConcurrent;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    private static final int LOOP_COUNT = 100;
    private static final int MAX_AVAILABLE = 2;
    private final static Semaphore semaphore =
      new Semaphore(MAX_AVAILABLE, true);   
  
    private static class Pricer {
      private static final Random random = new Random();
      public static int getGoodPrice() {
        int price = random.nextInt(100);
        try {
          Thread.sleep(50);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
        return price;
      }
      public static int getBadPrice() {
        return 20;
      }
    }
  
    public static void main(String args[]) {
      for (int i=0; i<LOOP_COUNT; i++) {
        final int count = i;
        new Thread() {
          public void run() {
            int price;
            if (semaphore.tryAcquire()) {
              try {
                price = Pricer.getGoodPrice();
              } finally {
                semaphore.release();
              }
            } else {
              price = Pricer.getBadPrice();
            }
            System.out.println(count + ": " + price);
          }
        }.start();
      }
    }
  }
