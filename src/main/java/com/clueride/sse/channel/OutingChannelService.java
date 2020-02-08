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

import org.glassfish.jersey.media.sse.SseBroadcaster;

/**
 * Defines operations on OutingChannels.
 */
public interface OutingChannelService {

    /**
     * Retrieves (and generates, if necessary) the Broadcaster
     * for a given Outing.
     *
     * This will be available whether or not any users have yet subscribed.
     * It will also report any subscribed users that may have gone stale.
     *
     * @param outingId unique identifier for the outing.
     * @return Broadcaster to be used for sending SSE to the users on this outing.
     */
    SseBroadcaster getOutingBroadcaster(Integer outingId);

    /**
     * For the given {@link UserChannel} instance, add it's ChunkedOutput to
     * the broadcaster associated with the given Outing.
     *
     * This enables all Outing Events to be sent to the UserChannel.
     *
     * @param userChannel Instance of {@link UserChannel} for a specific user.
     * @param outingId unique identifier for the outing.
     */
    void addUserChannelToOuting(UserChannel userChannel, Integer outingId);

    /**
     * Removes the given {@link UserChannel}'s EventOutput from the
     * broadcast channel for the given outing.
     *
     * This is done when the UserChannel is found to be closed (or possibly stale).
     *
     * @param userChannel Instance of {@link UserChannel} for a specific user.
     */
    void removeUserChannelFromOuting(UserChannel userChannel);

}
