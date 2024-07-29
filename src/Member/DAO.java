package Member;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.sql.*;

public class DAO {
    List<DTO> members = new ArrayList<> ();
    private int logInData = -1;
    String sql = "";
    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:xe";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    // 메뉴
    void menu() {
        initializeConsole();
        System.out.println("==================메뉴===================");
        System.out.println("1.회원가입\t 2.로그인\t 3.로그아웃");
        System.out.println("4.정보 변경\t 5.출력\t\t 6.회원탈퇴");
        System.out.println("0.프로그램 종료");
        System.out.print("=========================================\n\n\n\n");
        System.out.print("번호를 입력해주세요.\n> ");
    }


    // 회원가입
    void signUp() throws Exception {
        if(logInData != -1) {
            logInData = -1;
            sequenceMessage("\n자동으로 로그아웃되었습니다.");
            wait1Sec();
            initializeConsole();
        }

        DTO dto = new DTO();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sequenceMessage("\n회원가입을 시작합니다.");
        wait1Sec();
        initializeConsole();

        while (true) {
            dto.setId(signUpID());
            if(dto.getId().isEmpty()) return;
            dto.setPassword(signUpPassword());
            if(dto.getPassword().isEmpty()) return;
            dto.setName(signUpName());
            if(dto.getName().isEmpty()) return;
            dto.setBirthDate(signUpBirthDate());
            if(dto.getBirthDate().isEmpty()) return;
            dto.setEmail(signUpEmail());
            if(dto.getEmail().isEmpty()) return;
            dto.setAddress(signUpAddress());
            if(dto.getAddress().isEmpty()) return;

            sequenceMessage("\n입력이 완료되었습니다.");
            wait05Sec();
            sequenceMessage("\n\n입력하신 정보가 잘못되었다면 y 키를 입력해주세요.");
            wait05Sec();
            sequenceMessage("\n\n회원가입을 완료하시려면 x 키를 입력해주세요.\n> ");
            
            while(true) {
                String temp = br.readLine();
                if (temp.equals("X") || temp.equals("x")) {
                    sql = "INSERT INTO mini VALUES (mini_no.nextval,?,?,?,?,?,?,sysdate)";
                    pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, dto.getId());
                    pstmt.setString(2, dto.getPassword());
                    pstmt.setString(3, dto.getName());
                    pstmt.setString(4, dto.getBirthDate());
                    pstmt.setString(5, dto.getEmail());
                    pstmt.setString(6, dto.getAddress());
                    int result = pstmt.executeUpdate();
                    if(result == 1) {
                        moveMainWithMessage("회원가입이 완료되었습니다.");
                        return;
                    }
                } else if (temp.equals("Y") || (temp.equals("y"))) {
                    sequenceMessage("\n회원가입을 다시 시작합니다.");
                    wait1Sec();
                    initializeConsole();
                    break;
                }
                sequenceMessage("\n잘못된 값이 입력되었습니다.");
                wait05Sec();
                sequenceMessage("\n\n정보를 다시 입력하시려면 y 키를 입력해주세요.");
                wait05Sec();
                sequenceMessage("\n\n회원가입을 완료하시려면 x 키를 입력해주세요.\n> ");
            }
        }
    }


    // 로그인
    void logIn() throws InterruptedException, IOException, SQLException {
        if(logInData != -1) {
            sql = "SELECT id FROM mini WHERE no = " + logInData;
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            String currentID = rs.getString("id");
            String duplicateID = "\n이미 '" + currentID + "' 아이디로 로그인되어 있습니다.";
            sequenceMessage(duplicateID);
            wait1Sec();
            return;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        askMainWithMessage("아이디와 비밀번호를 입력하세요.");
        sql = "SELECT COUNT(*) FROM mini";
        pstmt = con.prepareStatement(sql);
        rs = pstmt.executeQuery();
        if(rs.next() == false) {
            while(true) {
                String inputID = br.readLine();
                if(inputID.equals("X") || inputID.equals("x")) {
                    moveMain();
                    return;
                }
                System.out.print("> ");
                String inputPassword = br.readLine();
                if(inputPassword.equals("X") || inputPassword.equals("x")) {
                    moveMain();
                    return;
                }
                askMainWithMessage2("로그인에 실패하였습니다.", "아이디와 비밀번호를 다시 입력해주세요.");
            }
        }

        int cnt = 0;
        while(rs.next()) {
            cnt++;
        }

        while(true) {
            for(int i=0; i<cnt; i++) {
                String inputID = br.readLine();
                if(inputID.equals("X") || inputID.equals("x")) {
                    moveMain();
                    return;
                }
                System.out.print("> ");
                String inputPassword = br.readLine();
                if(inputPassword.equals("X") || inputPassword.equals("x")) {
                    moveMain();
                    return;
                }
                if (inputID.equals(members.get(i).getId()) && inputPassword.equals(members.get(i).getPassword())) {
                    logInData = i;
                    moveMainWithMessage("로그인에 성공하였습니다.");
                    return;
                } else {
                    askMainWithMessage2("로그인에 실패하였습니다.", "아이디와 비밀번호를 다시 입력해주세요.");
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
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            askMainWithMessage("로그아웃하시려면 y 키를 입력해주세요.");
            String temp = br.readLine();
            if(temp.equals("Y") || temp.equals("y")) {
                logInData = -1;
                moveMainWithMessage("정상적으로 로그아웃되었습니다.");
                return;
            } else if(temp.equals("X") || temp.equals("x")) {
                moveMainWithMessage("로그아웃을 취소하였습니다.");
                return;
            }
            sequenceMessage("\n잘못된 값이 입력되었습니다.\n");
            wait05Sec();
        }
    }


    // 정보 변경
    void change() throws InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        if(logInData == -1) {
            sequenceMessage("\n로그인 후에 다시 시도해주세요.");
            wait1Sec();
            return;
        }
        initializeConsole();
        System.out.print("\n\n\n================================================");
        System.out.print("\n1.아이디   2.비밀번호   3.이름   4.이메일   5.주소");
        System.out.print("\n================================================\n\n\n");
        askMainWithMessage("변경하실 정보의 번호를 입력해주세요.");
        String temp = br.readLine();
        DTO dto = new DTO();
        switch (temp) {
            case "1":
                sequenceMessage("\n아이디 변경을 시작합니다.");
                wait05Sec();
                initializeConsole();
                askMainWithMessage("현재 아이디를 입력해주세요.");
                while(true) {
                    dto.setId(br.readLine());
                    if(dto.getId().equals("X") || dto.getId().equals("x")) {
                        moveMain();
                        return;
                    } else if(dto.getId().equals(members.get(logInData).getId())) {
                        break;
                    }
                    askMainWithMessage2("입력하신 아이디가 현재 아이디와 일치하지 않습니다.", "현재 아이디를 다시 입력해주세요.");
                }
                askMainWithMessage2("아이디가 확인되었습니다.", "변경하실 아이디를 입력해주세요.");
                while(true) {
                    while(true) {
                        dto.setId(br.readLine());
                        if(dto.getId().equals("X") || dto.getId().equals("x")) {
                            moveMain();
                            return;
                        } else if(!validID(dto.getId())) {
                            askMainWithMessage("\n4글자 이상의 알파벳과 숫자, _로 이루어진 아이디를 입력해주세요.");
                            continue;
                        }
                        if(!dto.getId().equals(members.get(logInData).getId())) break;
                        sequenceMessage("\n현재 아이디와 동일합니다. 새로운 아이디를 입력해주세요.\n> ");
                    }
                    sequenceMessage("\n변경하실 아이디를 한번 더 입력해주세요.\n> ");
                    String nextIDCheck = br.readLine();
                    if(nextIDCheck.equals(dto.getId())) {
                        members.get(logInData).setId(dto.getId());
                        moveMainWithMessage("아이디가 성공적으로 변경되었습니다.");
                        return;
                    }
                    askMainWithMessage2("입력하신 아이디가 일치하지 않습니다.", "변경하실 아이디를 다시 입력해주세요.");
                }
            case "2":
                sequenceMessage("\n비밀번호 변경을 시작합니다.");
                wait1Sec();
                initializeConsole();
                askMainWithMessage("현재 비밀번호를 입력해주세요.");
                while(true) {
                    dto.setPassword(br.readLine());
                    if(dto.getPassword().equals("X") || dto.getPassword().equals("x")) {
                        moveMain();
                        return;
                    } else if (dto.getPassword().equals(members.get(logInData).getPassword())) {
                        break;
                    }
                    sequenceMessage("\n입력하신 비밀번호가 현재 비밀번호와 일치하지 않습니다.");
                    wait05Sec();
                    sequenceMessage("\n\n현재 비밀번호를 다시 입력해주세요.\n> ");
                }
                sequenceMessage("\n비밀번호가 확인되었습니다.");
                wait05Sec();
                initializeConsole();
                askMainWithMessage("\n변경하실 비밀번호를 입력해주세요.");
                while(true) {
                    while(true) {
                        dto.setPassword(br.readLine());
                        if(dto.getPassword().equals("X") || dto.getPassword().equals("x")) {
                            moveMain();
                            return;
                        } else if(!validPassword(dto.getPassword())) {
                            askMainWithMessage2("비밀번호의 형식에 맞게 다시 입력해주세요.",
                                                "비밀번호는 8자 이상이어야 하며, 대소문자와 특수문자, 숫자를 각각 하나 이상 포함해야 합니다.");
                            continue;
                        }
                        if(!dto.getPassword().equals(members.get(logInData).getPassword())) break;
                        askMainWithMessage("현재 비밀번호와 동일합니다. 새로운 비밀번호를 입력해주세요.");
                    }
                    sequenceMessage("\n변경하실 비밀번호를 한번 더 입력해주세요.\n> ");
                    String nextPasswordCheck = br.readLine();
                    if(nextPasswordCheck.equals(dto.getPassword())) {
                        members.get(logInData).setPassword(dto.getPassword());
                        moveMainWithMessage("비밀번호가 성공적으로 변경되었습니다.");
                        return;
                    }
                    askMainWithMessage2("입력하신 비밀번호가 일치하지 않습니다.", "변경하실 비밀번호를 다시 입력해주세요.");
                }
            case "3":
                sequenceMessage("\n이름 변경을 시작합니다.");
                wait05Sec();
                sequenceMessage("\n\n이름은 개명하신 경우에만 최초 1회 변경할 수 있습니다.");
                wait1Sec();
                initializeConsole();
                askMainWithMessage("개명하신 이름을 입력해주세요.");
                while(true) {
                    while(true) {
                        dto.setName(br.readLine());
                        if(dto.getName().equals("X") || dto.getName().equals("x")) {
                            moveMain();
                            return;
                        }
                        if(!validName(dto.getName())) {
                            askMainWithMessage("이름을 정확히 다시 입력해주세요.");
                            wait1Sec();
                            continue;
                        }
                        if(!dto.getName().equals(members.get(logInData).getName())) break;
                        sequenceMessage("\n현재 이름과 동일합니다. 개명하신 이름을 입력해주세요.\n> ");
                    }
                    String printName = "\n입력하신 이름은 \'" + dto.getName() + "\'입니다.";
                    sequenceMessage(printName);
                    wait05Sec();
                    sequenceMessage("\n\n이름이 맞다면 y 키를 입력해주세요.\n> ");
                    String nextNameCheck = br.readLine();
                    if(nextNameCheck.equals("Y") || nextNameCheck.equals("y")) {
                        members.get(logInData).setName(dto.getName());
                        moveMainWithMessage("이름이 성공적으로 변경되었습니다.");
                        return;
                    }
                    askMainWithMessage("개명하신 이름을 다시 입력해주세요.");
                }
            case "4":
                sequenceMessage("\n이메일 변경을 시작합니다.");
                wait1Sec();
                initializeConsole();
                askMainWithMessage("현재 이메일을 입력해주세요.");
                while(true) {
                    dto.setEmail(br.readLine());
                    if(dto.getEmail().equals("X") || dto.getEmail().equals("x")) {
                        moveMain();
                        return;
                    }
                    if(!validEmail(dto.getEmail())) {
                        askMainWithMessage("이메일을 형식에 맞게 다시 입력해주세요.");
                        wait1Sec();
                        continue;
                    }
                    if(dto.getEmail().equals(members.get(logInData).getEmail())) break;
                    askMainWithMessage2("입력하신 이메일이 현재 이메일과 일치하지 않습니다.", "현재 이메일을 다시 입력해주세요.");
                }
                sequenceMessage("\n변경하실 이메일을 입력해주세요.\n> ");
                while(true) {
                    while(true) {
                        dto.setEmail(br.readLine());
                        if(dto.getEmail().equals("X") || dto.getEmail().equals("x")) {
                            moveMain();
                            return;
                        }
                        if(!validEmail(dto.getEmail())) {
                            askMainWithMessage("이메일을 형식에 맞게 다시 입력해주세요.");
                            continue;
                        }
                        if(dto.getEmail().equals(members.get(logInData).getEmail())) {
                            askMainWithMessage("현재 이메일과 동일합니다. 새로운 이메일을 입력해주세요.");
                            continue;
                        }
                        break;
                    }
                    String printEmail = "\n입력하신 이메일은 \'" + dto.getEmail() + "\'입니다.";
                    sequenceMessage(printEmail);
                    wait05Sec();
                    sequenceMessage("\n\n입력하신 이메일이 맞다면 y 키를 입력해주세요.\n> ");
                    String nextEmailCheck = br.readLine();
                    if(nextEmailCheck.equals("Y") || nextEmailCheck.equals("y")) {
                        members.get(logInData).setEmail(dto.getEmail());
                        moveMainWithMessage("이메일이 성공적으로 변경되었습니다.");
                        return;
                    }
                    askMainWithMessage("변경하실 이메일을 다시 입력해주세요.");
                }
            case "5":
                sequenceMessage("\n주소 변경을 시작합니다.");
                wait1Sec();
                initializeConsole();
                askMainWithMessage("변경하실 주소를 입력해주세요.");
                while(true) {
                    while(true) {
                        dto.setAddress(br.readLine());
                        if(dto.getAddress().equals("X") || dto.getAddress().equals("x")) {
                            moveMain();
                            return;
                        }
                        if(!validAddress(dto.getAddress())) {
                            askMainWithMessage("정확한 주소를 다시 입력해주세요.");
                            wait1Sec();
                            continue;
                        }
                        if(!dto.getAddress().equals(members.get(logInData).getAddress())) break;
                        askMainWithMessage("현재 주소와 동일합니다. 주소를 다시 입력해주세요.");
                    }
                    String printAddress = "\n입력하신 주소는 \'" + dto.getAddress() + "\'입니다.";
                    sequenceMessage(printAddress);
                    wait05Sec();
                    sequenceMessage("\n\n주소가 맞다면 y 키를 입력해주세요.\n> ");
                    String addresbrheck = br.readLine();
                    if(addresbrheck.equals("Y") || addresbrheck.equals("y")) {
                        members.get(logInData).setAddress(dto.getAddress());
                        moveMainWithMessage("주소가 성공적으로 변경되었습니다.");
                        return;
                    }
                    askMainWithMessage("변경하실 주소를 다시 입력해주세요.");
                }
            case "X", "x":
                moveMain();
                return;
            default:
                moveMainWithMessage("\n잘못된 값이 입력되었습니다.");
        }
    }


    // 출력
    void print() throws InterruptedException {
        if (members.isEmpty()) {
            sequenceMessage("\n출력할 데이터가 없습니다.");
            wait1Sec();
            return;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            DTO dto = new DTO();
            System.out.println();
            sequenceMessage("아이디와 생년월일을 입력하세요.\n> ");
            dto.setId(br.readLine());
            System.out.print("> ");
            dto.setBirthDate(br.readLine());

            boolean inputCheckFlag = false;
            for(int i=0; i<members.size(); i++) {
                if (dto.getId().equals(members.get(i).getId()) && dto.getBirthDate().equals(members.get(i).getBirthDate())) {
                    inputCheckFlag = true;
                    wait05Sec();
                    System.out.println("\n\n\n--------------------------------------");
                    System.out.println(" 아이디: " + members.get(i).getId());
                    System.out.println(" 이름: " + members.get(i).getName());
                    System.out.println(" 생년월일: " + members.get(i).getBirthDate());
                    System.out.println(" 이메일: " + members.get(i).getEmail());
                    System.out.println(" 주소: " + members.get(i).getAddress());
                    System.out.println("--------------------------------------");
                    wait05Sec();
                }
            }
            if(!inputCheckFlag) {
                sequenceMessage("\n해당하는 정보가 없습니다.");
                wait05Sec();
            }
            while(true) {
                askMainWithMessage("\n다시 입력하시려면 y 키를 입력해주세요.");
                String temp = br.readLine();
                if(temp.equals("Y") || temp.equals("y")) {
                    initializeConsole();
                    break;
                } else if(temp.equals("X") || temp.equals("x")) {
                    moveMain();
                    return;
                }
                sequenceMessage("\n잘못된 값이 입력되었습니다.");
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
        branner br = new branner(System.in, "CP949");
        sequenceMessage("\n정말 탈퇴하시겠습니까?");
        while(true) {
            wait05Sec();
            askMainWithMessage("\n회원탈퇴를 진행하시려면 y 키를 입력해주세요.");
            String temp = br.readLine();
            if(temp.equals("X") || temp.equals("x")) {
                moveMain();
                return;
            } else if(temp.equals("Y") || temp.equals("y")) {
                sequenceMessage("\n회원탈퇴를 진행합니다.");
                wait1Sec();
                initializeConsole();
                break;
            }
            sequenceMessage("\n잘못된 값이 입력되었습니다.");
        }

        DTO dto = new DTO();
        sequenceMessage("현재 아이디를 입력해주세요.\n> ");
        while(true) {
            String ID = br.readLine();
            if(ID.equals(members.get(logInData).getId())) {
                break;
            }
            sequenceMessage("\n로그인하신 아이디와 일치하지 않습니다. 다시 입력해주세요.\n> ");
        }
        sequenceMessage("\n비밀번호를 입력해주세요.\n> ");
        while(true) {
            dto.setPassword(br.readLine());
            initializeConsole();
            if(dto.getPassword().equals(members.get(logInData).getPassword())) {
                sequenceMessage("\n비밀번호를 한번 더 입력해주세요.\n> ");
                String password = br.readLine();
                initializeConsole();
                if(password.equals(dto.getPassword())) break;
            }
            sequenceMessage("\n비밀번호가 일치하지 않습니다. 다시 입력해주세요.\n> ");
        }
        
        for(int i=0; i<3; i++) {
            askMainWithMessage("회원탈퇴를 원하시면 \"회원탈퇴\"를 입력해주세요.");
            String withdrawCheck = br.readLine();
            String failureInputMessage = "\n" + (i+1) + "번 입력 실패하였습니다. 3번 입력 실패 시 메인 화면으로 이동합니다.";
            if (withdrawCheck.equals("회원탈퇴")) {
                members.remove(logInData);
                logInData = -1;
                sequenceMessage("\n회원탈퇴가 완료되었습니다.");
                wait1Sec();
                return;
            } else if (withdrawCheck.equals("X") || withdrawCheck.equals("x")) {
                moveMain();
                return;
            } else if (i == 2) {
                sequenceMessage(i+1 + "번 입력 실패하셨습니다." + "\n");
                wait05Sec();
                break;
            }
            sequenceMessage(failureInputMessage);
            wait05Sec();
            System.out.println();
        }
        moveMain();
    }



    // 프로그램 종료
    void exit() throws InterruptedException {
        // saveMembersToCsv();
        sequenceMessage("\n프로그램을 종료합니다.");
        wait1Sec();
        initializeConsole();
    }


    // 잘못된 값 입력
    void defaultMessage() throws InterruptedException {
        sequenceMessage("\n잘못된 값이 입력되었습니다.");
        wait1Sec();
    }

    
    // 아이디 유효성 검사
    private boolean validID(String inputID) {
        Pattern patternID = Pattern.compile("[\\w_]{4,20}");
        Matcher matcherID = patternID.matcher(inputID);
        return matcherID.matches();
    }

    private String signUpID() throws InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        askMainWithMessage2("4자 이상, 20자 이하의 아이디를 입력하세요.", "알파벳과 숫자, _만 입력 가능합니다.");
        String inputID = br.readLine();
        while(true) {
            if(inputID.equals("X") || inputID.equals("x")) {
                moveMain();
                return "";
            }
            boolean duplicateID = false;
            for(int i=0; i<members.size(); i++) {
                if(inputID.equals(members.get(i).getId())) {
                    duplicateID = true;
                    break;
                }
            }
            if(duplicateID) {   
                askMainWithMessage("이미 존재하는 아이디입니다. 다시 입력해주세요.");
                inputID = br.readLine();
                continue;
            } else if (!validID(inputID)) {
                askMainWithMessage2("4자 이상, 20자 이하의 아이디를 다시 입력하세요.", "알파벳과 숫자, _만 입력 가능합니다.");
                inputID = br.readLine();
                continue;
            }
            break;
        }
        return inputID;
    }


    // 비밀번호 유효성 검사
    private boolean validPassword(String inputPassword) {
        Pattern patternPassword = Pattern.compile("(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*()])(?=.*\\d)[\\w!@#$%^&*()]{8,}");
        Matcher matcherPassword = patternPassword.matcher(inputPassword);
        return matcherPassword.matches();
    }

    private String signUpPassword() throws InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        DTO dto = new DTO();
        sequenceMessage("\n\n비밀번호를 입력하세요.");
        while(true) {
            wait05Sec();
            askMainWithMessage("\n비밀번호는 8자 이상이어야 하며, 대소문자와 특수문자, 숫자를 각각 하나 이상 포함해야 합니다.");
            dto.setPassword(br.readLine());
            initializeConsole();
            if(dto.getPassword().equals("X") || dto.getPassword().equals("x")) {
                moveMain();
                return "";
            } else if(!validPassword(dto.getPassword())) {
                sequenceMessage("\n비밀번호를 다시 입력하세요.");
                continue;
            }
            sequenceMessage("\n비밀번호를 한번 더 입력하세요.\n> ");
            String confirmPassword = br.readLine();
            initializeConsole();
            if(!confirmPassword.equals(dto.getPassword())) {
                sequenceMessage("\n비밀번호가 일치하지 않습니다. 다시 입력하세요.");
                continue;
            }
            break;
        }
        sequenceMessage("비밀번호가 확인되었습니다.");
        wait1Sec();
        initializeConsole();
        return dto.getPassword();
    }


    // 이름 유효성 검사
    private boolean validName(String inputName) {
        Pattern patternName = Pattern.compile("[A-Za-z가-힣]{2,}");
        Matcher matcherName = patternName.matcher(inputName);
        return matcherName.matches();
    }

    private String signUpName() throws InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        askMainWithMessage("이름을 입력하세요.");
        String inputName = br.readLine();
        
        while(true) {
            if(inputName.equals("X") || inputName.equals("x")) {
                moveMain();
                return "";
            }
            if(validName(inputName)) break;
            askMainWithMessage("이름을 다시 입력하세요.");
            inputName = br.readLine();
        }
        return inputName;
    }


    // 생년월일 유효성 검사
    private boolean validBirthDate(String inputBirthDate) {
        Pattern patternBirthDate = Pattern.compile("\\d{6}");
        Matcher matcherBirthDate = patternBirthDate.matcher(inputBirthDate);
        return matcherBirthDate.matches();
    }

    private boolean conditionBirthDate(String inputBirthDate) {
        if(inputBirthDate.length() < 6) return false;
        String birth = inputBirthDate.substring(2, 4);
        boolean checkBirth = Integer.parseInt(birth) < 13 && !birth.equals("00");
        String date = inputBirthDate.substring(4, 6);
        int[] dateList = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        boolean checkDate = true;
        if (birth.equals("00")) return false;
        for (int i = 0; i < dateList.length; i++) {
            if (Integer.parseInt(birth) - 1 == i) checkDate = Integer.parseInt(date) <= dateList[i];
        }
        return checkBirth && checkDate;
    }

    private String signUpBirthDate() throws InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        askMainWithMessage("\n생년월일을 입력하세요.");
        String inputBirthDate = br.readLine();
        while(true) {
            if(inputBirthDate.equals("X") || inputBirthDate.equals("x")) {
                moveMain();
                return "";
            }
            if(validBirthDate(inputBirthDate) && conditionBirthDate(inputBirthDate)) break;
            askMainWithMessage("생년월일을 다시 입력하세요.");
            inputBirthDate = br.readLine();
        }
        return inputBirthDate;
    }


    // 이메일 유효성 검사
    private boolean validEmail(String inputEmail) {
        Pattern patternEmail = Pattern.compile("([A-Za-z0-9]+_?){4,15}@\\w+\\.[a-z]+");
        Matcher matcherEmail = patternEmail.matcher(inputEmail);
        return matcherEmail.matches();
    }

    private String signUpEmail() throws InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        askMainWithMessage2("\n이메일을 입력하세요.", "이메일의 ID는 4자 이상, 20자 이하여야 합니다.");
        String inputEmail = br.readLine();
        while(true) {
            if(inputEmail.equals("X") || inputEmail.equals("x")) {
                moveMain();
                return "";
            }
            boolean duplicateEmail = false;
            for(int i=0; i<members.size(); i++) {
                if(inputEmail.equals(members.get(i).getEmail())) {
                    duplicateEmail = true;
                    break;
                }
            }
            if(duplicateEmail) {   
                askMainWithMessage("\n이미 존재하는 이메일입니다. 다시 입력해주세요.");
                inputEmail = br.readLine();
                continue;
            } else if (!validEmail(inputEmail)) {
                sequenceMessage("\n이메일을 다시 입력하세요.");    
                wait05Sec();
                askMainWithMessage("\n이메일의 ID는 4자 이상, 20자 이하여야 합니다.");
                inputEmail = br.readLine();
                continue;
            }
            break;
        }
        return inputEmail;
    }


    // 주소 유효성 검사
    private boolean validAddress(String inputAddress) {
        Pattern patternAddress = Pattern.compile("[가-힣a-zA-Z0-9]+([ |\\-]?[가-힣a-zA-Z0-9]+){2,50}");
        Matcher matcherAddress = patternAddress.matcher(inputAddress);
        return matcherAddress.matches();
    }

    private String signUpAddress() throws InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        askMainWithMessage("\n주소를 입력하세요.");
        String inputAddress = br.readLine();
        while(true) {
            if(inputAddress.equals("X") || inputAddress.equals("x")) {
                moveMain();
                return "";
            }
            if(validAddress(inputAddress)) break;
            askMainWithMessage("주소를 다시 입력하세요.");
            inputAddress = br.readLine();
        }
        return inputAddress;
    }


    // 대기
    private void waitingDot() throws InterruptedException {
        Thread.sleep(1000);
        for(int j=0; j<3; j++) {
            System.out.print(" ");
            System.out.print(".");
            Thread.sleep(1000);
        }
    }


    // 1초 대기
    private void wait1Sec() throws InterruptedException {
        // Thread.sleep(1000);
    }


    // 0.5초 대기
    private void wait05Sec() throws InterruptedException {
        // Thread.sleep(500);
    }


    // 콘솔창 초기화
    private void initializeConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        // System.out.print("\n".repeat(50));
    }


    // 순차 메시지
    private void sequenceMessage(String Message) throws InterruptedException {
        for(String s : Message.split("")) {
            System.out.print(s);
            // Thread.sleep(45);
        }
    }


    // 메인 화면 이동 메시지 출력
    private void moveMain() throws InterruptedException {
        sequenceMessage("\n메인 화면으로 이동합니다.");
        // wait1Sec();
    }


    // 메시지 출력 후 메인 화면 이동 메시지 출력
    private void moveMainWithMessage(String message) throws InterruptedException {
        sequenceMessage("\n" + message);
        // wait05Sec();
        sequenceMessage("\n\n메인 화면으로 이동합니다.");
        // wait1Sec();
    }


    // 메인 화면 이동 여부를 확인하는 메시지 출력
    private void askMain(String message) throws InterruptedException {
        sequenceMessage("\n메인 화면으로 돌아가시려면 x 키를 입력해주세요.\n> ");
    }


    // 메시지 출력 후 메인 화면 이동 여부를 확인하는 메시지 출력
    private void askMainWithMessage(String message) throws InterruptedException {
        sequenceMessage("\n" + message);
        // wait05Sec();
        sequenceMessage("\n\n메인 화면으로 돌아가시려면 x 키를 입력해주세요.\n> ");
    }


    // 메시지 2개 출력 후 메인 화면 이동 여부를 확인하는 메시지 출력
    private void askMainWithMessage2(String message1, String message2) throws InterruptedException {
        sequenceMessage("\n" + message1);
        // wait05Sec();
        sequenceMessage("\n\n" + message2);
        // wait05Sec();
        sequenceMessage("\n\n메인 화면으로 돌아가시려면 x 키를 입력해주세요.\n> ");
    }


    // // 메인 화면 복귀 && y 입력 시 재입력 선택지
    // private void mainReturn() throws InterruptedException {
    //     BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    //     System.out.print("\n다시 입력하고 싶으시면 y키를 입력해주세요");
    //     wait05Sec();
    //     System.out.print("\n메뉴로 돌아가시려면 아무 키를 입력해주세요.\n> ");
    //     String temp = br.readLine();
    //     if(temp.equals("Y") || temp.equals("y")) {
    //         initializeConsole();
    //     }
    //     sequenceMessage("\n메인 화면으로 이동합니다.");
    //     wait1Sec();
    // }
    
    // // csv 파일 저장
    // public void saveMembersToCsv() throws IOException {
    //     try (FileOutputStream fos = new FileOutputStream("members.csv")) {
    //         for(DTO member : members) {
    //                 fos.write((member + "\n").getBytes());
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
}