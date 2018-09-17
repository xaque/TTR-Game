package client;

import shared.CommandData;
import shared.CommonData;
import shared.Results;
import shared.Serializer;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class ClientCommunicator {

    private static final ClientCommunicator INSTANCE = new ClientCommunicator();

    private ClientCommunicator() {}

    public static ClientCommunicator getInstance(){
        return INSTANCE;
    }

    public Results send(String urlSuffix, CommandData cd){
        HttpURLConnection connection = openConnection(urlSuffix, true);
        sendRequest(connection, cd);
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

    private void sendRequest(HttpURLConnection connection, CommandData data){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
            osw.write(Serializer.serializeCommandData(data));
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
                    Scanner s = new Scanner(connection.getInputStream()).useDelimiter("\\A");
                    String raw = s.hasNext() ? s.next() : "";
                    result = Serializer.deserializeResults(raw);
                }
            }
            else {
                throw new Exception("http code " + connection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.getMessage();
        }
        return result;
    }

}
