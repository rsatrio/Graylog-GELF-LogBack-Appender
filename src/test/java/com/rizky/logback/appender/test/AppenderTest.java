package com.rizky.logback.appender.test;
import kong.unirest.Unirest;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.mock.Expectation;
import org.mockserver.model.Format;
import org.mockserver.model.RequestDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppenderTest {

    private static ClientAndServer mockServer;

    @BeforeClass
    public static void startServer() {
        mockServer = startClientAndServer(12501);
    }

    @AfterClass 
    public static void stopServer() { 
        mockServer.stop();
    }

    @Test
    public void Test1Appender() throws Exception {


        Logger log1=LoggerFactory.getLogger(AppenderTest.class);
        log1.info("testing123");
        log1.info("testing1234");
        

        String requests = mockServer
        .retrieveRecordedRequests(
                request().withPath("/gelf").withMethod("POST"),Format.JAVA
                );

        Assert.assertThat(requests.contains("host001"), CoreMatchers.is(true));
    }

}
