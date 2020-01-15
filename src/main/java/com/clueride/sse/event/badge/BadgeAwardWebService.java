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

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST API for broadcasting a Badge Award.
 */
@Path("badge-award")
public class BadgeAwardWebService {

    private BadgeAwardService badgeAwardService = new BadgeAwardServiceImpl();

    @POST
    @Path("{outingId}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String broadcastMessage(
            String badgeEvent,
            @PathParam("outingId") Integer outingId
    ) {
        String messageId = badgeAwardService.broadcastMessage(outingId, badgeEvent);
        return "Message ID '" + messageId + "' has been broadcast.";
    }

}
