package member.refactoring;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        DAO dao = new DAO();

        while(true) {
            dao.menu();
            try {
                String select = br.readLine();
                switch(select) {
                    case "1": dao.signUp(); break;
                    case "2": dao.logIn(); break;
                    case "3": dao.logOut(); break;
                    case "4": dao.change(); break;
                    case "5": dao.print(); break;
                    case "0": dao.exit(); break;
                    default: dao.inputFaultValue();
                }
            } catch(IOException e) {
                e.printStackTrace();
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
