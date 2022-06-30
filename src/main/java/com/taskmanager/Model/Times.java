package com.taskmanager.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Times")
public class Times {

    public Times() {
        super();
    }

    public Times(long id, String time) {
        super();
        this.id = id;
        this.time = time;
    }

    @Id
    private long id;

    @Column(name = "time")
    private String time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Time{" +
                "id=" + id +
                ", time='" + time + '\'' +
                '}';
    }
}
