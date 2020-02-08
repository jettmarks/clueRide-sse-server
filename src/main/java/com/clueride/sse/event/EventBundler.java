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
package com.clueride.sse.event;

import org.glassfish.jersey.media.sse.OutboundEvent;

/**
 * Common method for bundling up an event.
 */
public class EventBundler {

    private static int messageId = 101;

    /**
     * Generic message defaults to the eventType 'message'.
     *
     * @deprecated use {@link #bundleMessage(String,EventType)} instead.
     *
     * @param message String to be sent as the payload.
     * @return the event bundled up for broadcasting.
     */
    public OutboundEvent bundleMessage(String message) {
        return bundleMessage(message, "message");
    }

    public OutboundEvent getKeepAliveMessage() {
        final OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
        eventBuilder.name("message");
        eventBuilder.id("" + generateMessageId());
        eventBuilder.comment("Keep Alive");
        return eventBuilder.build();
    }

    /**
     * Wraps a message string (generally JSON represention of an event), with the SSE stuff required to broadcast the
     * message of the given Event Type.
     *
     * @param message JSON-representation of the event.
     * @param eventType Type of the Event.
     * @return An OutboundEvent instance ready for broadcast.
     */
    public OutboundEvent bundleMessage(String message, EventType eventType) {
        return bundleMessage(
                message,
                eventType.eventName
        );
    }

    private OutboundEvent bundleMessage(String message, String eventType) {
        final OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
        eventBuilder.name(eventType);
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
