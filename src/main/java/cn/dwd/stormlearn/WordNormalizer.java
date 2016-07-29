package cn.dwd.stormlearn;

import java.util.ArrayList;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class WordNormalizer implements IRichBolt {

	/**
		* <b>描述：</b> <p></p>0<br>
		* 	
		 */
	private static final long serialVersionUID = 4904064448163984145L;
	private OutputCollector collector;
	
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		
	}

	public void execute(Tuple input) {
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		System.out.println("=WordNormalizer");
		String sentence = input.getString(0);
		String[] words = sentence.split(" ");
		for(String word : words){
			word = word.trim();
			if(!word.isEmpty()){
				word= word.toLowerCase();
				ArrayList a = new ArrayList();
				a.add(input);
				collector.emit(a, new Values(word));
			}
		}
		collector.ack(input);
		
	}

	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
		
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
