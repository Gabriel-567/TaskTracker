import java.util.Scanner;

public class TaskApp {
    public static void main(String[] args) {
    TaskManager  taskManager = new TaskManager();
    MenuController menuController = new MenuController(taskManager);
    menuController.start();
    }
}