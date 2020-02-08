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
 * Holds the broadcaster and list of Users listening on that channel for a given Outing.
 *
 * Note that the ChunkedOutput / EventOutput instances are owned by the User and
 * associated with this broadcaster for broadcast only.
 */
public class OutingChannel {
    private final Integer outingId;
    private final SseBroadcaster broadcaster;

    public OutingChannel(Integer outingId) {
        this.outingId = outingId;
        this.broadcaster = new SseBroadcaster();
    }

    public Integer getOutingId() {
        return outingId;
    }

    public SseBroadcaster getBroadcaster() {
        return broadcaster;
    }

}
