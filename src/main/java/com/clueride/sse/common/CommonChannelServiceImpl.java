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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseBroadcaster;

/**
 * Implementation of {@link CommonChannelService}.
 *
 */
public class CommonChannelServiceImpl implements CommonChannelService {

    /* Map of the channel for each User. */
    private static final Map<Integer,ServerSentEventChannel> channelPerUser = new HashMap<>();
    /* Map of the channel for each Outing. */
    private static final Map<Integer, ServerSentEventChannel> channelPerOuting = new HashMap<>();
    /* Map of the users for each Outing. */
    private static final Map<Integer, List<Integer>> usersPerOuting = new HashMap<>();

    @Override
    public Map<Integer, ServerSentEventChannel> getOutingChannelMap() {
        return channelPerOuting;
    }

    @Override
    public ServerSentEventChannel getOutingChannel(Integer outingId) {
        return channelPerOuting.get(outingId);
    }

    @Override
    public void addUserEventOutput(Integer badgeOsId, EventOutput eventOutput) {

        if (channelPerUser.containsKey(badgeOsId)) {
            /* We already have a channel for this user; nothing to be done. */
            return;
        }

        /* Create a channel for the user and add the output to its broadcaster. */
        ServerSentEventChannel serverSentEventChannel = new ServerSentEventChannel();
        channelPerUser.put(badgeOsId, serverSentEventChannel);
        serverSentEventChannel.getBroadcaster().add(eventOutput);
    }

    @Override
    public void addOutingEventOutput(Integer badgeOsId, Integer outingId, EventOutput eventOutput) {
        /* First, check if this outing has been requested before. */
        if (usersPerOuting.containsKey(outingId)) {
            if (usersPerOuting.get(outingId).contains(badgeOsId)) {
                /* We've already handled this user for this outing; nothing to do. */
                return;
            } else {
                usersPerOuting.get(outingId).add(badgeOsId);
                channelPerOuting.get(outingId).getBroadcaster().add(eventOutput);
            }
        } else {
            /* Need to create new Channel for this outing. */
            ServerSentEventChannel channel = new ServerSentEventChannel(outingId);
            channelPerOuting.put(outingId, channel);
            channelPerOuting.get(outingId).getBroadcaster().add(eventOutput);
            usersPerOuting.put(outingId, new ArrayList<Integer>());
            usersPerOuting.get(outingId).add(badgeOsId);
        }
    }

    /* Not clear if we'll keep this, but maybe it will be useful. */
    public void releaseChannelResources(Integer outingId) {
        ServerSentEventChannel channel = channelPerOuting.get(outingId);
        SseBroadcaster broadcaster = channel.getBroadcaster();
//        broadcaster.broadcast(eventBundler.getClosingMessage());
        channel.getKeepAliveGenerator().release();
    }

}
