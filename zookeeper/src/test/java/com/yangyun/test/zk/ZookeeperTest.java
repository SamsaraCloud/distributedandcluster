package com.yangyun.test.zk;

import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ZookeeperTest
 * @Description:
 * @Author yangyun
 * @Date 2020/3/10 0010 14:16
 * @Version 1.0
 **/
public class ZookeeperTest {

    private static String url = "47.107.172.70:2181,47.107.172.70:2182,47.107.172.70:2183,47.107.172.70:2184";

    @Before
    public void befor(){
        List list = new ArrayList();
        list.stream().forEach(
                s -> {
                    System.out.println(s);
                }
        );
    }

    @Test
    public void testConnectionZookeeper () throws Exception {

        CountDownLatch cl = new CountDownLatch(1);

        // zk只有session的概念，没有连接池的概念；
        // 新建 zookeeper 时，watch只和session有关，跟path和node没有关系
        ZooKeeper zk = new ZooKeeper(url, 3000, (event) -> {
            Watcher.Event.KeeperState state = event.getState();

            switch (state) {
                case Unknown:
                    break;
                case Disconnected:
                    break;
                case NoSyncConnected:
                    break;
                case SyncConnected:
                    System.out.println("connected....");
                    // 已经建立连接，执行后面的操作
                    cl.countDown();
                    break;
                case AuthFailed:
                    break;
                case ConnectedReadOnly:
                    break;
                case SaslAuthenticated:
                    break;
                case Expired:
                    break;
            }
        });

        // 如果在没有确定已经建立连接成功的情况下，后面的操作可能会报错
        cl.await();
        ZooKeeper.States state = zk.getState();
        switch (state) {
            case CONNECTING:

                break;
            case ASSOCIATING:
                break;
            case CONNECTED:
                System.out.println("zookeeper connected...");
                break;
            case CONNECTEDREADONLY:
                break;
            case CLOSED:
                break;
            case AUTH_FAILED:
                break;
            case NOT_CONNECTED:
                break;
        }

        // 同步操作，会在创建完，同步到超过半数节点后返回
        String s = zk.create("/xxoo", "sync message number one".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("EPHEMERAL PATH ：" + s);

        // 异步操作，

        System.out.println("async is start...");
        zk.create("/ooxx", "async message number one".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                new AsyncCallback.StringCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx, String name) {
                        System.out.println("rc=" + rc);
                        System.out.println("path=" + path);
                        System.out.println("ctx=" + ctx);
                        System.out.println("name=" + name);
                    }
                }, "ctx");
        System.out.println("async is over...");


        TimeUnit.SECONDS.sleep(2222222);
    }




}
