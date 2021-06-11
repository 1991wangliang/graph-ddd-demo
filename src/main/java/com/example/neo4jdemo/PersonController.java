package com.example.neo4jdemo;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lorne
 * @since 1.0.0
 */
@RestController
@AllArgsConstructor
public class PersonController {

    private final PersonRepository personRepository;

    @GetMapping("/list")
    public List<Person> list(){
        return personRepository.findAll();
    }
    
    @PostMapping("/addPerson")
    public long addPerson(Person person){
        personRepository.save(person);
        return person.getId();
    }

}
