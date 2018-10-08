package cs340.game.client;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import cs340.game.shared.CommonData;
import cs340.game.shared.Results;
import cs340.game.shared.Serializer;
import cs340.game.shared.data.Data;

public final class ClientCommunicator {

    private static final ClientCommunicator INSTANCE = new ClientCommunicator();

    private ClientCommunicator() {}

    public static ClientCommunicator getInstance(){
        return INSTANCE;
    }

    /**
     * Send a command to the server
     * @param urlSuffix The uri context from which to send data
     * @param data The CommandData object to be sent to the server
     * @return The Results sent back from the server
     */
    public Results send(String urlSuffix, Data data){
        HttpURLConnection connection = openConnection(urlSuffix, true);
        sendRequest(connection, data);
        Results r = getResult(connection);
        connection.disconnect();
        return r;
    }

    /**
     * Open a Http connection
     * @param uri The uri context from which to open a connection
     * @param sendingObject Whether the server should wait for request body
     * @return A successfully established HttpURLConnection
     */
    private HttpURLConnection openConnection(String uri, boolean sendingObject){
        try {
            System.out.println("http://" + CommonData.HOSTNAME + ":" + CommonData.PORT_NUMBER + uri);
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

    /**
     * Send Http request to the server
     * @param connection HttpURLConnection from which to send the request
     * @param data The CommandData object to be sent to the server
     */
    private void sendRequest(HttpURLConnection connection, Data data){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
            osw.write(Serializer.serializeData(data));
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Results from the connected server.
     * @param connection HttpURLConnection from which to read a response.
     * @return The Http response converted into Results
     */
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
