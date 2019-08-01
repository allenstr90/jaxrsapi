package aem.example.jee.jaxrsapi.core.config;

public class CoreConstants {
    public static final String DB_SECURITY_DATASOURCE_NAME = "java:app/JaxRsApi/DerbySecurityStore";
    public static final String DB_SECURITY_USERNAME = "APP";
    public static final String DB_SECURITY_PASSWORD = "APP";
    public static final String DB_SECURITY_SERVER_HOST = "localhost";
    public static final int DB_SECURITY_PORT = 1527;
    public static final String DB_SECURITY_NAME = "SecurityDB";
    public static final String DB_SECURITY_VENDOR = "derby";
    public static final String DB_SECURITY_CLASSNAME = "org.apache.derby.jdbc.ClientDataSource";
    public static final String DB_SECURITY_URL = "jdbc:" + DB_SECURITY_VENDOR + "://" + DB_SECURITY_SERVER_HOST + ":" + DB_SECURITY_PORT + "/" + DB_SECURITY_NAME + ";create=true";
}
