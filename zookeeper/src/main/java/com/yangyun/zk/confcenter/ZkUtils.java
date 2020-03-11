package com.yangyun.zk.confcenter;

import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName ZkUtils
 * @Description:
 * @Author 86155
 * @Date 2020/3/10 22:26
 * @Version 1.0
 **/
public class ZkUtils {

    // /testConf 指定自己的工作目录
    private static String url = "47.107.172.70:2181,47.107.172.70:2182,47.107.172.70:2183,47.107.172.70:2184/testConf";


    private static ZooKeeper zk;

    private static CountDownLatch cl = new CountDownLatch(1);
    private static DefaultWatcher watcher = new DefaultWatcher(cl);

    /**
     * @Author yangyun
     * @Description:  获取 zookeeper 对象
     * @Date 2020/3/10 22:40
     * @Param []
     * @returnm org.apache.zookeeper.ZooKeeper
     **/
    public static ZooKeeper getZk ()  {
        try {
            zk = new ZooKeeper(url, 1000, watcher);
            // 等待 client 连接上 zk
            cl.await();
        } catch (Exception e){

        }
        return zk;
    }
}
