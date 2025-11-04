package model;

import java.time.LocalDateTime;

public class Task {

    //------------------------------attribute----------------------------------------
    private static int NEXT_ID = 1;
    public static final int MAX_TITLE_LEN = 100;

    private final int taskId;
    private String title;
    private String description;
    private Priority priority;
    private Status status;

    private final LocalDateTime creationDate;  // 任务创建时间
    private LocalDateTime dueAt;               // 计划截止时间
    private LocalDateTime startedAt;           // 实际开始时间
    private LocalDateTime completedAt;         // 实际结束时间

    //--------------------------------constructor----------------------------------------
    public Task(String title, String description, Priority priority, LocalDateTime dueAt) {
        this.taskId = NEXT_ID++;

        this.setTitle(title);
        this.setDescription(description);
        this.setPriority(priority);
        this.setDueAt(dueAt);

        this.setStatus(Status.TODO);
        this.creationDate = LocalDateTime.now();
    }

    //--------------------------------getter-------------------------------------
    public int getTaskId() { return taskId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Priority getPriority() { return priority; }
    public Status getStatus() { return status; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public LocalDateTime getDueAt() { return dueAt; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }

    //---------------------------------setter---------------------------------------
    public void setTitle(String title) {
        if (title == null) {
            title = "";
        } else if (title.length() > MAX_TITLE_LEN) {
            title = title.substring(0, MAX_TITLE_LEN);
        }
        this.title = title;
    }

    public void setDescription(String description) {
        if (description == null) {
            description = "";
        }
        this.description = description;
    }

    public void setPriority(Priority priority) {
        if (priority == null) {
            priority = Priority.NORMAL;
        }
        this.priority = priority;
    }

    public void setStatus(Status status) {
        if (status == null) {
            this.status = Status.TODO;
        } else {
            this.status = status;
        }
    }

    public void setDueAt(LocalDateTime dueAt) {
        this.dueAt = dueAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    //--------------------------behavior------------------------------------------
    public void start() {
        this.status = Status.IN_PROGRESS;
        if (startedAt == null) startedAt = LocalDateTime.now();
    }

    public void end() {
        this.status = Status.COMPLETED;
        if (completedAt == null) completedAt = LocalDateTime.now();
    }

    public boolean isOverdue(LocalDateTime now) {
        if (dueAt == null) return false;
        if (status == Status.COMPLETED) return false;
        return now.isAfter(dueAt);
    }

    //-------------------------toString---------------------------------------
    @Override
    public String toString() {
        return String.format("#%d [%s | %s] %s%s%s%s (created %s)",
                taskId,
                priority,                       // 优先级
                status,                         // 状态
                title,
                (description == null || description.isEmpty()) ? "" : " — " + description,
                (dueAt == null ? "" : " (due " + dueAt + ")"),
                (completedAt == null ? "" : " [done " + completedAt + "]"),
                creationDate
        );
    }
}
