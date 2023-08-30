public class ServiceMessageModel {

    public static int RESPONSE_PRODUCT_DETAILS = 5;
    public static int REQUEST_PRODUCT_DETAILS = 4;
    public static int SERVICE_PUBLISH_REQUEST = 1;
    public static int SERVICE_UNPUBLISH_REQUEST = 2;

    public static int SERVICE_DISCOVER_REQUEST = 3;

    public static int SERVICE_DISCOVER_NOT_FOUND = 101;

    public static int SERVICE_DISCOVER_OK = 103;

    public static int SERVICE_PUBLISH_OK = 102;

    public int code;
    public String data;

}
