import java.util.ArrayList;

public class task_2 {
    enum Permission { READ, WRITE, READ_WRITE, ALLOW, NOT_ALLOW }
    enum Type {FILE,DOMAIN}
    int N = (int) ((Math.random()*5)+3);
    int M = (int) ((Math.random()*5)+3);;
    ArrayList<objectAccess> accessList = new ArrayList<>(N+M);
    void initializeList(){
        for(int i = 0; i<N; i++){
            accessList.add(new objectAccess(Type.FILE));
        }
        for(int j= 0; j<M; j++){
            accessList.add(new objectAccess(Type.DOMAIN));
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