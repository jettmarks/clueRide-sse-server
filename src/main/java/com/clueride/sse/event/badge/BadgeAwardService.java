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
 * Created by jett on 9/16/19.
 */
package com.clueride.sse.event.badge;

/**
 * Defines operations on BadgeEvent instances that are being awarded.
 */
public interface BadgeAwardService {

    /**
     * Sends the JSON-representation of a BadgeEvent to the subscribers listening
     * on the given outing ID.
     *
     * @param outingId Unique identifier for the Outing, includes all team members.
     * @param badgeEvent JSON-representation of the Badge being awarded; includes the Member ID.
     * @return String representation of the unique Message ID.
     */
    String broadcastMessage(Integer outingId, String badgeEvent);

}
