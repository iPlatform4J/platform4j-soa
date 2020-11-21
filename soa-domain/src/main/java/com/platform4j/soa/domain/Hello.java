package com.platform4j.soa.domain;

import java.io.Serializable;

public class Hello implements Serializable {

    public Hello() {
    }

    public Hello(String hello) {
        this.hello = hello;
    }

    private String hello;
    private String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }
}
