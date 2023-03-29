package com.hw.epptec.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/person")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Person>> getPeople() {
        return ResponseEntity.ok(personService.getPeople());
    }

    @GetMapping("/{nationalId}")
    public ResponseEntity<PersonWithAge> getPerson(@PathVariable String nationalId) {
        Person person = personService.findPerson(nationalId);
        if (person != null) {
            int age = personService.calculateAge(nationalId);
            PersonWithAge personWithAge = new PersonWithAge(person, age);
            return ResponseEntity.ok(personWithAge);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> addPerson(@RequestBody Person person) {
        if (!personService.addPerson(person)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{nationalId}")
    public ResponseEntity<Void> deletePerson (@PathVariable String nationalId) {
        if (!personService.deletePerson(nationalId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }


}
