package com.nagi.codec.bean;

import java.util.Map;

public class Dog {
    private String name;
    private int age;
    private Map<String,Object> attributes;

    public Dog(){

    }

    public Dog(String name, int age, Map<String, Object> attributes) {
        this.name = name;
        this.age = age;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", attributes=" + attributes +
                '}';
    }
}
