package cn.dwd.zookeeper.distributelock;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * <p><b>类描述： </b></p><p>利用zookeeper实现分布式锁</p>    
 * <p><b>创建人：</b>duanweidong </p>       
 * <p><b>创建时间：</b>2016年8月3日</p>      
 * <p>@version  1.0  </p>
 */
public class DistributeLock {
	
	/**
	 * ZK
	 */
	private ZooKeeper zk ;
	
	
	
	public DistributeLock(){
		
	}


	public DistributeLock(ZooKeeper zk) {
		super();
		this.zk = zk;
	}
	
	/**
	 * <b>描述：</b> <p>获取锁</p><br>
	 * @return
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	public String getLock(String lockPath) throws KeeperException, InterruptedException{
		 Object tempLock = new Object();
		Stat s = zk.exists(lockPath, false);
		if(s==null){
			//锁不存在,create
			zk.create(lockPath, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		String tempPath = zk.create(lockPath+"/"+"disLock", new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		while(true){
			List<String> strList = zk.getChildren(lockPath, new TempWatcher(tempLock));
			if(strList.size()==0){
				throw new RuntimeException("zookeeper znode is delete");
			}
			String minStr = strList.get(0).substring(7);
			for(String tempStr: strList){
				String subStr = tempStr.substring(7);
				if(Long.parseLong(subStr)<Long.parseLong(minStr)){
					minStr = subStr;
				}
			}
			//比较当前znode是否是最小的znode
			int beginIndex = (lockPath+"/"+"disLock").length()+1;
			if(Long.parseLong(tempPath.substring(beginIndex))>Long.parseLong(minStr)){
				//当前点并不是最新的点
//				synchronized(tempLock){
//					tempLock.wait();//等待
//				}
				continue;
			}else{
				break;
			}
		}
		return tempPath;
		
	}
	
	public void removeLock(String nodePath) throws InterruptedException, KeeperException{
		//just delete
		if(nodePath==null){
			System.out.println("nodePath===="+nodePath);
			return;
		}
		zk.delete(nodePath, 0);
	}
	
}
