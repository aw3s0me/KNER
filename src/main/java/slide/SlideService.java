package slide;

import org.json.JSONObject;

/**
 * Created by akorovin on 26.02.2017.
 */
public interface SlideService {
    JSONObject getDeck(String deckId);
    JSONObject getSlide(String slideId);
}
