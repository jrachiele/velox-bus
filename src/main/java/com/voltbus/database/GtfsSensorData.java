package com.voltbus.database;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.time.temporal.Temporal;

import com.google.transit.realtime.GtfsRealtime.FeedMessage;
import com.voltbus.network.Request;
import com.voltbus.network.Response;
import com.voltbus.network.VoltHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 * Created by jacob on 3/20/16.
 */
final class GtfsSensorData  {

    private final FeedMessage data;
    private final long timestamp;

    static final GtfsSensorData newSensorData(final Instant instant, final Response<byte[]> response) {
        return new GtfsSensorData(instant, response);
    }

    private GtfsSensorData(final Instant instant, final Response<byte[]> response) {
        this.timestamp = instant.getEpochSecond();
        this.data = retrieveData(response);
    }

    private final FeedMessage retrieveData(final Response<byte[]> response) {
        try {
            return FeedMessage.parseFrom((byte[])response.responseBody());
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            // empty message
            return FeedMessage.getDefaultInstance();
        }
    }

    final FeedMessage data() {
        return this.data;
    }
    
    final long timestamp() {
    	return this.timestamp;
    }

}
