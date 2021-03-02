import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * 注册过程
 *
 * @author GC
 * @date 2020年 02月03日 19:37:46
 */
public class DistributeServer {

    public DistributeServer() throws KeeperException, InterruptedException {
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        DistributeServer distributeServer = new DistributeServer();

        //获取链接
        distributeServer.getConnect();

        //注册服务
        distributeServer.regist("first server");

        //distributeServer.setData();

        //业务逻辑  注意这里,线程睡眠20秒,因为是创建的临时节点,如果不睡眠,你不能使用命令在控制台看见创建的临时节点
        distributeServer.handle();
    }

    private void handle() throws InterruptedException {
        Thread.sleep(Integer.MAX_VALUE);
    }

    //127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183
    private String connecterString = "127.0.0.1:2181";//假集群改变端口，真集群改变IP
    private int sessionTimeout = 2000;//2秒超时
    private ZooKeeper zkclient;//客户端对象

    private void regist(String hostname) throws IOException, KeeperException, InterruptedException {

        //无论是同步还是异步接口，ZooKeeper都不支持递归创建，即无法在父节点不存在的情况下创建一个子节点。
        //短暂的要保持程序激活状态不然会清空该节点
        /* CreateMode:
         *     PERSISTENT (持续的，相对于EPHEMERAL，不会随着client的断开而消失)
         *     PERSISTENT_SEQUENTIAL（持久的且带顺序的）
         *     EPHEMERAL (短暂的，生命周期依赖于client session)
         *     EPHEMERAL_SEQUENTIAL  (短暂的，带顺序的)*/
        //String s = zkclient.create("/node1", hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        String s2 = zkclient.create("/node1/lalala", hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostname+"is online");
    }

    private void getConnect() throws IOException {
        zkclient = new ZooKeeper(connecterString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

//    private void setData() throws KeeperException, InterruptedException {
//        //一个同步一个异步，异步的带回调会把在回调里获得所传的东西,-1匹配任何版本
//        zkclient.setData("/node1/lalala", "萨达萨达萨达".getBytes(), -1);
//    }


}
