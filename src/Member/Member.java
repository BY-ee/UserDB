package Member;
import java.util.Scanner;

public class Member {
	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		DAO dao = new DAO();

		while(true) {
			for(int i=0; i<5; i++) {
				System.out.println("\n\n\n\n\n\n");
			}
			dao.menu();
			System.out.print("> ");
			String select = sc.nextLine();

			switch (select) {
				case "1":
					dao.signUp();
					break;

				case "2":
					break;

				case "3":
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
