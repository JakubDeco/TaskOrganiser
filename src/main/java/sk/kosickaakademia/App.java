package sk.kosickaakademia;


import com.google.gson.JsonObject;
import sk.kosickaakademia.database.MongoDBImpl;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        MongoDBImpl database = new MongoDBImpl();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name","povysavat");
        jsonObject.addProperty("priority",1);
        jsonObject.addProperty("done",false);
        jsonObject.addProperty("timeEstimate",1.5);

        database.insertTask(jsonObject);
    }
}
