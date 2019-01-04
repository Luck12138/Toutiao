package com.amaker.toutiao;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: toutiao
 * @Date: 2019/1/3 0003 18:52
 * @Author: GHH
 * @Description:
 */


public class MultiThread {

    public static void testThread(){
        for(int i=0;i<10;i++){
           // new MyThread(i).start();
        }

        for(int i=0;i<10;i++){
            final int tid=i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for(int  i=0;i<10;i++){
                            Thread.sleep(1000);
                            System.out.println(String.format("TT%d:%d",tid,i));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


    private static Object object=new Object();


    public static void testSychronized1(){
        synchronized (object){
            try {
                for(int  i=0;i<10;i++){
                    Thread.sleep(1000);
                    System.out.println(String.format("T1 :%d",i));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public static void testSychronized2(){
        synchronized (new Object()){
            try {
                for(int  i=0;i<10;i++){
                    Thread.sleep(1000);
                    System.out.println(String.format("T2 :%d",i));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void testSychronized(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    testSychronized1();
                    testSychronized2();
                }
            }
        }).start();
    }


    static class Producer implements Runnable{

        private BlockingQueue<String> q;

        public Producer(BlockingQueue<String> q){
            this.q=q;
        }

        @Override
        public void run() {

                try {
                    for(int i=0;i<100;i++) {
                        Thread.sleep(1000);
                        q.put(String.valueOf(i));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }
    }


    public static void testBlockingQueue(){
       BlockingQueue<String> q=new ArrayBlockingQueue<String>(10);
       new Thread(new Producer(q)).start();
       new Thread(new Consumer(q),"consumer1").start();
        new Thread(new Consumer(q),"consumer2").start();

    }

    static class Consumer implements Runnable{

        private BlockingQueue<String> q;

        public Consumer(BlockingQueue<String> q){
            this.q=q;
        }

        @Override
        public void run() {
           try {
               while (true){
                   System.out.println(Thread.currentThread().getName()+":"+q.take());
               }
           }catch (Exception e){
               e.printStackTrace();
           }
        }
    }

    public static ThreadLocal<Integer> threadLocalUserId=new ThreadLocal<Integer>();
    public static int userId;
    public static void testThreadLocal(){

            for(int i=0;i<10;i++){
               final int ftId=i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
//                            threadLocalUserId.set(ftId);
                            userId=ftId;
                            Thread.sleep(1000);
//                            System.out.println("threadlocal:"+threadLocalUserId.get());
                            System.out.println("threadlocal:"+userId);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

    }

    public static void sleep(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static  void testExecutor(){
//        ExecutorService executorService=Executors.newSingleThreadExecutor();
        ExecutorService executorService=Executors.newFixedThreadPool(3);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    sleep();
                    System.out.println("Executor1:"+i);
                }
            }
        });

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    sleep();
                    System.out.println("Executor2:"+i);
                }
            }
        });
        executorService.shutdown();
        while (!executorService.isTerminated()){
            sleep();
            System.out.println("wait !!!");
        }
    }

    public static void main(String[] args) {
       //testThread();
        // testSychronized();
       // testBlockingQueue();
       // testThreadLocal();
        testExecutor();
    }
}
