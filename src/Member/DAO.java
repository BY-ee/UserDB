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
    int logInData = -1;

    // 메뉴
    void menu() {
        initializeConsole();
        System.out.println("================메뉴=================");
        System.out.println("1.회원가입\t 2.로그인\t 3.정보 변경");
        System.out.println("4.출력\t\t 5.회원탈퇴\t 0.프로그램 종료");
        System.out.print("=====================================\n\n\n\n\n> ");
    }


    // 회원가입
    void signUp() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n회원가입을 시작합니다.");
        wait1Sec();
        System.out.println("\n".repeat(50));
        while (true) {
            System.out.println("\n아이디를 입력하세요.");
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

            if (!cond) {
                System.out.print("\n회원가입이 완료되었습니다.");
                insert();
                wait1Sec();
                System.out.print("\n메인 화면으로 이동합니다.");
                Thread.sleep(1500);
                break;
            }
            wait1Sec();
            initializeConsole();
        }
    }


    // 데이터 삽입
    void insert() {
        members.add(Arrays.asList(dto.getID(), dto.getName(), dto.getBirthDate(), dto.getEmail(), dto.getAddress()));
    }


    // 로그인
    void logIn() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n아이디와 생년월일을 입력하세요.\n> ");
        while(true) {
            for(int i=0; i<members.size(); i++) {
                String inputID = sc.nextLine();
                if(inputID.equals("x")) return;
                System.out.print("> ");
                String inputBirthDate = sc.nextLine();
                if(inputBirthDate.equals("x")) return;
                if (inputID.equals(members.get(i).get(0)) && inputBirthDate.equals(members.get(i).get(2))) {
                    System.out.print("\n로그인에 성공하였습니다.");
                    logInData = i;
                    wait1Sec();
                    return;
                } else {
                    System.out.print("\n로그인에 실패하였습니다.");
                    wait1Sec();
                    System.out.print("\n아이디와 생년월일을 다시 입력하세요.\n> ");
                }
            }
        }
    }


    // 정보 변경
    void change() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        if(logInData == -1) {
            System.out.print("\n로그인한 후에 다시 시도해주세요.");
            wait1Sec();
            return;
        }
        initializeConsole();
        System.out.println("어떤 정보를 변경하시겠습니까?");
        wait1Sec();
        System.out.println("\n\n===================================");
        System.out.println("1.아이디   2.이름   3.이메일   4.주소");
        System.out.print("===================================\n\n\n\n> ");
        String temp = sc.nextLine();
        switch (temp) {
            case "1":
                System.out.print("아이디 변경을 시작합니다.");
                wait1Sec();
                initializeConsole();
                System.out.print("\n현재 아이디를 입력해주세요.\n> ");
                while(true) {
                    String currentID = sc.nextLine();
                    if(currentID.equals(members.get(logInData).get(0))) {
                        break;
                    }
//                    waitingDot();
                    System.out.print("\n\n입력하신 아이디가 현재 아이디와 일치하지 않습니다.\n");
                    wait1Sec();
                    System.out.print("다시 입력해주세요.\n> ");
                }
//                waitingDot();
                System.out.print("\n아이디가 확인되었습니다.");
                Thread.sleep(500);
                System.out.print("\n변경하실 아이디를 입력해주세요.\n> ");
                while(true) {
                    String nextID = sc.nextLine();
                    System.out.print("\n아이디를 한번 더 입력해주세요.\n> ");
                    String nextIDCheck = sc.nextLine();
                    if(nextID.equals(nextIDCheck)) {
                        System.out.print("\n아이디가 성공적으로 변경되었습니다.");
                        dto.setID(nextID);
                        wait1Sec();
                        break;
                    }
                    System.out.print("\n아이디가 일치하지 않습니다. 변경하실 아이디를 다시 입력해주세요.\n> ");
                }
        }
    }


    // 출력
    void print() throws InterruptedException {
        if (members.isEmpty()) {
            System.out.print("출력할 내용이 없습니다.");
            wait1Sec();
            return;
        }
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.print("아이디와 생년월일을 입력하세요.\n> ");
            String inputID = sc.nextLine();
            System.out.print("> ");
            String inputBirthDate = sc.nextLine();

            for(int i=0; i<members.size(); i++) {
                if(inputID.equals(members.get(i).get(0)) && inputBirthDate.equals(members.get(i).get(2))) {
                    wait1Sec();
                    System.out.println("\n\n\n--------------------------------------");
                    System.out.println(" 아이디: " + members.get(i).get(0));
                    System.out.println(" 이름: " + members.get(i).get(1));
                    System.out.println(" 생년월일: " + members.get(i).get(2));
                    System.out.println(" 이메일: " + members.get(i).get(3));
                    System.out.println(" 주소: " + members.get(i).get(4));
                    System.out.println("--------------------------------------");
                    wait1Sec();
//                    waitingDot();
                    System.out.println("\n\n메뉴로 돌아가시려면 아무 키를 입력해주세요.");
                    wait1Sec();
                    System.out.print("다시 입력하고 싶으시면 y키를 눌러주세요\n> ");
                    String temp = sc.nextLine();
                    if(temp.equals("y") || temp.equals("Y")) {
                        continue;
                    } else {
                        return;
                    }
                }
                System.out.println("\n해당하는 정보가 없습니다.");
                System.out.print("다시 입력하고 싶으시면 y키를 눌러주세요.\n> ");
                String temp = sc.nextLine();
                if(!(temp.equals("y") || temp.equals("Y"))) {
                    System.out.print("메뉴로 돌아갑니다.");
                    wait1Sec();
                    return;
                }
            }
        }
    }

    
    // 회원탈퇴


    // 프로그램 종료
    void exit() throws InterruptedException {
        String exit = "프로그램을 종료합니다.";
        for(String s : exit.split("")) {
            System.out.print(s);
            Thread.sleep(100);
        }
        wait1Sec();
        initializeConsole();
    }


    // 잘못된 값 입력
    void defaultMessage() throws InterruptedException {
        String  defaultMessage= "잘못된 값을 입력하셨습니다.";
        for(String s : defaultMessage.split("")) {
            System.out.print(s);
            Thread.sleep(100);
        }
        wait1Sec();
    }

    
    // 회원가입 유효성 검사
    private boolean checkID() {
        Scanner sc = new Scanner(System.in);
        dto.setID(sc.nextLine());
        Pattern patternID = Pattern.compile("([A-Za-z0-9]+_?){4,}");
        Matcher matcherID = patternID.matcher(dto.getID());
        return matcherID.matches();
    }

    void invalidID() {
        while (!checkID()) {
            System.out.print("\n형식에 맞게 아이디를 다시 입력하세요.\n> ");
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
            System.out.print("\n이름을 다시 입력하세요.\n> ");
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
            System.out.print("\n생년월일을 다시 입력하세요.\n> ");
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
            System.out.print("\n유효한 이메일을 다시 입력하세요.\n> ");
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
            System.out.print("\n유효한 주소를 다시 입력하세요.\n> ");
        }
    }


    // 대기
    void waitingDot() throws InterruptedException {
        Thread.sleep(1000);
        for(int j=0; j<3; j++) {
            System.out.print(" ");
            System.out.print(".");
            Thread.sleep(1000);
        }
    }


    // 1초 대기
    void wait1Sec() throws InterruptedException {
        Thread.sleep(1000);
    }


    // 콘솔창 초기화
    void initializeConsole() {
        System.out.print("\n".repeat(50));
    }
}