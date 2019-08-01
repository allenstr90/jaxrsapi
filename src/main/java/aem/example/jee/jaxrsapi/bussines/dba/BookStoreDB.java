package aem.example.jee.jaxrsapi.bussines.dba;

import javax.annotation.sql.DataSourceDefinition;
import javax.enterprise.context.ApplicationScoped;

@DataSourceDefinition(
        name = "java:app/JaxRsApi/BookStore",
        className = "org.apache.derby.jdbc.ClientDataSource",
        url = "jdbc:derby://localhost:1527/BookStore",
        databaseName = "BookStore",
        user = "APP",
        password = "APP",
        portNumber = 1527,
        properties = {"databaseName=BookStore", "create=true"}
)
@ApplicationScoped
public class BookStoreDB {
}
