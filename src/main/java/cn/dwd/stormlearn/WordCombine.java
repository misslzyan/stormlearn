package cn.dwd.stormlearn;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

/**
 * <p><b>类描述： </b></p><p> 合并汇总结果</p>    
 * <p><b>创建人：</b>duanweidong </p>       
 * <p><b>创建时间：</b>2016年8月1日</p>      
 * <p>@version  1.0  </p>
 */
public class WordCombine implements IRichBolt {
	
	private OutputCollector collector;
	
	private Map<String,Integer> comMap;

	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		comMap = new HashMap<String,Integer>();
	}

	public void execute(Tuple input) {
		String key = input.getString(0);
		Integer value = input.getInteger(1);
		comMap.put(key, value);
		System.out.println("combine");
		System.out.println(comMap);
		collector.ack(input);
		
	}

	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
