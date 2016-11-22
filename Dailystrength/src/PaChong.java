

import java.awt.print.Printable;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sun.util.logging.resources.logging;

import com.sun.org.apache.xpath.internal.operations.Div;

public class PaChong {

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        char page='a';
        String url_str = "https://www.dailystrength.org/groups?all=true&letter=z";
        String charset = "utf-8";
        String filepath = "/home/tianhao/study/研究/groupname.txt";
        
        HttpClient hc = new DefaultHttpClient();
        HttpGet hg = new HttpGet(url_str);
        HttpResponse response = hc.execute(hg);
        HttpEntity entity = response.getEntity();
        
        
        InputStream htm_in = null;
        
        if(entity != null){
            htm_in = entity.getContent();
           
            
            String htm_str = InputStream2String(htm_in,charset);
            Document  doc =  Jsoup.parse(htm_str);
			//Elements links= doc.select("div[class=left-sidebar-layout__container]").select("div[class=left-sidebar-layout__content  ]").select("div[class= featured-groups-index  ]").select("div[class=index-list]").select("div[class=index-list__item]");
            Elements links= doc.select("div[class=index-list]").select("div[class=index-list__item]");
			for (Element link : links) {
				Elements lin = link.select("h3[class=index-list__title]").select("a");  
				String re_url = lin.attr("href");
			    System.out.println(re_url);
			    saveHtml(filepath, re_url);
			}
        }
        
    }
    
    public static void saveHtml(String filepath, String str){
        
        try {
            OutputStreamWriter outs = new OutputStreamWriter(new FileOutputStream(filepath, true), "utf-8");
            outs.write("http://www.dailystrength.org"+str+"\r\n");
            outs.close();
        } catch (IOException e) {
            System.out.println("Error at save html...");
            System.out.println(str);
            e.printStackTrace();
        }
    }

    public static String InputStream2String(InputStream in_st,String charset) throws IOException{
        BufferedReader buff = new BufferedReader(new InputStreamReader(in_st, charset));
        StringBuffer res = new StringBuffer();
        String line = "";
        while((line = buff.readLine()) != null){
        	res.append(line);
        }
        return res.toString();
    }
}