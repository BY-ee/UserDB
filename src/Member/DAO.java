package Member;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class DAO {
    List<DTO> members = new ArrayList<DTO> ();
    private int logInData = -1;


    // 메뉴
    void menu() {
        initializeConsole();
        System.out.println("===============메뉴================");
        System.out.println("1.회원가입\t 2.로그인\t 3.로그아웃");
        System.out.println("4.정보 변경\t 5.출력\t\t 6.회원탈퇴");
        System.out.println("0.프로그램 종료");
        System.out.print("===================================\n\n\n\n");
        System.out.print("번호를 입력해주세요.\n> ");
    }


    // 회원가입
    void signUp() throws InterruptedException {
        if(logInData != -1) {
            logInData = -1;
            System.out.print("\n자동으로 로그아웃되었습니다.");
            wait1Sec();
            wait05Sec();
            initializeConsole();
        }
        DTO dto = new DTO();
        Scanner sc = new Scanner(System.in);
        System.out.print("\n회원가입을 시작합니다.");
        wait1Sec();
        System.out.println("\n".repeat(50));
        while (true) {
            dto.setID(signUpID());
            dto.setPassword(signUpPassword());
            dto.setName(signUpName());
            dto.setBirthDate(signUpBirthDate());
            dto.setEmail(signUpEmail());
            dto.setAddress(signUpAddress());

            System.out.print("\n입력이 완료되었습니다.");
            wait05Sec();
            System.out.print("\n다시 입력하시려면 y를 입력해주세요.\n> ");

            String temp = sc.nextLine();
            boolean cond = temp.equals("Y") || temp.equals("y");

            if (!cond) {
                sequenceMessage("\n회원가입이 완료되었습니다.");
                wait05Sec();
                members.add(dto);
                sequenceMessage("\n메인 화면으로 이동합니다.");
                wait1Sec();
                break;
            }
            wait1Sec();
            initializeConsole();
        }
    }


    // 로그인
    void logIn() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        if(logInData != -1) {
            System.out.printf("\n이미 \"%s\" 아이디로 로그인되어 있습니다.", members.get(logInData).getID());
            wait1Sec();
            wait05Sec();
            return;
        }
        System.out.print("\n아이디와 비밀번호를 입력하세요.\n> ");
        while(true) {
            for(int i=0; i<members.size(); i++) {
                String inputID = sc.nextLine();
                if(inputID.equals("x") || inputID.equals("X")) return;
                System.out.print("> ");
                String inputPassword = sc.nextLine();
                if(inputPassword.equals("x")) return;
                if (inputID.equals(members.get(i).getID()) && inputPassword.equals(members.get(i).getPassword())) {
                    sequenceMessage("\n로그인에 성공하였습니다.");
                    logInData = i;
                    wait05Sec();
                    sequenceMessage("\n메인 화면으로 이동합니다.");
                    wait1Sec();
                    return;
                } else {
                    System.out.print("\n로그인에 실패하였습니다.");
                    wait05Sec();
                    System.out.print("\n\n아이디와 비밀번호를 다시 입력해주세요.\n> ");
                }
            }
        }
    }
    
    
    //로그아웃
    void logOut() throws InterruptedException {
        if(logInData == -1) {
            System.out.print("\n로그인되어 있지 않은 상태입니다.");
            wait1Sec();
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("\n로그아웃하시려면 y키를 입력해주세요.\n> ");
        String temp = sc.nextLine();
        if(temp.equals("Y") || temp.equals("y")) {
            logInData = -1;
            sequenceMessage("\n정상적으로 로그아웃되었습니다.");
            wait05Sec();
            sequenceMessage("\n메인 화면으로 이동합니다");
            wait1Sec();
            return;
        }
        sequenceMessage("\n로그아웃을 취소하였습니다.");
        wait05Sec();
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
        System.out.print("\n\n\n===================================");
        System.out.print("\n1.아이디   2.이름   3.이메일   4.주소");
        System.out.print("\n===================================\n\n\n\n> ");
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
                    System.out.print("\n\n입력하신 아이디가 현재 아이디와 일치하지 않습니다.");
                    wait1Sec();
                    System.out.print("\n\n현재 아이디를 다시 입력해주세요.\n> ");
                }
                System.out.print("\n아이디가 확인되었습니다.");
                wait1Sec();
                System.out.print("\n변경하실 아이디를 입력해주세요.\n> ");
                while(true) {
                    dto.setID(sc.nextLine());
                    if(!dto.getID().equals(members.get(logInData).getID())) break;
                    System.out.print("\n현재 아이디와 동일합니다. 새로운 아이디를 입력해주세요.\n> ");
                }
                while(true) {
                    System.out.print("\n변경하실 아이디를 한번 더 입력해주세요.\n> ");
                    String nextIDCheck = sc.nextLine();
                    if(nextIDCheck.equals(dto.getID())) {
                        sequenceMessage("\n아이디가 성공적으로 변경되었습니다.");
                        members.get(logInData).setID(dto.getID());
                        wait05Sec();
                        sequenceMessage("\n메인 화면으로 이동합니다.");
                        wait1Sec();
                        break;
                    }
                    System.out.print("\n입력하신 아이디가 일치하지 않습니다.");
                    System.out.print("\n변경하실 아이디를 다시 입력해주세요.\n> ");
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
                    wait05Sec();
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
                    wait05Sec();
                    System.out.print("\n다시 입력하고 싶으시면 y키를 입력해주세요\n> ");
                    String temp = sc.nextLine();
                    if(temp.equals("Y") || temp.equals("y")) {
                        initializeConsole();
                        continue;
                    }
                    sequenceMessage("\n메인 화면으로 이동합니다.");
                    wait1Sec();
                    return;
                }
                System.out.println("\n해당하는 정보가 없습니다.");
                wait05Sec();
                System.out.print("다시 입력하고 싶으시면 y키를 입력해주세요.\n> ");
                String temp = sc.nextLine();
                if(!(temp.equals("Y") || temp.equals("y"))) {
                    sequenceMessage("\n메인 화면으로 이동합니다.");
                    wait1Sec();
                    return;
                }
            }
        }
    }

    
    // 회원탈퇴
    void withdraw() throws InterruptedException {
        if(logInData == -1) {
            System.out.print("\n로그인되어 있지 않은 상태입니다.");
            wait1Sec();
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("\n정말 탈퇴하시겠습니까?");
        wait1Sec();
        System.out.print("\n탈퇴하시려면 y키를 입력해주세요.\n> ");
        String temp = sc.nextLine();
        if(!(temp.equals("Y") || temp.equals("y"))) {
            sequenceMessage("\n메인 화면으로 이동합니다.");
            wait1Sec();
            return;
        }
        System.out.print("\n회원탈퇴를 진행합니다.");
        wait1Sec();
        initializeConsole();

        DTO dto = new DTO();
        System.out.print("현재 아이디를 입력해주세요.\n> ");
        while(true) {
            String ID = sc.nextLine();
            if(ID.equals(members.get(logInData).getID())) {
                break;
            }
            System.out.print("\n로그인하신 아이디와 일치하지 않습니다. 다시 입력해주세요.\n> ");
        }
        System.out.print("\n비밀번호를 입력해주세요.\n> ");
        while(true) {
            dto.setPassword(sc.nextLine());
            initializeConsole();
            if(dto.getPassword().equals(members.get(logInData).getPassword())) {
                System.out.print("\n비밀번호를 한번 더 입력해주세요.\n> ");
                String password = sc.nextLine();
                initializeConsole();
                if(password.equals(dto.getPassword())) break;
            }
            System.out.print("\n비밀번호가 일치하지 않습니다. 다시 입력해주세요.\n> ");
        }
        System.out.print("\n회원탈퇴를 원하시면 \"회원탈퇴\"를 입력해주세요.\n> ");
        for(int i=0; i<2; i++) {
            String withdraw = sc.nextLine();
            if (withdraw.equals("회원탈퇴")) {
                members.remove(logInData);
                System.out.print("\n회원탈퇴가 완료되었습니다.");
                logInData = -1;
                wait1Sec();
            } else if (withdraw.equals("X") || withdraw.equals("x")) {
                sequenceMessage("\n메인 화면으로 이동합니다.");
                wait1Sec();
            } else if (i == 1) {
                System.out.print("\n잘못된 값이 입력되었습니다.");
                wait05Sec();
                break;
            }
            System.out.print("\n잘못된 값이 입력되었습니다.");
            wait05Sec();
            System.out.print("\n\n회원탈퇴를 원하시면 \"회원탈퇴\"를 입력해주세요.\n> ");
        }
        sequenceMessage("\n메인 화면으로 이동합니다.");
        wait1Sec();
    }



    // 프로그램 종료
    void exit() throws InterruptedException {
        sequenceMessage("\n프로그램을 종료합니다.");
        wait1Sec();
        initializeConsole();
    }


    // 잘못된 값 입력
    void defaultMessage() throws InterruptedException {
        sequenceMessage("\n잘못된 값이 입력되었습니다.");
        wait1Sec();
    }

    
    // 회원가입 유효성 검사
    private boolean validID(String inputID) {
        Pattern patternID = Pattern.compile("[A-Za-z0-9_]{4,}");
        Matcher matcherID = patternID.matcher(inputID);
        return matcherID.matches();
    }

    String signUpID() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n4자 이상의 아이디를 입력하세요.");
        wait05Sec();
        System.out.print("\n알파벳과 숫자, _만 입력 가능합니다.\n> ");
        String inputID = sc.nextLine();
        while(!validID(inputID)) {
            System.out.print("\n4자 이상의 아이디를 다시 입력하세요.");
            wait05Sec();
            System.out.print("\n알파벳과 숫자, _만 입력 가능합니다.\n> ");
            inputID = sc.nextLine();
        }
        System.out.print("\n아이디가 확인되었습니다.");
        wait1Sec();
        return inputID;
    }

    private boolean validPassword(String inputPassword) {
        Pattern patternPassword = Pattern.compile("(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*()])(?=.*\\d)[\\w!@#$%^&*()]{8,}");
        Matcher matcherPassword = patternPassword.matcher(inputPassword);
        return matcherPassword.matches();
    }

    String signUpPassword() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n\n비밀번호를 입력하세요.");
        wait05Sec();
        System.out.print("\n비밀번호는 8자 이상이어야 하며, 대소문자와 특수문자, 숫자를 각각 하나 이상 포함해야 합니다.\n> ");
        String inputPassword = sc.nextLine();
        while(!validPassword(inputPassword)) {
            System.out.print("\n비밀번호를 다시 입력하세요.");
            wait05Sec();
            System.out.print("\n비밀번호는 8자 이상이어야 하며, 대문자와 특수문자, 숫자를 각각 하나 이상 포함해야 합니다.\n> ");
            inputPassword = sc.nextLine();
        }
        System.out.print("\n비밀번호가 확인되었습니다.");
        wait1Sec();
        initializeConsole();
        return inputPassword;
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

    // 0.5초 대기
    void wait05Sec() throws InterruptedException {
        Thread.sleep(500);
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
    }


    // x키 입력으로 메인 화면 이동
    boolean moveMain(String input) throws InterruptedException {
        System.out.print("\n메인 화면으로 이동하시려면 x 키를 입력해주세요");
        if(input.equals("X") || input.equals("x")) {
            sequenceMessage("메인 화면으로 이동합니다.");
            wait1Sec();
            return true;
        }
        return false;
    }
}