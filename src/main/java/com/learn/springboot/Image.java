package com.learn.springboot;

import com.learn.springboot.security.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Image {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToOne
    private User owner;

    private Image() {
    }

    public Image(String name, User owner){
        this.name = name;
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
