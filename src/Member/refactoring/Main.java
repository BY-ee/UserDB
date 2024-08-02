package member.refactoring;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserService ur = new UserService();

        while(true) {
            ur.menu();
            try {
                String select = br.readLine();
                switch(select) {
                    case "1": ur.signUp(); break;
                    case "2": ur.logIn(); break;
                    case "3": ur.logOut(); break;
                    case "4": ur.change(); break;
                    case "5": ur.print(); break;
                    case "6": ur.withdrawal(); break;
                    case "0": ur.exit(); break;
                    default: ur.inputFaultValue();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
