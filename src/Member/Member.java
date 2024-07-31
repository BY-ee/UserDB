package member;
import java.io.*;

public class Member {
	public static void main(String[] args) throws InterruptedException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		DAO dao = new DAO();

		while(true) {
			dao.menu();
			String select = br.readLine();
			try {
				switch (select) {
						case "1":
							dao.signUp();
							break;

						case "2":
							dao.logIn();
							break;

						case "3":
							dao.logOut();
							break;

						case "4":
							dao.change();
							break;

						case "5":
							dao.print();
							break;

						case "6":
							dao.withdraw();
							break;

						case "0":
							dao.exit();
							return;

						default:
							dao.defaultMessage();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
