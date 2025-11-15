package com.tasktracker.model;

import java.time.LocalDateTime;

public class Task implements Displayable, Completable {

    // ------------------------- Interface Implementation ---------------------------
    @Override
    public void display() {
        System.out.println(this.toString());
    }

    @Override
    public void markDone() {
        this.end(); // 复用 end()，保证时间戳与状态流一致
    }

    @Override
    public boolean isDone() {
        return this.status == Status.COMPLETED;
    }

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
    private LocalDateTime archivedAt;          // 归档时间

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
    public LocalDateTime getArchivedAt() { return archivedAt; }

    //---------------------------------setter---------------------------------------
    public void setTitle(String title) {
        if (title == null) {
            title = "";
        } else {
            // 去掉首尾空白并截断长度，避免脏数据
            title = title.trim();
            if (title.length() > MAX_TITLE_LEN) {
                title = title.substring(0, MAX_TITLE_LEN);
            }
        }
        this.title = title;
    }

    public void setDescription(String description) {
        if (description == null) {
            description = "";
        } else {
            description = description.trim();
        }
        this.description = description;
    }

    public void setPriority(Priority priority) {
        if (priority == null) {
            priority = Priority.NORMAL;
        }
        this.priority = priority;
    }

    // 【关键】：状态只应通过行为方法修改，避免外部直接跳转破坏时间戳与规则
    private void setStatus(Status status) {
        if (status == null) {
            this.status = Status.TODO;
        } else {
            this.status = status;
        }
    }

    public void setDueAt(LocalDateTime dueAt) {
        this.dueAt = dueAt;
    }

    // 这些时间戳也不建议外部直接改动；保留 private，统一由行为方法维护
    private void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    private void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    // -------------------------- guards（显式可/不可） --------------------------
    public boolean canStart() {
        // 归档/完成都不能再开始；TODO 或 IN_PROGRESS 可以
        return this.status == Status.TODO || this.status == Status.IN_PROGRESS;
    }

    public boolean canEnd() {
        // 归档/已完成都不能再完成；TODO 或 IN_PROGRESS 可以
        return this.status == Status.TODO || this.status == Status.IN_PROGRESS;
    }

    public boolean canArchive() {
        // 只有已完成才能归档
        return this.status == Status.COMPLETED;
    }

    public boolean canUnarchive() {
        return this.status == Status.ARCHIVED;
    }

    //--------------------------behavior------------------------------------------
    public void start() {
        // 保护：不允许归档/完成后再次开始
        if (!canStart()) {
            return;
        }
        this.setStatus(Status.IN_PROGRESS);
        if (startedAt == null) setStartedAt(LocalDateTime.now());
    }

    public void end() {
        // 保护：不允许归档/已完成后再次完成
        if (!canEnd()) {
            return;
        }
        this.setStatus(Status.COMPLETED);
        if (completedAt == null) setCompletedAt(LocalDateTime.now());
    }

    public boolean isOverdue(LocalDateTime now) {
        if (dueAt == null) return false;
        if (status == Status.COMPLETED || status == Status.ARCHIVED) return false;
        return now.isAfter(dueAt);
    }

    public boolean isArchived() {
        return this.status == Status.ARCHIVED;
    }

    public boolean archive() {
        if (canArchive()) {
            this.setStatus(Status.ARCHIVED);
            if (archivedAt == null) archivedAt = LocalDateTime.now();
            return true;
        }
        return false;
    }

    public boolean unarchive() {
        if (canUnarchive()) {
            this.setStatus(Status.COMPLETED);
            return true;
        }
        return false;
    }

    //-------------------------toString---------------------------------------
    @Override
    public String toString() {
        return String.format("#%d [%s | %s] %s%s%s%s%s (created %s)",
                taskId,
                priority,
                status,
                title,
                (description == null || description.isEmpty()) ? "" : " — " + description,
                (dueAt == null ? "" : " (due " + dueAt + ")"),
                (completedAt == null ? "" : " [done " + completedAt + "]"),
                (archivedAt == null ? "" : " [arch " + archivedAt + "]"),
                creationDate
        );
    }
}
