package Member;

class MemberInfo {
    private String name = "";
    private int age = 0;
    private String email = "";
    private String address = "";


    // setter 선언
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = Integer.parseInt(age);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    // getter 선언
    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAddress() {
        return this.address;
    }
}
