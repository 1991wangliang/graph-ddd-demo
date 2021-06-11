package com.example.neo4jdemo;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

/**
 * @author lorne
 * @since 1.0.0
 */
public interface PersonRepository extends Neo4jRepository<Person,Long> {

    Person findByName(String name);

    List<Person> findByTeammatesName(String name);

}
