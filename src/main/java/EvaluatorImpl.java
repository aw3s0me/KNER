import components.NER;
import components.dbpedia.DBpediaNER;
import org.json.JSONObject;
import slide.SlideService;
import slide.SlideServiceImpl;

import static utils.ConnectionHelper.disableSSLCheck;

/**
 * Created by akorovin on 21.02.2017.
 */
public class EvaluatorImpl implements Evaluator {
    NER dbpedia = new DBpediaNER();
    SlideService slideService = new SlideServiceImpl();

    public EvaluatorImpl() {

    }

    @Override
    public void evaluate() {
        disableSSLCheck();
        JSONObject deck = slideService.getDeck("1");
        System.out.println(deck);

        //List<String> entities = dbpedia.getEntities(curSlide.getContent());
    }

    public static void main(String[] args) {
        Evaluator evaluator = new EvaluatorImpl();
        evaluator.evaluate();
    }
}
