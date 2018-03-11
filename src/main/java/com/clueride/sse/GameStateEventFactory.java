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
 * Created by jett on 3/9/18.
 */
package com.clueride.sse;

import org.glassfish.jersey.media.sse.OutboundEvent;

/**
 * Constructs the different events produced for the Game State.
 *
 * The following events are constructed:
 * <UL>
 *     <li>Game State message updating subscribers to game state changes.</li>
 *     <li>Closing message to alert subscribers the channel is closing down.</li>
 *     <li>Keep-alive message sent when no message has been broadcast for 30 seconds.</li>
 * </UL>
 */
public class GameStateEventFactory {
    static Integer messageId = 101;

    public OutboundEvent getGameStateEvent(String payload) {
        return bundleMessage(payload);
    }

    public OutboundEvent getClosingMessage() {
        return bundleMessage("Closing channel");
    }

    public OutboundEvent getKeepAliveEvent() {
        return bundleMessage(null);
    }

    private OutboundEvent bundleMessage(String message) {
        final OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
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
