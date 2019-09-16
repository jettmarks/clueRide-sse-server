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
 * Created by jett on 2/24/19.
 */
package com.clueride.sse.event.tether;

import com.clueride.sse.common.ServerSentEventChannel;

/**
 * Defines operations on the Tethered Position publish/subscribe service.
 */
// TODO: SSE-8 rename Heartbeat to Tether.
public interface TetherService {

    /**
     * Channels are optionally opened against an Outing.
     * TODO: Is this currently used by MobiLoc without an Outing? (See SSE-5's PR commit comments)
     * @param outingId when provided, allows returning the tethered
     *                 position data for the outing.
     * @return Channel holding the EventOutput events, with or without tether data.
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
     * For subscribers to an Outing, send this tether position.
     * @param outingId unique identifier for the Outing.
     * @param latLon new value for tether position to broadcast.
     */
    void broadcastTetherPosition(Integer outingId, LatLon latLon);

}
