package Presentation;

import java.io.*;
import java.net.Socket;


public class Client  {

    private int serverPort;
    private Socket socket = null;

    public Client (int port)
    {
        this.serverPort = port;

        try
        {
            this.socket = new Socket("localhost", serverPort);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void sendToServer(String stringToSend)
    {
        try {
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            if (stringToSend.charAt(stringToSend.length()-1) != '\n')
                stringToSend = stringToSend + "\n";

            outputStream.writeBytes(stringToSend);
            outputStream.flush();

        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    public String receiveFromServer()
    {
        String res = "-1";

        try
        {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(inputStream));

            res = clientReader.readLine();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return res;

    }

}

