package cn.dwd.stormlearn;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class TopologyMain {

	public static void main(String[] args) throws InterruptedException, AlreadyAliveException, InvalidTopologyException, AuthorizationException {
		TopologyBuilder builder  = new TopologyBuilder();
		builder.setSpout("word-reader", new WordReader());
		builder.setBolt("word-normalizer", new WordNormalizer())
		.shuffleGrouping("word-reader");
		builder.setBolt("word-counter", new WordCounter()).fieldsGrouping("word-normalizer", new Fields("word"));
		
		//configuration
		Config conf  = new Config();
		conf.put("wordsFile","a");
		conf.setDebug(true);
		//topology run
		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
		
		
//		LocalCluster cluster = new LocalCluster();
//		cluster.submitTopology("Getting-startted-topology", conf, builder.createTopology());
//		Thread.sleep(3000);
//		cluster.killTopology("Getting-startted-topology");
//		cluster.shutdown();//guanbi
		
		StormSubmitter.submitTopology("Getting-startted-topologydwd", conf, builder.createTopology());
		
	}
}
