package com.leilei.support;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;

/**
 * @author lei
 * @create 2022-03-23 14:39
 * @desc forkJoin 分支合并测试
 **/
public final class BatchTaskRunner<T> extends RecursiveAction {
    protected int threshold = 5;
    protected List<T> taskList;
    Consumer<List<T>> action;

    /**
     * @param taskList      任务列表
     * @param threshold     每个线程处理的任务数
     */
    private BatchTaskRunner(List<T> taskList, int threshold, Consumer<List<T>> action) {
        this.taskList = taskList;
        this.threshold = threshold;
        this.action = action;
    }

    /**
     * 多线程批量执行任务
     * @param taskList
     * @param threshold
     * @param action
     */
    public static <T> void execute(List<T> taskList, int threshold, Consumer<List<T>> action) {
        new BatchTaskRunner<>(taskList, threshold, action).invoke();
    }

    @Override
    protected void compute() {
        if (taskList.size() <= threshold) {
            this.action.accept(taskList);
        }
        else {
            this.splitFromMiddle(taskList);
        }
    }

    /**
     * 任务中分
     * @param list
     */
    private void splitFromMiddle(List<T> list) {
        int middle = (int)Math.ceil(list.size() / 2.0);
        List<T> leftList = list.subList(0, middle);
        List<T> rightList = list.subList(middle, list.size());
        BatchTaskRunner<T> left = newInstance(leftList);
        BatchTaskRunner<T> right = newInstance(rightList);
        ForkJoinTask.invokeAll(left, right);
    }

    private BatchTaskRunner<T> newInstance(List<T> taskList) {
        return new BatchTaskRunner<T>(taskList, threshold, action);
    }

    public static void main(String[] args) {
        List<Integer> allTasks = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
        int taskPerThread = 1;
        BatchTaskRunner.execute(allTasks, taskPerThread, tasks -> System.out.printf("[%s]: %s\n", Thread.currentThread().getName(), tasks));
    }

}