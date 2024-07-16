package Member;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class MemberDB {
    MemberInfo m = new MemberInfo();
    List<List<Object>> members = new ArrayList<> ();

    // 입력 데이터의 유효성 검사
    private boolean checkName() {
        Pattern patternN = Pattern.compile("[A-za-z가-힇]{2,}");
        Matcher matcherN = patternN.matcher(m.getName());
        return matcherN.matches();
    }
    void invalidName() {
        Scanner sc = new Scanner(System.in);
        while(!checkName()) {
            System.out.println("\n이름을 다시 입력하세요");
            m.setName(sc.nextLine());
        }
    }

    private boolean checkAge() {
        Pattern patternAge = Pattern.compile("[0-9]{1,3}");
        Matcher matcherAge = patternAge.matcher(String.valueOf(m.getAge()));
        return (matcherAge.matches()) && (m.getAge() < 120);
    }
    void invalidAge() {
        Scanner sc = new Scanner(System.in);
        while (!checkAge()) {
            System.out.println("\n나이를 다시 입력하세요");
            m.setAge(sc.nextLine());
        }
    }


    private boolean checkEmail() {
        Pattern patternE = Pattern.compile("\\w+@\\w+\\.[a-z]+");
        Matcher matcherE = patternE.matcher(m.getEmail());
        return matcherE.matches();
    }
    void invalidEmail() {
        Scanner sc = new Scanner(System.in);
        while (!checkEmail()) {
            System.out.println("\n유효한 이메일을 다시 입력하세요");
            m.setEmail(sc.nextLine());
        }
    }

    private boolean checkAddress() {
        Pattern patternA = Pattern.compile("[가-힇a-zA-Z0-9]+");
        Matcher matcherA = patternA.matcher(m.getAddress());
        return matcherA.matches();
    }
    void invalidAddress() {
        Scanner sc = new Scanner(System.in);
        while (!checkAddress()) {
            System.out.println("\n유효한 주소를 다시 입력하세요");
            m.setAddress(sc.nextLine());
        }
    }

    void insert() {
        members.add(Arrays.asList(m.getName(), m.getAge(), m.getEmail(), m.getAddress()));
    }
}