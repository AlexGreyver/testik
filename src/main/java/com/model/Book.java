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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;


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

    public User getUser()
    {
        return user;
    }

    public void setUser(User employee)
    {
        this.user = user;
    }
    public Book(){
        super();
    }

    public Book(Integer id, String name, User user){
        super();
        this.user = user;
        this.id = id;
        this.name = name;


    }
}
