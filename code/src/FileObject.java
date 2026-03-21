/* Team Members: Abigail Boggs (C00513558), Chad Dauphiney (C00510239), Parker Inzerella (C00512851), Darshan Kumar (C00529580)
 * CMPS 455
 * Project 2 - File Object
 */

// Begin code changes by Darshan Kumar.
public class FileObject {
    private String data = "Initial File Data";
    private Lock lock = new Lock();

    public String read() {
        try {
            lock.lock();
            // Read here
            return data;
        }
        catch (InterruptedException e) {
            System.out.println("FileObject read interrupted.");
            return null;
        }
        finally {
            lock.unlock();  // Alwasys unlocks
        }
    }

    public void write(String newData) {
        try {
            lock.lock();
            // Write here
            data = newData;
        }
        catch (InterruptedException e) {
            System.out.println("FileObject write interrupted.");
        }
        finally {
            lock.unlock();  // Always unlocks
        }
    }
}
// End code changes by Darshan Kumar.
