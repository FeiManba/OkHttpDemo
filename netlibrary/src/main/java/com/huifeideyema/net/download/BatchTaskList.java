package com.huifeideyema.net.download;

import java.util.concurrent.ConcurrentHashMap;

public class BatchTaskList<T> {

    private ConcurrentHashMap<String, T> ts;
    private int allTaskCount;
    private int currentTaskFinishedCount; //当前完成任务

    public int getAllTaskCount() {
        return allTaskCount;
    }

    public ConcurrentHashMap<String, T> getTs() {
        return ts;
    }

    public int getCurrentTaskFinishedCount() {
        return currentTaskFinishedCount;
    }

    public BatchTaskList(ConcurrentHashMap<String, T> ts) {
        this.ts = ts;
    }

    public BatchTaskList() {
        this.ts = new ConcurrentHashMap<>();
    }

    public int getTaskCount() {
        return ts.size();
    }

    public void addTask(String taskId, T t) throws Exception {
        ts.put(taskId, t);
        allTaskCount++;
    }

    public T getTask(String taskId) throws Exception {
        return ts.get(taskId);
    }

    public void removeTask(String taskId) throws Exception {
        ts.remove(taskId);
        currentTaskFinishedCount++;
    }

    public void removeTask(T t) throws Exception {
        ts.remove(t);
        currentTaskFinishedCount++;
    }

}
