package microservice.product;

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
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class GraphQLService {

    @Autowired
    ProductRepository productRepository;

    @Value("classpath:product.graphql")
    Resource resource;      //this must be of springframework.core.io type.

    private GraphQL graphQL;

    @Autowired
    ProductDataFetcher productDataFetcher;

    @Autowired
    MultipleProductDataFetcher multipleProductDataFetcher;

    @PostConstruct
    private void loadSchema() throws IOException    {

        //get the Schema
        //File schemaFile = resource.getFile();

        //String schemaFileText = new String(Files.readAllBytes(Paths.get(resource.getFile().getPath())));

        String schemaFileText = "";
        ClassPathResource resource = new ClassPathResource("/product.graphql");
        try {
            byte[] dataArr = FileCopyUtils.copyToByteArray(resource.getInputStream());
            schemaFileText = new String(dataArr, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // do whatever
        }

        //parse the Schema
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schemaFileText);
        RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
                                .type("Query", typeWiring -> typeWiring
                                .dataFetcher("allProducts", multipleProductDataFetcher)
                                .dataFetcher("product", productDataFetcher))
                                .build();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry,wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}
