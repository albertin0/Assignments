import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;

import java.util.List;

public class CustomJsonTransformer {
    public static void main(String[] args) throws Exception {
        List<Object> specs = JsonUtils.classpathToList("/spec2.json");
        Chainr chainr = Chainr.fromSpec(specs);

        Object inputJSON = JsonUtils.classpathToObject("/input2.json");
        System.out.println(JsonUtils.toPrettyJsonString(inputJSON));
        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println();
        Object transformedOutput = chainr.transform(inputJSON);
        System.out.println(JsonUtils.toPrettyJsonString(transformedOutput));
    }
}
