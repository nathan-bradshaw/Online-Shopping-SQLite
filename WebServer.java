import com.hp.gagawa.java.elements.*;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WebServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
        HttpContext root = server.createContext("/");
        root.setHandler(WebServer::handleRequest);

        HttpContext user = server.createContext("/users");
        user.setHandler(WebServer::handleRequestOneUser);

        HttpContext allusers = server.createContext("/users/all");
        allusers.setHandler(WebServer::handleRequestAllUsers);

        HttpContext product = server.createContext("/products");
        product.setHandler(WebServer::handleRequestOneProduct);

        HttpContext allproducts = server.createContext("/products/all");
        allproducts.setHandler(WebServer::handleRequestAllProducts);

        HttpContext order = server.createContext("/orders");
        order.setHandler(WebServer::handleRequestOneOrder);

        HttpContext allorders = server.createContext("/orders/all");
        allorders.setHandler(WebServer::handleRequestAllOrders);

        server.start();
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {
        /*Html html = new Html();
        Head head = new Head();

        html.appendChild( head );

        Title title = new Title();
        title.appendChild( new Text("Online shopping web server") );
        head.appendChild( title );

        Body body = new Body();

        P para = new P();

        A link = new A("/products/all", "/products/all");
        link.appendText("Product list");

        A link2 = new A("/users/all", "/users/all");
        link2.appendText("User list");

        A link3 = new A("/orders/all", "/orders/all");
        link3.appendText("Order list");

        para.appendChild(link);
        para.appendChild(new Text(" | "));
        para.appendChild(link2);
        para.appendChild(new Text(" | "));
        para.appendChild(link3);
        body.appendChild(para);
        html.appendChild( body );
        String response = html.write();
        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();*/
        Html html = new Html();
        Head head = new Head();

        html.appendChild(head);

        Title title = new Title();
        title.appendChild(new Text("Online Shopping System Management"));
        head.appendChild(title);

        Body body = new Body();
        body.setStyle("font-family: Arial, sans-serif;");

        Div header = new Div();
        header.setStyle("background-color: #f2f2f2; padding: 10px;");
        H1 heading = new H1();
        heading.setStyle("margin: 0;");
        heading.appendText("Online Shopping System View");
        header.appendChild(heading);
        body.appendChild(header);

        Div container = new Div();
        container.setStyle("margin: 20px;");

        P description = new P();
        description.appendText("This system allows you to view the products, users, and orders for your online shopping website.");

        A link = new A("/products/all", "/products/all");
        link.appendText("Product List");

        A link2 = new A("/users/all", "/users/all");
        link2.appendText("User List");

        A link3 = new A("/orders/all", "/orders/all");
        link3.appendText("Order List");

        P links = new P();
        links.appendChild(link);
        links.appendChild(new Text(" | "));
        links.appendChild(link2);
        links.appendChild(new Text(" | "));
        links.appendChild(link3);

        container.appendChild(description);
        container.appendChild(links);
        body.appendChild(container);

        html.appendChild(body);

        String response = html.write();
        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private static void handleRequestAllUsers(HttpExchange exchange) throws IOException {
//        String response =  "This simple web server is designed with help from ChatGPT!";

        String url = "jdbc:sqlite:store.db";

        SQLiteDataAdapter dao = new SQLiteDataAdapter();

        dao.connect(url);

        List<UserModel> list = dao.loadAllUsers();

        Html html = new Html();
        Head head = new Head();

        html.appendChild( head );

        Title title = new Title();
        title.appendChild( new Text("Users") );
        head.appendChild( title );

        Body body = new Body();

        html.appendChild( body );

        H1 h1 = new H1();
        h1.appendChild( new Text("User List") );
        body.appendChild( h1 );

        P para = new P();
        para.appendChild( new Text("The server time is " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))) );
        body.appendChild(para);

        para = new P();
        para.appendChild( new Text("The server has " + list.size() + " users." ));
        body.appendChild(para);

        Table table = new Table();
        Tr row = new Tr();
        Th header = new Th(); header.appendText("UserID"); row.appendChild(header);
        header = new Th(); header.appendText("UserName"); row.appendChild(header);
        header = new Th(); header.appendText("Password"); row.appendChild(header);
        header = new Th(); header.appendText("DisplayName"); row.appendChild(header);
        header = new Th(); header.appendText("IsManager"); row.appendChild(header);
        table.appendChild(row);

        for (UserModel user : list) {
            row = new Tr();
            Td cell = new Td(); cell.appendText(String.valueOf(user.userID)); row.appendChild(cell);
            cell = new Td(); cell.appendText(user.userName); row.appendChild(cell);
            cell = new Td(); cell.appendText(user.password); row.appendChild(cell);
            cell = new Td(); cell.appendText(user.displayName); row.appendChild(cell);
            cell = new Td(); cell.appendText(String.valueOf(user.isManager)); row.appendChild(cell);
            table.appendChild(row);
        }

        table.setBorder("1");

        html.appendChild(table);
        String response = html.write();

        System.out.println(response);


        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void handleRequestOneUser(HttpExchange exchange) throws IOException {
        //String response = "Hi there! I am a simple web server!";

        String uri =  exchange.getRequestURI().toString();

        int id = Integer.parseInt(uri.substring(uri.lastIndexOf('/')+1));

        System.out.println(id);


        String url = "jdbc:sqlite:store.db";

        SQLiteDataAdapter dao = new SQLiteDataAdapter();

        dao.connect(url);

        Html html = new Html();
        Head head = new Head();

        html.appendChild( head );

        Title title = new Title();
        title.appendChild( new Text("User") );
        head.appendChild( title );

        Body body = new Body();

        html.appendChild( body );

        H1 h1 = new H1();
        h1.appendChild( new Text("User Information") );
        body.appendChild( h1 );

        P para = new P();
        para.appendChild( new Text("The server time is " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))) );
        body.appendChild(para);

        UserModel user = dao.loadUser(id);

        if (user != null) {

            para = new P();
            para.appendText("UserID: " + user.userID);
            html.appendChild(para);
            para = new P();
            para.appendText("Username: " + user.userName);
            html.appendChild(para);
            para = new P();
            para.appendText("Password: " + user.password);
            html.appendChild(para);
            para = new P();
            para.appendText("Display Name: " + user.displayName);
            html.appendChild(para);
            para = new P();
            para.appendText("Manager: " + user.isManager);
            html.appendChild(para);
        }
        else {
            para = new P();
            para.appendText("User not found");
            html.appendChild(para);
        }

        String response = html.write();

        System.out.println(response);


        //Done under
        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void handleRequestAllProducts(HttpExchange exchange) throws IOException {
//        String response =  "This simple web server is designed with help from ChatGPT!";

        String url = "jdbc:sqlite:store.db";

        SQLiteDataAdapter dao = new SQLiteDataAdapter();

        dao.connect(url);

        List<ProductModel> list = dao.loadAllProducts();

        Html html = new Html();
        Head head = new Head();

        html.appendChild( head );

        Title title = new Title();
        title.appendChild( new Text("Products") );
        head.appendChild( title );

        Body body = new Body();

        html.appendChild( body );

        H1 h1 = new H1();
        h1.appendChild( new Text("Product List") );
        body.appendChild( h1 );

        P para = new P();
        para.appendChild( new Text("The server time is " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))) );
        body.appendChild(para);

        para = new P();
        para.appendChild( new Text("The server has " + list.size() + " products." ));
        body.appendChild(para);

        Table table = new Table();
        Tr row = new Tr();
        Th header = new Th(); header.appendText("ProductID"); row.appendChild(header);
        header = new Th(); header.appendText("Product name"); row.appendChild(header);
        header = new Th(); header.appendText("Price"); row.appendChild(header);
        header = new Th(); header.appendText("Quantity"); row.appendChild(header);
        table.appendChild(row);

        for (ProductModel product : list) {
            row = new Tr();
            Td cell = new Td(); cell.appendText(String.valueOf(product.productID)); row.appendChild(cell);
            cell = new Td(); cell.appendText(product.name); row.appendChild(cell);
            cell = new Td(); cell.appendText(String.valueOf(product.price)); row.appendChild(cell);
            cell = new Td(); cell.appendText(String.valueOf(product.quantity)); row.appendChild(cell);
            table.appendChild(row);
        }

        table.setBorder("1");

        html.appendChild(table);
        String response = html.write();

        System.out.println(response);


        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


    private static void handleRequestOneProduct(HttpExchange exchange) throws IOException {
        String uri =  exchange.getRequestURI().toString();

        int id = Integer.parseInt(uri.substring(uri.lastIndexOf('/')+1));

        System.out.println(id);


        String url = "jdbc:sqlite:store.db";

        SQLiteDataAdapter dao = new SQLiteDataAdapter();

        dao.connect(url);

        Html html = new Html();
        Head head = new Head();

        html.appendChild( head );

        Title title = new Title();
        title.appendChild( new Text("Product") );
        head.appendChild( title );

        Body body = new Body();

        html.appendChild( body );

        H1 h1 = new H1();
        h1.appendChild( new Text("Product") );
        body.appendChild( h1 );

        P para = new P();
        para.appendChild( new Text("The server time is " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        body.appendChild(para);

        ProductModel product = dao.loadProduct(id);

        if (product != null) {

            para = new P();
            para.appendText("ProductID:" + product.productID);
            html.appendChild(para);
            para = new P();
            para.appendText("Product name:" + product.name);
            html.appendChild(para);
            para = new P();
            para.appendText("Price:" + product.price);
            html.appendChild(para);
            para = new P();
            para.appendText("Quantity:" + product.quantity);
            html.appendChild(para);
        }
        else {
            para = new P();
            para.appendText("Product not found");
            html.appendChild(para);
        }

        String response = html.write();

        System.out.println(response);

        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void handleRequestAllOrders(HttpExchange exchange) throws IOException {
//        String response =  "This simple web server is designed with help from ChatGPT!";

        String url = "jdbc:sqlite:store.db";

        SQLiteDataAdapter dao = new SQLiteDataAdapter();

        dao.connect(url);

        List<OrderModel> list = dao.loadAllOrders();

        Html html = new Html();
        Head head = new Head();

        html.appendChild( head );

        Title title = new Title();
        title.appendChild( new Text("Orders") );
        head.appendChild( title );

        Body body = new Body();

        html.appendChild( body );

        H1 h1 = new H1();
        h1.appendChild( new Text("Order List") );
        body.appendChild( h1 );

        P para = new P();
        para.appendChild( new Text("The server time is " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        body.appendChild(para);

        para = new P();
        para.appendChild( new Text("The server has " + list.size() + " orders." ));
        body.appendChild(para);

        Table table = new Table();
        Tr row = new Tr();
        Th header = new Th(); header.appendText("OrderID"); row.appendChild(header);
        header = new Th(); header.appendText("Date"); row.appendChild(header);
        header = new Th(); header.appendText("Customer name"); row.appendChild(header);
        header = new Th(); header.appendText("Total Cost"); row.appendChild(header);
        header = new Th(); header.appendText("Total Tax"); row.appendChild(header);

        table.appendChild(row);

        for (OrderModel order : list) {
            row = new Tr();
            Td cell = new Td(); cell.appendText(String.valueOf(order.orderID)); row.appendChild(cell);
            cell = new Td(); cell.appendText(String.valueOf(order.date)); row.appendChild(cell);
            cell = new Td(); cell.appendText(order.customerName); row.appendChild(cell);
            cell = new Td(); cell.appendText(String.valueOf(order.totalCost)); row.appendChild(cell);
            cell = new Td(); cell.appendText(String.valueOf(order.totalTax)); row.appendChild(cell);

            table.appendChild(row);
        }

        table.setBorder("1");

        html.appendChild(table);
        String response = html.write();

        System.out.println(response);


        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


    private static void handleRequestOneOrder(HttpExchange exchange) throws IOException {
        String uri =  exchange.getRequestURI().toString();

        int id = Integer.parseInt(uri.substring(uri.lastIndexOf('/')+1));

        System.out.println(id);


        String url = "jdbc:sqlite:store.db";

        SQLiteDataAdapter dao = new SQLiteDataAdapter();

        dao.connect(url);

        Html html = new Html();
        Head head = new Head();

        html.appendChild( head );

        Title title = new Title();
        title.appendChild( new Text("Order") );
        head.appendChild( title );

        Body body = new Body();

        html.appendChild( body );

        H1 h1 = new H1();
        h1.appendChild( new Text("Order") );
        body.appendChild( h1 );

        P para = new P();
        para.appendChild( new Text("The server time is " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        body.appendChild(para);

        OrderModel order = dao.loadOrder(id);

        if (order != null) {

            para = new P();
            para.appendText("Order ID:" + order.orderID);
            html.appendChild(para);
            para = new P();
            para.appendText("Date:" + order.date);
            html.appendChild(para);
            para = new P();
            para.appendText("Customer Name:" + order.customerName);
            html.appendChild(para);
            para = new P();
            para.appendText("Total Cost:" + order.totalCost);
            html.appendChild(para);
            para = new P();
            para.appendText("Total Tax:" + order.totalTax);
            html.appendChild(para);
        }
        else {
            para = new P();
            para.appendText("Order not found");
            html.appendChild(para);
        }

        String response = html.write();

        System.out.println(response);

        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
