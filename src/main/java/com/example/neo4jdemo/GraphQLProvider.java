package com.example.neo4jdemo;

import com.google.common.io.Resources;
import graphql.schema.GraphQLSchema;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.graphql.Cypher;
import org.neo4j.graphql.SchemaBuilder;
import org.neo4j.graphql.Translator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
public class GraphQLProvider {

    private final Driver driver;

    private final Translator translator;

    public GraphQLProvider(Driver driver) throws IOException {
        URL url = Resources.getResource("starWarsSchemaAnnotated.graphqls");
        String sdl = IOUtils.toString(url, StandardCharsets.UTF_8);
        GraphQLSchema graphQLSchema =  SchemaBuilder.buildSchema(sdl);

        this.driver = driver;
        this.translator = new Translator(graphQLSchema);
    }


    @SneakyThrows
    public Result execute(String query, Map<String, Object> variables)  {
        List<Cypher> cypherList = translator.translate(query,variables);
        if(cypherList.size()>0) {
            Cypher cypher = cypherList.get(0);
            return driver.session().run(cypher.getQuery(),cypher.getParams());
        }
        return null;
    }
}
