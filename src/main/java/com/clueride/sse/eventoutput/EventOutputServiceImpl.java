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

import com.clueride.sse.channel.OutingChannelService;
import com.clueride.sse.channel.OutingChannelServiceImpl;
import com.clueride.sse.channel.UserChannel;
import com.clueride.sse.channel.UserChannelService;
import com.clueride.sse.channel.UserChannelServiceImpl;

/**
 * Default implementation of {@link EventOutputService}.
 */
public class EventOutputServiceImpl implements EventOutputService {
    private UserChannelService userChannelService = new UserChannelServiceImpl();
    private OutingChannelService outingChannelService = new OutingChannelServiceImpl();

    private static Map<Integer, EventOutput> eventOutputPerUser = new HashMap<>();

    @Override
    public EventOutput getEventOutputForUser(Integer badgeOsId, String requestId) {
        return userChannelService.getUserChannel(badgeOsId, requestId).getEventOutput();
    }

    @Override
    public EventOutput getEventOutputForOuting(
            Integer badgeOsId,
            Integer outingId,
            String requestId
    ) {
        UserChannel userChannel = userChannelService.getUserChannel(
               badgeOsId,
               requestId
        );
        outingChannelService.addUserChannelToOuting(userChannel, outingId);
        return userChannel.getEventOutput();
    }

    public Set<Integer> getSubscribingUserIds() {
        return eventOutputPerUser.keySet();
    }

}
