package com.akashali.eusaserviceprovider;

//DATA CLASS TO HOLD SERVICE PROVIDERS

public class Contact {

    // DATA FIELDS (CAN BE MODIFIED) == CHECK ACCESSOR WHERE INSTANCE IS CREATED TO CORRECTLY MODIFY

    private String uid;
    private String loc;
    private String fname;
    private String lname;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String type;
    private String cnic;
    private String rating;
    private String pricerat;
    private String isAvailable;

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getCnic() {
        return cnic;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPricerat() {
        return pricerat;
    }

    public void setPricerat(String pricerat) {
        this.pricerat = pricerat;
    }

    public Contact(String loc,String cnic, String fname, String lname, String phone, String email, String address, String city, String type, String rating, String pricerat,String uid,String isAvailable) {
        this.loc = loc;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.type = type;
        this.cnic = cnic;
        this.rating = rating;
        this.pricerat = pricerat;
        this.uid=uid;
        this.isAvailable=isAvailable;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
