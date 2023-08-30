import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TestClientQuantity4 {


    public static void main(String[] args) throws IOException {

        // ask for service from Registry

        Socket socket = new Socket("localhost", 5000);

        ServiceMessageModel req = new ServiceMessageModel();
        req.code = ServiceMessageModel.SERVICE_DISCOVER_REQUEST;
        req.data = String.valueOf(ServiceInfoModel.PRODUCT_QUANTITY_UPDATE_SERVICE);

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
        Socket socket2 = new Socket("localhost", 5053);

        // Send the product ID to the server
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter product id: ");
        int productId = scanner.nextInt();

        System.out.println("Enter new quantity: ");
        double quantity = scanner.nextDouble();

        writer.writeInt(productId);
        writer.writeDouble(quantity);
        writer.flush();

        // Receive the product info from the server
        DataInputStream reader2 = new DataInputStream(socket.getInputStream());
        String json = reader2.readUTF();

        // Deserialize the product info
        Gson gson2 = new Gson();
        ProductModel product = gson2.fromJson(json, ProductModel.class);

        // Print the product info
        System.out.println("Product ID: " + product.productID);
        System.out.println("Name: " + product.name);
        System.out.println("Price: " + product.price);
        System.out.println("Quantity: " + product.quantity);

        // Clean up
        reader2.close();
        writer.close();
        socket2.close();

    }
}
