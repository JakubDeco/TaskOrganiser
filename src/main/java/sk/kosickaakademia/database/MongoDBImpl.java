package sk.kosickaakademia.database;

import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class MongoDBImpl implements MongoDB {
    private MongoClient mongoClient;
    private MongoDatabase mongoDbs;
    private MongoCollection<Document> mongoColl;

    @Override
    public void insertTask(JsonObject jsonObject) {
        if (jsonObject == null)
            return;

        try {
            getConnection();
            Document document = Document.parse(jsonObject.toString());
            mongoColl.insertOne(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getConnection() throws Exception{
        mongoClient = new MongoClient();
        mongoDbs = mongoClient.getDatabase("taskOrganiser");
        mongoColl = mongoDbs.getCollection("task");
    }

    @Override
    public void completeTask(int id) {

    }

    @Override
    public FindIterable<Document> getAllTasks() {
        try {
            getConnection();

            return mongoColl.find();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FindIterable<Document> getAllTasks(boolean completed) {
        try {
            getConnection();

            BasicDBObject query = new BasicDBObject();
            query.put("done", completed);

            return mongoColl.find(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FindIterable<Document> getTaskByName(String name) {
        if(name == null)
            return null;

        try {
            getConnection();

            BasicDBObject query = new BasicDBObject();
            query.put("name", name);

            return mongoColl.find(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FindIterable<Document> getTasksByPriority(int priority) {
        if (priority < 1 || priority > 3)
            return null;

        try {
            getConnection();

            BasicDBObject query = new BasicDBObject();
            query.put("priority", priority);

            return mongoColl.find(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FindIterable<Document> getAllTasksByPriority() {
        try {
            getConnection();

            BasicDBObject query = new BasicDBObject("priority",1);

            return mongoColl.find().sort(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FindIterable<Document> getTasksByTimeEst() {
        try {
            getConnection();

            BasicDBObject query = new BasicDBObject("timeEst",1);

            return mongoColl.find().sort(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
