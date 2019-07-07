import java.lang.reflect.Method;

public class Runner {
    public static void main(String[] args)throws Exception  {
//        Object object = new Child1();
//        object.print();

        Class clazz = Class.forName(Child1.class.getName());
        Object object = clazz.cast(clazz.newInstance());

        Method m = clazz.getMethod("print");
        m.invoke(object);
    }
}
