package cn.dwd.zookeeper.distributelock;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class TestResult {

	public static void main(String[] args) throws IOException {
		String path = "C:\\Users\\bjduanweidong\\Desktop\\1.txt";
		FileInputStream fin = new FileInputStream(path);
		InputStreamReader isr = new InputStreamReader(fin, "GBK");
		BufferedReader br = new BufferedReader(isr);
		String line;
		String data = null;
		while ((line = br.readLine()) != null) {
			if (data != null && line != null) {
				if((Long.parseLong(data)+1)!=Long.parseLong(line)){
					System.out.println("error");
				}else{
				}
			}
			data = line;
		}
	}
}
