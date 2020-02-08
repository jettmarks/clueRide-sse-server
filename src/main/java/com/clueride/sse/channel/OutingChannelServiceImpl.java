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
 * Created by jett on 2/6/20.
 */
package com.clueride.sse.channel;

import java.util.HashMap;
import java.util.Map;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseBroadcaster;

/**
 * Default implementation of {@link OutingChannelService}.
 */
public class OutingChannelServiceImpl implements OutingChannelService {

    private static Map<Integer, OutingChannel> outingChannelById = new HashMap<>();
    private static Map<Integer, UserChannel> userChannelById = new HashMap<>();
    private static Map<Integer, Integer> outingIdByUserId = new HashMap<>();

    @Override
    public SseBroadcaster getOutingBroadcaster(Integer outingId) {

        OutingChannel outingChannel = outingChannelById.get(outingId);
        if (outingChannel == null) {
            outingChannel = new OutingChannel(outingId);
            outingChannelById.put(outingId, outingChannel);
        }
        return outingChannel.getBroadcaster();
    }

    /**
     * This clears any previous ChunkedOutput that may have been previously
     * assigned for the user before adding the new one. This serves to clear
     * out stale connections.
     */
    @Override
    public void addUserChannelToOuting(UserChannel userChannel, Integer outingId) {
        /* The broadcaster for the outing we're working with. */
        SseBroadcaster broadcaster = getOutingBroadcaster(outingId);

        /* Check for old EventOutput and remove old one if we have one. */
        if (userChannelById.containsKey(userChannel.getBadgeOsId())) {
            EventOutput oldEventOutput = userChannelById.get(userChannel.getBadgeOsId()).getEventOutput();
            broadcaster.remove(oldEventOutput);
        }
        userChannelById.put(userChannel.getBadgeOsId(), userChannel);

        /* Record the outing this user is participating in. */
        outingIdByUserId.put(userChannel.getBadgeOsId(), outingId);

        /* Add the EventOutput to the OutingChannel's broadcaster. */
        broadcaster.add(userChannel.getEventOutput());
    }

    @Override
    public void removeUserChannelFromOuting(UserChannel userChannel) {
        /* Find the outing this user had been participating in. */
        Integer userId = userChannel.getBadgeOsId();
        Integer outingId = outingIdByUserId.get(userId);

        /* The broadcaster for the outing we're working with. */
        SseBroadcaster broadcaster = getOutingBroadcaster(outingId);

        if (userChannelById.containsKey(userId)) {
            EventOutput oldEventOutput = userChannelById.get(userId).getEventOutput();
            broadcaster.remove(oldEventOutput);
            userChannelById.remove(userId);
            outingIdByUserId.remove(userId);
        }

    }

}
