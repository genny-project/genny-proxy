package life.genny.gennyproxy.resource;

import life.genny.gennyproxy.application.AccessTokenParser;
import life.genny.gennyproxy.model.address.AddressResp;
import life.genny.gennyproxy.repository.entity.timezone.GoogleTimezone;
import life.genny.gennyproxy.service.GoogleApiService;
import life.genny.qwandautils.JsonUtils;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import life.genny.gennyproxy.repository.entity.address.Addresses;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/googleapi")
public class GoogleApiResource {

  @Inject
  private GoogleApiService googleApiService;

  @Inject
  private AccessTokenParser accessTokenParser;

  @GET
  @Path("v1/map")
  @Produces(MediaType.APPLICATION_JSON)
  public Response retrieveGoogleMapApi() {
    System.out.println("Call endpoint v1/map");

//    String realm = accessTokenParser.validateRole("user", "superadmin");
    String realm = "";

    String respGoogleMapJs = googleApiService.retrieveGoogleMapApi(realm);
    System.out.println("RESPOSNE FROM GOOGLE API: "+ respGoogleMapJs);

    return Response.ok(respGoogleMapJs, MediaType.TEXT_PLAIN).build();
  }

  @GET
  @Path("v1/timezone")
  @NoCache
  @Produces(MediaType.APPLICATION_JSON)
  public Response retrieveGoogleTimeZoneApi(@QueryParam("location") String location, @QueryParam("timestamp") long timestamp ) {
    System.out.println("Call endpoint v1/timezone");

//    String realm = accessTokenParser.validateRole("user", "superadmin");
    String realm = "";

    GoogleTimezone timeZone = googleApiService.retrieveGoogleTimeZoneApi(realm, location, timestamp);
    System.out.println("timezone: "+timeZone.toString());
    System.out.println("RESPOSNE FROM GOOGLE API: "+ timeZone.getTimeZoneId());

    return Response.ok(timeZone, MediaType.APPLICATION_JSON).build();
  }

  @GET
  @Path("v1/geocode")
  @NoCache
  @Produces(MediaType.APPLICATION_JSON)
  public Response retrieveGoogleAddressApi(@QueryParam("address") String address) {

    String realm = accessTokenParser.validateRole("user", "superadmin");

    List<AddressResp> addresses = googleApiService.retrieveGoogleAddressApi(realm, address);

    return Response.ok(addresses, MediaType.APPLICATION_JSON).build();

  }



}
