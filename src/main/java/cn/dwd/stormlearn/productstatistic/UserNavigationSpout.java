package cn.dwd.stormlearn.productstatistic;

import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

/**
 * <p><b>类描述： </b></p><p>模拟一个用户点击某一个页面的某个位置</p>    
 * <p><b>创建人：</b>duanweidong </p>       
 * <p><b>创建时间：</b>2016年7月29日</p>      
 * <p>@version  1.0  </p>
 */
public class UserNavigationSpout implements IRichSpout {
	
	private String[] users ;
	
	private String[] areas;
	
	private SpoutOutputCollector collector;

	private void init(){
		System.out.println("init=============");
		String[] users = new String[10];
		for(int i = 0;i<users.length;i++){
			users[i]="user"+i;
			
		}
		this.users = users;
		
		String[] areas = new String[10];
		for(int i=0;i<areas.length;i++){
			areas[i] = "area"+i;
		}
		this.areas = areas;
	}
	
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		init();
		this.collector = collector;
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

	public void activate() {
		// TODO Auto-generated method stub
		
	}

	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 生产数据
	 */
	public void nextTuple() {

		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		
		
		
		sb.append(users[r.nextInt(10)]).append(":").append(areas[r.nextInt(10)]);
		
		collector.emit(new Values(sb.toString()));
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	public void ack(Object msgId) {
		System.out.println("UserNavigationSpout:ack:"+msgId);
	}

	public void fail(Object msgId) {
		System.out.println("UserNavigationSpout:fail:"+msgId);
		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("all"));
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
