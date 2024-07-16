package Member;
import java.util.Scanner;

public class Member {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		MemberInfo mi = new MemberInfo();
		MemberDB mdb = new MemberDB();
		
		while(true) {
			System.out.println("이름을 입력하세요");
			mi.setName(sc.nextLine());
			mdb.invalidName();
			
			System.out.println("\n나이를 입력하세요");
			mi.setAge(sc.nextLine());
			mdb.invalidAge();
			
			System.out.println("\n이메일을 입력하세요");
			mi.setEmail(sc.nextLine());
			mdb.invalidEmail();
			
			System.out.println("\n주소를 입력하세요");
			mi.setAddress(sc.nextLine());
			mdb.invalidAddress();

			mdb.insert();

			System.out.println("\n다시 입력하시려면 y를 눌러주세요");
			
			String temp = sc.nextLine();
			boolean cond = temp.equals("Y") || temp.equals("y");

			if(cond) {
				System.out.println();
			} else {
				System.out.println("\n프로그램을 종료합니다.");
				System.out.println(mdb.members);
				break;
			}
		}
	}
}
