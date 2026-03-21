// Begin code changes by <Parker Inzerella>
import java.util.ArrayList;

public class task_2 {
    enum Permission2 {READ, WRITE, READ_WRITE, ALLOW}

    enum Type {FILE, DOMAIN}

    //N = domains and M = files
    static int N = (int) ((Math.random() * 5) + 3);
    static int M = (int) ((Math.random() * 5) + 3);
    ;
    static AIAgent_Task2 Agents[] = new AIAgent_Task2[N];
    static ArrayList<objectAccess> accessList = new ArrayList<>(N + M);
    //make each list
    static void initializeList() {
        for (int i = 0; i < M; i++) {
            accessList.add(new objectAccess(Type.FILE));
        }
        for (int j = 0; j < N; j++) {
            accessList.add(new objectAccess(Type.DOMAIN));
        }
    }
    //run the agents
    static void runAgents() {
        FileObject[] fileObjArr = new FileObject[M];
        for (int i = 0; i < M; i++) {
            fileObjArr[i] = new FileObject();
        }
        initializeList();
        populateEntries();
        printAccessLists();
        for (int i = 0; i < N; i++) {
            Agents[i] = new AIAgent_Task2(N, M, i, fileObjArr);
            Agents[i].start();
        }
    }
    //randomize the perms
    static Permission2 pickPerm() {
        int roll = (int) (Math.random() * 3);
        switch (roll) {
            case 0:
                return Permission2.READ_WRITE;
            case 1:
                return Permission2.READ;
        }
        return Permission2.WRITE;
    }
//pop the entries for each list within the list
    static void populateEntries() {
        for (int i = 0; i < accessList.size(); i++) {
            objectAccess current = accessList.get(i);
            for (int j = 0; j < N; j++) {
                if (current.type == Type.FILE) {
                    if (Math.random() < 0.5) {
                        current.objects.add(new objectEntry(j, pickPerm()));
                    }
                } else if (current.type == Type.DOMAIN) {
                    if (Math.random() < 0.5) {
                        current.objects.add(new objectEntry(j, Permission2.ALLOW));
                    }
                }
            }
        }
    }
    //prints the access list
    static void printAccessLists() {
        System.out.println("Access control scheme: Access Lists");
        for (int i = 0; i < accessList.size(); i++) {
            objectAccess current = accessList.get(i);
            String label = (current.type == Type.FILE) ? "F" + (i+1) : "D" + (i - M + 1);
            StringBuilder sb = new StringBuilder(label + " --> ");
            if (current.objects.isEmpty()) {
                sb.append("(none)");
            } else {
                for (int j = 0; j < current.objects.size(); j++) {
                    objectEntry entry = current.objects.get(j);
                    String permStr = switch (entry.permission) {
                        case READ -> "R";
                        case WRITE -> "W";
                        case READ_WRITE -> "R/W";
                        case ALLOW -> "allow";
                    };
                    sb.append("D" + (entry.Domain + 1) + ":" + permStr);
                    if (j < current.objects.size() - 1) sb.append(", ");
                }
            }
            System.out.println(sb);
        }
    }

    //entry object
    static class objectEntry {
        int Domain;
        Permission2 permission;

        objectEntry(int Domain, Permission2 permission) {
            this.Domain = Domain;
            this.permission = permission;
        }
    }

    static class objectAccess {
        Type type;
        ArrayList<objectEntry> objects;
        objectAccess(Type type, ArrayList<objectEntry> objects) {
            this.type = type;
            this.objects = objects;
        }
        objectAccess(Type type) {
            this.type = type;
            this.objects = new ArrayList<>();
        }
    }

}
//agent / thread implementation
class AIAgent_Task2 extends AIAgent {
    @Override
    public boolean checkPermissions(int[] currentDomainArr, String requestedAction, int objID) {
        task_2.objectAccess current = task_2.accessList.get(objID);
        for (int i = 0; i < current.objects.size(); i++) {
            task_2.objectEntry entry = current.objects.get(i);
            if(entry.Domain == currentDomainArr[0]){
                if (requestedAction.equals("switch")) {
                    return entry.permission == task_2.Permission2.ALLOW;
                }
                else if (requestedAction.equals("read")) {
                    return entry.permission == task_2.Permission2.READ || entry.permission == task_2.Permission2.READ_WRITE;
                }
                else if (requestedAction.equals("write")) {
                    return entry.permission == task_2.Permission2.WRITE || entry.permission == task_2.Permission2.READ_WRITE;
                }
            }
        }
        return false;
    }

    public AIAgent_Task2(int numDomains, int numFiles, int threadNumber, FileObject fileObjArr[]) {
        super(numDomains, numFiles, threadNumber, fileObjArr);

    }
}
// End code changes by <Parker Inzerella>