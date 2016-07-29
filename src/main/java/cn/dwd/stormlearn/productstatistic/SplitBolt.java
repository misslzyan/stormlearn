package cn.dwd.stormlearn.productstatistic;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class SplitBolt implements IRichBolt {

	private OutputCollector collector;
	
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("user","area"));
		
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		
	}

	public void execute(Tuple input) {
		String value = input.getString(0);
		
		String[] values = value.split(":");
		collector.emit(new Values(values[0],values[1]));
		collector.ack(input);
	}

	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	
}
