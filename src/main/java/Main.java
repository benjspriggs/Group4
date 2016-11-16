import com.sun.org.apache.xpath.internal.SourceTree;
import sqldb.ChocanConnection;
import sqldb.schemas.ChocanSchema;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by bspriggs on 11/10/2016.
 */
public class Main {
    public static void main(String[] args) throws Exception{

        ChocanConnection obj = new ChocanConnection();
        System.out.println("Hello world!");

        obj.getProviderReport();

    }


}
