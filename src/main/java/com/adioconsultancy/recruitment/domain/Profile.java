package com.adioconsultancy.recruitment.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name="Profile")
public class Profile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 1000)
    private String firstame;

    @NotNull
    @Size(max = 1000)
    private String surname;

    @NotNull
    @Size(max = 1000)
    private String phoneno;

    @NotNull
    @Size(max = 1000)
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(max = 10000)
    @Column(unique = true)
    private String coverletter;

    @NotNull
    @Size(max = 10000)
    @Column(unique = true)
    private String resumefilename;

    @NotNull
    @Size(max = 10000)
    @Column(unique = true)
    private String photofilename;

    @OneToOne(mappedBy="profile", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Profile()
    { }

    public Profile(String firstame,
                   String surname,
                   String phoneno,
                   String email,
                   String coverletter,
                   String resumefilename,
                   String photofilename) {
        this.firstame = firstame;
        this.surname = surname;
        this.phoneno = phoneno;
        this.email = email;
        this.coverletter = coverletter;
        this.resumefilename = resumefilename;
        this.photofilename = photofilename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstame() {
        return firstame;
    }

    public void setFirstame(String firstame) {
        this.firstame = firstame;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCoverletter() {
        return coverletter;
    }

    public void setCoverletter(String coverletter) {
        this.coverletter = coverletter;
    }

    public String getResumefilename() {
        return resumefilename;
    }

    public void setResumefilename(String resumefilename) {
        this.resumefilename = resumefilename;
    }

    public String getPhotofilename() {
        return photofilename;
    }

    public void setPhotofilename(String photofilename) {
        this.photofilename = photofilename;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }








}
