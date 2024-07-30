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
                    
                }
            } catch(IOException e) {
                e.printStackTrace();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
