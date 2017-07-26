package netty;



import io.netty.util.HashedWheelTimer;
import org.apache.storm.shade.org.json.simple.JSONObject;
import org.asynchttpclient.*;
import org.asynchttpclient.channel.ChannelPool;
import org.asynchttpclient.netty.channel.DefaultChannelPool;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by bjduanweidong on 2017/6/30.
 */
public class TestAsynClient {

    private AsyncHttpClient client;

    @Before
    public void init(){
        client = new DefaultAsyncHttpClient();
    }

    @org.junit.Test
    public void testSimpleGet() throws ExecutionException, InterruptedException {
        Future<Response> f = client.prepareGet("http://www.example.com/").execute();
        Response r = f.get();
        String res = r.getResponseBody();
        System.out.println(res);
    }

    @Test
    public void testAsynGet() throws InterruptedException {
        client.prepareGet("http://www.example.com/").execute(new AsyncCompletionHandler<Response>() {
            @Override
            public Response onCompleted(Response response) throws Exception {
                System.out.println(response.getResponseBody());
                return response;
            }

            @Override
            public void onThrowable(Throwable t) {
                t.printStackTrace();
            }
        });
        //sleep for end
        Thread.sleep(10000);
    }

    @Test
    public void testSimplePost() throws ExecutionException, InterruptedException {
        Future<Response> f = client.preparePost("http://10.171.161.1:8080/datacenter/query")
                .setBody("select adPlanId, sum(costSum) as totalCost, sum(showCount) as showCount, sum(clickCount) as totalClick, div(totalClick, showCount, 0) as clickShowRate, div(totalCost, showCount, 0)  as averageClickCost, div(totalCost, totalClick, 0) as costClickRate, thb(totalCost,\"--\") as dailyChainStr from p4pData where (( logType = 1 and status = 0 ) or (logType=2 and status in (0,1) ) or (logType in (3,4,5,6,7) and (status = 0 or status=\"-\")) ) and adPlanId in ( 11931,11933,13958,13335,12701,11935,12145,6033,6034,6035,6036,6037,6038,6901,7491,6918,7798,7184,10049,6090,7855,7858,7220,7331,14415,7450,7926,7932,7935,8705,8270,10101,9227,9982,9510,11103,9601,10105,9049,12707,8415,8799,8800,8869,10515,9517,9208,11106,11895,11411,12590,11894,8864,8409,14416,14407 )   group by adPlanId interval( 1497888000000 , 1497974399999 ) GRANULARITY all")
                .execute();
        Response res = f.get();
        System.out.println(res.getResponseBody());
    }

    @Test
    public void testAsynPost() throws InterruptedException {
        client.preparePost("http://10.171.161.1:8080/datacenter/query")
                .setBody("select adPlanId, sum(costSum) as totalCost, sum(showCount) as showCount, sum(clickCount) as totalClick, div(totalClick, showCount, 0) as clickShowRate, div(totalCost, showCount, 0)  as averageClickCost, div(totalCost, totalClick, 0) as costClickRate, thb(totalCost,\"--\") as dailyChainStr from p4pData where (( logType = 1 and status = 0 ) or (logType=2 and status in (0,1) ) or (logType in (3,4,5,6,7) and (status = 0 or status=\"-\")) ) and adPlanId in ( 11931,11933,13958,13335,12701,11935,12145,6033,6034,6035,6036,6037,6038,6901,7491,6918,7798,7184,10049,6090,7855,7858,7220,7331,14415,7450,7926,7932,7935,8705,8270,10101,9227,9982,9510,11103,9601,10105,9049,12707,8415,8799,8800,8869,10515,9517,9208,11106,11895,11411,12590,11894,8864,8409,14416,14407 )   group by adPlanId interval( 1497888000000 , 1497974399999 ) GRANULARITY all")
                .execute(new  AsyncCompletionHandler<Response>(){
                    @Override
                    public Response onCompleted(Response response) throws Exception {
                        System.out.println(response.getResponseBody());
                        return  response;
                    }
                });
        Thread.sleep(10000);
    }

    @Test
    public void testAsynPool() throws InterruptedException {
        DefaultAsyncHttpClientConfig.Builder builder = new  DefaultAsyncHttpClientConfig.Builder();
        builder.setKeepAlive(true);
        builder.setChannelPool(new DefaultChannelPool(builder.build(),new HashedWheelTimer()));
        DefaultAsyncHttpClientConfig config = builder.build();
        AsyncHttpClient client = new DefaultAsyncHttpClient(config);
        client.preparePost("http://localhost:8080/datacenter/query")
                .setBody("select adPlanId,a sum(costSum) as totalCost, sum(showCount) as showCount, sum(clickCount) as totalClick, div(totalClick, showCount, 0) as clickShowRate, div(totalCost, showCount, 0)  as averageClickCost, div(totalCost, totalClick, 0) as costClickRate, thb(totalCost,\"--\") as dailyChainStr from p4pData where (( logType = 1 and status = 0 ) or (logType=2 and status in (0,1) ) or (logType in (3,4,5,6,7) and (status = 0 or status=\"-\")) ) and adPlanId in ( 11931,11933,13958,13335,12701,11935,12145,6033,6034,6035,6036,6037,6038,6901,7491,6918,7798,7184,10049,6090,7855,7858,7220,7331,14415,7450,7926,7932,7935,8705,8270,10101,9227,9982,9510,11103,9601,10105,9049,12707,8415,8799,8800,8869,10515,9517,9208,11106,11895,11411,12590,11894,8864,8409,14416,14407 )   group by adPlanId interval( 1497888000000 , 1497974399999 ) GRANULARITY all")
                .execute(new  AsyncCompletionHandler<Response>(){
                    @Override
                    public Response onCompleted(Response response) throws Exception {
                        System.out.println(response.getResponseBody());
                        System.out.println(response.getStatusCode());
                        return  response;
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        t.printStackTrace();
                    }
                });
        Thread.sleep(10000);
    }

    @Test
    public void testJson(){
        Map m = new HashMap<String,String>();
        m.put("a","ha\"aaa\"");
        System.out.println(m.get("a"));
        System.out.println(JSONObject.toJSONString(m));
    }

    class Student{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}


