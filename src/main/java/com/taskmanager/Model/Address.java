package com.taskmanager.Model;

import javax.persistence.*;

@Entity
@Table(name = "Addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "build")
    private String build;

    @Column(name = "ent")
    private String ent;

    @Column(name = "latitude")
    private String lat;

    @Column(name = "long")
    private String lon;

    public Address() {
        super();
    }

    public Address(String city, String street, String build, String ent, String lat, String lon) {
        super();
        this.city = city;
        this.street = street;
        this.build = build;
        this.ent = ent;
        this.lat = lat;
        this.lon = lon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getEnt() {
        return ent;
    }

    public void setEnt(String ent) {
        this.ent = ent;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "г. " + city +
                ", ул. " + street +
                ", " + build +
                ", подъезд " + ent;
    }
}
