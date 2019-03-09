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
 * Created by jett on 3/6/19.
 */
package com.clueride.sse.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link CommonChannelService}.
 *
 * TODO: The Map of channels opened would serve as a good way to implement SSE-7.
 */
public class CommonChannelServiceImpl implements CommonChannelService {

    /* The single channel used when there is no outing. */
    private static ServerSentEventChannel noOutingEventChannel = null;
    private static final Integer NO_OUTING = -1;

    /* Map of the channels for each Outing. */
    private static final Map<Integer,ServerSentEventChannel> channelsPerOuting = new HashMap<>();

    @Override
    public ServerSentEventChannel openChannelResources() {
        if (noOutingEventChannel == null) {
            noOutingEventChannel = new ServerSentEventChannel(NO_OUTING);
        }
        return noOutingEventChannel;
    }

    @Override
    public ServerSentEventChannel getEventChannel(Integer outingId) {
        if (!channelsPerOuting.containsKey(outingId)) {
            channelsPerOuting.put(outingId, new ServerSentEventChannel(outingId));
        }
        return channelsPerOuting.get(outingId);
    }

}
