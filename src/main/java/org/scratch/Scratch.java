package org.scratch;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.Duration.ofSeconds;

class Scratch {
    private static final Duration DEFAULT_HTTP_TIMEOUT = ofSeconds(10);
    

    public static void main(String[] args) throws Exception {

        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

                    SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());

        HttpClient httpClient =
                HttpClient.newBuilder()
                        .sslContext(sc)
                        .connectTimeout(DEFAULT_HTTP_TIMEOUT)
                        .build();

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(new URI("https://gitlab.cee.redhat.com/api/v4/user"))
                        .build();

        HttpResponse<String> result = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(result.toString());
    }
}