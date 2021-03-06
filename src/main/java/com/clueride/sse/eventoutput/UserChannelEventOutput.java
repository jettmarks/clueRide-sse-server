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
 * Created by jett on 2/9/20.
 */
package com.clueride.sse.eventoutput;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.glassfish.jersey.media.sse.EventOutput;

/**
 * Extension of EventOutput that can carry our identification info.
 */
public class UserChannelEventOutput extends EventOutput {
    private final String requestId;

    public UserChannelEventOutput(String requestId) {
        super();
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("requestId", requestId)
                .toString();
    }

}
