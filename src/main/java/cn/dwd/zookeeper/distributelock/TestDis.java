package cn.dwd.zookeeper.distributelock;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

/**
 * <p><b>类描述： </b></p><p>测试分布式锁</p>    
 * <p><b>创建人：</b>duanweidong </p>       
 * <p><b>创建时间：</b>2016年8月3日</p>      
 * <p>@version  1.0  </p>
 */
public class TestDis {

	public static void main(String[] args) throws IOException, InterruptedException  {
		
		final CountDownLatch l1 = new CountDownLatch(1);
		final CountDownLatch l2 = new CountDownLatch(1);
		final CountDownLatch l3 = new CountDownLatch(1);
		
		final ZooKeeper zk1 = new ZooKeeper("192.168.197.129:2181", 3000, new Watcher(){

			public void process(WatchedEvent event) {
				System.out.println("ok zk");
				l1.countDown();
			}
			
		});
		final ZooKeeper zk2 = new ZooKeeper("192.168.197.129:2181", 3000, new Watcher(){
			
			public void process(WatchedEvent event) {
				System.out.println("ok zk");
				l2.countDown();
			}
			
		});
		final ZooKeeper zk3 = new ZooKeeper("192.168.197.129:2181", 3000, new Watcher(){
			
			public void process(WatchedEvent event) {
				System.out.println("ok zk");
				l3.countDown();
			}
			
		});
		l1.await();
		l2.await();
		l3.await();
		final DistributeLock dl1 = new DistributeLock(zk1);
		final DistributeLock dl2 = new DistributeLock(zk2);
		final DistributeLock dl3 = new DistributeLock(zk3);
		
		//C:\Users\bjduanweidong\Desktop
		Thread t1 = new Thread(new Runnable(){

			public void run() {
				while(true){
					String nodePath = null;
					try {
						nodePath = dl1.getLock( "/filelock");
						TestDis.incrFile();
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						try {
							dl1.removeLock(nodePath);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (KeeperException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
		});
		Thread t2 = new Thread(new Runnable(){

			public void run() {
				while(true){
					String nodePath = null;
					try {
						nodePath = dl2.getLock( "/filelock");
						TestDis.incrFile();
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						try {
							dl2.removeLock(nodePath);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (KeeperException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
		});
		Thread t3 = new Thread(new Runnable(){

			public void run() {
				while(true){
					String nodePath = null;
					try {
						nodePath = dl3.getLock( "/filelock");
						TestDis.incrFile();
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						try {
							dl3.removeLock(nodePath);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (KeeperException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
		});
		t1.start();
		t2.start();
		t3.start();
	}
	
	public static void incrFile()throws IOException{
		String path = "C:\\Users\\bjduanweidong\\Desktop\\1.txt";
		FileInputStream fin = new FileInputStream(path);
		InputStreamReader isr = new InputStreamReader(fin,"GBK");
		BufferedReader br = new BufferedReader(isr);
		String line ;
		String data = null;
		while((line=br.readLine())!=null){
			data = line;
		}
		int num = Integer.parseInt(data);
		num++;
		br.close();
		FileOutputStream fout = new FileOutputStream(path,true);
		OutputStreamWriter osw = new OutputStreamWriter(fout);
		PrintWriter pw = new PrintWriter(osw);
		pw.println(num);
		pw.close();
		System.out.println("ok file");
	}
}
