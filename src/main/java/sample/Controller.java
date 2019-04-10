package sample;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TextArea queryReader;
    public Button doNLP;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        doNLP.setOnAction(event -> {
            CloseableHttpClient httpClient=HttpClients.createDefault();
            CloseableHttpClient httpClient2=HttpClients.createDefault();

            HttpPost httpPost=new HttpPost("https://cxl-services.appspot.com/proxy?url=https://language.googleapis.com/v1beta2/documents:analyzeSentiment");
            HttpPost httpPost2=new HttpPost("https://cxl-services.appspot.com/proxy?url=https://language.googleapis.com/v1beta2/documents:analyzeEntitySentiment");

            JsonObject paramNLP=new JsonObject();
            JsonObject document=new JsonObject();

            document.addProperty("type","PLAIN_TEXT");
            document.addProperty("content",queryReader.getText());


            paramNLP.add("document",document);
            paramNLP.addProperty("encodingType","UTF16");

            try {
                StringEntity stringEntity=new StringEntity(paramNLP.toString());
                httpPost.setEntity(stringEntity);
                httpPost2.setEntity(stringEntity);

                CloseableHttpResponse response=httpClient.execute(httpPost);
                CloseableHttpResponse response2=httpClient2.execute(httpPost2);

                System.out.println(EntityUtils.toString(response.getEntity()));
                System.out.println(EntityUtils.toString(response2.getEntity()));

                httpClient.close();
                httpClient2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
