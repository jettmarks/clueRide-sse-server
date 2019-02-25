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
 * Created by jett on 2/24/19.
 */
package com.clueride.sse.heartbeat;

import java.lang.invoke.MethodHandles;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;

import com.clueride.sse.common.ServerSentEventChannel;

/**
 * Broadcasts a Heartbeat to subscribers to verify a connection
 * with the server is still alive.
 *
 * For subscribers providing an Outing ID, the heartbeat message will
 * include tethered position for that Outing.
 */
@Singleton
@Path("heartbeat")
public class HeartbeatWebService {
    private static Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private HeartbeatService heartbeatService = new HeartbeatServiceImpl();

    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput subscribeToHeartbeat(
            @QueryParam("r") final String requestId
    ) {
        LOGGER.debug("Heartbeat subscribe - requestId: " + requestId);
        ServerSentEventChannel channel = heartbeatService.openChannelResources(null);
        SseBroadcaster broadcaster = channel.getBroadcaster();
        EventOutput eventOutput = new EventOutput();
        broadcaster.add(eventOutput);
        return eventOutput;
    }

}
