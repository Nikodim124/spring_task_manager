package com.taskmanager.Model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "Appeals")
public class Appeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "date")
    private Date date;

    @PrePersist
    protected void onCreate() {
        date = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "appart")
    private String appart;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "task_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date taskdate;

    @ManyToOne
    @JoinColumn(name = "time_id")
    private Times tasktime;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @Column(name = "comment")
    private String comment;

    @Column(name = "is_done")
    private boolean done;

    public Appeal() {
        super();
    }

    public Appeal(Contract contract, Employee employee, Date date, Address address, String appart, Client client, Date taskdate, Times tasktime, Employee manager, String comment, boolean done) {
        super();
        this.contract = contract;
        this.employee = employee;
        this.date = date;
        this.address = address;
        this.appart = appart;
        this.client = client;
        this.taskdate = taskdate;
        this.tasktime = tasktime;
        this.manager = manager;
        this.comment = comment;
        this.done = done;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getAppart() {
        return appart;
    }

    public void setAppart(String appart) {
        this.appart = appart;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getTaskdate() {
        return taskdate;
    }

    public void setTaskdate(Date taskdate) {
        this.taskdate = taskdate;
    }

    public Times getTasktime() {
        return tasktime;
    }

    public void setTasktime(Times tasktime) {
        this.tasktime = tasktime;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String stringTaskdate() {
        DateFormat targetFormat = new SimpleDateFormat("EEE dd MMMM yyyy");
        String formatted = targetFormat.format(date);
        return formatted;
    }

    @Override
    public String toString() {
        return "Appeal{" +
                "id=" + id +
                ", contract=" + contract +
                ", employee=" + employee +
                ", date=" + date +
                ", address=" + address +
                ", appart='" + appart + '\'' +
                ", client=" + client +
                ", taskdate=" + taskdate +
                ", tasktime=" + tasktime +
                ", manager=" + manager +
                ", comment='" + comment + '\'' +
                ", done=" + done +
                '}';
    }
}
