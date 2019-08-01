package aem.example.jee.jaxrsapi.core.config;

import javax.annotation.sql.DataSourceDefinition;
import javax.enterprise.context.ApplicationScoped;

@DataSourceDefinition(
        name = CoreConstants.DB_SECURITY_DATASOURCE_NAME,
        className = CoreConstants.DB_SECURITY_CLASSNAME,
        url = CoreConstants.DB_SECURITY_URL,
        user = CoreConstants.DB_SECURITY_USERNAME,
        password = CoreConstants.DB_SECURITY_PASSWORD,
        databaseName = CoreConstants.DB_SECURITY_NAME
)
@ApplicationScoped
public class SecurityStore {
}
