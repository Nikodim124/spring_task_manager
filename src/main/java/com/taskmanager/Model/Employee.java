package com.taskmanager.Model;


import javax.persistence.*;

@Entity
@Table(name = "Employees")
public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

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

    public Employee() {
        super();
    }

    public Employee(Post post, String firstname, String lastname, String upname, String phone, String email) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
        this.upname = upname;
        this.phone = phone;
        this.email = email;
        this.post = post;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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
        return "Employee{" +
                "id=" + id +
                ", post=" + post +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", upname='" + upname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getInitials() {
        return firstname.substring(0,1)+lastname.substring(0,1);
    }
}
