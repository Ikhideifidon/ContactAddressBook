package com.example.contactaddressbook;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Contact implements Serializable {
    private int ID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Date createDate;

    public Contact(int ID, String firstName, String lastName, String phoneNumber, String email, Date createDate) {
        setID(ID);
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        //make a defensive copy of the mutable Date passed to the constructor
        setCreateDate(new Date(createDate.getTime()));
    }
    public Contact(int ID, String firstName, String lastName, String phoneNumber, String email) {
        setID(ID);
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setEmail(email);
    }

    public Contact() {}

    // Class Instances Get methods
    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() { return phoneNumber;}

    public String getEmail() {
        return email;
    }

    /**
     * Returns a defensive copy of the field.
     * The caller may change the state of the returned object in any way,
     * without affecting the internals of this class.
     */
    public Date getCreateDate() {
        return new Date(createDate.getTime());
    }

    // Class Instances set methods
    public void setID(int ID) {
        validateID(ID);
        this.ID = ID;
    }

    public void setFirstName(String firstName) {
        validateName(firstName);
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        validateName(lastName);
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        validateName(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    public void setCreateDate(Date createDate) {
        //make a defensive copy of the mutable date object
        Date newlyCreateDate = new Date(createDate.getTime());
        validateDate(newlyCreateDate);
        createDate = newlyCreateDate;
    }

    // Class Instances validation methods
    private void validateID(int id) {

    }

    private void validateName(String firstName) {
        String regex = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regex);
        if (firstName != null) {
            Matcher matcher = pattern.matcher(firstName);
            matcher.matches();

        }


    }

    private void validateEmail(String email) {

    }

    private void validatePhoneNumber(String phoneNumber) {}

    private void validateDate(Date newlyCreateDate) {

    }

}
