import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDataAdapter implements DataAccess {
    Connection conn = null;

    @Override
    public void connect(String url) {
        try {
            // db parameters
            // create a connection to the database
            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection(url);

            if (conn == null)
                System.out.println("Cannot make the connection!!!");
            else
                System.out.println("The connection object is " + conn);

            System.out.println("Connection to SQLite has been established.");

            /* Test data!!!
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Product");

            while (rs.next())
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            */

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void disconnect() throws SQLException {
        conn.close();
    }

    @Override
    public void saveProduct(ProductModel product) {
        try {
            Statement stmt = conn.createStatement();

            if (loadProduct(product.productID) == null) {           // this is a new product!
                stmt.execute("INSERT INTO Product(productID, name, price, quantity) VALUES ("
                        + product.productID + ","
                        + '\'' + product.name + '\'' + ","
                        + product.price + ","
                        + product.quantity + ")"
                );
            }
            else {
                stmt.executeUpdate("UPDATE Product SET "
                        + "productID = " + product.productID + ","
                        + "name = " + '\'' + product.name + '\'' + ","
                        + "price = " + product.price + ","
                        + "quantity = " + product.quantity +
                        " WHERE productID = " + product.productID
                );

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ProductModel loadProduct(int productID) {
        ProductModel product = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Product WHERE ProductID = " + productID);
            if (rs.next()) {
                product = new ProductModel();
                product.productID = rs.getInt(1);
                product.name = rs.getString(2);
                product.price = rs.getDouble(3);
                product.quantity = rs.getDouble(4);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return product;
    }

    @Override
    public List<ProductModel> loadAllProducts() {
        List<ProductModel> list = new ArrayList<>();
        ProductModel product = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Product ");
            while (rs.next()) {
                product = new ProductModel();
                product.productID = rs.getInt(1);
                product.name = rs.getString(2);
                product.price = rs.getDouble(3);
                product.quantity = rs.getDouble(4);
                list.add(product);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    /*public  UserModel loadUser(int user) {
        String username = user.userName;
        String password = user.password;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM User WHERE UserName = ? AND Password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = new UserModel();
                user.userID = rs.getInt(1);
                user.userName = rs.getString(2);
                user.password = rs.getString(3);
                user.isSeller = rs.getBoolean(6);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }*/

    public UserModel loadUser(int userID) {
        UserModel user = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE UserID = " + userID);
            if (rs.next()) {
                user = new UserModel();
                user.userID = rs.getInt(1);
                user.userName = rs.getString(2);
                user.password = rs.getString(3);
                user.displayName = rs.getString(4);
                user.isManager = rs.getBoolean(5);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public OrderModel loadOrder(int orderID) {
        OrderModel order = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Orders WHERE OrderID = " + orderID);
            if (rs.next()) {
                order = new OrderModel();
                order.orderID = rs.getInt(1);
                order.date = rs.getString(2);
                order.customerName = rs.getString(3);
                order.totalCost = rs.getDouble(4);
                order.totalTax = rs.getDouble(5);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return order;
    }

    public List<UserModel> loadAllUsers() {
        List<UserModel> list = new ArrayList<>();
        UserModel user = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User ");
            while (rs.next()) {
                user = new UserModel();
                user.userID = rs.getInt(1);
                user.userName = rs.getString(2);
                user.password = rs.getString(3);
                user.displayName = rs.getString(4);
                user.isManager = rs.getBoolean(5);
                list.add(user);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List<OrderModel> loadAllOrders() {
        List<OrderModel> list = new ArrayList<>();
        OrderModel order = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Orders ");
            while (rs.next()) {
                order = new OrderModel();
                order.orderID = rs.getInt(1);
                order.date = rs.getString(2);
                order.customerName = rs.getString(3);
                order.totalCost = rs.getDouble(4);
                order.totalTax = rs.getDouble(5);
                list.add(order);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public void updateProductPrice(int productId, double price)
    {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:store.db");
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE Product SET Price = ? WHERE ProductID = ?")) {
            stmt.setDouble(1, price);
            stmt.setInt(2, productId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Price updated successfully");
            } else {
                System.out.println("Product not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductQuantity(int productId, double quantity)
    {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:store.db");
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE Product SET Quantity = ? WHERE ProductID = ?")) {
            stmt.setDouble(1,quantity);
            stmt.setInt(2, productId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Quantity updated successfully");
            } else {
                System.out.println("Product not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int checkUserAuth(String username, String password) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:store.db");
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM User WHERE UserName = ? AND Password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int checkUserCancel(String username, int orderId) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:store.db");
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT * FROM Orders WHERE OrderID = ? AND Customer = ?")) {
            stmt.setInt(1, orderId);
            stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
