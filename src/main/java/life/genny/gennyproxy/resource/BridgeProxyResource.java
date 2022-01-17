package life.genny.gennyproxy.resource;

import io.vertx.mutiny.core.MultiMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import life.genny.gennyproxy.service.BridgeProxyService;

@Path("/")
public class BridgeProxyResource {

  @Inject BridgeProxyService serv;

  @Context UriInfo uriInfo;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/api/b2bdata")
  public Response apiB2BHandlerPost(Object dataMsg) {
    serv.proxyPostToBridge(dataMsg);

    return Response.ok().build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/api/b2bdata")
  public Response apiB2BHandlerGet() {
    MultiMap multiMap = MultiMap.caseInsensitiveMultiMap();
    Map<String, String> queryParams =
        uriInfo.getQueryParameters().entrySet().stream()
            .collect(
                Collectors.toMap(
                    k -> k.getKey(), v -> v.getValue().stream().findFirst().orElse("NULL")));
    multiMap.addAll(queryParams);
    serv.proxyToBridge(multiMap);
    return Response.ok().build();
  }
}
