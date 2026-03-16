// Begin code changes by Darshan Kumar.
import java.util.*;
import java.util.concurrent.Semaphore;
enum Permission { READ, WRITE, READ_WRITE, ALLOW, NONE }


public class task_3{
    private static Random random = new Random();
    public static int randDomains = random.nextInt(3, 8);
    public static int randFiles = random.nextInt(3, 8);
    private static FileObject fileObjArr[] = new FileObject[randFiles];
    private static AIAgent_Task3 Agents[] = new AIAgent_Task3[randDomains]; // Need as many threads as domains

    static void run() {
        for (int i = 0; i < randFiles; i++) {
            fileObjArr[i] = new FileObject();
        }
        initCapabilityLists(randDomains, randFiles, random);
        printCapabilityLists(randDomains);
        for (int i = 0; i < randDomains; i++) {
            Agents[i] = new AIAgent_Task3(randDomains, randFiles, i, fileObjArr);
            // Starts new thread
            Agents[i].start();
        }
    }


    // Make capability list
    static HashMap<Integer, HashMap<String, Permission>> capabilityLists = new HashMap<>();
    static void initCapabilityLists(int numDomains, int numFiles, Random random) {
        Permission[] filePerms = {Permission.READ, Permission.WRITE, Permission.READ_WRITE};

        for (int d = 0; d < numDomains; d++) {
            HashMap<String, Permission> caps = new HashMap<>();

            for (int f = 0; f < numFiles; f++) {
                if (random.nextBoolean()) {
                    caps.put("F" + (f + 1), filePerms[random.nextInt(filePerms.length)]);
                }
            }

            for (int target = 0; target < numDomains; target++) {
                if (target != d && random.nextBoolean()) {
                    caps.put("D" + (target + 1), Permission.ALLOW);
                }
            }

            capabilityLists.put(d, caps);
        }
    }

    // Print capability list
    static void printCapabilityLists(int numDomains) {
        System.out.println("Access control scheme: Capability List");
        for (int d = 0; d < numDomains; d++) {
            StringBuilder sb = new StringBuilder("D" + (d + 1) + " --> ");
            HashMap<String, Permission> caps = capabilityLists.get(d);
            if (caps.isEmpty()) {
                sb.append("(none)");
            } else {
                StringJoiner sj = new StringJoiner(", ");
                for (Map.Entry<String, Permission> entry : caps.entrySet()) {
                    String permStr = switch (entry.getValue()) {
                        case READ -> "R";
                        case WRITE -> "W";
                        case READ_WRITE -> "R/W";
                        case ALLOW -> "allow";
                        default -> "none";
                    };
                    sj.add(entry.getKey() + ":" + permStr);
                }
                sb.append(sj);
            }
            System.out.println(sb);
        }
    }
}


class AIAgent_Task3 extends AIAgent {
    public AIAgent_Task3 (int numDomains, int numFiles, int threadNumber, FileObject fileObjArr[]) {
        super(numDomains, numFiles, threadNumber, fileObjArr);
    }

    @Override
    public boolean checkPermissions(int[] currentDomainArr, String requestedAction, int objID) {
        // Get hashmap for current domain
        HashMap<String, Permission> caps = task_3.capabilityLists.get(currentDomainArr[0]);

        // determine file or domain request
        String objectKey;
        if (objID < numFiles) {
            objectKey = "F" + (objID + 1);
        } else {
            objectKey = "D" + (objID - numFiles + 1);
        }

        // Ensure something is there
        Permission granted = caps.getOrDefault(objectKey, Permission.NONE);

        if (requestedAction.equals("switch")) {
            return granted == Permission.ALLOW;
        }
        else if (requestedAction.equals("read")) {
            return granted == Permission.READ || granted == Permission.READ_WRITE;
        }
        else if (requestedAction.equals("write")) {
            return granted == Permission.WRITE || granted == Permission.READ_WRITE;
        }

        return false;
    }
}

// End code changes by Darshan Kumar.