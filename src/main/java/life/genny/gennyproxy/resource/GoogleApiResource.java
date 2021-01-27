package life.genny.gennyproxy.resource;

import life.genny.gennyproxy.application.AccessTokenParser;
import life.genny.gennyproxy.model.address.Addresses;
import life.genny.gennyproxy.service.GoogleApiService;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    accessTokenParser.validateRole("user", "superadmin");

    String respGoogleMapJs = googleApiService.retrieveGoogleMapApi();

    return Response.ok(respGoogleMapJs, MediaType.TEXT_PLAIN).build();
  }

  @GET
  @Path("v1/timezone")
  @NoCache
  @Produces(MediaType.APPLICATION_JSON)
  public Response retrieveGoogleTimeZoneApi(@QueryParam String location, @QueryParam long timestamp ) {

    accessTokenParser.validateRole("user", "superadmin");

    String timeZoneId = googleApiService.retrieveGoogleTimeZoneApi(location, timestamp);

    return Response.ok(timeZoneId, MediaType.TEXT_PLAIN).build();
  }

  @GET
  @Path("v1/geocode")
  @NoCache
  @Produces(MediaType.APPLICATION_JSON)
  public Response retrieveGoogleAddressApi(@QueryParam String address) {

    accessTokenParser.validateRole("user", "superadmin");

    Addresses addresses = googleApiService.retrieveGoogleAddressApi(address);

    return Response.ok(addresses, MediaType.APPLICATION_JSON).build();

  }



}
