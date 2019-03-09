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
package com.clueride.sse.answer;

import java.lang.invoke.MethodHandles;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;

import com.clueride.sse.common.CommonChannelService;
import com.clueride.sse.common.CommonChannelServiceImpl;
import com.clueride.sse.common.ServerSentEventChannel;

/**
 * REST API for subscribing to and broadcasting to the Puzzle Events.
 */
@Singleton
@Path("answer-summary")
public class AnswerSummaryWebService {
    private static Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private AnswerSummaryService answerSummaryService = new AnswerSummaryServiceImpl();

    private CommonChannelService commonChannelService = new CommonChannelServiceImpl();

    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    @Path("{outingId}")
    public EventOutput subscribeToOutingIdChannel(
            @PathParam("outingId") Integer outingId,
            @QueryParam("r") final String requestId
    ) {
        LOGGER.debug("Outing ID: " + outingId);

        ServerSentEventChannel channel = commonChannelService.getEventChannel(
                outingId
        );
        SseBroadcaster broadcaster = channel.getBroadcaster();

        EventOutput eventOutput = new EventOutput();
        broadcaster.add(eventOutput);
        return eventOutput;
    }

    @POST
    @Path("{outingId}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String broadcastMessage(
            String answerSummary,
            @PathParam("outingId") Integer outingId
    ) {
        String messageId = answerSummaryService.broadcastMessage(outingId, answerSummary);
        return "Message ID '" + messageId + "' has been broadcast.";
    }

}
