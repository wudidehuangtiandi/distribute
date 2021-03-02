import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * 测试监听器配置
 *
 * @author GC
 * @date 2020年 02月03日 18:36:07
 */
public class test implements Watcher {

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("果然是这样，实际使用将这个配置到ioc初始化zookeeper实例，此处就写实现即可");
    }
}
