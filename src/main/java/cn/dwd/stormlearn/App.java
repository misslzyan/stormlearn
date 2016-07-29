package cn.dwd.stormlearn;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String body = "adsafd\\tadfafd";
        for(String t : body.split("\\\\t")){
        	System.out.println(t);
        }
    }
}
