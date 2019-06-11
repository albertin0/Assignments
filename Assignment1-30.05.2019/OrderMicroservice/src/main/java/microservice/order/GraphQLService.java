package microservice.order;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class GraphQLService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDataFetcher orderDataFetcher;

    @Autowired
    MultipleOrderDataFetcher multipleOrderDataFetcher;

    @Value("classpath:order.graphql")
    Resource resource;

    private GraphQL graphQL;

    @PostConstruct
    private void loadSchema() throws IOException {

        //get the schema
        //File schemaFile = resource.getFile();

        String schemaFileText = "";
        ClassPathResource resource = new ClassPathResource("/order.graphql");
        try {
            byte[] dataArr = FileCopyUtils.copyToByteArray(resource.getInputStream());
            schemaFileText = new String(dataArr, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // do whatever
        }

        //parse the schema
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schemaFileText);
        RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allOrders", multipleOrderDataFetcher)
                        .dataFetcher("order", orderDataFetcher))
                .build();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry,wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}

