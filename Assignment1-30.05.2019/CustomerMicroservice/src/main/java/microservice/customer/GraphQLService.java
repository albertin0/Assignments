package microservice.customer;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class GraphQLService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerDataFetcher customerDataFetcher;

    @Autowired
    MultipleCustomerDataFetcher multipleCustomerDataFetcher;

    @Value("classpath:customer.graphql")
    Resource resource;

    private GraphQL graphQL;

    @PostConstruct
    private void loadSchema() throws IOException    {

        //get the schema
        File schemaFile = resource.getFile();

        //parse the schema
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
                                .type("Query", typeWiring -> typeWiring
                                .dataFetcher("allCustomers", multipleCustomerDataFetcher)
                                .dataFetcher("customer", customerDataFetcher))
                                .build();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry,wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}
