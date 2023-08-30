import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TestClientOrder2 {


    public static void main(String[] args) throws IOException {

        // ask for service from Registry

        Socket socket = new Socket("localhost", 5000);

        ServiceMessageModel req = new ServiceMessageModel();
        req.code = ServiceMessageModel.SERVICE_DISCOVER_REQUEST;
        req.data = String.valueOf(ServiceInfoModel.ORDER_INFO_SERVICE);

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
        Socket socket2 = new Socket("localhost", 5051);

        // Send the order ID to the server
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter order id: ");
        int orderId = scanner.nextInt();

        writer.writeInt(orderId);
        writer.flush();

        // Receive the order info from the server
        DataInputStream reader2 = new DataInputStream(socket.getInputStream());
        String json = reader2.readUTF();

        // Deserialize the order info
        Gson gson2 = new Gson();
        OrderModel order = gson2.fromJson(json, OrderModel.class);

        // Print the order info
        System.out.println("Order ID: " + order.orderID);
        System.out.println("Date: " + order.date);
        System.out.println("Customer Name: " + order.customerName);
        System.out.println("Total Cost: " + order.totalCost);
        System.out.println("Total Tax: " + order.totalTax);

        // Clean up
        reader2.close();
        writer.close();
        socket2.close();

    }
}
