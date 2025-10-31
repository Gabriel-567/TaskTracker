import java.util.ArrayList;

public class TaskManager {
   private ArrayList<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void displayAllTasks(){
        if(tasks.isEmpty()){
            System.out.println("There are no tasks in the system");
        }else {
            for(Task task: tasks){
                task.displayTaskInfo();
            }
        }System.out.println("--------------------------------");
    }

    public void markTaskCompleted(int taskId){
        Task targetTask = getTaskById(taskId);

        if(targetTask == null){
            System.out.println("No such task");
        }else  {
            targetTask.markAsCompleted();
            System.out.println("Task" + taskId + " marked as completed.");
        }

    }
    public void deleteTask(int taskId){
        Task targetTask = getTaskById(taskId);

        if(targetTask == null){
            System.out.println("No such task");
        }else  {
            tasks.remove(targetTask);
            System.out.println("Task" + taskId + " deleted.");
        }

    }



    public Task getTaskById(int taskId) {
        for (Task task : tasks) {
            if (task.getTaskId() == taskId) {
                return task;
            }
        }
        return null;
    }
}
