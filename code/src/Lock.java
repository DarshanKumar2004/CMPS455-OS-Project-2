/* Team Members: Abigail Boggs (C00513558), Chad Dauphiney (C00510239), Parker Inzerella (C00512851), Darshan Kumar (C00529580)
 * CMPS 455
 * Project 2 - Lock
 */

// Begin code changes by Abigail Boggs.
import java.util.concurrent.Semaphore;

public class Lock {
    private final Semaphore mutex = new Semaphore(1);

    public void lock() throws InterruptedException {
        mutex.acquire();
    }

    public void unlock() {
        mutex.release();
    }
}
// End code changes by Abigail Boggs.
