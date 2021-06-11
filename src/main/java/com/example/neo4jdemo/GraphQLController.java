package com.example.neo4jdemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.neo4j.driver.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class GraphQLController {

    private final GraphQLProvider graphQLProvider;
    private final ObjectMapper objectMapper;

    @Autowired
    public GraphQLController(GraphQLProvider graphQLProvider, ObjectMapper objectMapper) {
        this.graphQLProvider = graphQLProvider;
        this.objectMapper = objectMapper;
    }

    @RequestMapping(value = "/graphql", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public Result graphqlGET(@RequestParam("query") String query,
                                          @RequestParam("variables") String variablesJson) throws Exception {
        if (query == null) {
            query = "";
        }

        Map<String, Object> variables = new LinkedHashMap<>();

        if (variablesJson != null) {
            variables = objectMapper.readValue(variablesJson, new TypeReference<Map<String, Object>>() {
            });
        }

        return executeGraphqlQuery(query, variables);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/graphql", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public Result graphql(@RequestBody Map<String, Object> body) throws Exception {
        String query = (String) body.get("query");

        if (query == null) {
            query = "";
        }

        String operationName = (String) body.get("operationName");
        Map<String, Object> variables = (Map<String, Object>) body.get("variables");

        if (variables == null) {
            variables = new LinkedHashMap<>();
        }

        return executeGraphqlQuery( query, variables);
    }

    private Result executeGraphqlQuery(String query, Map<String, Object> variables) throws Exception {
        return graphQLProvider.execute(query,variables);
    }
}
