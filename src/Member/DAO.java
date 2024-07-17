package Member;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class DAO {
    List<DTO> members = new ArrayList<DTO> ();
    int logInData = -1;

    // 메뉴
    void menu() {
        initializeConsole();
        System.out.println("===============메뉴================");
        System.out.println("1.회원가입\t 2.로그인\t 3.로그아웃");
        System.out.println("4.정보 변경\t 5.출력\t\t 6.회원탈퇴");
        System.out.println("0.프로그램 종료");
        System.out.print("===================================\n\n\n\n\n> ");
    }


    // 회원가입
    void signUp() throws InterruptedException {
        DTO dto = new DTO();
        Scanner sc = new Scanner(System.in);
        System.out.print("\n회원가입을 시작합니다.");
        wait1Sec();
        System.out.println("\n".repeat(50));
        while (true) {
            dto.setID(signUpID());
            dto.setName(signUpName());
            dto.setBirthDate(signUpBirthDate());
            dto.setEmail(signUpEmail());
            dto.setAddress(signUpAddress());

            System.out.print("\n입력이 완료되었습니다.\n");
            System.out.print("다시 입력하시려면 y를 눌러주세요.\n> ");

            String temp = sc.nextLine();
            boolean cond = temp.equals("Y") || temp.equals("y");

            if (!cond) {
                sequenceMessage("\n회원가입이 완료되었습니다.");
                members.add(dto);
                wait1Sec();
                sequenceMessage("\n메인 화면으로 이동합니다.");
                break;
            }
            wait1Sec();
            initializeConsole();
        }
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
                if (inputID.equals(members.get(i).getID()) && inputBirthDate.equals(members.get(i).getBirthDate())) {
                    sequenceMessage("\n로그인에 성공하였습니다.");
                    logInData = i;
                    wait1Sec();
                    sequenceMessage("\n메인 화면으로 이동합니다.");
                    return;
                } else {
                    System.out.print("\n로그인에 실패하였습니다.");
                    wait1Sec();
                    System.out.print("\n\n아이디와 생년월일을 다시 입력하세요.\n> ");
                }
            }
        }
    }
    
    
    //로그아웃
    void logOut() throws InterruptedException {
        if(logInData == -1) {
            System.out.println("\n로그인이 되어 있지 않은 상태입니다.");
            wait1Sec();
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("\n로그아웃하시려면 y키를 눌러주세요.\n> ");
        String temp = sc.nextLine();
        if(temp.equals("y")) {
            logInData = -1;
            sequenceMessage("\n정상적으로 로그아웃되었습니다.\n");
            wait1Sec();
            sequenceMessage("메인 화면으로 이동합니다");
            wait1Sec();
            return;
        }
        sequenceMessage("\n작업을 취소하셨습니다.");
        Thread.sleep(1000);
        sequenceMessage("\n메인 화면으로 이동합니다.");
        wait1Sec();
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
        System.out.print("어떤 정보를 변경하시겠습니까?");
        wait1Sec();
        System.out.println("\n\n\n===================================");
        System.out.println("1.아이디   2.이름   3.이메일   4.주소");
        System.out.print("===================================\n\n\n\n> ");
        String temp = sc.nextLine();
        DTO dto = new DTO();
        switch (temp) {
            case "1":
                System.out.print("\n아이디 변경을 시작합니다.");
                wait1Sec();
                initializeConsole();
                System.out.print("\n현재 아이디를 입력해주세요.\n> ");
                while(true) {
                    dto.setID(sc.nextLine());
                    if(dto.getID().equals(members.get(logInData).getID())) {
                        break;
                    }
//                    waitingDot();
                    System.out.print("\n\n입력하신 아이디가 현재 아이디와 일치하지 않습니다.");
                    wait1Sec();
                    System.out.print("\n현재 아이디를 다시 입력해주세요.\n> ");
                }
//                waitingDot();
                System.out.print("\n아이디가 확인되었습니다.");
                Thread.sleep(1000);
                System.out.print("\n변경하실 아이디를 입력해주세요.\n> ");
                while(true) {
                    dto.setID(sc.nextLine());
                    System.out.print("\n아이디를 한번 더 입력해주세요.\n> ");
                    String nextIDCheck = sc.nextLine();
                    if(nextIDCheck.equals(dto.getID())) {
                        sequenceMessage("\n아이디가 성공적으로 변경되었습니다.");
                        members.get(logInData).setID(dto.getID());
                        wait1Sec();
                        sequenceMessage("\n메인 화면으로 이동합니다.");
                        break;
                    }
                    System.out.print("\n아이디가 일치하지 않습니다. 변경하실 아이디를 다시 입력해주세요.\n> ");
                }
        }
    }


    // 출력
    void print() throws InterruptedException {
        if (members.isEmpty()) {
            System.out.print("\n출력할 데이터가 없습니다.");
            wait1Sec();
            return;
        }
        Scanner sc = new Scanner(System.in);
        while (true) {
            DTO dto = new DTO();
            System.out.println();
            System.out.print("아이디와 생년월일을 입력하세요.\n> ");
            dto.setID(sc.nextLine());
            System.out.print("> ");
            dto.setBirthDate(sc.nextLine());

            for(int i=0; i<members.size(); i++) {
                if(dto.getID().equals(members.get(i).getID()) && dto.getBirthDate().equals(members.get(i).getBirthDate())) {
                    wait1Sec();
                    System.out.println("\n\n\n--------------------------------------");
                    System.out.println(" 아이디: " + members.get(i).getID());
                    System.out.println(" 이름: " + members.get(i).getName());
                    System.out.println(" 생년월일: " + members.get(i).getBirthDate());
                    System.out.println(" 이메일: " + members.get(i).getEmail());
                    System.out.println(" 주소: " + members.get(i).getAddress());
                    System.out.println("--------------------------------------");
                    wait1Sec();
//                    waitingDot();
                    System.out.print("\n메뉴로 돌아가시려면 아무 키를 입력해주세요.");
                    wait1Sec();
                    System.out.print("\n다시 입력하고 싶으시면 y키를 눌러주세요\n> ");
                    String temp = sc.nextLine();
                    if(temp.equals("y") || temp.equals("Y")) {
                        continue;
                    }
                    sequenceMessage("\n\n메인 화면으로 이동합니다.");
                    return;
                }
                System.out.println("\n해당하는 정보가 없습니다.");
                Thread.sleep(1000);
                System.out.print("다시 입력하고 싶으시면 y키를 눌러주세요.\n> ");
                String temp = sc.nextLine();
                if(!(temp.equals("y") || temp.equals("Y"))) {
                    sequenceMessage("메인 화면으로 이동합니다.");
                    return;
                }
            }
        }
    }

    
    // 회원탈퇴


    // 프로그램 종료
    void exit() throws InterruptedException {
        sequenceMessage("\n프로그램을 종료합니다.");
        initializeConsole();
    }


    // 잘못된 값 입력
    void defaultMessage() throws InterruptedException {
        sequenceMessage("\n잘못된 값이 입력되었습니다.");
        wait1Sec();
    }

    
    // 회원가입 유효성 검사
    private boolean validID(String inputID) {
        Pattern patternID = Pattern.compile("([A-Za-z0-9]+_?){4,}");
        Matcher matcherID = patternID.matcher(inputID);
        return matcherID.matches();
    }

    String signUpID() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n아이디를 입력하세요.");
        System.out.print("입력값은 알파벳과 숫자, _만 가능합니다.\n> ");
        String inputID = sc.nextLine();
        while(!validID(inputID)) {
            System.out.println("\n아이디를 다시 입력하세요.");
            System.out.print("입력값은 알파벳과 숫자, _만 가능합니다.\n> ");
            inputID = sc.nextLine();
        }
        return inputID;
    }

    private boolean validName(String inputName) {
        Pattern patternName = Pattern.compile("[A-Za-z가-힇]{2,}");
        Matcher matcherName = patternName.matcher(inputName);
        return matcherName.matches();
    }

    String signUpName() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n이름을 입력하세요.\n> ");
        String inputName = sc.nextLine();
        while(!validName(inputName)) {
            System.out.print("이름을 다시 입력하세요.\n> ");
            inputName = sc.nextLine();
        }
        return inputName;
    }

    private boolean validBirthDate(String inputBirthDate) {
        Pattern patternBirthDate = Pattern.compile("[0-9]{6}");
        Matcher matcherBirthDate = patternBirthDate.matcher(inputBirthDate);
        return matcherBirthDate.matches();
    }

    private boolean conditionBirthDate(String inputBirthDate) {
        String birth = inputBirthDate.substring(2, 4);
        boolean checkBirth = Integer.parseInt(birth) < 13 && !birth.equals("00");
        String date = inputBirthDate.substring(4, 6);
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

    String signUpBirthDate() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n생년월일을 입력하세요.\n> ");
        String inputBirthDate = sc.nextLine();
        while(!validBirthDate(inputBirthDate) || !conditionBirthDate(inputBirthDate)) {
            System.out.print("생년월일을 다시 입력하세요.\n> ");
            inputBirthDate = sc.nextLine();
        }
        return inputBirthDate;
    }

    private boolean validEmail(String inputEmail) {
        Pattern patternEmail = Pattern.compile("([A-Za-z0-9]+_?){4,}@\\w+\\.[a-z]+");
        Matcher matcherEmail = patternEmail.matcher(inputEmail);
        return matcherEmail.matches();
    }

    String signUpEmail() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n이메일을 입력하세요.\n> ");
        String inputEmail = sc.nextLine();
        while(!validEmail(inputEmail)) {
            System.out.print("이메일을 다시 입력하세요.\n> ");
            inputEmail = sc.nextLine();
        }
        return inputEmail;
    }

    private boolean validAddress(String inputAddress) {
        Pattern patternAddress = Pattern.compile("[가-힇a-zA-Z0-9]+([ |\\-]?[가-힇a-zA-Z0-9]+)+");
        Matcher matcherAddress = patternAddress.matcher(inputAddress);
        return matcherAddress.matches();
    }

    String signUpAddress() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n주소를 입력하세요.\n> ");
        String inputAddress = sc.nextLine();
        while(!validAddress(inputAddress)) {
            System.out.print("주소를 다시 입력하세요.\n> ");
            inputAddress = sc.nextLine();
        }
        return inputAddress;
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


    // 순차 메시지
    void sequenceMessage(String Message) throws InterruptedException {
        for(String s : Message.split("")) {
            System.out.print(s);
            Thread.sleep(100);
        }
        wait1Sec();
    }
}