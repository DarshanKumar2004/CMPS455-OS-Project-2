import java.util.*;

public class task_1 {

    private static Random rand = new Random();

    public static int domainCount = rand.nextInt(3,8);

    public static int fileObjCount = rand.nextInt(3,8);

    private static FileObject fileObjArr[] = new FileObject[fileObjCount];
    private static AIAgent_Task1 Agents[] = new AIAgent_Task1[domainCount];
    static Permission[][] accessMatrix = new Permission[domainCount][domainCount + fileObjCount -1];
    public static void main(String[] args) {
        run();
    }
    static void run() {
        for (int i = 0; i < fileObjCount; i++) {
            fileObjArr[i] = new FileObject();
        }

        createAccessMatrix(domainCount, fileObjCount, rand);
        printAccessMatrix(domainCount, fileObjCount);

        for (int i = 0; i < domainCount; i++) {
            Agents[i] = new AIAgent_Task1(domainCount, fileObjCount, i, fileObjArr);
            // Starts new thread
            Agents[i].start();
        }
    }
    static void createAccessMatrix(int domainCount, int fileObjCount, Random random) {

        Permission[] filePerms = {Permission.READ, Permission.WRITE, Permission.READ_WRITE};
        Permission[] domainPerms = {Permission.ALLOW};
        for (int i = 0; i < domainCount; i++) {

            for (int j = 0; j < domainCount + fileObjCount - 1; j++) {

                if (j < fileObjCount) {
                    if (random.nextBoolean()) {
                        accessMatrix[i][j] = filePerms[random.nextInt(filePerms.length)];
                    }
                    else {
                        accessMatrix[i][j] = Permission.NONE;
                    }
                }
                else {
                    if (random.nextBoolean()) {
                        accessMatrix[i][j] = Permission.ALLOW;
                    }
                    else {
                        accessMatrix[i][j] = Permission.NONE;
                    }
                }
            }
        }
    }
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
    static void printAccessMatrix(int domainCount, int fileObjCount) {
        System.out.println("Access control scheme: Access Matrix");
        System.out.println("Domain Count: " + domainCount);
        System.out.println("Object Count: " + fileObjCount);

        System.out.print("Domain/Object");
        for (int i = 0; i < fileObjCount; i++) {
            System.out.printf("%6s", "F" + (i+1));
        }

        for (int i = 0; i < domainCount; i++) {
            System.out.printf("%8s", "D" + (i+1));
        }

        System.out.println();

        for (int i = 0; i < domainCount; i++) {

            System.out.printf("%14s", "D" + (i+1));

            for (int j = 0; j < fileObjCount; j++) {
                    System.out.printf("%6s", getPermissionString(accessMatrix[i][j]));
                }

            int column = fileObjCount;

            for (int j = 0; j < domainCount; j++) {
                if (j == i) {
                    System.out.printf("%8s", "");

                }
                else {
                    System.out.printf("%8s", getPermissionString(accessMatrix[i][column++]));
                }
            }
            System.out.println();
        }






    }
}

class AIAgent_Task1 extends AIAgent {
    public AIAgent_Task1 (int numDomains, int numFiles, int threadNumber, FileObject fileObjArr[]) {
        super(numDomains, numFiles, threadNumber, fileObjArr);
    }

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