package microservice.order;

import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {

    @Autowired
    GraphQLService graphQLService;

    @Autowired
    OrderRepository orderRepository;

    @PostMapping(value = "/webservice3/create")
    public ResponseEntity<String> createOrder(@RequestBody Order order)   {
        orderRepository.save(order);
        return  new ResponseEntity<String>(order.toString()+ " added to db.", HttpStatus.OK);
    }

    @PostMapping(value = "/webservice3/retrieve")
    public ResponseEntity<Object> retrieveOrders(@RequestBody String query)    {
        ExecutionResult execute = graphQLService.getGraphQL().execute(query);
        return new ResponseEntity<>(execute, HttpStatus.OK);
    }

    @PutMapping(value = "/webservice3/update")
    public ResponseEntity<String> updateOrder(@RequestBody Order order)   {
        orderRepository.save(order);
        return new ResponseEntity<String>(order.toString() + " updated to db.", HttpStatus.OK);
    }

    @DeleteMapping(value = "/webservice3/delete/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable String orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        if(order!=null)   {
            orderRepository.delete(order);
            return new ResponseEntity<String>(order.toString() + " deleted from db.", HttpStatus.OK);
        }
        return new ResponseEntity<>(orderId + ", no order with this Id in db.", HttpStatus.OK);
    }
}
