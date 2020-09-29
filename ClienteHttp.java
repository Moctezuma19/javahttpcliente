import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClienteHttp {

    public static void main(String[] args) throws Exception {

        ClienteHttp obj = new ClienteHttp();
        System.out.println("Testing 1 - Send Http GET request");
        obj.sendGet();

    }

    private void sendGet() throws Exception {

        String url = "http://localhost:8000/api";
        String params = "op=aut&usuario=usuario&clave=clave";
        String token = null;
        HttpURLConnection httpClient =
                (HttpURLConnection) new URL(url + "?" + params).openConnection();

        // optional default is GET
        httpClient.setRequestMethod("GET");
        //
        int responseCode = httpClient.getResponseCode();
        String respuesta = obtenRespuesta(httpClient);
        System.out.println("\nAutenticando ..." + url);
        if(responseCode == 200){
            System.out.println("Listo!");
            Thread.sleep(61000);
            params = "op=rep&id=hola";
            httpClient = (HttpURLConnection) new URL(url + "?" +params).openConnection();
            httpClient.setRequestMethod("GET");
            httpClient.setRequestProperty ("Authorization","Bearer " + respuesta);
            responseCode = httpClient.getResponseCode();
            System.out.println(obtenRespuesta(httpClient));
        } else {
            System.out.println(respuesta);
        }

    }

    private String obtenRespuesta(HttpURLConnection httpClient){
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpClient.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            System.out.println(response.toString());
            return response.toString();

        } catch(IOException e){
            if(e.getMessage().contains("401")){
                return "Token invalido";
            }
            return null;

        }
    }

    /*private void sendPost() throws Exception {

		// url is missing?
        //String url = "https://selfsolve.apple.com/wcResults.do";
        String url = "https://httpbin.org/post";

        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        httpClient.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }

        int responseCode = httpClient.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            //print result
            System.out.println(response.toString());

        }

    }*/

}