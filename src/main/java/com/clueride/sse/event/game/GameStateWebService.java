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
 * Created by jett on 2/25/18.
 */
package com.clueride.sse.event.game;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

/**
 * REST API for broadcasting Game State changes to each of the clients
 * who have subscribed to the events for a given Outing ID.
 */
@Singleton
@Path("game-state-broadcast")
public class GameStateWebService {
    private static final Logger LOGGER = Logger.getLogger(GameStateWebService.class);

    private GameStateService gameStateService = new GameStateServiceImpl();

    /**
     * Used by Game State Event generators to broadcast the event
     * to all subscribers on the Outing ID channel.
     *
     * @param message JSON representation of the Game State event.
     * @param outingId unique identifier for the channel assigned to the Outing.
     * @return human-readable string result.
     */
    @POST
    @Path("{outingId}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String broadcastMessage(
            String message,
            @PathParam("outingId") Integer outingId
    ) {
        gameStateService.broadcastMessage(outingId, message);
        return "Message '" + message + "' has been broadcast.";
    }

}
