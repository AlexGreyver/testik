package com.model;

import javax.persistence.*;


@Entity
@Table(name = "books")
public class Book{

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private Integer year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lockedBy")
    private User locker;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public User getLocker()
    {
        return locker;
    }

    public void setLocker(User locker)
    {
        this.locker = locker;
    }

    public Book(){
        super();
    }

    public Book(Integer id, String name, Integer year, User user, User locker){
        super();
        this.user = user;
        this.id = id;
        this.name = name;
        this.year = year;
        this.locker = locker;
    }
}
