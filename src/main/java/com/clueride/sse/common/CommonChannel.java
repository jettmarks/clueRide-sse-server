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
 * Created by jett on 9/20/19.
 */
package com.clueride.sse.common;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Holds details about the Channel Assignments for Outings including the
 * SSE Channel itself.
 */
public class CommonChannel {
    @JsonIgnore
    private ServerSentEventChannel serverSentEventChannel;
    private Set<Integer> subscribedUsers = new HashSet<>();
    private Integer outingId;

    public CommonChannel(
            ServerSentEventChannel serverSentEventChannel,
            Integer outingId
    ) {
        this.serverSentEventChannel = serverSentEventChannel;
        this.outingId = outingId;
    }

    @JsonIgnore
    public ServerSentEventChannel getServerSentEventChannel() {
        return serverSentEventChannel;
    }

    public void setServerSentEventChannel(ServerSentEventChannel serverSentEventChannel) {
        this.serverSentEventChannel = serverSentEventChannel;
    }

    public Set<Integer> getSubscribedUsers() {
        return subscribedUsers;
    }

    public Set<Integer> subscribeUser(Integer userId) {
        this.subscribedUsers.add(userId);
        return this.subscribedUsers;
    }

    public Set<Integer> unsubscribeUser(Integer userId) {
        this.subscribedUsers.remove(userId);
        return this.subscribedUsers;
    }

    public Integer getOutingId() {
        return outingId;
    }

    public void setOutingId(Integer outingId) {
        this.outingId = outingId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
//                .append("serverSentEventChannel", serverSentEventChannel)
                .append("subscribedUsers", subscribedUsers)
                .append("outingId", outingId)
                .toString();
    }

}
