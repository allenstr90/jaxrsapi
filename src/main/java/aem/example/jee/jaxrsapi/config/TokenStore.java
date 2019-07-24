package aem.example.jee.jaxrsapi.config;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;

@DataSourceDefinition(
        name = "java:app/JaxRsApi/MyDataSource",
        className = "org.h2.jdbcx.JdbcDataSource",
        url = "jdbc:h2:mem:tokenStore"
)
@Stateless
public class TokenStore {
}
