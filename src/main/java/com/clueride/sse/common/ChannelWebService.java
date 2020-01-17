/*
 * Copyright 2019 Jett Marks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by jett on 3/6/19.
 */
package com.clueride.sse.common;

import java.lang.invoke.MethodHandles;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;

/**
 * Accepts requests to subscribe to SSE Events both
 * with or without an Outing ID.
 */
@Singleton
@Path("sse-channel")
public class ChannelWebService {
    private static Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private CommonChannelService commonChannelService = new CommonChannelServiceImpl();

    /**
     * Request subscription to SSE Events outside of a specific Outing.
     *
     * This is typically used by Ranger to listen for Badge Events.
     * TODO: SSE-17 Isn't this per user regardless of Outing?
     *
     * @param requestId unique identifier for the request, suitable for retries.
     * @return EventOutput instance that holds open the session.
     */
    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput subscribeToGenericChannel(
           @QueryParam("r") final String requestId
    ) {
        LOGGER.debug("Generic SSE Channel request: " + requestId);
        return commonChannelService.getGenericEventOutput();
    }

    /**
     * This call establishes a persistent session for the SSE channel to publish events to
     * the clients for the specified Outing.
     *
     * @param outingId - Unique identifier for the Outing whose events we want to listen for.
     * @param requestId - Unique identifier for this connection request.
     * @return EventOutput instance which -- in the hands of the client -- maintains the open connection.
     */
    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    @Path("{outingId}")
    public EventOutput subscribeToOutingIdChannel(
            @PathParam("outingId") Integer outingId,
            @QueryParam("r") final String requestId
    ) {
        LOGGER.debug(
                "Outing ID " + outingId +
                " SSE Channel request " + requestId
        );
        return commonChannelService.getEventOutputForOuting(outingId);
    }

    // TODO: This looks like one of the calls for SSE-7
    @GET
    @Path("map")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<Integer, CommonChannel> getChannelMap() {
        return commonChannelService.getChannelMap();
    }

}
