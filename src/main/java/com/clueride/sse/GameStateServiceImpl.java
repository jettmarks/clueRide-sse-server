/*
 * Copyright 2018 Jett Marks
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
 * Created by jett on 3/6/18.
 */
package com.clueride.sse;

import java.util.HashMap;
import java.util.Map;

import org.glassfish.jersey.media.sse.SseBroadcaster;

/**
 * Implementation of GameStateService.
 */
public class GameStateServiceImpl implements GameStateService {
    private Map<Integer,ServerSentEventChannel> channelMap = new HashMap<>();
    private final GameStateEventFactory gameStateEventFactory = new GameStateEventFactory();

    @Override
    public ServerSentEventChannel openChannelResources(Integer outingId) {
        ServerSentEventChannel channel = getEventChannel(outingId);
        return channel;
    }

    @Override
    public void releaseChannelResources(Integer outingId) {
        ServerSentEventChannel channel = getEventChannel(outingId);
        SseBroadcaster broadcaster = channel.getBroadcaster();
        broadcaster.broadcast(gameStateEventFactory.getClosingMessage());
        channel.getKeepAliveGenerator().release();
    }

    @Override
    public void broadcastMessage(Integer outingId, String message) {
        ServerSentEventChannel channel = getEventChannel(outingId);
        SseBroadcaster broadcaster = channel.getBroadcaster();
        broadcaster.broadcast(gameStateEventFactory.getGameStateEvent(message));
        KeepAliveGenerator keepAliveGenerator = channel.getKeepAliveGenerator();
        keepAliveGenerator.reset();
    }

    ServerSentEventChannel getEventChannel(Integer outingId) {
        ServerSentEventChannel channel;
        if (channelMap.containsKey(outingId)) {
            channel = channelMap.get(outingId);
        } else {
            channel = new ServerSentEventChannel(outingId);
            channelMap.put(outingId, channel);
        }
        return channel;
    }

}
