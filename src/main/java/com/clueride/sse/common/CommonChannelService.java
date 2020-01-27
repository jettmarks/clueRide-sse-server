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

import java.util.Map;

import org.glassfish.jersey.media.sse.EventOutput;

/**
 * Defines common operations on Channels.
 */
public interface CommonChannelService {

    /**
     * Retrieves the channel setup for a given Outing.
     *
     * There should only be one of these per outing and all
     * listeners for that outing will add their EventOutput
     * to that channel's broadcaster.
     *
     * @param outingId unique identifier for the Outing.
     * @return ServerSentEventChannel for the outing.
     */
    ServerSentEventChannel getOutingChannel(Integer outingId);

    /**
     * Adds the EventOutput instance to the User-specific broadcaster.
     *
     * @param badgeOsId unique identifier for the User.
     * @param eventOutput the EventOutput instance the user's session will use.
     */
    void addUserEventOutput(Integer badgeOsId, EventOutput eventOutput);

    /**
     * Adds the EventOutput to the Outing-specific channel.
     *
     * @param badgeOsId unique identifier for the User.
     * @param outingId unique identifier for the Outing.
     * @param eventOutput the EventOutput instance the user's session will use.
     */
    void addOutingEventOutput(
            Integer badgeOsId,
            Integer outingId,
            EventOutput eventOutput
    );

    /**
     * TODO: SSE-7
     * Returns the map of Channel per Outing ID.
     * @return Map of ServerSentEventChannel per Integer outingId.
     */
    Map<Integer, ServerSentEventChannel> getOutingChannelMap();
}
