package microservice.order.responseCustomer;

import java.util.List;

public class CustomerResponse {
    private List<String> errors;
    private Data data;
    private String extensions;

    public CustomerResponse() {
    }

    public CustomerResponse(List<String> errors, Data data, String extensions) {
        this.errors = errors;
        this.data = data;
        this.extensions = extensions;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }
}
