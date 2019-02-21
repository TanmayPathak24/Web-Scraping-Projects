/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.JSONObject;

/**
 *
 * @author DELL
 */
public class TimesOfNow {

    public static ArrayList<JSONObject> fetchnews() throws IOException, JSONException {

        Document doc = Jsoup.connect("https://timesofindia.indiatimes.com/india").get();
        Element div1 = doc.selectFirst("div#c_02");
        Element div2 = div1.selectFirst("div#c_0201").selectFirst("div#c_wdt_list_1");
        Element ul1 = div2.selectFirst("ul.top-newslist");
        Elements an = ul1.select("li").select("span.w_tle").select("a[href]");

        ArrayList<JSONObject> returnlist = new ArrayList<JSONObject>();
        for (Element a : an) {
            JSONObject obj = new JSONObject();
            String key = "TITLE";
            String value = a.text();
            obj.putOpt(key, value);
            Document doc2 = Jsoup.connect("https://timesofindia.indiatimes.com" + a.attr("href")).get();
            Element div = doc2.selectFirst("div#container").selectFirst("div#content").selectFirst("div").selectFirst("div.articlepage").selectFirst("div.article-content-wrapper").selectFirst("div.main-content").selectFirst("div.article_content").selectFirst("arttextxml").selectFirst("div.section1").selectFirst("div.Normal");
            key = "CONTENT";
            value = div.text();
            obj.putOpt(key, value);
            returnlist.add(obj);
        }
        return returnlist;
    }
}
