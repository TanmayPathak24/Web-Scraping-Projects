/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author DELL
 */
public class NdtvIndiaNewsFeed {

    public static String getInnerData(String link) throws IOException {
        String out = "";
        Document doc = Jsoup.connect(link).get();
        Element body = doc.selectFirst("body");
        if (body.html().toLowerCase().contains("class=\"ins_storybody\"")) {
            Element ins_storybody = body.selectFirst("div.ins_storybody");
            if (ins_storybody.html().toLowerCase().contains("<p>")) {
                Elements para = ins_storybody.select("p");
                for (Element p : para) {
                    out += p.text() + "\n";
                }
                return out;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static ArrayList<JSONObject> getfeed() {
        try {
            ArrayList<JSONObject> returnlist = new ArrayList<JSONObject>();
            // Fetching the whole page
            Document document = Jsoup.connect("https://www.ndtv.com/").get();
            // feting the main content
            Element body = document.selectFirst("body");
            Element midcontent = body.selectFirst("div#midcontent");
            Element newcont1 = midcontent.selectFirst("div.newcont1");
            Element newcont2 = newcont1.selectFirst("div.newcont2");
            Element newcont3 = newcont2.selectFirst("div.newcont3");
            Element row = newcont3.selectFirst("div.row");
            Element hpage_topsty_wrap = row.selectFirst("div.hpage_topsty_wrap");
            Element hmpage_lhs = hpage_topsty_wrap.selectFirst("div.hmpage_lhs");

            {
                // fetching the first news heading
                if (hmpage_lhs.html().toLowerCase().contains("div.cont_cmn")) {
                    Element cont_cmn = hmpage_lhs.selectFirst("div.cont_cmn");
                    Element h1 = cont_cmn.selectFirst("h1");
                    Element anchor = h1.selectFirst("a");

                    // putting the title of the news
                    String key = "TITLE";
                    String value = anchor.text();
                    JSONObject obj = new JSONObject();
                    obj.putOpt(key, value);

                    String out = NdtvIndiaNewsFeed.getInnerData(anchor.attr("href"));
                    key = "CONTENT";
                    value = out;
                    obj.putOnce(key, value);

                    returnlist.add(obj);
                }

            }
            {
                // fetching second news
                Element lhs_col_one = hmpage_lhs.selectFirst("div.lhs_col_one");
                Element top_stories_68 = lhs_col_one.selectFirst("div.top-stories-68");

                {
                    // fetching the top news headings
                    Element featured_cont = top_stories_68.selectFirst("div.featured_cont");
                    Element ul = featured_cont.selectFirst("ul");
                    Elements li = ul.select("li");
                    for (Element l : li) {
                        Element h2 = l.selectFirst("h2");
                        Element anchor = h2.selectFirst("a.item-title");

                        if (anchor.attr("href").substring(0, 20).equalsIgnoreCase("https://www.ndtv.com")) {
                            // putting the title of the news

                            String out = NdtvIndiaNewsFeed.getInnerData(anchor.attr("href"));
                            if (!out.equalsIgnoreCase("")) {
                                String key = "TITLE";
                                String value = anchor.text();
                                JSONObject obj = new JSONObject();
                                obj.putOpt(key, value);

                                key = "CONTENT";
                                value = out;
                                obj.putOpt(key, value);

                                returnlist.add(obj);
                            }
                        }

                    }
                }
            }

            return returnlist;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
