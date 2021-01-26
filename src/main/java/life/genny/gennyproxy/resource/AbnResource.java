package life.genny.gennyproxy.resource;

import life.genny.gennyproxy.model.abn.AbnSearchResult;
import life.genny.gennyproxy.application.AccessTokenParser;
import life.genny.gennyproxy.application.ApiKeyRetriever;
import life.genny.gennyproxy.service.AbnLookupService;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/json")
public class AbnResource {

  @Inject
  private AbnLookupService abnLookupService;

  @Inject
  private AccessTokenParser accessTokenParser;

  @Inject
  private ApiKeyRetriever apiKeyRetriever;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response retrieveCompanyAbn(@QueryParam String name, @QueryParam int size) throws Exception {

    accessTokenParser.validateRole("user", "superadmin");

    AbnSearchResult abnSearchResult = abnLookupService.retrieveCompanyAbn(name, size);
    return Response.ok(abnSearchResult, MediaType.APPLICATION_JSON).build();
  }


}
