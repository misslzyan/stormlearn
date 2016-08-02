package cn.dwd.stormlearn;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.tuple.Fields;

public class TopologyMain {

	public static void main(String[] args) throws InterruptedException, AlreadyAliveException, InvalidTopologyException, AuthorizationException {
		TopologyBuilder builder  = new TopologyBuilder();
		builder.setSpout("word-reader", new WordReader());
		builder.setBolt("word-normalizer", new WordNormalizer(),2)
		.shuffleGrouping("word-reader");
		builder.setBolt("word-counter", new WordCounter(),4).fieldsGrouping("word-normalizer", new Fields("word"));
		builder.setBolt("word-combine", new WordCombine()).shuffleGrouping("word-counter");
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
		
//		StormSubmitter.submitTopology("Getting-startted-topologydwd", conf, builder.createTopology());
//		
//		TridentTopology topology = new TridentTopology();
//		TridentState urlToTweeters =
//			       topology.newStaticState(getUrlToTweetersState());
//			TridentState tweetersToFollowers =
//			       topology.newStaticState(getTweeterToFollowersState());
//
//			topology.newDRPCStream("reach")
//			       .stateQuery(urlToTweeters, new Fields("args"), new MapGet(), new Fields("tweeters"))
//			       .each(new Fields("tweeters"), new ExpandList(), new Fields("tweeter"))
//			       .shuffle()
//			       .stateQuery(tweetersToFollowers, new Fields("tweeter"), new MapGet(), new Fields("followers"))
//			       .parallelismHint(200)
//			       .each(new Fields("followers"), new ExpandList(), new Fields("follower"))
//			       .groupBy(new Fields("follower"))
//			       .aggregate(new One(), new Fields("one"))
//			       .parallelismHint(20)
//			       .aggregate(new Count(), new Fields("reach"));
	}
}
