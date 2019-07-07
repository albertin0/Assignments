package microservice.POC.SPQR.models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

@Service
public final class ObjectInstantiator {
    private static Object object;

    private static final Logger logger = LoggerFactory.getLogger(ObjectInstantiator.class);

    public static void setObject(Object obj) {
        object = obj;
    }

    public static Object getObject() {
        return object;
    }

    public static Object instantiateObject(Object o, List<String> roles)  throws Exception  {
        Class clazz = Class.forName(o.getClass().getName());
        object = clazz.cast(clazz.newInstance());

        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("C:\\Users\\albart\\IdeaProjects\\Assignments\\Assignment2-01.06.2019\\POC_using_SPQR\\src\\main\\resources\\spec.json");
        Object jsonObject = jsonParser.parse(reader);
//        logger.info(o.getClass().getName());
        JSONObject dataObject = (JSONObject)((JSONObject)jsonObject).get("DataObject");
        String[] arr = o.getClass().getName().split("\\.");
        String className = arr[arr.length-1];
        dataObject = (JSONObject)dataObject.get(className);

        for(String role: roles) {
            JSONArray fieldArray = (JSONArray) dataObject.get(role);
            for(Object field: fieldArray)   {
                String fieldName = (String)field;

                Method getMethod = o.getClass().getMethod("get" + fieldName);
                Object getObject = getMethod.invoke(o);
                Class returnParam = getMethod.getReturnType();


                Method setMethod2 = clazz.getDeclaredMethod("set" + fieldName, returnParam);
                setMethod2.invoke(object, getObject);
            }

        }
        return object;
    }

}
