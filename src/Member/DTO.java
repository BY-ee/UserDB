package Member;

class DTO {
    private String id;
    private String password;
    private String name;
    private String birthDate;
    private String email;
    private String address;


    // setter 선언
    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) { this.password = password; }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    // getter 선언
    public String getId() {
        return this.id;
    }

    public String getPassword() { return this.password; }

    public String getName() {
        return this.name;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAddress() {
        return this.address;
    }
}
