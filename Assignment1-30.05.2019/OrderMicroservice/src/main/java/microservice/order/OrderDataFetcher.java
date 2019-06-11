package microservice.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import microservice.order.responseCustomer.Customer;
import microservice.order.responseCustomer.CustomerResponse;
import microservice.order.responseProduct.Product;
import microservice.order.responseProduct.ProductResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class OrderDataFetcher implements DataFetcher<Detail> {

    @Autowired
    OrderRepository orderRepository;

    @Value("${product.api.end.point}")
    String productApiEndPoint;

    @Value("${customer.api.end.point}")
    String customerApiEndPoint;

    public Detail get(DataFetchingEnvironment dataFetchingEnvironment) {
        Order order = orderRepository.findByOrderId(dataFetchingEnvironment.getArgument("orderId"));
        Product product = getProductFromResponse(order.getProductId());
        Customer customer = getCustomerFromResponse(order.getCustomerId());
        return new Detail(order,customer,product);
    }

    public Product getProductFromResponse(String productId) {
        String productRequestBody = "{product(productId:\""+productId+"\"){productId productName productCost productModel}}";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(productApiEndPoint);
        httpPost.setHeader("Content-type", "application/json");
        try {
            StringEntity stringEntity = new StringEntity(productRequestBody);
            httpPost.getRequestLine();
            httpPost.setEntity(stringEntity);
            HttpResponse result = httpClient.execute(httpPost);
            String r = EntityUtils.toString(result.getEntity());
            //System.out.println(r);
            ProductResponse productResponse = (new ObjectMapper().readValue(r,ProductResponse.class));
            return productResponse.getData().getProduct();
            //System.out.println(r.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Customer getCustomerFromResponse(String customerId) {
        String customerRequestBody = "{customer(customerId:\""+customerId+"\"){customerId firstName lastName age}}";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(customerApiEndPoint);
        httpPost.setHeader("Content-type", "application/json");
        try {
            StringEntity stringEntity = new StringEntity(customerRequestBody);
            httpPost.getRequestLine();
            httpPost.setEntity(stringEntity);
            HttpResponse result = httpClient.execute(httpPost);
            String r = EntityUtils.toString(result.getEntity());
            //System.out.println(r);
            CustomerResponse customerResponse = (new ObjectMapper().readValue(r,CustomerResponse.class));
            return customerResponse.getData().getCustomer();
            //System.out.println(r.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
