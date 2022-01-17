package life.genny.gennyproxy.service;

import io.vertx.mutiny.core.MultiMap;
import io.vertx.mutiny.ext.web.client.WebClient;
import java.time.Duration;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class BridgeProxyService {

  @Inject private WebClient webClient;

  @ConfigProperty(name = "quarkus.bridge.api.host")
  private String host;

  @ConfigProperty(name = "quarkus.bridge.api..detail.path")
  private String bridgeDetailPath;

  @ConfigProperty(name = "quarkus.bridge.api.port")
  private int port;

  @ConfigProperty(name = "quarkus.bridge.api.service.token")
  private String token;

  public String proxyPostToBridge(Object obj) {
    return webClient
        .post(host, bridgeDetailPath)
        .putHeader("Authorization", String.format("Bearer %s", token))
        .port(port)
        .send()
        .await()
        .atMost(Duration.ofSeconds(15))
        .bodyAsString();
  }

  public String proxyToBridge(MultiMap body) {
    return webClient
        .get(host, bridgeDetailPath)
        .putHeader("Authorization", String.format("Bearer %s", token))
        .port(port)
        .sendForm(body)
        .await()
        .atMost(Duration.ofSeconds(15))
        .bodyAsString();
  }
}
