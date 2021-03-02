import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 注册监听服务
 *
 * @author GC
 * @date 2020年 02月03日 20:09:45
 */
public class DistributeClient {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        DistributeClient distributeClient = new DistributeClient();

        //获取链接
        distributeClient.getConnect();

        //获取信息,每当节点内容改变则会触发
        distributeClient.getChildren();

        //业务逻辑,可以放到获取信息里面，这里让它 注意这里,线程睡眠20秒,因为是创建的临时节点,如果不睡眠,你不能使用命令在控制台看见创建的临时节点
        distributeClient.handle();
    }

    private void getChildren() throws KeeperException, InterruptedException, IOException {
        ArrayList<String> hosts = new ArrayList<>();
        List<String> servers = zkclient.getChildren("/node1", true);
        for (String server : servers) {
            byte[] data = zkclient.getData("/node1/" + server, false, null);
            hosts.add(new String(data));
        }
        System.out.println(hosts);
        //重复调用达到监听效果
        Thread.sleep(1000);
        getConnect();
    }

    private void handle() throws InterruptedException {
        Thread.sleep(Integer.MAX_VALUE);
    }

    //,127.0.0.1:2182,127.0.0.1:2183
    private String connecterString = "127.0.0.1:2181";//假集群改变端口，真集群改变IP
    private int sessionTimeout = 2000;//2秒超时
    private ZooKeeper zkclient;//客户端对象




    private void getConnect() throws IOException {
        zkclient = new ZooKeeper(connecterString, sessionTimeout, new Watcher() {
            //会不断回调，应为getChildren中第二参数为true,相当于调用监听器，监听器中的执行又调用了监听器所以会不断循环。
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    getChildren();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
