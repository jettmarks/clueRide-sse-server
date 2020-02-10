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
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ChunkedOutput;

/**
 * Default implementation of {@link UserChannelService}.
 */
public class UserChannelServiceImpl implements UserChannelService {
    private static Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static Map<ChunkedOutput, UserChannel> channelPerChunkedOutput = new HashMap<>();
    private static Map<Integer, ChunkedOutput> chunkedOutputPerUserId = new HashMap<>();
    private final OutingChannelService outputChannelService = new OutingChannelServiceImpl();

    @Override
    public UserChannel getUserChannel(Integer badgeOsId, String requestId) {
        /* If we have a channel mapped for this user, release it; the user doesn't want the old one anymore. */
        if (chunkedOutputPerUserId.keySet().contains(badgeOsId)) {
            ChunkedOutput oldChunkedOutput = chunkedOutputPerUserId.get(badgeOsId);
            closeUserChannel(oldChunkedOutput);
            chunkedOutputPerUserId.remove(badgeOsId);
        }

        /* Create a new one as if this is the first time we've ever interacted with the user. */
        UserChannel userChannel = new UserChannel(badgeOsId, requestId);

        /* Use the ChunkedOutput as index for finding UserChannel instance when we want to close. */
        ChunkedOutput eventOutput = userChannel.getEventOutput();
        channelPerChunkedOutput.put(eventOutput, userChannel);
        chunkedOutputPerUserId.put(badgeOsId, eventOutput);

        /* Complete initialization. */
        userChannel.setRequestId(requestId);
        userChannel.start();

        return userChannel;
    }

    @Override
    public String closeUserChannel(ChunkedOutput chunkedOutput) {
        String originalRequestId = "unknown";
        UserChannel userChannel = channelPerChunkedOutput.get(chunkedOutput);

        if (userChannel == null) {
            LOGGER.warn("No Channel found for chunked output");
        } else {
            originalRequestId = userChannel.getRequestId();
            userChannel.getKeepAliveGenerator().release();
            if (userChannel.getOutingId() != null) {
                /* Only attempt to remove this UserChannel if we have an outing. */
                outputChannelService.removeUserChannelFromOuting(userChannel);
            }
            channelPerChunkedOutput.remove(chunkedOutput);
        }

        return originalRequestId;
    }

}
