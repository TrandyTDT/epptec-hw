package com.hw.epptec.person;

public class PersonWithAge {
    private Person person;
    private int age;

    public PersonWithAge(Person person, int age) {
        this.person = person;
        this.age = age;
    }

    public Person getPerson() {
        return person;
    }

    public int getAge() {
        return age;
    }
}
