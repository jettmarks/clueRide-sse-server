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

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;

/**
 * Accepts requests to subscribe to all SSE Events.
 */
@Singleton
@Path("sse-channel")
public class ChannelWebService {
    private static Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private CommonChannelService commonChannelService = new CommonChannelServiceImpl();

    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput subscribeToGenericChannel(
           @QueryParam("r") final String requestId
    ) {
        LOGGER.debug("Generic SSE Channel request: " + requestId);
        ServerSentEventChannel channel = commonChannelService.openChannelResources();
        SseBroadcaster broadcaster = channel.getBroadcaster();
        EventOutput eventOutput = new EventOutput();
        broadcaster.add(eventOutput);
        return eventOutput;
    }

}
