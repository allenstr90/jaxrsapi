package aem.example.jee.jaxrsapi.reader;

import aem.example.jee.jaxrsapi.dto.LoginDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Provider
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class LoginMessageBodyReader implements MessageBodyReader<LoginDTO> {


    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return aClass.isAssignableFrom(LoginDTO.class);
    }

    @Override
    public LoginDTO readFrom(Class<LoginDTO> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String collect = reader.lines().collect(Collectors.joining());
        String decode = URLDecoder.decode(collect, "UTF-8");
        Map<String, String> map = new HashMap<>();
        String[] split = decode.split("&");
        String lastKey = "";
        for (String s : split) {
            if (s.lastIndexOf('=') >= 0) {
                String[] keysValues = s.split("=");
                String value = keysValues.length > 1 ? keysValues[1] : "";
                if (!map.containsKey(keysValues[0])) {
                    lastKey = keysValues[0];
                    map.put(lastKey, value);
                }
            } else {
                String keyValue = map.get(lastKey) + "&" + s;
                map.replace(lastKey, keyValue);
            }
        }
        LoginDTO dto = new LoginDTO();
        map.forEach((s, s2) -> {
            if (s.equalsIgnoreCase("username")) {
                dto.setUsername(s2);
            } else if (s.equalsIgnoreCase("password"))
                dto.setPassword(s2);

        });
        return dto;
    }
}
