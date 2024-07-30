package member.refactoring;
import java.util.*;
import java.io.*;
import java.sql.*;
import java.util.regex.*;

public class DAO {
    List<DTO> members = new ArrayList<>();
    private String logInData = null;
    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:xe";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = null;


    // 메뉴
    void menu() {
        initializeConsole();
        System.out.println("===================메뉴====================");
        System.out.println("1.회원가입\t 2.로그인\t 3.로그아웃");
        System.out.println("4.정보 변경\t 5.출력\t\t 6.회원탈퇴");
        System.out.println("0.프로그램 종료");
        System.out.println("===========================================\n\n\n");
        System.out.print("번호를 입력해주세요.\n> ");
    }


    // 회원가입
    void signUp() throws InterruptedException {
        if(logInData != null) {
            logInData = null;
            sequenceMessage("\n자동 로그아웃되었습니다.");
            wait1Sec();
            initializeConsole();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sequenceMessage("\n회원가입을 시작합니다.");
        wait1Sec();
        initializeConsole();

        while(true) {
            try {
                String signUpId = signUpId();
                String signUpPassword = signUpPassword();
                String signUpName = signUpName();
                String signUpBirthDate = signUpBirthDate();
                String signUpEmail = signUpEmail();
                String signUpAddress = signUpAddress();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
        askMainWithMessage2("6자 이상, 20자 이하의 아이디를 입력하세요.", "알파벳과 숫자, _만 입력 가능합니다.");

        do {
            inputId = br.readLine();
            checkedId = isValidId(inputId);
            boolean duplicateId = false;

            if(inputId.equals("X") || inputId.equals("x")) {
                moveMain();
                return "";
            } else if (!checkedId) {
                askMainWithMessage2("6자 이상, 20자 이하의 아이디를 다시 입력하세요.", "알파벳과 숫자, _만 입력 가능합니다.");
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
                        askMainWithMessage2("중복된 아이디입니다. 다시 입력하세요.", "4자 이상, 20자 이하의 아이디를 입력하세요.");
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                } finally {
                    if(rs!= null) try {rs.close();} catch (SQLException e) {e.printStackTrace();}
                    if(pstmt!= null) try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
                    if(con!= null) try {con.close();} catch (SQLException e) {e.printStackTrace();}
                }
            }
        } while(!checkedId);
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
        boolean differPassword = false;
        boolean checkedPassword;
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
        if(Integer.parseInt(birth) < 13) return false;

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
            checkedBirthDate = isValidBirthDate(inputBirthDate) || verifyBirthDate(inputBirthDate);

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
        Pattern patternEmail = Pattern.compile("^[A-Za-z0-9][A-Za-z0-9_.-]{5,19}@[A-Za-z0-9-.]+\\.[a-z]+$");
        Matcher matcherEmail = patternEmail.matcher(inputEmail);
        return matcherEmail.matches();
    }

    private String signUpEmail() throws IOException, InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputEmail;
        boolean checkedEmail;
        askMainWithMessage2("이메일을 입력하세요.", "이메일의 ID는 6자 이상, 20자 이하여야 합니다.");

        do {
            inputEmail = br.readLine();
            checkedEmail = isValidEmail(inputEmail);
            boolean duplicateEmail = false;

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
        } while(!checkedEmail);
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
    private void wait1Sec() throws InterruptedException {
        // Thread.sleep(1000);
    }


    // 0.5초 대기
    private void wait05Sec() throws InterruptedException {
        // Thread.sleep(500);
    }


    // 콘솔창 초기화
    private void initializeConsole() {
        // System.out.print("\033[H\033[2J");
        // System.out.flush();
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
}
