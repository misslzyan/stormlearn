package cn.dwd.stormlearn;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class WordReader implements IRichSpout{
	
	private boolean completed = false;
	
	private SpoutOutputCollector collector;
	
	private TopologyContext context ;

	/**
		* <b>描述：</b> <p></p>0<br>
		* 	
		 */
	private static final long serialVersionUID = -8016028106569158071L;
	
	
	

	/**
	 * 
	 */
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("line"));
		
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 初始化的时候调用
	 */
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.context = context;
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

	public void nextTuple() {
		System.out.println("=WordReader");
		System.out.println("=WordReader");
		System.out.println("=WordReader");
		System.out.println("=WordReader");
		System.out.println("=WordReader");
		System.out.println("=WordReader");
		System.out.println("=WordReader");
		System.out.println("=WordReader");
		System.out.println("=WordReader");
		System.out.println("=WordReader");
		System.out.println("=WordReader");
		System.out.println("=WordReader");
		System.out.println("=WordReader");
		if(completed){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
			}
			return;
		}
		String str = "wo shi xiao xiao niao a b c d a s d";
		collector.emit(new Values(str),str);
		completed = true;
	}

	public void ack(Object msgId) {
		// TODO Auto-generated method stub
		
	}

	public void fail(Object msgId) {
		// TODO Auto-generated method stub
		
	}

}
