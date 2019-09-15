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
package com.clueride.sse.keepalive;

import org.glassfish.jersey.media.sse.OutboundEvent;

import com.clueride.sse.common.EventBundler;
import com.clueride.sse.common.EventFactory;

/**
 * Implementation of EventFactory that can produce just the Keep Alive event.
 */
public class KeepAliveEventFactory implements EventFactory {
    EventBundler eventBundler = new EventBundler();

    @Override
    public OutboundEvent getKeepAliveEvent() {
        return eventBundler.bundleMessage(null);
    }

}
