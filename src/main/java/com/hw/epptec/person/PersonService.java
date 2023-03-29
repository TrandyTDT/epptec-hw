package com.hw.epptec.person;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
    private List<Person> people = new ArrayList<>();

    public List<Person> getPeople () {
        return people;
    }

    public Person findPerson(String nationalId) {
        for (Person person : people) {
            if (person.getNationalId().equals(nationalId)) {
                return person;
            }
        }
        return null;
    }

    public boolean addPerson(Person person) {
        if (person.getFirstName().isEmpty()) return false;
        if (person.getLastName().isEmpty()) return false;
        if (checkNationalId(person.getNationalId())) return false;
        if (findPerson(person.getNationalId()) != null) {
            return false;
        }
        people.add(person);
        return true;
    }

    public boolean deletePerson(String nationalId) {
        Person person = findPerson(nationalId);
        if (person == null) {
            return false;
        }
        people.remove(person);
        return true;
    }

    public boolean checkNationalId(String nationalId) {
        if (nationalId.isEmpty()) return false;
        String regex = "^\\d{6}\\/?\\d{4}$\n";
        return nationalId.matches(regex);
    }

    public int calculateAge(String nationalId) {
        int day = Integer.parseInt(nationalId.substring(4, 6));
        int month = Integer.parseInt(nationalId.substring(2, 4));
        int year = Integer.parseInt(nationalId.substring(0, 2));
        if (month > 50) {
            month -= 50;
        } else {
            year += 1900;
        }
        LocalDate dateOfBirth = LocalDate.of(year, month, day);
        LocalDate today = LocalDate.now();
        Period period = Period.between(dateOfBirth, today);
        return period.getYears();
    }
}
