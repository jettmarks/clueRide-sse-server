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
package com.clueride.sse.event.game;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import com.clueride.sse.common.CommonChannelService;
import com.clueride.sse.common.CommonChannelServiceImpl;
import com.clueride.sse.keepalive.KeepAliveGenerator;
import com.clueride.sse.common.ServerSentEventChannel;

/**
 * Implementation of GameStateService.
 */
public class GameStateServiceImpl implements GameStateService {
    private static final Logger LOGGER = Logger.getLogger(GameStateServiceImpl.class);

    private final GameStateEventFactory gameStateEventFactory = new GameStateEventFactory();
    private CommonChannelService commonChannelService = new CommonChannelServiceImpl();

    @Override
    public ServerSentEventChannel openChannelResources(Integer outingId) {
        return commonChannelService.getEventChannel(outingId);
    }

    @Override
    public void releaseChannelResources(Integer outingId) {
        ServerSentEventChannel channel = commonChannelService.getEventChannel(outingId);
        SseBroadcaster broadcaster = channel.getBroadcaster();
        broadcaster.broadcast(gameStateEventFactory.getClosingMessage());
        channel.getKeepAliveGenerator().release();
    }

    @Override
    public void broadcastMessage(Integer outingId, String message) {
        LOGGER.debug(String.format("Broadcasting on Outing %d: %s", outingId, message));
        ServerSentEventChannel channel = commonChannelService.getEventChannel(outingId);
        SseBroadcaster broadcaster = channel.getBroadcaster();
        broadcaster.broadcast(gameStateEventFactory.getGameStateEvent(message));
        KeepAliveGenerator keepAliveGenerator = channel.getKeepAliveGenerator();
        keepAliveGenerator.reset();
    }

}