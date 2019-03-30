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

/**
 * Defines common operations on Channels.
 */
public interface CommonChannelService {
    /**
     * Open a generic channel that is non-specific to an outing.
     *
     * @return instance of {@link ServerSentEventChannel} for communication without
     * knowledge of a specific Outing.
     */
    ServerSentEventChannel openChannelResources();

    /**
     * Retrieve a previously opened Event Channel for the given Outing and Puzzle.
     *
     * @param outingId unique identifier for the outing.
     * @return instance of {@link ServerSentEventChannel} for events specific
     * to the Outing and the Puzzle.
     */
    ServerSentEventChannel getEventChannel(Integer outingId);

}