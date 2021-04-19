package sk.kosickaakademia.database;

import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import sk.kosickaakademia.document.Task;

import java.util.List;

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
    public List<Task> getAllTasks(boolean completed) {
        return null;
    }

    @Override
    public List<Task> getTaskByName(String name) {
        return null;
    }

    @Override
    public List<Task> getTasksByPriority(int priority) {
        return null;
    }

    @Override
    public List<Task> getAllTasksByPriority() {
        return null;
    }

    @Override
    public List<Task> getTasksByTimeEst() {
        return null;
    }
}
