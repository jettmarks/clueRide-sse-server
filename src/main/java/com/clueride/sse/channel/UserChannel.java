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
 * Created by jett on 2/5/20.
 */
package com.clueride.sse.channel;

import java.util.Date;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import com.clueride.sse.keepalive.KeepAliveGenerator;
import com.clueride.sse.keepalive.KeepAliveGeneratorFactory;
import com.clueride.sse.keepalive.KeepAliveRunnable;

/**
 * Aggregate of the pieces used to communicate all SSE Events to a particular User.
 */
public class UserChannel {
    private Integer badgeOsId;
    private Date creationTimestamp;
    private EventOutput eventOutput;
    private SseBroadcaster sseBroadcaster;
    private KeepAliveGenerator keepAliveGenerator;
    private KeepAliveGeneratorFactory keepAliveGeneratorFactory = new KeepAliveGeneratorFactory();

    private String requestId = null;

    public UserChannel(
            Integer badgeOsId,
            String requestId
    ) {
        this.badgeOsId = badgeOsId;
        this.eventOutput = new EventOutput();
        this.requestId = requestId;
        this.sseBroadcaster = new ChannelBroadcaster(this);
    }

    /**
     * Verifies the required pieces, opens the channel and kicks off the KeepAlive Thread.
     */
    public EventOutput start() {
        creationTimestamp = new Date();
        sseBroadcaster.add(eventOutput);
        this.keepAliveGenerator = keepAliveGeneratorFactory.provideKeepAliveGenerator(
                new KeepAliveRunnable(this)
        );
        return getEventOutput();
    }

    public Integer getBadgeOsId() {
        return badgeOsId;
    }

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public EventOutput getEventOutput() {
        return eventOutput;
    }

    public SseBroadcaster getSseBroadcaster() {
        return sseBroadcaster;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public KeepAliveGenerator getKeepAliveGenerator() {
        return this.keepAliveGenerator;
    }

}
