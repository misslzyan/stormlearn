package cn.dwd.stormlearn;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

public class WordCounter implements  IRichBolt{
	
	private OutputCollector collector;
	
	Map<String,Integer> counters;
	
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.counters = new HashMap<String,Integer>();
		this.collector = collector;
		
	}

	public void execute(Tuple input) {
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		System.out.println("=WordCounter");
		String str  = input.getString(0);
		if(!counters.containsKey(str)){
			counters.put(str, 1);
		}else{
			Integer c = counters.get(str)+1;
			counters.put(str,c);
		}
		System.out.println(counters);
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
		// TODO Auto-generated method stub
		
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
