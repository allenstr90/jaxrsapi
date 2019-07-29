package aem.example.jee.jaxrsapi.core.config;

import javax.annotation.sql.DataSourceDefinition;
import javax.enterprise.context.ApplicationScoped;

@DataSourceDefinition(
        name = "java:app/JaxRsApi/H2TokenStore",
        className = "org.h2.jdbcx.JdbcDataSource",
        url = "jdbc:h2:mem:tokenStore"
)
@ApplicationScoped
public class TokenStore {
}
