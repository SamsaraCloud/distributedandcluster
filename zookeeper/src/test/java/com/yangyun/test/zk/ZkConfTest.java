package com.yangyun.test.zk;

import com.yangyun.zk.confcenter.WatcherCallBack;
import com.yangyun.zk.confcenter.ZkUtils;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @ClassName ZkConfTest
 * @Description:
 * @Author 86155
 * @Date 2020/3/10 22:51
 * @Version 1.0
 **/
public class ZkConfTest {

    private ZooKeeper zk = null;

    private WatcherCallBack watcher = null;

    @Before
    public void befor (){
        zk = ZkUtils.getZk();
        watcher = new WatcherCallBack(zk);
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
    public void testZookeeperConfCenter (){
        while (true){

            watcher.aWait();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
