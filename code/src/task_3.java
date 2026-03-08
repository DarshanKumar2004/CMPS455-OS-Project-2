// Begin code changes by Darshan Kumar.
import java.util.*;
import java.util.concurrent.Semaphore;

public class task_3 {

    // *** temp code below ***
    enum Permission { READ, WRITE, READ_WRITE, ALLOW, NONE }

    static class FileObject {
        private String data = "";
        private final Lock lock = new Lock();

        public void write(String val) throws InterruptedException {
            lock.lock();
            data = val;
            lock.unlock();
        }

        public String read() throws InterruptedException {
            lock.lock();
            String val = data;
            lock.unlock();
            return val;
        }
    }

    // *** Not temp anymore ***

    // Capability list hash map
    static HashMap<Integer, HashMap<String, Permission>> capabilityLists = new HashMap<>();

    // Populate capability lists randomly
    static void initCapabilityLists(int numDomains, int numFiles, Random rand) {
        Permission[] filePerms = { Permission.READ, Permission.WRITE, Permission.READ_WRITE };

        for (int d = 0; d < numDomains; d++) {
            HashMap<String, Permission> caps = new HashMap<>();

            // Randomly assign file permissions
            for (int f = 0; f < numFiles; f++) {
                if (rand.nextBoolean()) { // ~50% chance domain has access to this file
                    caps.put("F" + (f + 1), filePerms[rand.nextInt(filePerms.length)]);
                }
            }

            // Randomly assign domain switch permissions (cannot switch to itself)
            for (int target = 0; target < numDomains; target++) {
                if (target != d && rand.nextBoolean()) {
                    caps.put("D" + (target + 1), Permission.ALLOW);
                }
            }

            capabilityLists.put(d, caps);
        }
    }

    // Print capability lists
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


    static boolean arbitrate(int[] agentDomain, String objectID, Permission requested) {
        HashMap<String, Permission> caps = capabilityLists.get(agentDomain[0]);
        Permission granted = caps.getOrDefault(objectID, Permission.NONE);

        // Domain switch request
        if (requested == Permission.ALLOW) {
            if (granted == Permission.ALLOW) {
                int targetDomain = Integer.parseInt(objectID.substring(1)) - 1;
                agentDomain[0] = targetDomain; // perform the switch
                return true;
            }
            return false;
        }

        // File access request
        if (requested == Permission.READ && (granted == Permission.READ || granted == Permission.READ_WRITE))
            return true;
        if (requested == Permission.WRITE && (granted == Permission.WRITE || granted == Permission.READ_WRITE))
            return true;

        return false;
    }
}
// End code changes by Darshan Kumar.