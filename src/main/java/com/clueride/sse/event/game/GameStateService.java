/*
 * Copyright 2018 Jett Marks
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
 * Created by jett on 3/6/18.
 */
package com.clueride.sse.event.game;

/**
 * Defines operations on the Game State publish/subscribe service.
 */
public interface GameStateService {

    /**
     * Sends the message to the matching outingId's channel.
     *
     * @param outingId Unique identifier for the Outing.
     * @param gameStateEventAsJSON JSON representation of the Game State.
     */
    void broadcastMessage(Integer outingId, String gameStateEventAsJSON);

}
