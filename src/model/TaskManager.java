package model;

import java.util.ArrayList;

/**
 * TaskManager 管理所有任务对象。
 * 功能：
 *  - 添加任务
 *  - 展示所有任务 / 活动任务 / 已归档任务
 *  - 标记任务为完成
 *  - 归档任务（仅允许 COMPLETED）
 *  - 取消归档任务
 *  - 删除任务
 *  - 按ID查找任务
 */
public class TaskManager {

    private ArrayList<Task> tasks; // 存储任务列表

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    /** 添加一个新任务 */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /** 获取所有任务列表 */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /** 展示所有任务 */
    public void displayAllTasks() {
        if (getAllTasks().isEmpty()) {
            System.out.println("There are no tasks in the system");
        } else {
            System.out.println("======== ALL TASKS ========");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                System.out.println(task);
            }
        }
        System.out.println("--------------------------------");
    }

    /** 展示未归档任务（活动任务） */
    public void displayActiveTasks() {
        if (getAllTasks().isEmpty()) {
            System.out.println("There are no tasks in the system");
        } else {
            System.out.println("======== ACTIVE TASKS ========");
            boolean found = false; // 判断是否有任务被打印
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                if (!task.isArchived()) {
                    System.out.println(task);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("(No active tasks to display)");
            }
        }
        System.out.println("--------------------------------");
    }

    /** 展示已归档任务 */
    public void displayArchivedTasks() {
        if (getAllTasks().isEmpty()) {
            System.out.println("There are no tasks in the system");
        } else {
            System.out.println("======== ARCHIVED TASKS ========");
            boolean found = false;
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                if (task.isArchived()) {
                    System.out.println(task);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("(No archived tasks to display)");
            }
        }
        System.out.println("--------------------------------");
    }

    /** 根据ID查找任务 */
    public Task getTaskById(int taskId) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getTaskId() == taskId) {
                return task;
            }
        }
        return null;
    }

    /** 将任务标记为完成 */
    public void markTaskCompleted(int taskId) {
        Task targetTask = getTaskById(taskId);

        if (targetTask == null) {
            System.out.println("No such task.");
        } else if (targetTask.isArchived()) {
            System.out.println("Task " + taskId + " is archived and cannot be modified.");
        } else if (targetTask.getStatus() == Status.COMPLETED) {
            System.out.println("Task " + taskId + " is already completed.");
        } else {
            // 使用Task自己的end()方法，自动更新时间戳
            targetTask.end();
            System.out.println("Task " + taskId + " marked as completed.");
        }
    }

    /** 将任务归档（仅允许 COMPLETED 状态的任务） */
    public void archiveTask(int taskId) {
        Task targetTask = getTaskById(taskId);

        if (targetTask == null) {
            System.out.println("No such task.");
        } else if (targetTask.isArchived()) {
            System.out.println("Task " + taskId + " is already archived.");
        } else if (targetTask.getStatus() != Status.COMPLETED) {
            System.out.println("Only COMPLETED tasks can be archived. Please complete task " + taskId + " first.");
        } else {
            targetTask.archive(); // 调用 Task 类中的归档方法
            System.out.println("Task " + taskId + " archived.");
        }
    }

    /** 取消归档：将任务从 ARCHIVED 恢复到 COMPLETED */
    public void unarchiveTask(int taskId) {
        Task targetTask = getTaskById(taskId);

        if (targetTask == null) {
            System.out.println("No such task.");
        } else if (!targetTask.isArchived()) {
            System.out.println("Task " + taskId + " is not archived.");
        } else {
            targetTask.unarchive(); // 调用 Task 类中的取消归档方法
            System.out.println("Task " + taskId + " unarchived (back to COMPLETED).");
        }
    }

    /** 删除任务 */
    public void deleteTask(int taskId) {
        Task targetTask = getTaskById(taskId);

        if (targetTask == null) {
            System.out.println("No such task.");
        } else {
            tasks.remove(targetTask);
            System.out.println("Task " + taskId + " deleted.");
        }
    }
}
