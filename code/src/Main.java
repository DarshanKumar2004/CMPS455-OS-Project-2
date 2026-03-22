/* Team Members: Abigail Boggs (C00513558), Chad Dauphiney (C00510239), Parker Inzerella (C00512851), Darshan Kumar (C00529580)
 * CMPS 455
 * Project 2 - Main
 */

// Begin code changes by Chad Dauphiney
public class Main{
    public static void main(String[] args) {
        if(args.length == 0) {
            return;
        }
        if(!args[0].equals("-S")) {
            return;
        }
        if(args.length < 2) {
            return;
        }
        int argNum;

        try {
            argNum = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            return;
        }

        switch (argNum) {
            case 1:
                System.out.println("Access Control Scheme: Access Matrix");
                task_1.run();
            case 2:
                System.out.println("Access Control Scheme: Access for Objects");


        }

    }
}
// End code changes by Chad Dauphiney