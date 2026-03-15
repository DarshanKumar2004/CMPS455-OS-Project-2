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
        finally {
            lock.unlock();  // Always unlocks
        }
    }
}
// End code changes by Darshan Kumar.
