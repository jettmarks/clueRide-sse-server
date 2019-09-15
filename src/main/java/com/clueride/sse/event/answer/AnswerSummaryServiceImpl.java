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
 * Created by jett on 3/7/19.
 */
package com.clueride.sse.event.answer;

import java.lang.invoke.MethodHandles;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.sse.OutboundEvent;

import com.clueride.sse.common.CommonChannelService;
import com.clueride.sse.common.CommonChannelServiceImpl;
import com.clueride.sse.common.ServerSentEventChannel;

/**
 * Implementation of {@link AnswerSummaryService}.
 */
public class AnswerSummaryServiceImpl implements AnswerSummaryService {
    private static Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private CommonChannelService commonChannelService = new CommonChannelServiceImpl();
    private AnswerSummaryEventFactory answerSummaryEventFactory = new AnswerSummaryEventFactory();

    @Override
    public String broadcastMessage(
            Integer outingId,
            String message
    ) {
        LOGGER.debug(
                String.format("Broadcasting on Outing ID %d: %s",
                        outingId,
                        message
                )
        );

        ServerSentEventChannel channel = commonChannelService.getEventChannel(
                outingId
        );

        OutboundEvent event = answerSummaryEventFactory.generateEvent(message);
        channel.getBroadcaster().broadcast(event);

        channel.getKeepAliveGenerator().reset();

        return event.getId();
    }

}
