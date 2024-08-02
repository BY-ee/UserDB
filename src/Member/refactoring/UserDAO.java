package member.refactoring;

public class UserDAO {
    /**
     *  DAO는 DB 관련 로직을 넣는 객체 (Data Access Object)
     *  비즈니스 로직은 UserService에서 처리
     *  사용자  -  Main 클래스  -  Service 클래스  -  DAO 클래스  -  DB
     *             Main.java     UserService.java  UserDAO.java    DB
     * 
     *  DTO는 DB에서 꺼내온 데이터를 저장하고 다른 Logic으로 전송하는 클래스
     *  DB에서 DAO로, 또는 DAO에서 Service로, 또 반대 방향으로도 전송
     *  getters, setters만을 가짐 (필요 시 toString() method를 override)
     * 
     *  Main: Main.java
     *  Service: UserService.java
     *  DAO: UserDAO.java 
     */
}