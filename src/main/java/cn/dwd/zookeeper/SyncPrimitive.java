package cn.dwd.zookeeper;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;


/**
 * <p>
 * <b>类描述： </b>
 * </p>
 * <p>
 * 同步的基类
 * </p>
 * <p>
 * <b>创建人：</b>duanweidong
 * </p>
 * <p>
 * <b>创建时间：</b>2016年8月3日
 * </p>
 * <p>
 * 
 * @version 1.0
 *          </p>
 */
public class SyncPrimitive implements Watcher {

	Integer mutex;

	ZooKeeper zk;

	String root;

	/**
	 * watch 监听的事件
	 */
	synchronized public void process(WatchedEvent event) {
		if(event.getPath()==null){
			System.out.println("hahha");
			return;
		}
		// 只要触发了事件，就唤醒一个阻塞的进程
		synchronized (mutex) {
			System.out.println("======mutext");
			mutex.notify();
		}
	}

	public SyncPrimitive(String address) {
		if (zk == null) {
			try {
				System.out.println("starting zk:");
				zk = new ZooKeeper(address, 3000, this);
				mutex = new Integer(-1);
				System.out.println("finish zk");
			} catch (IOException e) {
				System.out.println(e.toString());
				zk = null;
			}
		}
	}

	static public class Barrier extends SyncPrimitive {

		private String name;

		private int size;
		
		private String path;

		public Barrier(String address, String root, int size) {
			super(address);
			this.root = root;
			this.size = size;

			// create barrier node
			if (zk != null) {
				try {
					Stat s = zk.exists(root, false);
					if (s == null) {
						zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					}
				} catch (KeeperException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//my node name
			name = "name"+(new Random().nextInt(1000));
			
		}
		
		boolean enter() throws KeeperException, InterruptedException {
			String a = zk.create(root+"/"+name, "aaa".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			this.path = a;
			while(true){
				synchronized(mutex){
					List<String> list = zk.getChildren(root, true);
					if(list.size()<size){
						mutex.wait();
					}else{
						return true;
					}
				}
			}
		}

		boolean leave() throws InterruptedException, KeeperException{
			zk.delete(this.path, 0);
			while(true){
				synchronized(mutex){
					List<String> list = zk.getChildren(root, true);
					if(list.size()>0){
						mutex.wait();
					}else{
						return true;
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Barrier b = new Barrier("192.168.197.129:2181","/barrier",3);
		try {
			boolean flag = b.enter();
			System.out.println("entered barrier:"+3);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 Random rand = new Random();
	        int r = rand.nextInt(100);
	        // Loop for rand iterations
	        for (int i = 0; i < r; i++) {
	            try {
	                Thread.sleep(100);
	            } catch (InterruptedException e) {

	            }
	        }
	        try{
	            b.leave();
	        } catch (KeeperException e){
	        	e.printStackTrace();
	        } catch (InterruptedException e){
	        	e.printStackTrace();
	        }
	        System.out.println("Left barrier");
	}

}
