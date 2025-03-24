package org.qa.network;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.Nullable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NetworkClient {

    private static final int DEFAULT_TIMEOUT_SECONDS = 30;
    private static final String APPLICATION_JSON = "application/json";

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public NetworkClient() {
        client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();
        objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
    }


    public <T> T execute(String url, HttpMethods method, @Nullable Object requestBody, @Nullable Map<String, String> headers, Class<T> responseClass) throws NetworkException {
        Request request = buildRequest(url, requestBody, headers, method);
        try (Response response = client.newCall(request).execute()) {
            handleResponse(response);
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String bodyString = responseBody.string();
                if (!bodyString.isEmpty()) {
                    log.info("Response JSON: {}", bodyString);
                    try {
                        return objectMapper.readValue(bodyString, responseClass); // Map the JSON response
                    } catch (IOException e) {
                        throw new NetworkException("Mapping failed: " + e.getMessage() + " for body: " + bodyString, e);
                    }
                }
            }

            // Handle void or empty responses
            if (responseClass.equals(Void.class) || responseClass.equals(Void.TYPE)) {
                return null;
            } else {
                try {
                    return responseClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new NetworkException("Could not create an instance of: " + responseClass.getName(), e);
                }
            }
        } catch (IOException e) {
            throw new NetworkException("Network request failed: " + e.getMessage(), e);
        }
    }

    private Request buildRequest(String url, @Nullable Object requestBody, @Nullable Map<String, String> headers, HttpMethods method) {
        Map<String, String> finalHeaders = new HashMap<>();
        finalHeaders.put("Content-Type", APPLICATION_JSON);
        finalHeaders.put("Accept", APPLICATION_JSON);
        if (headers != null) {
            finalHeaders.putAll(headers);
        }

        Request.Builder requestBuilder = new Request.Builder().url(url);
        finalHeaders.forEach(requestBuilder::addHeader);

        RequestBody body = null;
        if (requestBody != null) {
            try {
                String jsonBody = objectMapper.writeValueAsString(requestBody);
                body = RequestBody.create(jsonBody, MediaType.parse(APPLICATION_JSON));
                logRequest(method, url, jsonBody);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            logRequest(method, url, null);
        }

        switch (method) {
            case GET:
                requestBuilder.get();
                break;
            case POST:
                requestBuilder.post(Objects.requireNonNullElseGet(body, () -> RequestBody.create(new byte[0], null)));
                break;
            case PUT:
                if (body != null) {
                    requestBuilder.put(body);
                }
                break;
            case DELETE:
                requestBuilder.delete(body);
                break;
            default:
                throw new IllegalArgumentException("Unknown HTTP method: " + method);
        }

        return requestBuilder.build();
    }

    private void handleResponse(Response response) throws NetworkException {
        if (!response.isSuccessful()) {
            String errorBody;
            try {
                errorBody = response.body() != null ? response.body().string() : "No error details";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            log.error("Request failed with code {}: {}", response.code(), errorBody);
            throw new NetworkException(String.format("HTTP request failed with code %d", response.code()), response.code());
        }

        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            throw new NetworkException("Empty response body", response.code());
        }
    }

    private void logRequest(HttpMethods method, String url, String jsonBody) {
        log.info("[{}] URL: {}", method, url);
        if (jsonBody != null) {
            log.info("[{}] Payload: {}", method, jsonBody);
        }
    }

    @Getter
    public static class NetworkException extends RuntimeException {
        private final int httpStatusCode;

        public NetworkException(String message, int httpStatusCode) {
            super(message);
            this.httpStatusCode = httpStatusCode;
        }

        public NetworkException(String message, Throwable cause) {
            super(message, cause);
            this.httpStatusCode = -1;
        }
    }
}
