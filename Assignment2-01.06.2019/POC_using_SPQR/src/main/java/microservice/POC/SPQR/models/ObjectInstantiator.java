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
    private static DataObject object;

    private static final Logger logger = LoggerFactory.getLogger(ObjectInstantiator.class);

    public static void setObject(DataObject obj) {
        object = obj;
    }

    public static DataObject getObject() {
        return object;
    }

    public static DataObject instantiateObject(DataObject o, List<String> roles)  throws Exception  {
        Class<?> clazz = Class.forName(o.getClass().getName());
        object = (DataObject)clazz.newInstance();
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("C:\\Users\\albart\\IdeaProjects\\Assignments\\Assignment2-01.06.2019\\POC_using_SPQR\\src\\main\\resources\\spec.json");
        Object jsonObject = jsonParser.parse(reader);
//        logger.info(o.getClass().getName());
        JSONObject dataObject = (JSONObject)((JSONObject)jsonObject).get("DataObject");
        String[] arr = o.getClass().getName().split("\\.");
        String className = arr[arr.length-1];
        dataObject = (JSONObject)dataObject.get(className);
//        logger.info(dataObject.toJSONString());
        for(String role: roles) {
            JSONArray fieldArray = (JSONArray) dataObject.get(role);
            for(Object field: fieldArray)   {
//                logger.info((String)field);
                String fieldName = (String)field;

                Method getMethod = o.getClass().getMethod("get" + fieldName);
                Object getObject = getMethod.invoke(o);

//                logger.info(getObject.toString());

                Class c = Class.forName(ObjectInstantiator.class.getName());
//                logger.info(c.toString());
                Class<?>[] paramType = {Object.class};
//                logger.info(paramType.toString());
                Method setMethod = c.getDeclaredMethod("set" + fieldName, paramType);
//                logger.info(setMethod.toString());
                setMethod.invoke(null, getObject);
//                logger.info("Execution reached line 59.");
            }

        }
//        if(roles.contains("ADMIN") || roles.contains("USER"))   {
//            setId(o.getId());
//            setFirstName(o.getFirstName());
//            setUserName(o.getUserName());
//            setPassword(o.getPassword());
//            setToken(o.getToken());
//            setRole(o.getRole());
//        }
//        if(roles.contains("ADMIN")) {
//            setLastName(o.getLastName());
//            setAge(o.getAge());
//        }
        return object;
    }

    public static void setId(Object id)    {
        object.setId((String)id);
    }

    public static void setFirstName(Object firstName)  {
        object.setFirstName((String)firstName);
    }

    public static void setLastName(Object lastName)    {
        object.setLastName((String)lastName);
    }

    public static void setUserName(Object userName) {
        object.setUserName((String)userName);
    }

    public static void setPassword(Object password) {
        object.setPassword((String)password);
    }

    public static void setAge(Object age) {
        object.setAge((Integer)age);
    }

    public static void setToken(Object token) {
        object.setToken((HashMap<String, String>)token);
    }

    public static void setRole(Object role) {
        object.setRole((List<String>)role);
    }

    public static void setProductId(Object productId) {
        object.setProductId((String)productId);
    }

    public static void setProductName(Object productName) {
        object.setProductName((String)productName);
    }

    public static void setProductModel(Object productModel) {
        object.setProductModel((String)productModel);
    }

    public static void setProductCost(Object productCost) {
        object.setProductCost((Integer)productCost);
    }
}
