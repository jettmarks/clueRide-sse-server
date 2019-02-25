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
package com.clueride.sse.heartbeat;

import java.lang.invoke.MethodHandles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.sse.OutboundEvent;

import com.clueride.sse.common.EventBundler;

/**
 * Creates the different events for Heartbeat, tethered or without
 * the tether.
 */
public class HeartbeatEventFactory {
    private static Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    EventBundler eventBundler = new EventBundler();

    OutboundEvent getHeartbeatEvent() {
        return eventBundler.bundleMessage("Heartbeat");
    }

    public OutboundEvent getTetherEvent(LatLon latLon) {
        String payload = "";
        try {
            payload = new ObjectMapper().writeValueAsString(latLon);
        } catch (JsonProcessingException e) {
            LOGGER.error("Unable to marshall this: " + latLon);
            e.printStackTrace();
        }
        return eventBundler.bundleMessage(payload);
    }

}
