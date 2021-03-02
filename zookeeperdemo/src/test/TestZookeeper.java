import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author GC
 * @date 2020年 02月03日 15:46:27
 */
public class TestZookeeper {
    private String connecterString="127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";//假集群改变端口，真集群改变IP
    private int sessionTimeout=2000;//2秒超时
    private ZooKeeper zkclient;//客户端对象
    @Before
    public void init() throws IOException {

        //获取客户端对象
        zkclient= new ZooKeeper(connecterString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                //监听器具体实现，获取子节点并监听数据变化
                List<String> children = null;
                try {
                    children = zkclient.getChildren("/", true);
                } catch (KeeperException e) {
                    //处理异常
                } catch (InterruptedException e) {
                    //处理异常
                }
                System.out.println("------start监听得来------");
                for (String child : children) {
                    System.out.println(child);
                }
                System.out.println("------end监听得来------");
            }
        });
    }

    //创建一个节点,重复声明节点会报错
    @Test
    public void createNode() throws KeeperException, InterruptedException {
        String path = zkclient.create("/hahahaha", "lalalalala".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);//路径，内容，权限，创建模式（短暂还是持久带不带序号,列子是持久的不带序号的）
        System.out.println(path);
    }


    //获取子节点并选择是否监听数据变化
    @Test
    public void getdataandwatch() throws KeeperException, InterruptedException {
        //获取的节点，第二的参数为是否监听，具体的监听方式由zkclient对象创建时的第三个参数来实现
       //涉及一个回调机制，如果第二个参数为true,将会回调zkclient创建时第三个参数的实现类的process方法
        List<String> children = zkclient.getChildren("/", false);
        for (String child : children) {
            System.out.println(child);
        }
        System.out.println("----------以上来自子节点的获取------------");
        Thread.sleep(Integer.MAX_VALUE);
    }

    //判断节点是否存在
    @Test
    public void exist() throws KeeperException, InterruptedException {
        Stat exists = zkclient.exists("/hahaha", false);
        System.out.println(exists==null?"not exists":"exists");
    }

    //获取节点内容
    @Test
    public void getData() throws KeeperException, InterruptedException {
        //要先判断路径存在不（不监听的情况下）,也可以直接根据路径和监听器来获得,路径也可以null
        Stat exists = zkclient.exists("/hahaha", false);
        byte[] data = zkclient.getData("/hahaha", false, exists);
        String s = new String(data);
        System.out.println(s);
    }


}
