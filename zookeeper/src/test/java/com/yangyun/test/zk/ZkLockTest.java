package com.yangyun.test.zk;

import com.yangyun.zk.confcenter.ZkUtils;
import com.yangyun.zk.lock.WatcherCallBack;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @ClassName ZkLockTest
 * @Description:
 * @Author 86155
 * @Date 2020/3/12 9:30
 * @Version 1.0
 **/
public class ZkLockTest {

    private ZooKeeper zk = null;


    @Before
    public void befor (){
        zk = ZkUtils.getZk("lock");
    }

    @After
    public void after (){
        if (zk != null){
            try {
                zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testLock (){
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                String threadName = Thread.currentThread().getName();
                WatcherCallBack watcher = new WatcherCallBack(zk, threadName);

                // 抢锁
                watcher.tyLock();


                // 业务代码
                System.out.println(threadName + "is working ");

                // 释放锁
                watcher.unlock();

            }).start();
        }

        while (true);
    }
}
