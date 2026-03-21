import java.util.ArrayList;

public class task_2 {
    enum Permission {READ, WRITE, READ_WRITE, ALLOW}
    enum Type {FILE,DOMAIN}
    //N = domains and M = files
    int N = (int) ((Math.random()*5)+3);
    int M = (int) ((Math.random()*5)+3);;
    ArrayList<objectAccess> accessList = new ArrayList<>(N+M);
    void initializeList(){
        for(int i = 0; i<M; i++){
            accessList.add(new objectAccess(Type.FILE));
        }
        for(int j= 0; j<N; j++){
            accessList.add(new objectAccess(Type.DOMAIN));
        }
    }
    Permission pickPerm(){
        int roll = (int)(Math.random() * 3);
        switch(roll) {
            case 0:
                return Permission.READ_WRITE;
            case 1:
                return Permission.READ;
        }
        return Permission.WRITE;
    }
    void populateEntries(){
        for(int i = 0; i < accessList.size(); i++) {
            objectAccess current = accessList.get(i);
            for (int j = 0; j < N; j++) {
                if (current.type == Type.FILE) {
                    if (Math.random() < 0.5) {
                        current.objects.add(new objectEntry(j, pickPerm()));
                    }
                } else if (current.type == Type.DOMAIN) {
                    if (Math.random() < 0.5) {
                        current.objects.add(new objectEntry(j, Permission.ALLOW));
                    }
                }
            }
        }
    }
    class objectEntry{
        int Domain;
        Permission permission;
        objectEntry(int Domain, Permission permission){
            this.Domain = Domain;
            this.permission = permission;
        }
    }
    class objectAccess{
        Type type;
        ArrayList<objectEntry> objects;
        objectAccess(Type type, ArrayList<objectEntry> objects){
            this.type = type;
            this.objects = objects;
        }
        objectAccess(Type type){
            this.type = type;
            this.objects = new ArrayList<>();
        }
    }
}
