package aem.example.jee.jaxrsapi;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("api")
@DeclareRoles({"user", "admin"})
public class JAXRSConfiguration extends Application {
}
