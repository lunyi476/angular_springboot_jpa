package com.springbootjpaangular2.domain;


/**Livelock is another concurrency problem and is similar to deadlock. In livelock, 
 * two or more threads keep on transferring states between one another instead of 
 * waiting infinitely as we saw in the deadlock example. Consequently, 
 * the threads are not able to perform their respective tasks.
 * 
 * @author Lun Yi
 *
 */
public class DeadLockExample {

    static class Friend {
        private final String name;
        public Friend(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
        
        public synchronized void bow(Friend bower) { // me get lock1, other side gets lock2
            System.out.format("%s: %s"
                + "  has bowed to me!%n", 
                this.name, bower.getName());
            //when you bow to a friend, you must remain bowed until your friend has a chance to return the bow
            // this call need to get friend lock but friend is in bow which hold lock
            // friend  this call need to get my lock but I am in bow whch hold lock
            bower.bowBack(this); // me waiting lock2 which was hold by other, other side waiting lock1 which was hold by me
        }
        
        public synchronized void bowBack(Friend bower) {
            System.out.format("%s: %s"
                + " has bowed back to me!%n",
                this.name, bower.getName());
        }
    }

    public static void main(String[] args) {
        final Friend alphonse = new Friend("Alphonse");
        final Friend gaston = new Friend("Gaston");
        new Thread(new Runnable() { public void run() { alphonse.bow(gaston); }}).start();
        new Thread(new Runnable() { public void run() { gaston.bow(alphonse); }}).start();
    }
}
