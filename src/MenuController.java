import java.util.Scanner;
import model.*;
public class MenuController {
    private TaskManager taskManager;
    private Scanner scanner;

    public MenuController(TaskManager tm) {
        this.taskManager = tm;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu(){
        System.out.println("==============================");
        System.out.println(" TASK TRACKER MAIN MENU");
        System.out.println("==============================");
        System.out.println("1. Add new task");
        System.out.println("2. View all tasks");
        System.out.println("3. Mark task as completed");
        System.out.println("4. Delete a task");
        System.out.println("5. Exit");
        System.out.println("==============================");
        System.out.println("Enter your choice: ");
    }

    public void handleAddTask() {
        System.out.println("Enter task ID: ");
        int taskID = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter task name: ");
        String taskName = scanner.nextLine();

        Task task = new Task(taskID, taskName);
        taskManager.addTask(task);

        System.out.println("Task added successfully!");
    }

    public void handleMarkTask() {
        System.out.println("Enter task ID to mark as completed: ");
        int taskID = scanner.nextInt();
        taskManager.markTaskCompleted(taskID);
    }
    public void handleDeleteTask() {
        System.out.println("Enter task ID to delete: ");
        int taskID = scanner.nextInt();
        taskManager.deleteTask(taskID);
    }

    public void start(){
        boolean flag = true;
        while(flag){
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    handleAddTask();
                    break;
                case 2:
                    taskManager.displayAllTasks();
                    break;
                case 3:
                    handleMarkTask();
                    break;
                case 4:
                    handleDeleteTask();
                    break;
                case 5:
                    System.out.println("Thank you for using our application!");
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid choice!");

            }
        }
    }

}
