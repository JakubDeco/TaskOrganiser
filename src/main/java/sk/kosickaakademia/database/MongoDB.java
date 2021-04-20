package sk.kosickaakademia.database;

import com.google.gson.JsonObject;

import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.types.ObjectId;


public interface MongoDB {
    public void insertTask(JsonObject jsonObject);

    public boolean completeTask(ObjectId id);

    public FindIterable<Document> getAllTasks();

    public FindIterable<Document> getAllTasks(boolean completed);

    public FindIterable<Document> getTaskByName(String name);

    public FindIterable<Document> getTasksByPriority(int priority);

    public FindIterable<Document> getAllTasksByPriority();

    public FindIterable<Document> getTasksByTimeEst();
}
