package com.taskmanager.Model;

import javax.persistence.*;

@Entity
@Table(name = "Clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "up_name")
    private String upname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    public Client() {
        super();
    }

    public Client(String firstname, String lastname, String upname, String phone, String email) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
        this.upname = upname;
        this.phone = phone;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUpname() {
        return upname;
    }

    public void setUpname(String upname) {
        this.upname = upname;
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

    @Override
    public String toString() {
        return lastname + ' ' +
                firstname + ' ' +
                upname +
                ", тел. " + phone;
    }
}
