package life.genny.googleapi.resource;

import io.netty.util.internal.StringUtil;
import life.genny.googleapi.model.address.Addresses;
import life.genny.googleapi.service.GoogleApiService;
import life.genny.googleapi.model.timezone.TimezoneResp;
import life.genny.models.GennyToken;
import life.genny.utils.EnvKeyReaderUtil;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/googleapi")
public class GoogleApiResource {

  @Inject
  private GoogleApiService googleApiService;

  @Inject
  private JsonWebToken accessToken;


  @GET
  @Path("v1/map")
  @Produces(MediaType.APPLICATION_JSON)
  public Response retrieveGoogleMapApi() throws Exception {
    String apiKey = retrieveApiKey("ENV_GOOGLE_MAPS_APIKEY_", "ENV_GOOGLE_MAPS_APIKEY_DEFAULT");

    if(StringUtil.isNullOrEmpty(apiKey)){
       throw new Exception("Google Map API key is not set in build.sh or current IDE env");
    }

    String respGoogleMapJs = googleApiService.retrieveGoogleMapApi(apiKey);
    return Response.ok(respGoogleMapJs, MediaType.TEXT_PLAIN).build();
  }

  @GET
  @Path("v1/timezone")
  @NoCache
  @Produces(MediaType.APPLICATION_JSON)
  public Response retrieveGoogleTimeZoneApi(@QueryParam String location, @QueryParam long timestamp ) throws Exception {

    String apiKey = retrieveApiKey("ENV_GOOGLE_TIMEZONE_APIKEY_", "ENV_GOOGLE_TIMEZONE_APIKEY_DEFAULT");

    if(StringUtil.isNullOrEmpty(apiKey)){
      throw new Exception("Google Time Zone API key is not set in build.sh or current IDE env");
    }

    TimezoneResp timezoneResp = googleApiService.retrieveGoogleTimeZoneApi(location, timestamp, apiKey);
    return Response.ok(timezoneResp.getTimeZoneId(), MediaType.TEXT_PLAIN).build();
  }

  @GET
  @Path("v1/geocode")
  @NoCache
  @Produces(MediaType.APPLICATION_JSON)
  public Response retrieveGoogleAddressApi(@QueryParam String address) throws Exception {

    String apiKey = retrieveApiKey("ENV_GOOGLE_TIMEZONE_APIKEY_", "ENV_GOOGLE_TIMEZONE_APIKEY_DEFAULT");

    if(StringUtil.isNullOrEmpty(apiKey)){
      throw new Exception("Google Geocode API key is not set in build.sh or current IDE env");
    }

    Addresses addresses = googleApiService.retrieveGoogleAddressApi(address, apiKey);
    return Response.ok(addresses, MediaType.APPLICATION_JSON).build();
  }


  private String retrieveApiKey(String envName, String defaultFileName){
    GennyToken userToken = new GennyToken(accessToken.getRawToken());

    String realm = userToken.getRealm();

    System.out.println("realm detected "+realm +" roles  "+userToken.getUserRoles());

    if (!userToken.hasRole("dev") && !userToken.hasRole("superadmin")) {
      throw new WebApplicationException("User not recognised. Entity not being created", Response.Status.FORBIDDEN);
    }

    return EnvKeyReaderUtil.retrieveKey(envName+realm.toUpperCase(), defaultFileName);

  }
}
