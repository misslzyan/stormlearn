package cn.dwd.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.alibaba.fastjson.JSONObject;

/**
 * <p><b>类描述： </b></p><p>zookeeper测试</p>    
 * <p><b>创建人：</b>duanweidong </p>       
 * <p><b>创建时间：</b>2016年8月2日</p>      
 * <p>@version  1.0  </p>
 */
public class ZookeeperTest {
	
	public static CountDownLatch latch = new CountDownLatch(1);

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		final ZooKeeper zk = new ZooKeeper("192.168.197.129:2181",3000,new Watcher(){

			public void process(WatchedEvent event) {
				if(event.getPath()!=null){
					
				}
				if(event.getState() == KeeperState.SyncConnected){
					ZookeeperTest.latch.countDown();
				}
			}});
		latch.await();
		Stat stat = new Stat();
		JSONObject json = new JSONObject();
		json.put("id", 2);
		json.put("name", "zhangsna");
		zk.create("/zk3", json.toJSONString().getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		byte[] result = zk.getData("/zk3", true, stat);
		System.out.println(new String(result));
		while(true){
			Thread.sleep(5000);
		}
	}
}
