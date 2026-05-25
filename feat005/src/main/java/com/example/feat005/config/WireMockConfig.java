package com.example.feat005.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Configuration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Configuration
public class WireMockConfig {

    private WireMockServer wireMockServer;

    @PostConstruct
    public void startWireMock() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8089));
        wireMockServer.start();

        // /api/mock/success: 즉시 200 응답
        wireMockServer.stubFor(get(urlEqualTo("/api/mock/success"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Success Response")));

        // /api/mock/slow: 10초 지연 (타임아웃 유발)
        wireMockServer.stubFor(get(urlEqualTo("/api/mock/slow"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withFixedDelay(10000)
                        .withBody("Slow Response")));

        // /api/mock/error: 500 에러 반복 (재시도/서킷브레이커 유발)
        wireMockServer.stubFor(get(urlEqualTo("/api/mock/error"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Internal Server Error")));
    }

    @PreDestroy
    public void stopWireMock() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
}
