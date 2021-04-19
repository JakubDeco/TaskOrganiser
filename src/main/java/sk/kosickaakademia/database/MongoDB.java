package sk.kosickaakademia.database;


import sk.kosickaakademia.document.Task;

import java.util.List;

public interface MongoDB {
    public void insertTask(Task task);

    public void completeTask(int id);

    public List<Task> getAllTasks();

    public List<Task> getAllTasks(boolean completed);

    public List<Task> getTaskByName(String name);

    public List<Task> getTasksByPriority(int priority);

    public List<Task> getAllTasksByPriority();

    public List<Task> getTasksByTimeEst();
}
