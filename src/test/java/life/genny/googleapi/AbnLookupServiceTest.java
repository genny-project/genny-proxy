package life.genny.googleapi;

import io.quarkus.test.junit.QuarkusTest;
import life.genny.gennyproxy.model.abn.AbnSearchResult;
import life.genny.gennyproxy.service.AbnLookupService;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
public class AbnLookupServiceTest {


    @Inject
    AbnLookupService abnLookupService;

    @Test
    public void retrieveCompanyAbn_passValidParameter_return200() throws  Exception {
        AbnSearchResult abnSearchResult = abnLookupService.retrieveCompanyAbn("NAB", 20);
        System.out.println(abnSearchResult);
    }
}