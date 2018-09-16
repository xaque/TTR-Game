package client;

import shared.CommonData;
import shared.Results;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class ClientCommunicator {

    private static final ClientCommunicator INSTANCE = new ClientCommunicator();

    private ClientCommunicator() {}

    public static ClientCommunicator getInstance(){
        return INSTANCE;
    }

    public Results send(String urlSuffix, String requestInfo){
        HttpURLConnection connection = openConnection(urlSuffix, true);
        sendRequest(connection, requestInfo);
        Results r = getResult(connection);
        connection.disconnect();
        return r;
    }

    private HttpURLConnection openConnection(String uri, boolean sendingObject){
        try {
            URL url = new URL("http://" + CommonData.HOSTNAME + ":" + CommonData.PORT_NUMBER + uri);
            HttpURLConnection result = (HttpURLConnection)url.openConnection();
            result.setRequestMethod("POST");
            result.setDoOutput(sendingObject);
            //result.setRequestProperty(AUTH_KEY, authToken);
            result.connect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Failed to make HTTP connection on http://" + CommonData.HOSTNAME + ":" + CommonData.PORT_NUMBER + uri);
        System.exit(0);
        return null;
    }

    private void sendRequest(HttpURLConnection connection, String data){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
            Serializer.serializeObject(data, osw);
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Results getResult(HttpURLConnection connection){
        Results result = null;
        try {
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                if(connection.getContentLength() == -1) {
                    InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                    result = Serializer.deserializeResults(isr);
                    isr.close();
                }
            }
            else {
                throw new Exception("http code " + connection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
