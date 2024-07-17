package Member;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class DAO {
    DTO dto = new DTO();
    List<List<Object>> members = new ArrayList<>();

    // 메뉴
    void menu() {
        System.out.println("~~~~~~~~~~~~~~~~메뉴~~~~~~~~~~~~~~~~");
        System.out.println("1.회원가입\t 2.로그인\t 3.정보 변경");
        System.out.println("4.출력\t\t 5.회원탈퇴\t 0.프로그램 종료");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }


    // 회원가입
    void signUp() throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n아이디를 입력하세요");
            System.out.print("입력값은 알파벳과 숫자, _만 가능합니다.\n> ");
            invalidID();
            System.out.print("\n이름을 입력하세요.\n> ");
            invalidName();
            System.out.print("\n생년월일을 입력하세요.\n> ");
            invalidBirthDate();
            System.out.print("\n이메일을 입력하세요.\n> ");
            invalidEmail();
            System.out.print("\n주소를 입력하세요.\n> ");
            invalidAddress();

            System.out.print("\n다시 입력하시려면 y를 눌러주세요.\n> ");

            String temp = sc.nextLine();
            boolean cond = temp.equals("Y") || temp.equals("y");

            if (cond) {
                System.out.println();
            } else {
                System.out.print("\n회원가입이 완료되었습니다.");
                insert();
                Thread.sleep(1500);
                break;
            }
        }
    }


    // 로그인
    void logIn() {
        Scanner sc = new Scanner(System.in);

    }


    // 정보 변경


    // 출력
    void print() throws InterruptedException {
        if (members.isEmpty()) {
            System.out.print("출력할 내용이 없습니다.");
            Thread.sleep(1000);
            return;
        }
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.print("아이디와 생년월일을 입력하세요.\n> ");
            String printBirthDate = sc.nextLine();
            System.out.print("> ");
            String printName = sc.nextLine();

            System.out.println(members.get(0).get(0));
            System.out.println(members.get(0).get(1));
            System.out.println(members.get(1));
        }
    }

    // 회원가입 입력 데이터의 유효성 검사
    private boolean checkID() {
        Scanner sc = new Scanner(System.in);
        dto.setID(sc.nextLine());
        Pattern patternID = Pattern.compile("([A-Za-z0-9]+_?){4,}");
        Matcher matcherID = patternID.matcher(dto.getID());
        return matcherID.matches();
    }

    void invalidID() {
        while (!checkID()) {
            System.out.print("\n형식에 맞게 아이디를 다시 입력하세요\n> ");
        }
    }

    private boolean checkName() {
        Scanner sc = new Scanner(System.in);
        dto.setName(sc.nextLine());
        Pattern patternName = Pattern.compile("[A-Za-z가-힇]{2,}");
        Matcher matcherName = patternName.matcher(dto.getName());
        return matcherName.matches();
    }

    void invalidName() {
        while (!checkName()) {
            System.out.print("\n이름을 다시 입력하세요\n> ");
        }
    }

    private boolean checkBirthDate() {
        Scanner sc = new Scanner(System.in);
        dto.setBirthDate(sc.nextLine());
        Pattern patternBirthDate = Pattern.compile("[0-9]{6}");
        Matcher matcherBirthDate = patternBirthDate.matcher(String.valueOf(dto.getBirthDate()));
        return (matcherBirthDate.matches());
    }

    private boolean conditionBirthDate() {
        String birth = dto.getBirthDate().substring(2, 4);
        boolean checkBirth = Integer.parseInt(birth) < 13 && !birth.equals("00");
        String date = dto.getBirthDate().substring(4, 6);
        int[] dateList = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        boolean checkDate = true;
        if (birth.equals("00")) {
            return false;
        } else {
            for (int i = 0; i < dateList.length; i++) {
                if (Integer.parseInt(birth) - 1 == i) checkDate = Integer.parseInt(date) <= dateList[i];
            }
        }
        return checkBirth && checkDate;
    }

    void invalidBirthDate() {
        while (!checkBirthDate() || !conditionBirthDate()) {
            System.out.print("\n생년월일을 다시 입력하세요\n> ");
        }
    }


    private boolean checkEmail() {
        Scanner sc = new Scanner(System.in);
        dto.setEmail(sc.nextLine());
        Pattern patternEmail = Pattern.compile("([A-Za-z0-9]+_?){4,}@\\w+\\.[a-z]+");
        Matcher matcherEmail = patternEmail.matcher(dto.getEmail());
        return matcherEmail.matches();
    }

    void invalidEmail() {
        while (!checkEmail()) {
            System.out.print("\n유효한 이메일을 다시 입력하세요\n> ");
        }
    }

    private boolean checkAddress() {
        Scanner sc = new Scanner(System.in);
        dto.setAddress(sc.nextLine());
        Pattern patternAddress = Pattern.compile("[가-힇a-zA-Z0-9]+([ |\\-]?[가-힇a-zA-Z0-9]+)+");
        Matcher matcherAddress = patternAddress.matcher(dto.getAddress());
        return matcherAddress.matches();
    }

    void invalidAddress() {
        while (!checkAddress()) {
            System.out.print("\n유효한 주소를 다시 입력하세요\n> ");
        }
    }


    // 데이터 삽입
    void insert() {
        members.add(Arrays.asList(dto.getID(), dto.getName(), dto.getBirthDate(), dto.getEmail(), dto.getAddress()));
    }


    // 프로그램 종료
    void exit() throws InterruptedException {
        String exit = "프로그램을 종료합니다.";
        for(String s : exit.split("")) {
            System.out.print(s);
            Thread.sleep(200);
        }
        Thread.sleep(1000);
        for(int i=0; i<5; i++) {
            System.out.println("\n\n\n\n\n\n");
        }
    }


    // 잘못된 값 입력
    void defaultMessage() throws InterruptedException {
        String defaultMessage = "잘못된 값을 입력하셨습니다.";
        for(String s : defaultMessage.split("")) {
            System.out.print(s);
            Thread.sleep(200);
        }
        Thread.sleep(500);
    }
}