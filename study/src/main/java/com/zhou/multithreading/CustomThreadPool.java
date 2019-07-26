package com.zhou.multithreading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Function:线程池
 * <p>
 * Date: 2019-05-14 10:51
 *
 * @since JDK 1.8
 */
public class CustomThreadPool {

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomThreadPool.class);
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 最小线程数，也叫核心线程数
     */
    private volatile int miniSize;

    /**
     * 最大线程数
     */
    private volatile int maxSize;

    /**
     * 任务线程需要被回收的时间
     */
    private long keepAliveTime;

    private TimeUnit unit;

    /**
     * 存放线程的阻塞队列
     */
    private BlockingQueue<Runnable> workQueue;

    /**
     * 存放线程池
     */
    private volatile Set<Worker> workers;

    /**
     * 是否关闭线程池标志
     */
    private AtomicBoolean isShutDown = new AtomicBoolean(false);

    /**
     * 提交到线程池中的任务总数
     */
    private AtomicInteger totalTask = new AtomicInteger(0);

    /**
     * 线程池任务全部执行完毕后的通知组件
     */
    private final Object shutDownNotify = new Object();

    /**
     * 外部通知接口，当前作用为线程池运行中时通知
     */
    private Notify notify;

    /**
     * @param miniSize      最小线程数
     * @param maxSize       最大线程数
     * @param keepAliveTime 线程保活时间
     * @param unit          时间单位
     * @param workQueue     阻塞队列
     * @param notify        通知接口
     */
    public CustomThreadPool(int miniSize, int maxSize, long keepAliveTime,
                            TimeUnit unit, BlockingQueue<Runnable> workQueue, Notify notify) {
        this.miniSize = miniSize;
        this.maxSize = maxSize;
        this.keepAliveTime = keepAliveTime;
        this.unit = unit;
        this.workQueue = workQueue;
        //线程池执行过程中调用通知接口,通知外部应用线程池工作中
        this.notify = notify;

        workers = new ConcurrentHashSet<>();
    }

    /**
     * 有返回值
     *
     * @param callable  Callable
     * @return Future
     */
    public <T> Future<T> submit(Callable<T> callable) {
        FutureTask<T> future = new FutureTask(callable);
        execute(future);
        return future;
    }

    /**
     * 执行任务
     * @param runnable 需要执行的任务
     */
    public void execute(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("runnable nullPointerException");
        }
        if (isShutDown.get()) {
            LOGGER.info("线程池已经关闭，不能再提交任务！");
            return;
        }
        //提交的线程 计数
        totalTask.incrementAndGet();

        //小于最小线程数时新建线程
        //判断线程池大小时是否需处理并发
        if (workers.size() < miniSize) {
            // 双重监测是否超过最大线程数
            synchronized (this) {
                if (workers.size() < maxSize) {
                    addWorker(runnable);
                    return;
                }
            }
        }
        //将任务放入队列（offer方法不等待，空间不足则返回false）
        boolean offer = workQueue.offer(runnable);
        //写入队列失败，说明队列空间不足
        if (!offer) {
            //创建新的线程执行
            if (workers.size() < maxSize) {
                //新线程立即执行当前任务
                addWorker(runnable);
                return;
            } else {
                LOGGER.error("超过最大线程数,当前线程等待");
                try {
                    //等待队列空闲 此时外部线程会阻塞
                    workQueue.put(runnable);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    /**
     * 添加任务，需要加锁
     * @param runnable 任务
     */
    private synchronized void  addWorker(Runnable runnable) {
        Worker worker = new Worker(runnable, true);
        worker.startTask();
        workers.add(worker);
    }

    /**
     * 从队列中获取任务
     *
     * @return Runnable
     */
    private Runnable getTask() {
        //关闭标识及任务是否全部完成
        if (isShutDown.get() && totalTask.get() == 0) {
            return null;
        }
        lock.lock();
        try {
            Runnable task = null;
            if (workers.size() > miniSize) {
                //大于核心线程数时需要用保活时间获取任务
                task = workQueue.poll(keepAliveTime, unit);
            } else {
                task = workQueue.take();
            }

            if (task != null) {
                return task;
            }
        } catch (InterruptedException e) {
            return null;
        } finally {
            lock.unlock();
        }
        return null;
        //}
    }

    /**
     * 任务执行完毕后关闭线程池
     */
    public void shutdown() {
        isShutDown.set(true);
        tryClose(true);
    }

    /**
     * 立即关闭线程池，会造成任务丢失
     */
    public void shutDownNow() {
        isShutDown.set(true);
        tryClose(false);

    }

    /**
     * 阻塞等到任务执行完毕
     */
    public void mainNotify() {
        synchronized (shutDownNotify) {
            while (totalTask.get() > 0) {
                try {
                    shutDownNotify.wait();
                    if (notify != null) {
                        notify.notifyListen();
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    /**
     * 关闭线程池
     *
     * @param isTry true 尝试关闭      --> 会等待所有任务执行完毕
     *              false 立即关闭线程池--> 任务有丢失的可能
     */
    private void tryClose(boolean isTry) {
        if (!isTry) {
            closeAllTask();
        } else {
            if (isShutDown.get() && totalTask.get() == 0) {
                closeAllTask();
            }
        }

    }

    /**
     * 关闭所有任务
     */
    private void closeAllTask() {

        /*for (Worker worker : workers) {
            //LOGGER.info("开始关闭");
            worker.close();
        }*/
        for (Iterator<Worker> iterator = workers.iterator(); iterator.hasNext(); ) {
            Worker worker =  iterator.next();
            //关闭 worker ,中断worker线程
            worker.close();
            //将worker对象移除队列 以便gc回收对象
            workers.remove(worker);
        }
    }

    /**
     * 获取工作线程数量
     *
     * @return 工作线程数量
     */
    public int getWorkerCount() {
        return workers.size();
    }

    /**
     * 内部存放工作线程容器，并发安全。
     *
     * @param <T>
     */
    private final class ConcurrentHashSet<T> extends AbstractSet<T> {

        private ConcurrentHashMap<T, Object> map = new ConcurrentHashMap<>();
        private final Object PRESENT = new Object();

        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Iterator<T> iterator() {
            return map.keySet().iterator();
        }

        @Override
        public boolean add(T t) {
            count.incrementAndGet();
            return map.put(t, PRESENT) == null;
        }

        @Override
        public boolean remove(Object o) {
            count.decrementAndGet();
            return map.remove(o) == PRESENT;
        }

        @Override
        public int size() {
            return count.get();
        }
    }

    /**
     * 工作线程
     */
    private final class Worker extends Thread {

        private Runnable task;

        private Thread thread;
        /**
         * true --> 创建新的线程执行
         * false --> 从队列里获取线程执行
         */
        private boolean isNewTask;

        public Worker(Runnable task, boolean isNewTask) {
            this.task = task;
            this.isNewTask = isNewTask;
            // worker线程为本身
            thread = this;
        }

        void startTask() {
            thread.start();
        }

        void close() {
            thread.interrupt();
        }

        @Override
        public void run() {
            Runnable task = null;

            if (isNewTask) {
                task = this.task;
            }

            boolean workerIsAlive = true;
            try {
                while ((task != null || (task = getTask()) != null)) {
                    try {
                        //执行任务
                        task.run();
                    } catch (Exception e) {
                        workerIsAlive = false;
                        throw e;
                    } finally {
                        //任务执行完毕
                        task = null;
                        int number = totalTask.decrementAndGet();
                        //LOGGER.info("number={}",number);
                        if (number == 0) {
                            synchronized (shutDownNotify) {
                                shutDownNotify.notify();
                            }
                        }
                    }
                }
            } finally {
                // 工作线程数超过最低线程数，释放线程
                if(miniSize < workers.size()) {
                    workers.remove(this);
                }
                //LOGGER.info("remove={},size={}", remove, workers.size());
                //当前worker线程发生异常，新建一个worker
                if (!workerIsAlive) {
                    addWorker(null);
                }
                tryClose(true);
            }
        }
    }

}

