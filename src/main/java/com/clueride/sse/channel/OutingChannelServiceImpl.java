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

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.server.Broadcaster;

/**
 * Default implementation of {@link OutingChannelService}.
 */
public class OutingChannelServiceImpl implements OutingChannelService {
    private static Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

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

        Integer userChannelCount = getChunkedOutputsSize(outingChannel.getBroadcaster());
        LOGGER.debug("During request for OutingChannel, it has this many ChunkedOutputs: " + userChannelCount);

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

        Integer userChannelCount = getChunkedOutputsSize(broadcaster);
        LOGGER.debug("After adding, Outing Channel has this many ChunkedOutputs: " + userChannelCount);
    }

    @Override
    public void removeUserChannelFromOuting(UserChannel userChannel) {
        /* Make sure we have a channel for this user. */
        Integer outingId = userChannel.getOutingId();
        Integer userId = userChannel.getBadgeOsId();
        if (!userChannelById.keySet().contains(userId)) {
            LOGGER.warn("Requesting to remove channel for user who hasn't got one for any outing");
            return;
        }

        if (!outingIdByUserId.keySet().contains(userId)) {
            LOGGER.warn("Requesting to update Outing for user who isn't registered with one.");
            return;
        }

        LOGGER.debug("Dropping user channel for " + userChannel.getRequestId());

        /* The broadcaster for the outing we're working with. */
        SseBroadcaster broadcaster = getOutingBroadcaster(outingId);

        synchronized (outingChannelById) {
            List<UserChannel> userChannelsOriginallySubscribed = getUserChannelsForOuting(outingId);

            /* Clear all previous. */
            for (UserChannel currentChannel : userChannelsOriginallySubscribed) {
                broadcaster.remove(currentChannel.getEventOutput());
            }

            /* Clear from each of the lists. */
            userChannelById.remove(userId);
            outingIdByUserId.remove(userId);

            List<UserChannel> userChannelsStillSubscribed = getUserChannelsForOuting(outingId);
            /* Now re-add back in remaining channels. */

            for (UserChannel remainingChannel : userChannelsStillSubscribed) {
                broadcaster.add(remainingChannel.getEventOutput());
            }
        }

        Integer userChannelCount = getChunkedOutputsSize(broadcaster);
        LOGGER.debug("After removal, Outing Channel has this many ChunkedOutputs: " + userChannelCount);
    }

    /**
     * Uses reflection to read the private value of the `chunkedOutputs` field.
     * @param sseBroadcaster Broadcaster that holds zero or more ChunkedOutputs.
     */
    private Integer getChunkedOutputsSize(SseBroadcaster sseBroadcaster) {
        Integer countChunkedOutputs = null;
        try {
            Field fieldChunkedOutputs = Broadcaster.class.getDeclaredField("chunkedOutputs");
            Field fieldListeners = Broadcaster.class.getDeclaredField("listeners");
            fieldChunkedOutputs.setAccessible(true);
            fieldListeners.setAccessible(true);
            ConcurrentLinkedQueue concurrentLinkedQueue = (ConcurrentLinkedQueue) fieldChunkedOutputs.get(sseBroadcaster);
            for (Object o : concurrentLinkedQueue) {
                LOGGER.debug(o);
            }
            countChunkedOutputs = ((ConcurrentLinkedQueue)fieldChunkedOutputs.get(sseBroadcaster)).size();
        } catch(Exception e) {
            LOGGER.error("Wasn't expecting this", e);
        }
        return countChunkedOutputs;
    }

    private List<UserChannel> getUserChannelsForOuting(Integer outingId) {
        List<UserChannel> userChannels = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : outingIdByUserId.entrySet()) {
            if (entry.getValue().equals(outingId)) {
                userChannels.add(userChannelById.get(entry.getKey()));
            }
        }
        return userChannels;
    }

}
