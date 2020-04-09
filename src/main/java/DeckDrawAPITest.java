import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

public class DeckDrawAPITest {
    public static final Integer failure = 0;
    public static final Integer success = 1;
    public static final Integer exception = 2;
    private String New_Deck = "https://deckofcardsapi.com/api/deck/new/";
    private String draw_card = "https://deckofcardsapi.com/api/deck/<<deck_id>>/draw/";

    public Result testNew_Deck() {
        Result result = null;
        HttpResponse response = null;
        String deckId = "-1";
        try {
            deckId = "-1";

            response = buildGetResponse(New_Deck);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            String responseContent = "";
            String line = "";
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream is = entity.getContent();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    while ((line = br.readLine()) != null) {
                        responseContent += line;
                    }
                }
                deckId = responseContent.substring(responseContent.length() - 14, responseContent.length() - 2);
            }
        } catch (NoSuchAlgorithmException e) {
            result = new Result(e.getMessage(), exception);
        } catch (IOException e) {
            result = new Result(e.getMessage(), exception);
        } catch (KeyManagementException e) {
            result = new Result(e.getMessage(), exception);
        }

        try {
            draw_card = draw_card.replaceAll("<<deck_id>>", deckId);
            response = buildGetResponse(draw_card);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                result = new Result(response.getEntity().getContentType().toString(), success);
            } else {
                result = new Result(response.getEntity().getContentType().toString(), failure);
            }
        } catch (IOException e) {
            result = new Result(e.getMessage(), exception);
        } catch (NoSuchAlgorithmException e) {
            result = new Result(e.getMessage(), exception);
        } catch (KeyManagementException e) {
            result = new Result(e.getMessage(), exception);
        } finally {
            return result;
        }
    }

    public Result testDraw_card() {
        Result result = null;
        HttpResponse response = null;
        try {
            response = buildGetResponse(New_Deck);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                result = new Result(response.getEntity().getContentType().toString(), success);
            } else {
                result = new Result(statusLine.toString(), failure );
            }
        } catch (IOException e) {
            result = new Result(e.getMessage(), exception);
        } catch (NoSuchAlgorithmException e) {
            result = new Result(e.getMessage(), exception);
        } catch (KeyManagementException e) {
            result = new Result(e.getMessage(), exception);
        } finally {
            return result;
        }
    }

    public HttpResponse buildGetResponse(String url) throws IOException, NoSuchAlgorithmException, KeyManagementException {

        SSLConnectionSocketFactory sslConSocFactory = new SSLConnectionSocketFactory(SSLContexts.custom().build(), new NoopHostnameVerifier());

        HttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslConSocFactory).build();

        HttpGet httpget = new HttpGet(url);

        HttpResponse httpresponse = httpclient.execute(httpget);

        return httpresponse;
    }
}
