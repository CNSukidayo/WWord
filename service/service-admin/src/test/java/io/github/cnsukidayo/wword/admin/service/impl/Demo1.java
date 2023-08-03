package io.github.cnsukidayo.wword.admin.service.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo1 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        System.out.println("main begin....");
        CompletableFuture<Void> completableFuture =
                CompletableFuture.runAsync(()->{
                    System.out.println("当前线程:"+Thread.currentThread().getId());
                    int result = 1024;
                    System.out.println("result:"+result);
                },executorService);
        System.out.println("main over....");
    }
}