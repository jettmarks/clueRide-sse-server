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

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Broadcasts a Tether to subscribers who wish to follow the
 * Tethered Position (usually the Guide).
 *
 * For subscribers providing an Outing ID, the tethered position
 * follows the tethered position for that Outing.
 */
@Singleton
@Path("tether")
public class TetherWebService {
    private TetherService tetherService = new TetherServiceImpl();

    @POST
    @Path("{outingId}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String broadcastMessage(
            LatLon latLon,
            @PathParam("outingId") Integer outingId
    ) {
        Integer messageId = tetherService.broadcastTetherPosition(outingId, latLon);
        return "Message ID '" + messageId + "' has been broadcast.";
    }

}
