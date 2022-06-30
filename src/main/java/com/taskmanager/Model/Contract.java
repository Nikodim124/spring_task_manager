package com.taskmanager.Model;


import javax.persistence.*;

@Entity
@Table(name = "Contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "appartments")
    private String appart;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "has_internet")
    private boolean internet;

    @Column(name = "has_tv")
    private boolean tv;

    @Column(name = "has_phone")
    private boolean phone;

    @Column(name = "is_active")
    private boolean active;

    public Contract() {
        super();
    }

    public Contract(Address address, String appart, Client client, boolean internet, boolean tv, boolean phone, boolean active) {
        super();
        this.address = address;
        this.appart = appart;
        this.client = client;
        this.internet = internet;
        this.tv = tv;
        this.phone = phone;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isInternet() {
        return internet;
    }

    public void setInternet(boolean internet) {
        this.internet = internet;
    }

    public boolean isTv() {
        return tv;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public boolean isPhone() {
        return phone;
    }

    public void setPhone(boolean phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", address=" + address +
                ", appart='" + appart + '\'' +
                ", client=" + client +
                ", internet=" + internet +
                ", tv=" + tv +
                ", phone=" + phone +
                ", active=" + active +
                '}';
    }
}
