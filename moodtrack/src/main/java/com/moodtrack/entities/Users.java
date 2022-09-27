package com.moodtrack.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "FNAME")
    private String fName;

    @Column(name = "LNAME")
    private String lName;

    @Column(name = "CREATE_TS")
    private Date createTS;

    @Column(name = "LAST_UPD_TS")
    private Date lastUpdateTS;

}
