package utils;

import org.jsoup.Jsoup;

/**
 * Created by akorovin on 26.02.2017.
 */
public final class HtmlHelper {
    public final static String htmlToText(String html) {
        return Jsoup.parse(html).text();
    }
}
