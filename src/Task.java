public class Task {
    private int taskId;
    private String taskName;
    private boolean isComplete;

    public Task(int taskId, String taskName) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.isComplete = false;
    }
    public int getTaskId() {return taskId;}
    public String getTaskName() {return taskName;}
    public boolean getIsComplete() {return isComplete;}

    public void setTaskName(String taskName) {this.taskName = taskName;}
    public void setIsComplete(boolean isComplete) {this.isComplete = isComplete;}

    public void markAsCompleted(){
        setIsComplete(true);
    }
    public void displayTaskInfo(){
        System.out.println("Task ID: " + taskId);
        System.out.println("Task Name: " + taskName);
        System.out.println("Is complete: " + isComplete);
    }
}
