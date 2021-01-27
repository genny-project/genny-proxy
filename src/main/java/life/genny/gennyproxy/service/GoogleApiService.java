package life.genny.gennyproxy.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import life.genny.gennyproxy.application.IApiKeyRetriever;
import life.genny.gennyproxy.model.address.Addresses;
import life.genny.gennyproxy.model.timezone.TimezoneResp;
import life.genny.gennyproxy.repository.GoogleAddressRepository;
import life.genny.gennyproxy.repository.GoogleMapRepository;
import life.genny.gennyproxy.repository.TimezoneRepository;


@ApplicationScoped
public class GoogleApiService {

    @Inject
    private GoogleMapRepository googleMapRepository;

    @Inject
    private TimezoneRepository timezoneRepository;

    @Inject
    private GoogleAddressRepository googleAddressRepository;

    @Inject
    @Named("byEnv") //byInfinispan byEnv
    private IApiKeyRetriever apiKeyRetriever;

    public String retrieveGoogleMapApi() {

        String apiKey = apiKeyRetriever.retrieveApiKey("ENV_GOOGLE_MAPS_APIKEY_", "ENV_GOOGLE_MAPS_APIKEY_DEFAULT");

        return googleMapRepository.retrieveGoogleMap(apiKey);
    }

    public String retrieveGoogleTimeZoneApi(String location, long timestamp) {

        String apiKey = apiKeyRetriever.retrieveApiKey("ENV_GOOGLE_TIMEZONE_APIKEY_", "ENV_GOOGLE_TIMEZONE_APIKEY_DEFAULT");

        TimezoneResp timezoneResp = timezoneRepository.retrieveGoogleMap(location, timestamp, apiKey);

        return timezoneResp.getTimeZoneId();
    }

    public Addresses retrieveGoogleAddressApi(String address){

        String apiKey = apiKeyRetriever.retrieveApiKey("ENV_GOOGLE_TIMEZONE_APIKEY_", "ENV_GOOGLE_TIMEZONE_APIKEY_DEFAULT");

        return googleAddressRepository.retrieveGoogleMap(address, apiKey);
    }
}
