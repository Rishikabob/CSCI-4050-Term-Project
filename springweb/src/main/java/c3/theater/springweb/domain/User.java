package c3.theater.springweb.domain;

import javax.persistence.*;

@Entity
public class User {

    public enum Status{ACTIVE, INACTIVE, SUSPENDED}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Status status;
    private String cardNum1;
    private String cardNum2;
    private String cardNum3;
    private String cardBill1;
    private String cardBill2;
    private String cardBill3;
    private String cardExp1;
    private String cardExp2;
    private String cardExp3;

    public User() {
    }

    // Basic Constructor, /wo payment info or status
    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCardNum1() {
        return cardNum1;
    }

    public void setCardNum1(String cardNum1) {
        this.cardNum1 = cardNum1;
    }

    public String getCardNum2() {
        return cardNum2;
    }

    public void setCardNum2(String cardNum2) {
        this.cardNum2 = cardNum2;
    }

    public String getCardNum3() {
        return cardNum3;
    }

    public void setCardNum3(String cardNum3) {
        this.cardNum3 = cardNum3;
    }

    public String getCardBill1() {
        return cardBill1;
    }

    public void setCardBill1(String cardBill1) {
        this.cardBill1 = cardBill1;
    }

    public String getCardBill2() {
        return cardBill2;
    }

    public void setCardBill2(String cardBill2) {
        this.cardBill2 = cardBill2;
    }

    public String getCardBill3() {
        return cardBill3;
    }

    public void setCardBill3(String cardBill3) {
        this.cardBill3 = cardBill3;
    }

    public String getCardExp1() {
        return cardExp1;
    }

    public void setCardExp1(String cardExp1) {
        this.cardExp1 = cardExp1;
    }

    public String getCardExp2() {
        return cardExp2;
    }

    public void setCardExp2(String cardExp2) {
        this.cardExp2 = cardExp2;
    }

    public String getCardExp3() {
        return cardExp3;
    }

    public void setCardExp3(String cardExp3) {
        this.cardExp3 = cardExp3;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status=" + status +
                ", cardNum1='" + cardNum1 + '\'' +
                ", cardNum2='" + cardNum2 + '\'' +
                ", cardNum3='" + cardNum3 + '\'' +
                ", cardBill1='" + cardBill1 + '\'' +
                ", cardBill2='" + cardBill2 + '\'' +
                ", cardBill3='" + cardBill3 + '\'' +
                ", cardExp1='" + cardExp1 + '\'' +
                ", cardExp2='" + cardExp2 + '\'' +
                ", cardExp3='" + cardExp3 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
