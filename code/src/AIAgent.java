// Begin code changes by Darshan Kumar.
import java.util.Random;

public abstract class AIAgent extends Thread {
    protected int[] currentDomain = {0};
    protected  FileObject fileObjArr[];
    protected  int threadNumber, numFiles, numDomains;
    protected Random random = new Random();


    // Abstract check permissions function
    public abstract boolean checkPermissions(int[] currentDomainArr, String requestedAction, int objID);

    // Constructor
    public AIAgent(int numDomains, int numFiles, int threadNumber, FileObject fileObjArr[]) {
        this.currentDomain[0] = threadNumber;
        this.fileObjArr = fileObjArr;
        this.threadNumber = threadNumber;
        this.numFiles = numFiles;
        this.numDomains = numDomains;
    }

    // Run function
    public void run() {
        int newDomain, randX, randY, yield;
        boolean passed;
        String[] words = {"Red", "Blue", "Green", "Orange", "Pink", "Purple", "Yellow", "White"};

        for (int i = 0; i < 5; i++) {
            randX = random.nextInt(0, numFiles + numDomains - 1);
            yield = random.nextInt(3, 8);
            String word = words[random.nextInt(words.length)];

            // Domain switch
            if (randX >= numFiles) {
                newDomain = randX - numFiles;
                if (newDomain != currentDomain[0]) {
                    System.out.println("[Thread: "+threadNumber+"(D"+(currentDomain[0] + 1)+")] Attempting to switch from D"+(currentDomain[0]+1)+" to D"+(newDomain+1)+".");
                    passed = checkPermissions(currentDomain, "switch", randX);
                    if (passed) {
                        System.out.println("[Thread: " + threadNumber + "(D" + (currentDomain[0] + 1) + ")] Switched to D" + (newDomain+1) + ".");
                        currentDomain[0] = newDomain;
                    }
                    else {
                        System.out.println("[Thread: "+threadNumber+"(D"+(currentDomain[0] + 1)+")] Operation failed, permission denied");
                    }
                }
            }
            else {  // File actoin
                randY = random.nextInt(0, 2); // 0-1.99 with .floor
                // Write
                if (randY == 1) {
                    System.out.println("[Thread: " + threadNumber + "(D" + (currentDomain[0] + 1) + ")] Attemping to write resource: F" + (randX + 1));
                    passed = checkPermissions(currentDomain, "write", randX);
                    if (passed) {
                        fileObjArr[randX].write(word);
                        System.out.println("[Thread: " + threadNumber + "(D" + (currentDomain[0] + 1) + ")] Writing \'" + word + "\' to resource F" + (randX + 1) + ".");
                    }
                    else {
                        System.out.println("[Thread: "+threadNumber+"(D"+(currentDomain[0] + 1)+")] Operation failed, permission denied");
                    }
                }
                else {  // Read
                    System.out.println("[Thread: " + threadNumber + "(D" + (currentDomain[0] + 1) + ")] Attemping to read resource: F" + (randX + 1));
                    passed = checkPermissions(currentDomain, "read", randX);
                    if (passed) {
                        System.out.println("[Thread: " + threadNumber + "(D" + (currentDomain[0] + 1) + ")] Resource F" +(randX + 1)+ " contains \'" + fileObjArr[randX].read() + "\'.");
                    }
                    else {
                        System.out.println("[Thread: "+threadNumber+"(D"+(currentDomain[0] + 1)+")] Operation failed, permission denied");
                    }
                }
            }
            System.out.println("[Thread: "+threadNumber+"(D"+(currentDomain[0] + 1)+")] Yielding " + yield + " times.");
            for (int j = 0; j < yield; j++) {
                Thread.yield();
            }
        }
        System.out.println("[Thread: "+threadNumber+"(D"+(currentDomain[0]+1)+")] Operation Complete.");
    }
}
// End code changes by Darshan Kumar.
