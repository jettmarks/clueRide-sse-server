package com.clueride.sse.event;/*
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
 * Created by jett on 9/15/19.
 */

import java.util.HashMap;
import java.util.Map;

public enum EventType {
    GENERIC("message"),
    TETHER("tether"),
    GAME_STATE("game-state"),
    ANSWER_SUMMARY("answer-summary"),
    BADGE_AWARD("badge-award")
    ;

    public final String eventName;

    private static final Map<String,EventType> BY_EVENT_NAME = new HashMap<>();

    static {
        for (EventType m : values()) {
            BY_EVENT_NAME.put(m.eventName, m);
        }
    }

    EventType(String eventName) {
        this.eventName = eventName;
    }

    public static EventType fromMethodName(String methodName) {
        return BY_EVENT_NAME.get(methodName);
    }

}
