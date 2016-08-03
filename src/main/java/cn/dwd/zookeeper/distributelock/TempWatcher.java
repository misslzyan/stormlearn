package cn.dwd.zookeeper.distributelock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

public class TempWatcher implements Watcher {

	private Object lock;

	public TempWatcher() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TempWatcher(Object lock) {
		super();
		this.lock = lock;
	}

	public void process(WatchedEvent event) {
//		System.out.println("notify begin");
//		synchronized(lock){
//			lock.notify();
//		}
//		System.out.println("notify end");
	}

}
