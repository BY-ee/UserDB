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
            sequenceMessage("\n자동으로 로그아웃되었습니다.");
            wait1Sec();
            wait05Sec();
            initializeConsole();
        }
        DTO dto = new DTO();
        Scanner sc = new Scanner(System.in);
        sequenceMessage("\n회원가입을 시작합니다.");
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
            System.out.print("\n\n정보를 다시 입력하시려면 y 키를 입력해주세요.");
            wait05Sec();
            System.out.print("\n회원가입을 완료하시려면 x 키를 입력해주세요.\n> ");
            
            while(true) {
                String temp = sc.nextLine();
                if (temp.equals("X") || temp.equals("x")) {
                    members.add(dto);
                    sequenceMessage("\n회원가입이 완료되었습니다.");
                    wait05Sec();
                    sequenceMessage("\n\n메인 화면으로 이동합니다.");
                    wait1Sec();
                    return;
                } else if (temp.equals("Y") || (temp.equals("y"))) {
                    sequenceMessage("\n회원가입을 다시 시작합니다.");
                    wait1Sec();
                    initializeConsole();
                    break;
                }
                System.out.print("\n잘못된 값을 입력하셨습니다.");
                wait05Sec();
                System.out.print("\n\n정보를 다시 입력하시려면 y 키를 입력해주세요.");
                wait05Sec();
                System.out.print("\n\n회원가입을 완료하시려면 x 키를 입력해주세요.\n> ");
            }
        }
    }


    // 로그인
    void logIn() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        if(logInData != -1) {
            for(String s : ("이미 '" + members.get(logInData).getID() + "' 아이디로 로그인되어 있습니다.").split("")) {
                System.out.print(s);
                Thread.sleep(45);
            }
            wait1Sec();
            return;
        }
        System.out.print("\n아이디와 비밀번호를 입력하세요.");
        wait05Sec();
        System.out.print("\n\n메인 화면으로 돌아가시려면 x 키를 입력해주세요.\n> ");
        if(members.isEmpty()) {
            while(true) {
                String inputID = sc.nextLine();
                if(inputID.equals("X") || inputID.equals("x")) {
                    sequenceMessage("\n메인 화면으로 이동합니다.");
                    wait1Sec();
                    return;
                }
                System.out.print("> ");
                String inputPassword = sc.nextLine();
                if(inputPassword.equals("x")) {
                    sequenceMessage("\n메인 화면으로 이동합니다.");
                    wait1Sec();
                    return;
                }
                System.out.print("\n로그인에 실패하였습니다.");
                wait05Sec();
                System.out.print("\n\n아이디와 비밀번호를 다시 입력해주세요.");
                wait05Sec();
                System.out.print("\n\n메인 화면으로 돌아가시려면 x 키를 입력해주세요.\n> ");
            }
        }
        while(true) {
            for(int i=0; i<members.size(); i++) {
                String inputID = sc.nextLine();
                if(inputID.equals("X") || inputID.equals("x")) {
                    sequenceMessage("\n메인 화면으로 이동합니다.");
                    wait1Sec();
                    return;
                }
                System.out.print("> ");
                String inputPassword = sc.nextLine();
                if(inputPassword.equals("x")) {
                    sequenceMessage("\n메인 화면으로 이동합니다.");
                    wait1Sec();
                    return;
                }
                if (inputID.equals(members.get(i).getID()) && inputPassword.equals(members.get(i).getPassword())) {
                    sequenceMessage("\n로그인에 성공하였습니다.");
                    logInData = i;
                    wait05Sec();
                    sequenceMessage("\n\n메인 화면으로 이동합니다.");
                    wait1Sec();
                    return;
                } else {
                    System.out.print("\n로그인에 실패하였습니다.");
                    wait05Sec();
                    System.out.print("\n\n아이디와 비밀번호를 다시 입력해주세요.\n> ");
                    wait05Sec();
                    System.out.print("\n\n메인 화면으로 돌아가시려면 x 키를 입력해주세요.\n> ");
                }
            }
        }
    }
    
    
    //로그아웃
    void logOut() throws InterruptedException {
        if(logInData == -1) {
            sequenceMessage("\n로그인되어 있지 않은 상태입니다.");
            wait1Sec();
            return;
        }
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("\n로그아웃하시려면 y 키를 입력해주세요.");
            wait05Sec();
            System.out.print("\n\n메인 화면으로 돌아가시려면 x 키를 입력해주세요.\n> ");
            String temp = sc.nextLine();
            if(temp.equals("Y") || temp.equals("y")) {
                logInData = -1;
                sequenceMessage("\n정상적으로 로그아웃되었습니다.");
                wait05Sec();
                sequenceMessage("\n\n메인 화면으로 이동합니다.");
                wait1Sec();
                return;
            } else if(temp.equals("X") || temp.equals("x")) {
                sequenceMessage("\n로그아웃을 취소하였습니다.");
                wait05Sec();
                sequenceMessage("\n\n메인 화면으로 이동합니다.");
                wait1Sec();
                return;
            }
            System.out.print("\n잘못된 값을 입력하셨습니다.\n");
        }
    }


    // 정보 변경
    void change() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        if(logInData == -1) {
            sequenceMessage("\n로그인 후에 다시 시도해주세요.");
            wait1Sec();
            return;
        }
        initializeConsole();
        System.out.print("어떤 정보를 변경하시겠습니까?");
        wait05Sec();
        System.out.print("\n\n\n=============================================");
        System.out.print("\n1.아이디   2.비밀번호   3.이름   4.이메일   5.주소");
        System.out.print("\n=============================================\n\n\n\n> ");
        String temp = sc.nextLine();
        DTO dto = new DTO();
        switch (temp) {
            case "1":
                sequenceMessage("\n아이디 변경을 시작합니다.");
                wait05Sec();
                initializeConsole();
                System.out.print("\n현재 아이디를 입력해주세요.\n> ");
                while(true) {
                    dto.setID(sc.nextLine());
                    if(dto.getID().equals(members.get(logInData).getID())) {
                        break;
                    }
                    System.out.print("\n\n입력하신 아이디가 현재 아이디와 일치하지 않습니다.");
                    wait05Sec();
                    System.out.print("\n\n현재 아이디를 다시 입력해주세요.\n> ");
                }
                System.out.print("\n아이디가 확인되었습니다.");
                wait05Sec();
                System.out.print("\n변경하실 아이디를 입력해주세요.\n> ");
                while(true) {
                    while(true) {
                        dto.setID(sc.nextLine());
                        if(!validID(dto.getID())) {
                            System.out.print("\n4글자 이상의 알파벳과 숫자, _로 이루어진 아이디를 입력해주세요.\n> ");
                            wait1Sec();
                            continue;
                        }
                        if(!dto.getID().equals(members.get(logInData).getID())) break;
                        System.out.print("\n현재 아이디와 동일합니다. 새로운 아이디를 입력해주세요.\n> ");
                    }
                    System.out.print("\n변경하실 아이디를 한번 더 입력해주세요.\n> ");
                    String nextIDCheck = sc.nextLine();
                    if(nextIDCheck.equals(dto.getID())) {
                        sequenceMessage("\n아이디가 성공적으로 변경되었습니다.");
                        members.get(logInData).setID(dto.getID());
                        wait05Sec();
                        sequenceMessage("\n메인 화면으로 이동합니다.");
                        wait1Sec();
                        return;
                    }
                    System.out.print("\n입력하신 아이디가 일치하지 않습니다.");
                    wait05Sec();
                    System.out.print("\n\n변경하실 아이디를 다시 입력해주세요.\n> ");
                }
            case "2":
                sequenceMessage("\n비밀번호 변경을 시작합니다.");
                wait05Sec();
                initializeConsole();
                System.out.print("\n현재 비밀번호를 입력해주세요.\n> ");
                while(true) {
                    dto.setPassword(sc.nextLine());
                    if(dto.getPassword().equals(members.get(logInData).getPassword())) {
                        break;
                    }
                    System.out.print("\n입력하신 비밀번호가 현재 비밀번호와 일치하지 않습니다.");
                    wait05Sec();
                    System.out.print("\n\n현재 비밀번호를 다시 입력해주세요.\n> ");
                }
                System.out.print("\n비밀번호가 확인되었습니다.");
                wait05Sec();
                System.out.print("\n\n변경하실 비밀번호를 입력해주세요.\n> ");
                while(true) {
                    while(true) {
                        dto.setPassword(sc.nextLine());
                        if(!validPassword(dto.getPassword())) {
                            System.out.print("\n비밀번호의 형식에 맞게 입력해주세요.");
                            wait05Sec();
                            System.out.print("\n\n비밀번호는 8자 이상이어야 하며, 대소문자와 특수문자, 숫자를 각각 하나 이상 포함해야 합니다.\n> ");
                            continue;
                        }
                        if(!dto.getPassword().equals(members.get(logInData).getPassword())) break;
                        System.out.print("\n현재 비밀번호와 동일합니다. 새로운 비밀번호를 입력해주세요.\n> ");
                    }
                    System.out.print("\n변경하실 비밀번호를 한번 더 입력해주세요.\n> ");
                    String nextPasswordCheck = sc.nextLine();
                    if(nextPasswordCheck.equals(dto.getPassword())) {
                        sequenceMessage("\n비밀번호가 성공적으로 변경되었습니다.");
                        members.get(logInData).setPassword(dto.getPassword());
                        wait05Sec();
                        sequenceMessage("\n메인 화면으로 이동합니다.");
                        wait1Sec();
                        return;
                    }
                    System.out.print("\n입력하신 비밀번호가 일치하지 않습니다.");
                    wait05Sec();
                    System.out.print("\n\n변경하실 비밀번호를 다시 입력해주세요.\n> ");
                }
            case "3":
                sequenceMessage("\n이름 변경을 시작합니다.");
                wait05Sec();
                sequenceMessage("\n\n이름은 개명하신 경우에만 최초 1회 변경할 수 있습니다.");
                wait1Sec();
                initializeConsole();
                System.out.print("\n개명하신 이름을 입력해주세요.");
                wait05Sec();
                System.out.print("\n\n메인 화면으로 돌아가시려면 x 키를 입력해주세요.\n> ");
                while(true) {
                    while(true) {
                        dto.setName(sc.nextLine());
                        if(dto.getName().equals("X") || dto.getName().equals("x")) {
                            sequenceMessage("\n메인 화면으로 이동합니다.");
                            wait1Sec();
                            return;
                        }
                        if(!validName(dto.getName())) {
                            System.out.print("\n이름을 정확히 다시 입력해주세요.\n> ");
                            wait1Sec();
                            continue;
                        }
                        if(!dto.getName().equals(members.get(logInData).getName())) break;
                        System.out.print("\n현재 이름과 동일합니다. 개명하신 이름을 입력해주세요.\n> ");
                    }
                    System.out.printf("\n입력하신 이름은 \'%s\'입니다.", dto.getName());
                    wait05Sec();
                    System.out.print("\n\n이름이 맞다면 y를 눌러주세요.");
                    String nextNameCheck = sc.nextLine();
                    if(nextNameCheck.equals("Y") || nextNameCheck.equals("y")) {
                        sequenceMessage("\n이름이 성공적으로 변경되었습니다.");
                        members.get(logInData).setName(dto.getName());
                        wait05Sec();
                        sequenceMessage("\n메인 화면으로 이동합니다.");
                        wait1Sec();
                        return;
                    }
                    System.out.print("\n개명하신 이름을 다시 입력해주세요.");
                    wait05Sec();
                    System.out.print("\n\n메인 화면으로 돌아가시려면 x 키를 입력해주세요.\n> ");
                }
            case "4":
                sequenceMessage("\n이메일 변경을 시작합니다.");
                wait1Sec();
                initializeConsole();
                System.out.print("\n현재 이메일을 입력해주세요.");
                wait05Sec();
                System.out.print("\n\n메인 화면으로 돌아가시려면 x 키를 입력해주세요.\n> ");
                while(true) {
                    dto.setEmail(sc.nextLine());
                    if(dto.getEmail().equals("X") || dto.getEmail().equals("x")) {
                        sequenceMessage("\n메인 화면으로 이동합니다.");
                        wait1Sec();
                        return;
                    }
                    if(!validEmail(dto.getEmail())) {
                        System.out.print("\n이메일을 형식에 맞게 다시 입력해주세요.\n> ");
                        wait1Sec();
                        continue;
                    }
                    if(!dto.getEmail().equals(members.get(logInData).getEmail())) break;
                    System.out.print("\n현재 이메일과 동일합니다. 이메일을 다시 입력해주세요.\n> ");
                }
                System.out.print("\n변경하실 이메일을 입력해주세요.\n> ");
                while(true) {
                    while(true) {
                        dto.setEmail(sc.nextLine());
                        if(dto.getEmail().equals("X") || dto.getEmail().equals("x")) {
                            sequenceMessage("\n메인 화면으로 이동합니다.");
                            wait1Sec();
                            return;
                        }
                        if(!validEmail(dto.getEmail())) {
                            System.out.print("\n이메일을 형식에 맞게 다시 입력해주세요.\n> ");
                            continue;
                        }
                        break;
                    }
                    System.out.printf("\n입력하신 이메일은 \'%s\'입니다.", dto.getEmail());
                    wait05Sec();
                    System.out.print("\n\n입력하신 이메일이 맞다면 y를 입력해주세요.\n> ");
                    String nextEmailCheck = sc.nextLine();
                    if(nextEmailCheck.equals("Y") || nextEmailCheck.equals("y")) {
                        sequenceMessage("\n이메일이 성공적으로 변경되었습니다.");
                        members.get(logInData).setEmail(dto.getEmail());
                        wait05Sec();
                        sequenceMessage("\n메인 화면으로 이동합니다.");
                        wait1Sec();
                        return;
                    }
                    System.out.print("\n변경하실 이메일을 다시 입력해주세요.");
                    wait05Sec();
                    System.out.print("\n\n메인 화면으로 돌아가시려면 x 키를 입력해주세요.\n> ");
                }
            case "5":
                sequenceMessage("\n주소 변경을 시작합니다.");
                wait1Sec();
                initializeConsole();
                System.out.print("\n변경하실 주소를 입력해주세요.");
                wait05Sec();
                System.out.print("\n\n메인 화면으로 돌아가시려면 x 키를 입력해주세요.\n> ");
                while(true) {
                    while(true) {
                        dto.setAddress(sc.nextLine());
                        if(dto.getAddress().equals("X") || dto.getAddress().equals("x")) {
                            System.out.print("\n메인 화면으로 이동합니다.");
                            wait1Sec();
                            return;
                        }
                        if(!validAddress(dto.getAddress())) {
                            System.out.print("\n정확한 주소를 다시 입력해주세요.\n> ");
                            wait1Sec();
                            continue;
                        }
                        if(!dto.getAddress().equals(members.get(logInData).getAddress())) break;
                        System.out.print("\n현재 주소와 동일합니다. 주소를 다시 입력해주세요.\n> ");
                    }
                    System.out.printf("\n입력하신 주소는 \'%s\'입니다.", dto.getAddress());
                    wait05Sec();
                    System.out.print("\n\n주소가 맞다면 y를 입력해주세요.");
                    String nextAddressCheck = sc.nextLine();
                    if(nextAddressCheck.equals("Y") || nextAddressCheck.equals("y")) {
                        sequenceMessage("\n주소가 성공적으로 변경되었습니다.");
                        members.get(logInData).setAddress(dto.getAddress());
                        wait05Sec();
                        sequenceMessage("\n메인 화면으로 이동합니다.");
                        wait1Sec();
                        return;
                    }
                    System.out.print("\n변경하실 주소를 다시 입력해주세요.");
                    wait05Sec();
                    System.out.print("\n\n메인 화면으로 돌아가시려면 x 키를 입력해주세요.\n> ");
                }
            default:
                sequenceMessage("\n잘못된 값이 입력되었습니다.");
                wait05Sec();
                sequenceMessage("\n\n메인 화면으로 이동합니다.");
                wait1Sec();
        }
    }


    // 출력
    void print() throws InterruptedException {
        if (members.isEmpty()) {
            sequenceMessage("\n출력할 데이터가 없습니다.");
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

            boolean inputCheckFlag = false;
            for(int i=0; i<members.size(); i++) {
                if (dto.getID().equals(members.get(i).getID()) && dto.getBirthDate().equals(members.get(i).getBirthDate())) {
                    inputCheckFlag = true;
                    wait05Sec();
                    System.out.println("\n\n\n--------------------------------------");
                    System.out.println(" 아이디: " + members.get(i).getID());
                    System.out.println(" 이름: " + members.get(i).getName());
                    System.out.println(" 생년월일: " + members.get(i).getBirthDate());
                    System.out.println(" 이메일: " + members.get(i).getEmail());
                    System.out.println(" 주소: " + members.get(i).getAddress());
                    System.out.println("--------------------------------------");
                    wait05Sec();
                }
            }
            if(!inputCheckFlag) {
                System.out.print("\n해당하는 정보가 없습니다.");
                wait05Sec();
            }
            while(true) {
                System.out.print("\n\n다시 입력하시려면 y 키를 입력해주세요");
                wait05Sec();
                System.out.print("\n메뉴로 돌아가시려면 x 키를 입력해주세요.\n> ");
                String temp = sc.nextLine();
                if(temp.equals("Y") || temp.equals("y")) {
                    initializeConsole();
                    break;
                } else if(temp.equals("X") || temp.equals("x")) {
                    sequenceMessage("\n메인 화면으로 이동합니다.");
                    wait1Sec();
                    return;
                }
                sequenceMessage("\n잘못된 값을 입력하셨습니다.");
                wait05Sec();
            }
        }
    }

    
    // 회원탈퇴
    void withdraw() throws InterruptedException {
        if(logInData == -1) {
            sequenceMessage("\n로그인되어 있지 않은 상태입니다.");
            wait1Sec();
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("\n정말 탈퇴하시겠습니까?");
        wait05Sec();
        System.out.print("\n\n회원탈퇴를 진행하시려면 y 키를 입력해주세요.");
        wait05Sec();
        System.out.print("\n\n메인 화면으로 돌아가시려면 x 키를 입력해주세요.\n> ");
        while(true) {
            String temp = sc.nextLine();
            if(temp.equals("X") || temp.equals("x")) {
                sequenceMessage("\n메인 화면으로 이동합니다.");
                wait1Sec();
                return;
            } else if(temp.equals("Y") || temp.equals("y")) {
                System.out.print("\n회원탈퇴를 진행합니다.");
                wait1Sec();
                initializeConsole();
                break;
            }
            System.out.print("\n잘못된 값이 입력되었습니다.");
            wait05Sec();
            System.out.print("\n\n회원탈퇴를 진행하시려면 y 키를 입력해주세요.");
            wait05Sec();
            System.out.print("\n\n메인 화면으로 돌아가시려면 x 키를 입력해주세요.\n> ");
        }

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
        
        for(int i=0; i<3; i++) {
            System.out.print("\n\n회원탈퇴를 원하시면 \"회원탈퇴\"를 입력해주세요.\n> ");
            String withdrawCheck = sc.nextLine();
            if (withdrawCheck.equals("회원탈퇴")) {
                members.remove(logInData);
                sequenceMessage("\n회원탈퇴가 완료되었습니다.");
                logInData = -1;
                wait1Sec();
                return;
            } else if (withdrawCheck.equals("X") || withdrawCheck.equals("x")) {
                sequenceMessage("\n메인 화면으로 이동합니다.");
                wait1Sec();
                return;
            } else if (i == 2) {
                System.out.printf("\n%d번 입력 실패하였습니다.", i+1);
                wait05Sec();
                break;
            }
            System.out.printf("\n%d번 입력 실패하였습니다.", i+1);
            wait05Sec();
            System.out.print("\n\n3번 입력 실패 시 메인 화면으로 이동합니다.");
            wait05Sec();
        }
        sequenceMessage("\n\n메인 화면으로 이동합니다.");
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
        Pattern patternName = Pattern.compile("[A-Za-z가-힣]{2,}");
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

    String signUpEmail() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n이메일을 입력하세요.");
        wait05Sec();
        System.out.print("\n\n이메일의 ID는 4자 이상이어야 합니다.\n> ");
        String inputEmail = sc.nextLine();
        while(!validEmail(inputEmail)) {
            System.out.print("이메일을 다시 입력하세요.\n> ");    
            wait05Sec();
            System.out.print("\n\n이메일의 ID는 4자 이상이어야 합니다.\n> ");
            inputEmail = sc.nextLine();
        }
        return inputEmail;
    }

    private boolean validAddress(String inputAddress) {
        Pattern patternAddress = Pattern.compile("[가-힣a-zA-Z0-9]+([ |\\-]?[가-힣a-zA-Z0-9]+)+");
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
            Thread.sleep(45);
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


    // 메인 화면 복귀 && y 입력 시 재입력 선택지
    void mainReturn() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n다시 입력하고 싶으시면 y키를 입력해주세요");
        wait05Sec();
        System.out.print("\n메뉴로 돌아가시려면 아무 키를 입력해주세요.\n> ");
        String temp = sc.nextLine();
        if(temp.equals("Y") || temp.equals("y")) {
            initializeConsole();
        }
        sequenceMessage("\n메인 화면으로 이동합니다.");
        wait1Sec();
    }
}