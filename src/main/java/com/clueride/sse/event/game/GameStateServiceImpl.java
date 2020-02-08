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

import com.clueride.sse.channel.OutingChannelService;
import com.clueride.sse.channel.OutingChannelServiceImpl;
import com.clueride.sse.event.EventBundler;
import com.clueride.sse.event.EventType;

/**
 * Implementation of GameStateService.
 */
public class GameStateServiceImpl implements GameStateService {
    private static final Logger LOGGER = Logger.getLogger(GameStateServiceImpl.class);

    private OutingChannelService outingChannelService = new OutingChannelServiceImpl();
    private EventBundler eventBundler = new EventBundler();

    @Override
    public void broadcastMessage(Integer outingId, String gameStateEventAsJSON) {
        LOGGER.debug(
                String.format(
                        "Broadcasting Game State on Outing %d: %s",
                        outingId,
                        gameStateEventAsJSON
                )
        );

        SseBroadcaster broadcaster = outingChannelService.getOutingBroadcaster(outingId);
        broadcaster.broadcast(eventBundler.bundleMessage(gameStateEventAsJSON, EventType.GAME_STATE));
    }

}
