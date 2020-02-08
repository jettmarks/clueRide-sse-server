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
package com.clueride.sse.keepalive;

import java.lang.invoke.MethodHandles;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import com.clueride.sse.channel.UserChannel;
import com.clueride.sse.event.EventBundler;

/**
 * The guts of what is run whenever the KeepAlive interval expires.
 */
public class KeepAliveRunnable implements Runnable {
    private static Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private final UserChannel userChannel;
    private final SseBroadcaster broadcaster;
    private final EventBundler eventBundler = new EventBundler();
    private final String requestIdMessage;

    public KeepAliveRunnable(
            UserChannel userChannel
    ) {
        this.userChannel = userChannel;
        this.broadcaster = userChannel.getSseBroadcaster();
        this.requestIdMessage = "  Request ID: " + userChannel.getRequestId();
    }

    public void run() {
        broadcaster.broadcast(
                eventBundler.getKeepAliveMessage()
        );

        if (userChannel.getEventOutput() != null && userChannel.getEventOutput().isClosed()) {
            LOGGER.debug("Found eventOutput is closed");
        } else {
            LOGGER.debug("Channel Check for User ID: "
                    + userChannel.getBadgeOsId() + requestIdMessage
            );
        }
    }

}
