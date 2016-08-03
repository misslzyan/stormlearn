package cn.dwd.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ExecuteWatcher implements Watcher {

	private ZooKeeper zk ;
	
	
	
	public ExecuteWatcher(ZooKeeper zk) {
		super();
		this.zk = zk;
	}

	


	public ExecuteWatcher() {
		super();
	}




	public void process(WatchedEvent event) {
		String path = event.getPath();
		if(path==null) return;
		byte[] result = null;
		try {
			result = zk.getData(path, this, new Stat());
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(new String(result));
		
	}
	
	
}
