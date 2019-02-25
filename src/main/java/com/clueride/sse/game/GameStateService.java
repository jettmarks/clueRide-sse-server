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
package com.clueride.sse.game;

import com.clueride.sse.common.ServerSentEventChannel;

/**
 * Defines operations on the Game State publish/subscribe service.
 */
public interface GameStateService {
    /**
     * Channels are opened against an outing.
     * @param outingId Unique identifier for the associated Outing.
     * @return ServerSentEventChannel holding the EventOutput to be used.
     */
    ServerSentEventChannel openChannelResources(Integer outingId);

    /**
     * When no more broadcast messages will be sent, this releases the resources.
     *
     * Note that the client Web Service is expected to send out close messages to
     * the subscribers. This only shuts down the resources.
     * @param outingId Unique identifier for the associated Outing.
     */
    void releaseChannelResources(Integer outingId);

    /**
     * Sends the message to the matching outingId's channel.
     * @param outingId Unique identifier for the Outing.
     * @param message String to be sent to all subscribers of this Outing.
     */
    void broadcastMessage(Integer outingId, String message);

}
