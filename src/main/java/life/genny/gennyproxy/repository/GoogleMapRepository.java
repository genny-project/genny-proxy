package life.genny.gennyproxy.repository;

import io.vertx.mutiny.ext.web.client.WebClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;

@ApplicationScoped
public class GoogleMapRepository {

    @Inject
    private WebClient webClient;

    @ConfigProperty(name = "quarkus.google.api.map.path")
    private String mapPath;


    public String retrieveGoogleMap(String apiKey){
        return webClient.get(mapPath)
                .setQueryParam("key", apiKey)
                      /*
                 * The google api takes a paramter 'callback' that gets called
                 * when alyson loads the maps API. We don't use this feature,
                 * but the api throws an error when callback is not included.
                 * 
                 * To address this, passing 'Function.prototype' into 'callback' means
                 * that nothing will happen on alyson when the callback is called. (Function.prototype is a noop built into js)
                 */
                .setQueryParam("callback", "Function.prototype")
                .setQueryParam("libraries","places,drawing")
                .send()
                .await()
                .atMost(Duration.ofSeconds(15))
                .bodyAsString();
    }




}
