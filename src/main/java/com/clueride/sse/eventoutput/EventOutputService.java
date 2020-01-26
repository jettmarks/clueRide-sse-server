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
 * Created by jett on 1/25/20.
 */
package com.clueride.sse.eventoutput;

import org.glassfish.jersey.media.sse.EventOutput;

/**
 * Produces and maintains EventOutput instances appropriate
 * for the requests to open an SSE Channel.
 */
public interface EventOutputService {

    /**
     * Assigns an EventOutput that broadcasts events that are not specific
     * to a given outing (Badges and Open/Close).
     *
     * @param badgeOsId Unique identifier for the user's Badge Account.
     * @return EventOutput object that holds open the session and broadcasts appropriate events.
     */
    EventOutput getEventOutputForUser(Integer badgeOsId);

    /**
     * Given an Outing ID, return an instance of EventOutput which holds and open
     * session against the Outing's channel.
     *
     * Includes the events that are specific to a given user as well.
     *
     * @param badgeOsId Unique identifier for the user's Badge Account.
     * @param outingId unique identifier for the Outing and its corresponding Channel.
     * @return instance of EventOutput suitable for returning to the client to hold session open.
     */
    EventOutput getEventOutputForOuting(Integer badgeOsId, Integer outingId);

}
