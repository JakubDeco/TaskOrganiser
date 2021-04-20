package sk.kosickaakademia.database;

import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;


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
    public boolean completeTask(ObjectId id) {
        if (id == null)
            return false;

        try {
            getConnection();

            UpdateResult result = mongoColl.updateOne(eq("_id",id), combine(set("done",true)));

            return result.getModifiedCount() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
