package com.zhou.multithreading;

/**
 * wait()&&notify()方法
 * 这两个方法是在Object中定义的，用于协调线程同步，比join更加灵活
 */
public class NotifyDemo {

    public static void main(String[] args) {
        //写两个线程 1.图片下载

        // Object object = new Object();
        //这是个失败的线程同步案例，只考虑了show线程先于down线程执行
        //show线程添加延时后，将永远wait
        //将锁对象定义为一个标志值  增加同步

        final Boolean[] obj = {Boolean.FALSE};
        Thread download= new Thread(() -> {
            System.out.println("开始下载图片");
            for (int i = 0; i < 101; i+=10) {
                System.out.println("down"+i+"%");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("图片下载成功");
            synchronized (obj) {
                obj[0] = Boolean.TRUE;
                obj.notify();//唤起
            }
            System.out.println("开始下载附件");
            for (int i = 0; i < 101; i+=10) {
                System.out.println("附件下载"+i+"%");

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            System.out.println("附件下载成功");
        });
        //2.图片展示
        Thread show= new Thread(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            synchronized (obj) {
                try {
                    System.out.println("object lock wait");
                    while (!obj[0]){
                        obj.wait(100);//阻塞当前
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("show:开始展示图片");
                System.out.println("图片展示完毕");
            }

        });
        download.start();
//        try {
               // 使用join方法实现先后顺序
//            download.join();
//        } catch (InterruptedException e) {
//        }
        show.start();
    }
}
