/* Team Members: Abigail Boggs (C00513558), Chad Dauphiney (C00510239), Parker Inzerella (C00512851), Darshan Kumar (C00529580)
 * CMPS 455
 * Project 2 - Task 1
 */

// Begin code changes by Abigail Boggs
import java.util.*;

public class
task_1 {

    private static Random rand = new Random();

    // Randomly Initialize number of domain objects and file objects to an integer [3,7]
    public static int domainCount = rand.nextInt(3, 8);

    public static int fileObjCount = rand.nextInt(3, 8);

    private static FileObject fileObjArr[] = new FileObject[fileObjCount];
    private static AIAgent_Task1 Agents[] = new AIAgent_Task1[domainCount];

    // Create matrix of N x (N+M-1) where N is the number of domains and M is the number of file objects
    static Permission[][] accessMatrix = new Permission[domainCount][domainCount + fileObjCount - 1];

    // Run task 1
    static void run() {

        // Create file objects
        for (int i = 0; i < fileObjCount; i++) {
            fileObjArr[i] = new FileObject();
        }

        // Add random permissions to access matrix
        createAccessMatrix(domainCount, fileObjCount, rand);

        // Print access matrix
        printAccessMatrix(domainCount, fileObjCount);

        // Create agents to perform requests
        for (int i = 0; i < domainCount; i++) {
            Agents[i] = new AIAgent_Task1(domainCount, fileObjCount, i, fileObjArr);
            // Starts new thread
            Agents[i].start();
        }
    }

    // Fill access matrix with random permissions
    static void createAccessMatrix(int domainCount, int fileObjCount, Random random) {

        // Allowed file object permissions
        Permission[] filePerms = {Permission.READ, Permission.WRITE, Permission.READ_WRITE};

        // Loop through each domain object
        for (int i = 0; i < domainCount; i++) {

            // Loop through each file object and domain object to assign individual permissions
            for (int j = 0; j < domainCount + fileObjCount - 1; j++) {

                // Randomly assign permissions for file objects
                if (j < fileObjCount) {
                    if (random.nextBoolean()) {
                        accessMatrix[i][j] = filePerms[random.nextInt(filePerms.length)];
                    } else {
                        accessMatrix[i][j] = Permission.NONE;
                    }
                }
                // Randomly assign permissions for domain objects
                else {
                    if (random.nextBoolean()) {
                        accessMatrix[i][j] = Permission.ALLOW;
                    } else {
                        accessMatrix[i][j] = Permission.NONE;
                    }
                }
            }
        }
    }

    // Translate permissions to string representations
    static String getPermissionString(Permission p) {

        switch (p) {
            case READ:
                return "R";
            case WRITE:
                return "W";
            case READ_WRITE:
                return "R/W";
            case ALLOW:
                return "Allow";
            default:
                return "";
        }
    }

    // Print access matrix
    static void printAccessMatrix(int domainCount, int fileObjCount) {

        // Display parameters
        // System.out.println("Access control scheme: Access Matrix");
        System.out.println("Domain Count: " + domainCount);
        System.out.println("Object Count: " + fileObjCount);

        // Display header of file objects and domain objects
        System.out.print("Domain/Object");
        for (int i = 0; i < fileObjCount; i++) {
            System.out.printf("%6s", "F" + (i + 1));
        }
        for (int i = 0; i < domainCount; i++) {
            System.out.printf("%8s", "D" + (i + 1));
        }

        System.out.println();

        // Display file and domain object permissions for each domain
        for (int i = 0; i < domainCount; i++) {

            System.out.printf("%14s", "D" + (i + 1));

            for (int j = 0; j < fileObjCount; j++) {
                System.out.printf("%6s", getPermissionString(accessMatrix[i][j]));
            }

            int column = fileObjCount;

            for (int j = 0; j < domainCount; j++) {

                // Skip for self-domain
                if (j == i) {
                    System.out.printf("%8s", "");
                } else {
                    System.out.printf("%8s", getPermissionString(accessMatrix[i][column++]));
                }
            }
            System.out.println();
        }


    }
}

// Use AIAgent class to perform request s
class AIAgent_Task1 extends AIAgent {
    public AIAgent_Task1(int numDomains, int numFiles, int threadNumber, FileObject fileObjArr[]) {
        super(numDomains, numFiles, threadNumber, fileObjArr);
    }

    // Check if permission is granted based on agent action and access matrix
    @Override
    public boolean checkPermissions(int[] currentDomainArr, String requestedAction, int objID) {

        int currentDomain = currentDomainArr[0];
        Permission granted = task_1.accessMatrix[currentDomain][objID];
        switch (requestedAction) {
            case "switch":
                return granted == Permission.ALLOW;
            case "read":
                return granted == Permission.READ || granted == Permission.READ_WRITE;
            case "write":
                return granted == Permission.WRITE || granted == Permission.READ_WRITE;
            default:
                return false;
        }
    }
}
// End code changes by Abigail Boggs