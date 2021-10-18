package com.example.theatertest;

public class Customer extends User {

    public enum Active{Active, Inactive, Suspended}

    private String firstName;
    private String lastName;
    private String email;
    private Active status;
    private int cardNumOne;
    private int cardNumTwo;
    private int cardNumThree;
    private String cardBillingOne;
    private String cardBillingtwo;
    private String cardBillingThree;
    private int cardExpOne;
    private int cardExpTwo;
    private int cardExpThree;

    void bookMovie() {

    }

    void signIn() {
        
    }

}
