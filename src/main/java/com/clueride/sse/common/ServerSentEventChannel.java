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
 * Created by jett on 2/26/18.
 */
package com.clueride.sse.common;

import java.lang.invoke.MethodHandles;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import com.clueride.sse.keepalive.KeepAliveGenerator;

/**
 * Support for keeping an open session by providing the KeepAlive thread alive.
 */
public class ServerSentEventChannel {
    private static Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private final SseBroadcaster broadcaster = new SseBroadcaster();
    private final Integer outingId;
    private final KeepAliveGenerator keepAliveGenerator;
    private final Date creationTimestamp = new Date();
    /* This is chosen to be a bit under the 45-second interval that the client uses for checking up on us. */
    private final static int KEEP_ALIVE_INTERVAL = 35;
    private final EventBundler eventBundler = new EventBundler();
    private Integer userId;

    /* For diagnostics only. */
    private EventOutput eventOutput;

    public ServerSentEventChannel() {
        this.outingId = -1;
        keepAliveGenerator = provideKeepAliveGenerator();
    }

    public ServerSentEventChannel(
            Integer outingId
    ) {
        this.outingId = outingId;
        keepAliveGenerator = provideKeepAliveGenerator();
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setEventOutput(EventOutput eventOutput) {
        this.eventOutput = eventOutput;
    }

    private KeepAliveGenerator provideKeepAliveGenerator() {
        LOGGER.debug("New Keep Alive generated " + creationTimestamp);
        KeepAliveGenerator keepAliveGenerator = new KeepAliveGenerator(KEEP_ALIVE_INTERVAL);
        keepAliveGenerator.setAction(
                new Runnable() {
                    @Override
                    public void run() {
                        broadcaster.broadcast(
                                eventBundler.getKeepAliveMessage()
                        );
                        LOGGER.debug("User ID: " + userId);

                    }
                }
        );
        keepAliveGenerator.start();
        return keepAliveGenerator;
    }

    public Integer getOutingId() {
        return this.outingId;
    }

    public SseBroadcaster getBroadcaster() {
        return this.broadcaster;
    }

    public KeepAliveGenerator getKeepAliveGenerator() {
        return this.keepAliveGenerator;
    }

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("outingId", outingId)
                .append("userId", userId)
                .append("creationTimestamp", creationTimestamp)
                .toString();
    }

}
