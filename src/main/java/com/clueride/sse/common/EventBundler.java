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
package com.clueride.sse.common;

import org.glassfish.jersey.media.sse.OutboundEvent;

/**
 * Common method for bundling up an event.
 */
public class EventBundler {

    private static int messageId = 101;

    public OutboundEvent bundleMessage(String message) {
        final OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
        /* Apparently, this has to be the string "message". */
        eventBuilder.name("message");
        eventBuilder.id("" + generateMessageId());
        if (message == null) {
            eventBuilder.comment("Keep Alive");
        } else {
            eventBuilder.data(message);
        }
        return eventBuilder.build();
    }

    private Integer generateMessageId() {
        return messageId++;
    }

}