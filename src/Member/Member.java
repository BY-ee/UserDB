package Member;
import java.util.Scanner;

public class Member {
	public static void main(String[] args) throws InterruptedException {
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
					dao.change();
					break;

				case "4":
					dao.print();
					break;

				case "5":
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
