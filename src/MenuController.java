import java.util.Scanner;
import java.time.LocalDateTime;
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
        System.out.println("1.  Add new task");
        System.out.println("2.  View ALL tasks");
        System.out.println("3.  View ACTIVE tasks");
        System.out.println("4.  View ARCHIVED tasks");
        System.out.println("5.  Mark task as completed");
        System.out.println("6.  Archive task (COMPLETED only)");
        System.out.println("7.  Unarchive task");
        System.out.println("8.  Delete a task");
        System.out.println("9.  Exit");
        System.out.println("==============================");
        System.out.print("Enter your choice: ");
    }

    // =============== Handlers ===============

    /** 添加任务：采集标题/描述/优先级/截止时间(可空) */
    public void handleAddTask() {
        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Description (optional, can be blank): ");
        String desc = scanner.nextLine();

        System.out.print("Priority (LOW/NORMAL/HIGH). Press Enter for NORMAL: ");
        String priStr = scanner.nextLine();
        Priority pri = Priority.NORMAL;
        if (priStr != null && priStr.trim().length() > 0) {
            String p = priStr.trim().toUpperCase();
            if (p.equals("LOW")) pri = Priority.LOW;
            else if (p.equals("HIGH")) pri = Priority.HIGH;
            else pri = Priority.NORMAL; // 默认为 NORMAL
        }

        System.out.print("Due (yyyy-MM-dd HH:mm). Leave blank for none: ");
        String dueStr = scanner.nextLine();
        LocalDateTime dueAt = null;
        if (dueStr != null && dueStr.trim().length() > 0) {
            try {
                // 支持 "2025-11-12 18:00" 这种输入
                dueAt = LocalDateTime.parse(dueStr.trim().replace(" ", "T"));
            } catch (Exception e) {
                System.out.println("Invalid datetime format. Ignored due date.");
                dueAt = null;
            }
        }

        Task task = new Task(title, desc, pri, dueAt);
        taskManager.addTask(task);
        System.out.println("Task added successfully!");
    }

    /** 标记完成：调用 TaskManager.end() 封装的逻辑 */
    public void handleMarkTask() {
        System.out.print("Enter task id to mark as completed: ");
        int taskID;
        try {
            taskID = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid id.");
            return;
        }
        taskManager.markTaskCompleted(taskID);
    }

    /** 删除任务 */
    public void handleDeleteTask() {
        System.out.print("Enter task id to delete: ");
        int taskID;
        try {
            taskID = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid id.");
            return;
        }
        taskManager.deleteTask(taskID);
    }

    /** 归档（只允许 COMPLETED） */
    public void handleArchiveTask() {
        System.out.print("Enter task id to archive: ");
        int taskID;
        try {
            taskID = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid id.");
            return;
        }
        taskManager.archiveTask(taskID);
    }

    /** 取消归档：ARCHIVED -> COMPLETED */
    public void handleUnarchiveTask() {
        System.out.print("Enter task id to unarchive: ");
        int taskID;
        try {
            taskID = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid id.");
            return;
        }
        taskManager.unarchiveTask(taskID);
    }

    // =============== Main Loop ===============

    public void start(){
        boolean running = true;
        while (running) {
            displayMenu();
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid choice!");
                continue;
            }

            switch (choice){
                case 1:
                    handleAddTask();
                    break;
                case 2:
                    taskManager.displayAllTasks();
                    break;
                case 3:
                    taskManager.displayActiveTasks();
                    break;
                case 4:
                    taskManager.displayArchivedTasks();
                    break;
                case 5:
                    handleMarkTask();
                    break;
                case 6:
                    handleArchiveTask();
                    break;
                case 7:
                    handleUnarchiveTask();
                    break;
                case 8:
                    handleDeleteTask();
                    break;
                case 9:
                    System.out.println("Thank you for using Task Tracker!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
