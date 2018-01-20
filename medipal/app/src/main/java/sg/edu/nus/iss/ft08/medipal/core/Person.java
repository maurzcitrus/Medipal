package sg.edu.nus.iss.ft08.medipal.core;

import java.util.Date;

public class Person {
    private int id;
    private String name;
    private Date dateOfBirth;
    private String personIdNo;
    private String address;
    private String postalCode;
    private int height;
    private String bloodType;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPersonIdNo() {
        return personIdNo;
    }

    public void setPersonIdNo(String personIdNo) {
        this.personIdNo = personIdNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public boolean isValidName() {
        if (name == null)
            return false;

        if (name.isEmpty())
            return false;

        if (name.length() > 50)
            return false;

        return true;
    }

    public boolean isValidDob(){
        if(dateOfBirth==null)
            return false;
        return true;
    }

    public boolean isValidPostalCode(){
        if (postalCode == null)
            return false;

        if (postalCode.isEmpty())
            return false;

        if (postalCode.length() > 50)
            return false;

        return true;
    }
}
