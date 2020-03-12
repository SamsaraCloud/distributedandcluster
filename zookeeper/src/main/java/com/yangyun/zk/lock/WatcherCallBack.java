package com.yangyun.zk.lock;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName WatcherCallBack
 * @Description:
 * @Author 86155
 * @Date 2020/3/12 9:27
 * @Version 1.0
 **/
public class WatcherCallBack implements Watcher, AsyncCallback.StatCallback, AsyncCallback.StringCallback, AsyncCallback.Children2Callback {

    ZooKeeper zk;
    String threadName;
    CountDownLatch cc = new CountDownLatch(1);

    public WatcherCallBack (ZooKeeper zk, String threadName){
        this.zk = zk;
        this.threadName = threadName;
    }


    /**
     * @Author yangyun
     * @Description:  获取锁
     * @Date 2020/3/12 9:29
     * @Param
     * @returnm
     **/
    public void tyLock (){
        // 首先先判断是否被当前线程持有锁，如果是，直接返回，不是创建节点，等待获取锁
//        zk.getData();

        // 没有节点，创建排队等候锁
        System.out.println(threadName + " create >>>>>>>>>>>>");
        zk.create("/lock", threadName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,
                this, threadName);

        try {
            cc.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author yangyun
     * @Description:  释放锁
     * @Date 2020/3/12 9:29
     * @Param []
     * @returnm void
     **/
    public void unlock(){

    }

    @Override
    public void processResult(int i, String s, Object o, List<String> list, Stat stat) {
        // 2、对所有子目录排序，序列号小的先执行
        // 所有子目录
    }

    @Override
    public void processResult(int i, String s, Object o, Stat stat) {

    }

    @Override
    public void process(WatchedEvent watchedEvent) {

    }

    @Override
    public void processResult(int i, String path, Object o, String name) {
        // 1、create success
        if (StringUtils.isNotEmpty(name)){
            System.out.println(threadName + " create node " + name);

            // 获取当前目录下所有子目录，执行回调
            zk.getChildren("/", false,this, "abc");
        }
    }
}
