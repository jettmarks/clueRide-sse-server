/*
 * Copyright 2020 Jett Marks
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
 * Created by jett on 2/8/20.
 */
package com.clueride.sse.channel;

import java.lang.invoke.MethodHandles;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.server.ChunkedOutput;

/**
 * Extension of Broadcaster that can respond to the Close Events.
 */
public class ChannelBroadcaster extends SseBroadcaster {
    private static Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private final UserChannel userChannel;
    private final String requestIdMessage;

    private final UserChannelService userChannelService = new UserChannelServiceImpl();

    /**
     * When invoking the super() without an argument, the SseBroadcaster assumes
     * this class is overriding the {@link @onClose()} & {@link @onException} methods.
     *
     * @see
     * <a href="https://stackoverflow.com/questions/32770635/broadcasting-with-jersey-sse-detect-closed-connection">
     *   this post for additional details
     * </a>
     */
    @SuppressWarnings("WeakerAccess")
    protected ChannelBroadcaster(
            UserChannel userChannel
    ) {
        super();
        this.userChannel = userChannel;
        this.requestIdMessage = "  Request ID: " + userChannel.getRequestId();
    }

    @Override
    public void onClose(ChunkedOutput<OutboundEvent> chunkedOutput) {
        LOGGER.debug("Closing channel for user " + userChannel.getBadgeOsId());
        super.onClose(chunkedOutput);
        userChannelService.closeUserChannel(chunkedOutput);
    }

    @Override
    public void onException(ChunkedOutput<OutboundEvent> chunkedOutput, Exception exception) {
        String exceptionMessage = exception.getMessage();
        /* No need to report something that will get reported in the 'onClose' method. */
        if (!exceptionMessage.contains("Connection is closed")) {
            LOGGER.error("Exception found on channel for user ID: "
                    + userChannel.getBadgeOsId() + requestIdMessage);
            LOGGER.error(exception.getMessage());
        }
        super.onException(chunkedOutput, exception);
    }

}
