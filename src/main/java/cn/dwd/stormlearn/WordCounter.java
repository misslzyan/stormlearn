package cn.dwd.stormlearn;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class WordCounter implements  IRichBolt{
	
	private OutputCollector collector;
	
	Map<String,Integer> counters;
	
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.counters = new HashMap<String,Integer>();
		this.collector = collector;
		
	}

	public void execute(Tuple input) {
		String str  = input.getString(0);
		if(!counters.containsKey(str)){
			counters.put(str, 1);
		}else{
			Integer c = counters.get(str)+1;
			counters.put(str,c);
		}
		for(Entry<String,Integer> entry: counters.entrySet()){
			collector.emit(input,new Values(entry.getKey(),entry.getValue()));
		}
		
		collector.ack(input);
		
	}

	public void cleanup() {
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println("========cleanup=====");
		System.out.println(counters);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word","num"));
		
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
