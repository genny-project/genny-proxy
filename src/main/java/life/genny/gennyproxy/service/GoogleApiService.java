package life.genny.gennyproxy.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import life.genny.gennyproxy.application.IApiKeyRetriever;
import life.genny.gennyproxy.mapper.AddressRespMapper;
import life.genny.gennyproxy.model.address.AddressResp;
import life.genny.gennyproxy.repository.entity.address.Addresses;
import life.genny.gennyproxy.repository.entity.timezone.GoogleTimezone;
import life.genny.gennyproxy.repository.GoogleAddressRepository;
import life.genny.gennyproxy.repository.GoogleMapRepository;
import life.genny.gennyproxy.repository.TimezoneRepository;

import java.util.List;


@ApplicationScoped
public class GoogleApiService {

    @Inject
    private GoogleMapRepository googleMapRepository;

    @Inject
    private TimezoneRepository timezoneRepository;

    @Inject
    private GoogleAddressRepository googleAddressRepository;

    @Inject
    private AddressRespMapper addressRespMapper;

    @Inject
    @Named("byEnv")
    //@Named("byInfinispan")
    private IApiKeyRetriever apiKeyRetriever;

    public String retrieveGoogleMapApi(String realm) {

        String apiKey = apiKeyRetriever.retrieveApiKey("ENV_GOOGLE_MAPS_APIKEY", "ENV_GOOGLE_MAPS_APIKEY");
        System.out.println("ENV_GOOGLE_MAPS_APIKEY:" + apiKey);

        return googleMapRepository.retrieveGoogleMap(apiKey);
    }

    // timezone id value example Australia/Melbourne
    public GoogleTimezone retrieveGoogleTimeZoneApi(String realm, String location, long timestamp) {

        String apiKey = apiKeyRetriever.retrieveApiKey("ENV_GOOGLE_TIMEZONE_APIKEY", "ENV_GOOGLE_TIMEZONE_APIKEY");
        System.out.println("ENV_GOOGLE_TIMEZONE_APIKEY:" + apiKey);

        return timezoneRepository.retrieveGoogleMap(location, timestamp, apiKey);
        /*
        GoogleTimezone googleTimezone = timezoneRepository.retrieveGoogleMap(location, timestamp, apiKey);
        return googleTimezone.getTimeZoneId();
         */
    }

    public List<AddressResp> retrieveGoogleAddressApi(String realm, String address){

        String apiKey = apiKeyRetriever.retrieveApiKey("ENV_GOOGLE_TIMEZONE_APIKEY_"+realm, "ENV_GOOGLE_TIMEZONE_APIKEY");

        Addresses addresses = googleAddressRepository.retrieveGoogleMap(address, apiKey);

        return addressRespMapper.map(addresses);

    }
}
