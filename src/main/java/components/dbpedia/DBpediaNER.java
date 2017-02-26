package components.dbpedia;

import components.NER;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akorovin on 21.02.2017.
 */
public class DBpediaNER implements NER {
    private final static String SERVICE_URL = "http://spotlight.sztaki.hu:2222/rest/annotate";
    //private final static String SERVICE_URL = "http://api.dbpedia-spotlight.org/annotate";
    private final static Double CONFIDENCE = 0.7;
    private final static Integer SUPPORT = 20;

    public List<String> getEntities(String slide) {

        return sendUrl(SERVICE_URL, slide);
    }

    private List<String> sendUrl(String url, String slide) {
        List<String> retLst = new ArrayList<String>();

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);
            post.setHeader("Accept", "text/xml");
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("text", URLEncoder.encode(slide, "UTF-8")));
            urlParameters.add(new BasicNameValuePair("confidence", String.valueOf(CONFIDENCE)));
            urlParameters.add(new BasicNameValuePair("support", String.valueOf(SUPPORT)));
            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            HttpResponse response = client.execute(post);
            System.out.println("Response Code : "
                    + response.getStatusLine().getStatusCode());

//            BufferedReader rd = new BufferedReader(
//                    new InputStreamReader(response.getEntity().getContent()));
//            StringBuffer result = new StringBuffer();
//            String line = "";
//            while ((line = rd.readLine()) != null) {
//                result.append(line);
//            }
//            System.out.println(result.toString());
//            System.out.println(result.length());

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(response.getEntity().getContent());
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Resource");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String text = eElement.getAttribute("URI");
                    System.out.println(text);
                    retLst.add(text);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retLst;
    }
}
