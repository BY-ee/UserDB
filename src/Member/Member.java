package Member;
import java.io.IOException;
import java.util.Scanner;

public class Member {
	public static void main(String[] args) throws InterruptedException, IOException {
		Scanner sc = new Scanner(System.in);
		DAO dao = new DAO();

		while(true) {
			dao.menu();
			String select = sc.nextLine();

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
		}
	}
}
