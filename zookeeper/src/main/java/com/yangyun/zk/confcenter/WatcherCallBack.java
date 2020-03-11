package com.yangyun.zk.confcenter;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName WatcherCallBack
 * @Description:  在zk中，callback和watch都是在执行目标方法之后触发
 * @Author 86155
 * @Date 2020/3/10 22:55
 * @Version 1.0
 **/
public class WatcherCallBack implements Watcher, AsyncCallback.DataCallback, AsyncCallback.StatCallback {

    ZooKeeper zk;

    CountDownLatch cc = new CountDownLatch(1);

    public WatcherCallBack (ZooKeeper zk){
       this.zk = zk;
    }

    public void aWait(){
        // 1、从节点获取数据，判断是否存在节点，然后异步执行回调方法 StatCallback，watch 当前节点
        zk.exists("/AppConf", this, this, "abc");
        try {
            cc.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author yangyun
     * @Description: DataCallback.processResult
     * @Date 2020/3/11 20:02
     * @Param [i, s, o, bytes, stat]
     * @returnm void
     **/
    @Override
    public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
        System.out.println("DataCallback.processResult...");
        // 4、节点元数据不为空，然后解析数据
        if (stat != null){
            String data = new String(bytes);
            System.out.println("get data " + data);

            cc.countDown();
        }
    }

    /**
     * @Author yangyun
     * @Description: StatCallback.processResult
     * @Date 2020/3/11 20:01
     * @Param [i, s, o, stat]
     * @returnm void
     **/
    @Override
    public void processResult(int i, String s, Object o, Stat stat) {
        System.out.println("StatCallback.processResult...");
        ///2、如果节点元数据不为空，则存在节点
        if (stat != null){
            ///3、从节点获取数据，执行回调方法 DataCallback.processResult
            zk.getData("/AppConf", this, this, "abc");

        }
    }

    /**
     * @Author yangyun
     * @Description:  Watcher.process 当监控的节点产生事件
     * @Date 2020/3/11 20:02
     * @Param [watchedEvent]
     * @returnm void
     **/
    @Override
    public void process(WatchedEvent event) {
        System.out.println("Watcher.process...");
        switch (event.getType()) {
            case None:
                break;
            case NodeCreated:
                zk.getData("/AppConf", this, this, "abc");
                break;
            case NodeDeleted:
                break;
            case NodeDataChanged:
                zk.getData("/AppConf", this, this, "abc");
                break;
            case NodeChildrenChanged:
                break;
        }
    }
}
