package life.genny.gennyproxy.application;

import io.netty.util.internal.StringUtil;
import life.genny.models.GennyToken;
import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Arrays;

@ApplicationScoped
public class AccessTokenParser {

    @Inject
    private JsonWebToken accessToken;

    public void validateRole(String... roles){
        GennyToken userToken = new GennyToken(accessToken.getRawToken());

        String realm = userToken.getRealm();

        System.out.println("realm detected "+realm +" roles  "+userToken.getUserRoles());

        Arrays
           .asList(roles)
           .forEach( role ->{
                       if(userToken.hasRole(role)){
                          return;
                       }
                    });

        throw new WebApplicationException("User not recognised. Entity not being created", Response.Status.FORBIDDEN);

    }

}
