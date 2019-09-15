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
package com.clueride.sse.event.tether;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.clueride.sse.common.ServerSentEventChannel;
import static java.lang.Thread.*;

/**
 * Implementation of TetherService.
 */
public class TetherServiceImpl implements TetherService {
    private static Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
    private static final Integer BROADCAST_ALL = -1;
    private static Thread tetherRunnable = initTetherThread();

    private static Map<Integer, ServerSentEventChannel> channelMap = new HashMap<>();

    private static final TetherEventFactory TETHER_EVENT_FACTORY = new TetherEventFactory();

    @Override
    public ServerSentEventChannel openChannelResources(Integer outingId) {
        return getEventChannel(outingId);
    }

    private static Thread initTetherThread() {
        Thread thread = new Thread(
            new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        for (ServerSentEventChannel channel : channelMap.values()) {
                            LOGGER.debug("Sending Tether to Outing " + channel.getOutingId());
                            channel.getBroadcaster().broadcast(
                                    TETHER_EVENT_FACTORY.getHeartbeatEvent()
                            );
                        }
                        try {
                            sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        );
        thread.start();
        return thread;
    }

    @Override
    public void releaseChannelResources(Integer outingId) {
        try {
            tetherRunnable.join();
        } catch (InterruptedException e) {
            // Eat the exception when we bring this down intentionally
        }
    }

    @Override
    public void broadcastTetherPosition(Integer outingId, LatLon latLon) {

    }

    private ServerSentEventChannel getEventChannel(Integer outingId) {
        ServerSentEventChannel channel;
        /* Lazy init of the BROADCAST_ALL channel. */
        if (outingId == null) {
            outingId = BROADCAST_ALL;
        }

        if (channelMap.containsKey(outingId)) {
            channel = channelMap.get(outingId);
        } else {
            channel = new ServerSentEventChannel(
                    outingId
            );
            channelMap.put(outingId, channel);
        }
        return channel;
    }

}
