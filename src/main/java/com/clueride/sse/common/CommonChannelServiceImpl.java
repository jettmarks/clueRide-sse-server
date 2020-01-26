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

import java.util.HashMap;
import java.util.Map;

import org.glassfish.jersey.media.sse.SseBroadcaster;

/**
 * Implementation of {@link CommonChannelService}.
 *
 * TODO: The Map of channels opened would serve as a good way to implement SSE-7.
 */
public class CommonChannelServiceImpl implements CommonChannelService {

    /* The single channel used when there is no outing. */
    private static ServerSentEventChannel noOutingEventChannel = null;
    private static final Integer NO_OUTING = -1;

    /* Map of the channel for each Outing. */
    private static final Map<Integer,CommonChannel> channelsPerOuting = new HashMap<>();
    /* Map of the channel for each User. */
    private static final Map<Integer,CommonChannel> channelPerUser = new HashMap<>();

    @Override
    public ServerSentEventChannel getOutingChannel(Integer outingId) {
        // TODO: SSE-19: Are we going to need CommonChannel?
        CommonChannel channel = channelsPerOuting.get(outingId);

        if (!channelsPerOuting.containsKey(outingId)) {
            channel = new CommonChannel(
                    new ServerSentEventChannel(outingId),
                    outingId
            );

            channelsPerOuting.put(
                    outingId,
                    channel
            );
        }
        return channelsPerOuting.get(outingId).getServerSentEventChannel();
    }

    @Override
    public ServerSentEventChannel getUserChannel(Integer badgeOsId) {
        CommonChannel channel = channelPerUser.get(badgeOsId);

        if (!channelPerUser.containsKey(badgeOsId)) {
            channel = new CommonChannel(
                    new ServerSentEventChannel(NO_OUTING),
                    NO_OUTING
            );

            channelPerUser.put(badgeOsId, channel);
        }
        return channelPerUser.get(badgeOsId).getServerSentEventChannel();
    }

    @Override
    public Map<Integer, CommonChannel> getOutingChannelMap() {
        return channelsPerOuting;
    }

    @Override
    public Map<Integer, CommonChannel> getUserChannelMap() {
        return channelPerUser;
    }

    /* Not clear if we'll keep this, but maybe it will be useful. */
    public void releaseChannelResources(Integer outingId) {
        ServerSentEventChannel channel = getOutingChannel(outingId);
        SseBroadcaster broadcaster = channel.getBroadcaster();
//        broadcaster.broadcast(eventBundler.getClosingMessage());
        channel.getKeepAliveGenerator().release();
    }

}
