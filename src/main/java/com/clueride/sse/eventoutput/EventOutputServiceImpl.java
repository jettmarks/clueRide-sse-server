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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.glassfish.jersey.media.sse.EventOutput;

import com.clueride.sse.common.CommonChannelService;
import com.clueride.sse.common.CommonChannelServiceImpl;

/**
 * Default implementation of {@link EventOutputService}.
 */
public class EventOutputServiceImpl implements EventOutputService {

    private CommonChannelService channelService = new CommonChannelServiceImpl();

    private static Map<Integer, EventOutput> eventOutputPerUser = new HashMap<>();

    @Override
    public EventOutput getEventOutputForUser(Integer badgeOsId) {
        EventOutput eventOutput = eventOutputPerUser.get(badgeOsId);
        if (eventOutput == null) {
            eventOutput = new EventOutput();
            eventOutputPerUser.put(badgeOsId, eventOutput);
            channelService.addUserEventOutput(badgeOsId, eventOutput);
        }
        return eventOutput;
    }

    @Override
    public EventOutput getEventOutputForOuting(Integer badgeOsId, Integer outingId) {
        EventOutput eventOutput = getEventOutputForUser(badgeOsId);
        channelService.addOutingEventOutput(
                badgeOsId,
                outingId,
                eventOutput
        );
        return eventOutput;
    }

    public Set<Integer> getSubscribingUserIds() {
        return eventOutputPerUser.keySet();
    }

}
