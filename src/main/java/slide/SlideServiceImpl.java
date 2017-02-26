package slide;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.ConnectionHelper;
import utils.HtmlHelper;

import java.io.IOException;

/**
 * Created by akorovin on 26.02.2017.
 */
public class SlideServiceImpl implements SlideService {
    private final static String DECK_SERVICE_URL_BASE = "https://deckservice.experimental.slidewiki.org/";
    private final static String DECK_SERVICE_SLIDE = "slide/";
    private final static String DECK_SERVICE_DECK = "deck/";

    @Override
    public JSONObject getDeck(String deckId) {
        String urlString = DECK_SERVICE_URL_BASE + DECK_SERVICE_DECK + deckId;
        JSONObject result = null;
        try {
            result = ConnectionHelper.readJsonFromUrl(urlString);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not get data from slide url: " + urlString);
        }

        return result;
    }

    @Override
    public JSONObject getSlide(String slideId) {
        String urlString = DECK_SERVICE_URL_BASE + DECK_SERVICE_SLIDE + slideId;
        JSONObject result = null;
        try {
            result = ConnectionHelper.readJsonFromUrl(urlString);
        } catch (IOException e) {
            System.out.println("Could not get data from slide url: " + urlString);
        }

        return result;
    }

    @Override
    public String getDeckSlides(String deckId) {
        String deckSlideUrl = DECK_SERVICE_URL_BASE + DECK_SERVICE_DECK + deckId + "/slides";
        StringBuilder builder = new StringBuilder();
        JSONObject result = null;
        try {
            result = ConnectionHelper.readJsonFromUrl(deckSlideUrl);
            JSONArray arr = result.getJSONArray("children");
            for (int i = 0, size = arr.length(); i < size; i++)
            {
                JSONObject slide = arr.getJSONObject(i);
                String htmlContent = slide.getString("content");
                builder.append(HtmlHelper.htmlToText(htmlContent) + " ");
            }
        } catch (IOException e) {
            System.out.println("Could not get data from slide url: " + deckSlideUrl);
        }

        return builder.toString();
    }
}
