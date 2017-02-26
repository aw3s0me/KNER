import components.NER;
import components.dbpedia.DBpediaNER;
import slide.SlideService;
import slide.SlideServiceImpl;

import java.util.List;

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
        String allTextFromDeck = slideService.getDeckSlides("3");

        List<String> entities = dbpedia.getEntities(allTextFromDeck);

        for (String resource: entities) {
            System.out.println(resource);
        }

        // run rml transormer
        // be.ugent.mmlab.rml.main.Main.main('ttt');
    }

    public static void main(String[] args) {
        Evaluator evaluator = new EvaluatorImpl();
        evaluator.evaluate();
    }
}
