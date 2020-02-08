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
 * Created by jett on 2/8/20.
 */
package com.clueride.sse.channel;

import org.glassfish.jersey.server.ChunkedOutput;

/**
 * Defines the operations for {@link UserChannel} instances.
 */
public interface UserChannelService {

    /**
     * Given the Badge OS ID for a specific user and a unique
     * `requestId` for the new session, generate a new UserChannel.
     *
     * @param badgeOsId unique ID representing the User.
     * @param requestId unique ID representing the request to open a new Channel.
     * @return instance of {@link UserChannel} initialized for sending SSE events.
     */
    UserChannel getUserChannel(Integer badgeOsId, String requestId);

    /**
     * Given the ChunkedOutput which receives the Close event,
     * shut down the resources associated with that User's Channel.
     *
     * @param chunkedOutput instance that detects the closure.
     * @return String requestId when the channel was first opened.
     */
    String closeUserChannel(ChunkedOutput chunkedOutput);

}
