package com.yangyun.zk.confcenter;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName DefaultWatcher
 * @Description:
 * @Author 86155
 * @Date 2020/3/10 22:45
 * @Version 1.0
 **/
public class DefaultWatcher implements Watcher {

    private CountDownLatch cl;

    public DefaultWatcher (CountDownLatch cl){
        this.cl = cl;
    }

    @Override
    public void process(WatchedEvent event) {
        switch (event.getState()) {
            case Unknown:
                break;
            case Disconnected:
                break;
            case NoSyncConnected:
                break;
            case SyncConnected:
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

    }
}
