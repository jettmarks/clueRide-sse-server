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
 * Abstraction of the generation of Keep Alive Events.
 */
public interface EventFactory {

    /**
     * Produce an event that keeps the connection open.
     *
     * Generally, the event will be uniquely identified, but
     * there's no content other than its identity.
     */
    OutboundEvent getKeepAliveEvent();

}
