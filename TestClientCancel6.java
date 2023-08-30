import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TestClientCancel6 {


    public static void main(String[] args) throws IOException {

        // ask for service from Registry

        Socket socket = new Socket("localhost", 5000);

        ServiceMessageModel req = new ServiceMessageModel();
        req.code = ServiceMessageModel.SERVICE_DISCOVER_REQUEST;
        req.data = String.valueOf(ServiceInfoModel.USER_CANCELLATION_SERVICE);

        Gson gson = new Gson();

        DataOutputStream printer = new DataOutputStream(socket.getOutputStream());
        printer.writeUTF(gson.toJson(req));
        printer.flush();


        DataInputStream reader = new DataInputStream(socket.getInputStream());
        String msg = reader.readUTF();

        System.out.println("Message from server: " + msg);

        printer.close();
        reader.close();
        socket.close();

        ServiceMessageModel res = gson.fromJson(msg, ServiceMessageModel.class);

        if (res.code == ServiceMessageModel.SERVICE_DISCOVER_OK) {
            System.out.println(res.data);
        }
        else {
            System.out.println("Service not found");
            return;
        }

        ServiceInfoModel info = gson.fromJson(res.data, ServiceInfoModel.class);
        System.out.println(info);
        // Connect to the product info service and retrieve product details
        socket = new Socket(info.serviceHostAddress, info.serviceHostPort);

        /*DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeInt(1);
        out.flush();*/

        // Connect to the server on the specified port
        Socket socket2 = new Socket("localhost", 5055);

        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        System.out.println("Enter orderId: ");
        int orderId = scanner.nextInt();


        writer.writeUTF(username);
        writer.writeInt(orderId);
        writer.flush();

        DataInputStream reader2 = new DataInputStream(socket.getInputStream());
        int status = reader2.readInt();

        if(status == 1) {
            System.out.println("User can cancel this order");
        }
        else System.out.println("User is not authorized to cancel this order");

        // Clean up
        reader2.close();
        writer.close();
        socket2.close();
    }
}
