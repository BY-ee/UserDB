package member.refactoring;
import java.util.*;
import java.io.*;
import java.sql.*;
import java.util.regex.*;

public class DAO {
    List<DTO> members = new ArrayList<>();
    private String[] logInData = new String[5];
    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:xe";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = null;


    // 메뉴
    void menu() {
        initializeConsole();
        System.out.println("\n===================메뉴====================");
        System.out.println("1.회원가입\t 2.로그인\t 3.로그아웃");
        System.out.println("4.정보 변경\t 5.출력\t\t 6.회원탈퇴");
        System.out.println("0.프로그램 종료");
        System.out.println("===========================================\n\n\n");
        System.out.print("원하시는 메뉴의 번호를 입력하세요.\n> ");
    }


    // 회원가입
    void signUp() throws InterruptedException {
        if(logInData != null) {
            logInData = null;
            sequenceMessage("\n자동으로 로그아웃되었습니다.");
            wait1Sec();
            initializeConsole();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sequenceMessage("\n회원가입을 시작합니다.");
        wait1Sec();
        initializeConsole();

        try {
            String signUpId = signUpId();
            if(signUpId.isEmpty()) return;
            String signUpPassword = signUpPassword();
            if(signUpPassword.isEmpty()) return;
            String signUpName = signUpName();
            if(signUpName.isEmpty()) return;
            String signUpBirthDate = signUpBirthDate();
            if(signUpBirthDate.isEmpty()) return;
            String signUpEmail = signUpEmail();
            if(signUpEmail.isEmpty()) return;
            String signUpAddress = signUpAddress();
            if(signUpAddress.isEmpty()) return;
            
            System.out.println("\n============================");
            System.out.println(" 아이디: " + signUpId);
            System.out.println(" 이름: " + signUpName);
            System.out.println(" 생년월일: " + signUpBirthDate);
            System.out.println(" E-mail: " + signUpEmail);
            System.out.println(" 주소: " + signUpAddress);
            System.out.println("============================");

            boolean isRepeated;
            do {
                isRepeated = false;
                askMainWithMessage("입력하신 정보가 맞으면 y를 입력하세요.");
                String xyCheck = br.readLine();
                if(isX(xyCheck)) {
                    moveMain();
                    break;
                } else if(xyCheck.equalsIgnoreCase("y")) {
                    Class.forName(driver);
                    con = DriverManager.getConnection(url, "mini", "2417");

                    sql = "INSERT INTO mini VALUES (mini_seq.NEXTVAL,?,?,?,?,?,?,sysdate)";
                    pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, signUpId);
                    pstmt.setString(2, signUpPassword);
                    pstmt.setString(3, signUpName);
                    pstmt.setString(4, signUpBirthDate);
                    pstmt.setString(5, signUpEmail);
                    pstmt.setString(6, signUpAddress);

                    int result = pstmt.executeUpdate();
                    if(result == 1) {
                        sequenceMessage("\n회원가입이 완료되었습니다.");
                        wait1Sec();
                        initializeConsole();
                        break;
                    } else {
                        sequenceMessage("\n회원가입 실패");
                        wait1Sec();
                        initializeConsole();
                        break;
                    }
                } else {
                    inputFaultValue();
                    isRepeated = true;
                }
            } while(isRepeated);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt!= null) pstmt.close();
                if (con!= null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
    // 로그인
    void logIn() {
        if(logInData!= null) {
            sequenceMessage("\n이미 '" + logInData + "' 아이디로 로그인되어 있습니다.");
            wait1Sec();
            return;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        askMainWithMessage("아이디를 입력하세요.");
        while(true) {
            try {
                String logInId = br.readLine();
                if(isX(logInId)) break;

                askMainWithMessage("비밀번호를 입력하세요");
                String logInPassword = br.readLine();
                if(isX(logInPassword)) break;

                Class.forName(driver);
                con = DriverManager.getConnection(url, "mini", "2417");
                sql = "SELECT * FROM mini WHERE id=?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, logInId);
                rs = pstmt.executeQuery();
                if(rs.next()) {
                    String dbId = rs.getString("id");
                    String dbPassword = rs.getString("password");
                    String dbName = rs.getString("name");
                    String dbBirth = rs.getString("birth");
                    String dbEmail = rs.getString("email");
                    String dbAddress = rs.getString("address");

                    if(dbPassword.equals(logInPassword)) {
                        sequenceMessage("\n로그인에 성공하였습니다.");
                        logInData[0] = dbId;
                        logInData[1] = dbName;
                        logInData[2] = dbBirth;
                        logInData[3] = dbEmail;
                        logInData[4] = dbAddress;
                        wait1Sec();
                        initializeConsole();
                        break;
                    }
                }
                askMainWithMessage("로그인에 실패하였습니다. 아이디를 다시 입력하세요.");
                wait1Sec();
                initializeConsole();
            } catch (IOException | SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs!= null) rs.close();
                    if (pstmt!= null) pstmt.close();
                    if (con!= null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // 로그아웃
    void logOut() {
        if(logInData == null) {
            sequenceMessage("\n이미 로그아웃된 상태입니다.");
            wait1Sec();
            return;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean isRepeated;
        try {
            do {
                isRepeated = false;
                askMainWithMessage("로그아웃하시려면 y를 입력하세요.");
                String logOutCheck = br.readLine();
                if(isX(logOutCheck)) {
                    moveMain();
                    initializeConsole();
                } else if(logOutCheck.equalsIgnoreCase("y")) {
                    sequenceMessage("\n정상적으로 로그아웃되었습니다.");
                    logInData = null;
                    wait1Sec();
                    initializeConsole();
                } else {
                    inputFaultValue();
                    isRepeated = true;
                }
            } while(isRepeated);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    // 정보 변경
    void change() {
        if(logInData == null) {
            sequenceMessage("\n로그인 후에 다시 시도해주세요.");
            wait1Sec();
            return;
        }

        initializeConsole();
        System.out.print("\n\n\n================================================");
        System.out.print("\n1.아이디   2.비밀번호   3.이름   4.이메일   5.주소");
        System.out.print("\n================================================\n\n\n");
        askMainWithMessage("변경하실 정보의 번호를 입력해주세요.");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String select = null;
        try {select = br.readLine();} catch (IOException e) {e.printStackTrace();}
        
        switch (select) {
            case "1":
                changeId();
                break;
            case "2":
                changePassword();
                break;
            case "3":
                changeName();
                break;
            case "4":
                changeEmail();
                break;
            case "5":
                changeAddress();
                break;
            default:
                inputFaultValue();
        }
    }


    // 아이디 변경
    private void changeId() {
        sequenceMessage("\n아이디 변경을 시작합니다.");
        wait05Sec();
        initializeConsole();

        askMainWithMessage("현재 아이디를 입력해주세요.");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String currentId = null;
        boolean isRepeated;
        do {
            isRepeated = false;
            try {
                currentId = br.readLine();
                if(isX(currentId)) {
                    moveMain();
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!logInData[0].equals(currentId)) {
                askMainWithMessage("\n아이디가 일치하지 않습니다. 다시 입력해주세요.\n> ");
                isRepeated = true;
            }
        } while(isRepeated);

        String newId = null;
        askMainWithMessage("\n변경하실 아이디를 입력하세요.");
        do {
            try {newId = br.readLine();} catch (IOException e) {e.printStackTrace();}

            if(isX(newId)) {
                moveMain();
                return;
            } else if(currentId.equals(newId)) {
                askMainWithMessage("\n현재 아이디와 동일합니다. 다시 입력하세요.\n> ");
                continue;
            } else if(isExistId(newId)) {
                askMainWithMessage("\n이미 존재하는 아이디입니다. 다시 입력하세요.\n> ");
                continue;
            }
            
            askMainWithMessage("\n변경하실 아이디를 한번 더 입력하세요.");
            isRepeated = false;

            try {newId = br.readLine();} catch (IOException e) {e.printStackTrace();}

            if(isX(newId)) {
                moveMain();
                return;
            } else if(currentId.equals(newId)) {
                askMainWithMessage("\n아이디가 일치하지 않습니다. 변경하실 아이디를 다시 입력하세요.\n> ");
                isRepeated = true;
            }
        } while(isRepeated);

        if(updateIdInDB(newId)) {
            sequenceMessage("\n아이디가 성공적으로 변경되었습니다.");
            logInData[0] = newId;
            wait1Sec();
            initializeConsole();
        } else {
            sequenceMessage("\n아이디 변경에 실패하였습니다.");
            wait1Sec();
            initializeConsole();
        }
    }

    // 비밀번호 변경
    private void changePassword() {
    }

    // 이름 변경
    private void changeName() {
    }

    // 이메일 변경
    private void changeEmail() {
    }

    // 주소 변경
    private void changeAddress() {
    }


    // 아이디 존재 여부 확인
    private boolean isExistId(String inputId) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, "mini", "2417");

            sql = "SELECT * FROM mini WHERE id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, inputId);
            rs = pstmt.executeQuery();
            return rs.next();    
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con!= null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // DB 아이디 업데이트
    private boolean updateIdInDB(String newId) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, "mini", "2417");

            sql = "UPDATE mini SET id=? WHERE id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, newId);
            pstmt.setString(2, logInData[0]);
            int result = pstmt.executeUpdate();
            return (result == 1);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt!= null) pstmt.close();
                if (con!= null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    // 출력
    void print() {
        if(logInData == null) {
            sequenceMessage("\n로그인 후에 다시 시도해주세요.");
            wait1Sec();
            return;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String checkId;
        String checkPassword;
        boolean isRepeated;
        
        try {
            isRepeated = false;
            askMainWithMessage("아이디를 입력하세요.");
            checkId = br.readLine();
            if(isX(checkId)) {
                moveMain();
                return;
            }

            askMainWithMessage("비밀번호를 입력하세요.");
            checkPassword = br.readLine();
            if(isX(checkPassword)) {
                moveMain();
                return;
            }

            Class.forName(driver);
            con = DriverManager.getConnection(url, "mini", "2417");
            sql = "SELECT * FROM mini WHERE id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, checkId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                String dbPassword = rs.getString("password");
                initializeConsole();
                if(dbPassword.equals(checkPassword)) {
                    wait05Sec();
                    System.out.println("\n============================");
                    System.out.println(" 아이디: " + rs.getString("id"));
                    System.out.println(" 이름: " + rs.getString("name"));
                    System.out.println(" 생년월일: " + rs.getString("birth"));
                    System.out.println(" E-mail: " + rs.getString("email"));
                    System.out.println(" 주소: " + rs.getString("address"));
                    System.out.println("============================\n");
                } else {
                    sequenceMessage("현재 계정 정보와 일치하지 않습니다.");
                }
            } else {
                sequenceMessage("현재 계정 정보와 일치하지 않습니다.");
            }
            sequenceMessage("\n\n메인 화면으로 이동하시려면 아무 키나 입력하세요. ");
            br.readLine();
            moveMain();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs!= null) rs.close();
                if (pstmt!= null) pstmt.close();
                if (con!= null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




    // 프로그램 종료
    void exit() {
        System.out.println("\n프로그램을 종료합니다.");
        wait1Sec();
        System.exit(0);
    }

    // 잘못된 값 입력
    void inputFaultValue() {
        sequenceMessage("\n잘못된 값이 입력되었습니다.\n");
        wait05Sec();
    }

    // 아이디 유효성 검사
    private boolean isValidId(String inputId) {
        Pattern patternId = Pattern.compile("^[A-Za-z0-9][A-Za-z0-9_.-]{5,19}$");
        Matcher matcherId = patternId.matcher(inputId);
        return matcherId.matches();
    }

    private String signUpId() throws IOException, InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputId;
        boolean checkedId;
        boolean duplicateId;
        askMainWithMessage2("6자 이상, 20자 이하의 아이디를 입력하세요.", "알파벳과 숫자, 일부 특수문자(_-.)만 입력 가능합니다.");

        do {
            inputId = br.readLine();
            checkedId = isValidId(inputId);
            duplicateId = false;

            if(inputId.equals("X") || inputId.equals("x")) {
                moveMain();
                return "";
            } else if (!checkedId) {
                askMainWithMessage2("6자 이상, 20자 이하의 아이디를 다시 입력하세요.", "알파벳과 숫자, 일부 특수문자(_-.)만 입력 가능합니다.");
            } else {
                try {
                    Class.forName(driver);
                    con = DriverManager.getConnection(url, "mini", "2417");

                    sql = "SELECT id FROM mini";
                    pstmt = con.prepareStatement(sql);
                    rs = pstmt.executeQuery();
                    while(rs.next()) {
                        if(rs.getString("id").equals(inputId)) {
                            duplicateId = true;
                            break;
                        }
                    }

                    if(duplicateId) {
                        askMainWithMessage("중복된 아이디입니다. 다시 입력하세요.");
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                } finally {
                    if(rs!= null) try {rs.close();} catch (SQLException e) {e.printStackTrace();}
                    if(pstmt!= null) try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
                    if(con!= null) try {con.close();} catch (SQLException e) {e.printStackTrace();}
                }
            }
        } while(!checkedId || duplicateId);
        return inputId;
    }


    // 비밀번호 유효성 검사
    private boolean isValidPassword(String inputPassword) {
        Pattern patternPassword = Pattern.compile("(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*()])(?=.*\\d)[\\w!@#$%^&*()]{8,}");
        Matcher matcherPassword = patternPassword.matcher(inputPassword);
        return matcherPassword.matches();
    }

    private String signUpPassword() throws IOException, InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputPassword;
        boolean checkedPassword;
        boolean differPassword = false;
        sequenceMessage("\n\n비밀번호를 입력하세요.");

        do {
            wait05Sec();
            askMainWithMessage("\n비밀번호는 8자 이상이어야 하며, 대소문자와 특수문자, 숫자를 각각 하나 이상 포함해야 합니다.");
            inputPassword = br.readLine();
            initializeConsole();
            checkedPassword = isValidPassword(inputPassword);

            if(inputPassword.equals("X") || inputPassword.equals("x")) {
                moveMain();
                return "";
            } else if (!checkedPassword) {
                sequenceMessage("\n비밀번호를 다시 입력하세요.");
                continue;
            }

            differPassword = false;
            sequenceMessage("\n비밀번호를 한번 더 입력하세요.\n> ");
            String confirmPassword = br.readLine();
            initializeConsole();
            if(!inputPassword.equals(confirmPassword)) {
                sequenceMessage("\n비밀번호가 다릅니다. 다시 입력하세요.");
                differPassword = true;
            }
        } while(!checkedPassword || differPassword);
        return inputPassword;
    }
    
    
    // 이름 유효성 검사
    private boolean isValidName(String inputName) {
        Pattern patternName = Pattern.compile("^[A-Za-z가-힣]{2,}$");
        Matcher matcherName = patternName.matcher(inputName);
        return matcherName.matches();
    }

    private String signUpName() throws IOException, InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputName;
        boolean checkedName;
        askMainWithMessage("이름을 입력하세요.");

        do {
            inputName = br.readLine();
            checkedName = isValidName(inputName);

            if(inputName.equals("X") || inputName.equals("x")) {
                moveMain();
                return "";
            } else if (!checkedName) {
                sequenceMessage("\n이름을 다시 입력하세요.\n> ");
            }
        } while(!checkedName);
        return inputName;
    }
    

    // 생년월일 유효성 검사
    private boolean isValidBirthDate(String inputBirthDate) {
        Pattern patternBirthDate = Pattern.compile("^\\d{6}$");
        Matcher matcherBirthDate = patternBirthDate.matcher(inputBirthDate);
        return matcherBirthDate.matches();
    }

    private boolean verifyBirthDate(String inputBirthDate) {
        if(inputBirthDate.length() < 6) return false;

        String birth = inputBirthDate.substring(2, 4);
        if(birth.equals("00")) return false;
        if(Integer.parseInt(birth) > 12) return false;

        String date = inputBirthDate.substring(4, 6);
        int[] dateList = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        boolean checkDate = true;
        for(int i = 0; i < 12; i++) {
            if (Integer.parseInt(birth) - 1 == i) checkDate = Integer.parseInt(date) <= dateList[i];
        }
        return checkDate;
    }

    private String signUpBirthDate() throws IOException, InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputBirthDate;
        boolean checkedBirthDate;
        askMainWithMessage("생년월일을 입력하세요.");

        do {
            inputBirthDate = br.readLine();
            checkedBirthDate = isValidBirthDate(inputBirthDate) && verifyBirthDate(inputBirthDate);

            if(inputBirthDate.equals("X") || inputBirthDate.equals("x")) {
                moveMain();
                return "";
            } else if (!checkedBirthDate) {
                sequenceMessage("\n생년월일을 다시 입력하세요.\n> ");
            }
        } while(!checkedBirthDate);
        return inputBirthDate;
    }


    // 이메일 유효성 검사
    private boolean isValidEmail(String inputEmail) {
        Pattern patternEmail = Pattern.compile("^[A-Za-z0-9][A-Za-z0-9_.-]{5,19}@([A-Za-z0-9-]+\\.){1,10}[a-z]+$");
        Matcher matcherEmail = patternEmail.matcher(inputEmail);
        return matcherEmail.matches();
    }

    private String signUpEmail() throws IOException, InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputEmail;
        boolean checkedEmail;
        boolean duplicateEmail;
        askMainWithMessage2("이메일을 입력하세요.", "이메일의 아이디는 6자 이상, 20자 이하여야 합니다.");

        do {
            inputEmail = br.readLine();
            checkedEmail = isValidEmail(inputEmail);
            duplicateEmail = false;

            if(inputEmail.equals("X") || inputEmail.equals("x")) {
                moveMain();
                return "";
            } else if (!checkedEmail) {
                sequenceMessage("\n이메일을 다시 입력하세요.\n> ");
            } else {
                try {
                    Class.forName(driver);
                    con = DriverManager.getConnection(url, "mini", "2417");

                    sql = "SELECT email FROM mini";
                    pstmt = con.prepareStatement(sql);
                    rs = pstmt.executeQuery();
                    while(rs.next()) {
                        if(rs.getString("email").equals(inputEmail)) {
                            duplicateEmail = true;
                            break;
                        }
                    }

                    if(duplicateEmail) {
                        askMainWithMessage("이미 존재하는 이메일입니다. 다시 입력해주세요.");
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                } finally {
                    if(rs!= null) try {rs.close();} catch (SQLException e) {e.printStackTrace();}
                    if(pstmt!= null) try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
                    if(con!= null) try {con.close();} catch (SQLException e) {e.printStackTrace();}
                }
            }
        } while(!checkedEmail || duplicateEmail);
        return inputEmail;
    }
    

    // 주소 유효성 검사
    private boolean isValidAddress(String inputAddress) {
        Pattern patternAddress = Pattern.compile("^[A-Za-z0-9가-힣\\s.,/-]{1,50}$");
        Matcher matcherAddress = patternAddress.matcher(inputAddress);
        return matcherAddress.matches();
    }

    private String signUpAddress() throws IOException, InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputAddress;
        boolean checkedAddress;
        askMainWithMessage("주소를 입력하세요.");

        do {
            inputAddress = br.readLine();
            checkedAddress = isValidAddress(inputAddress);

            if(inputAddress.equals("X") || inputAddress.equals("x")) {
                moveMain();
                return "";
            } else if (!checkedAddress) {
                askMainWithMessage("주소를 다시 입력하세요.");
            }
        } while(!checkedAddress);
        return inputAddress;
    }




    // 1초 대기
    private void wait1Sec() {
        // try {
        //     Thread.sleep(1000);
        // } catch (InterruptedException e) {
        //     Thread.currentThread().interrupt();
        // }
    }


    // 0.5초 대기
    private void wait05Sec() {
        // try {
        //     Thread.sleep(500);
        // } catch (InterruptedException e) {
        //     Thread.currentThread().interrupt();
        // }
    }


    // 콘솔창 초기화
    private void initializeConsole() {
        // System.out.print("\033[H\033[2J");
        // System.out.flush();
        // System.out.print("\n".repeat(50));
    }


    // 순차 메시지
    private void sequenceMessage(String message) {
        // try {
            for(String s : message.split("")) {
                System.out.print(s);
                // Thread.sleep(45);
            }
        // } catch (InterruptedException e) {
        //     Thread.currentThread().interrupt();
        // }
    }


    // 메인 화면 이동 메시지 출력
    private void moveMain() {
        sequenceMessage("\n메인 화면으로 이동합니다.");
        // wait1Sec();
    }


    // 메시지 출력 후 메인 화면 이동 메시지 출력
    private void moveMainWithMessage(String message) {
        sequenceMessage("\n" + message);
        // wait05Sec();
        sequenceMessage("\n\n메인 화면으로 이동합니다.");
        // wait1Sec();
    }


    // 메인 화면 이동 여부를 확인하는 메시지 출력
    private void askMain() {
        sequenceMessage("\n메인 화면으로 돌아가시려면 x를 입력하세요.\n> ");
    }


    // 메시지 출력 후 메인 화면 이동 여부를 확인하는 메시지 출력
    private void askMainWithMessage(String message) {
        sequenceMessage("\n" + message);
        // wait05Sec();
        sequenceMessage("\n\n메인 화면으로 돌아가시려면 x를 입력하세요.\n> ");
    }


    // 메시지 2개 출력 후 메인 화면 이동 여부를 확인하는 메시지 출력
    private void askMainWithMessage2(String message1, String message2) {
        sequenceMessage("\n" + message1);
        // wait05Sec();
        sequenceMessage("\n\n" + message2);
        // wait05Sec();
        sequenceMessage("\n\n메인 화면으로 돌아가시려면 x를 입력하세요.\n> ");
    }


    // x, X를 입력하면 main 이동 메시지를 출력하고 true를 반환하는 메소드
    private boolean isX(String input) {
        return input.equalsIgnoreCase("x");
    }
}
