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
                break;
            case 2:
                System.out.println("Access Control Scheme: Access for Objects");
                task_2.runAgents();
                break;
            case 3:
                System.out.println("Access Control Scheme: Access for Capability List For Domains");
                task_3.run();
                break;
            default:
                System.out.println("Input Error. Exiting");
                break;
        }

    }
}
// End code changes by Chad Dauphiney