package life.genny.googleapi.resource;

import io.netty.util.internal.StringUtil;
import life.genny.googleapi.service.GoogleApiService;
import life.genny.googleapi.service.TimezoneResp;
import life.genny.utils.EnvKeyReaderUtil;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/googleapi")
public class GoogleApiResource {

  @Inject
  private GoogleApiService googleApiService;

  @GET
  @Path("v1/map")
  @Produces(MediaType.APPLICATION_JSON)
  public String getGoogleMapApi() throws Exception {
    String apiKey = EnvKeyReaderUtil.retrieveKey("ENV_GOOGLE_MAPS_APIKEY");;

    if(StringUtil.isNullOrEmpty(apiKey)){
       throw new Exception("Google Map API key is not set in build.sh or current IDE env");
    }

    return googleApiService.retrieveGoogleMapApi(apiKey);
  }

  @GET
  @Path("v1/timezone")
  @Produces(MediaType.APPLICATION_JSON)
  public String getGoogleTimeZoneApi(@QueryParam String location, @QueryParam long timestamp ) throws Exception {
    String apiKey = EnvKeyReaderUtil.retrieveKey("ENV_GOOGLE_TIMEZONE_APIKEY");;

    if(StringUtil.isNullOrEmpty(apiKey)){
      throw new Exception("Google Time Zone API key is not set in build.sh or current IDE env");
    }

    TimezoneResp timezoneResp = googleApiService.retrieveGoogleTimeZoneApi(location, timestamp, apiKey);
    return timezoneResp.getTimeZoneId();
  }
}
