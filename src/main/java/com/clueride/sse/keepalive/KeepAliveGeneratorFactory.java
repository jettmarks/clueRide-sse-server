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
 * Created by jett on 2/5/20.
 */
package com.clueride.sse.keepalive;

/**
 * Builds an instance of the KeepAliveGenerator given the Runnable.
 */
public class KeepAliveGeneratorFactory {

    /* This is chosen to be a bit under the 45-second interval that the client uses for checking up on us. */
    private final static int KEEP_ALIVE_INTERVAL = 35;

    public KeepAliveGenerator provideKeepAliveGenerator(Runnable runnable) {
        KeepAliveGenerator keepAliveGenerator = new KeepAliveGenerator(KEEP_ALIVE_INTERVAL);
        keepAliveGenerator.setAction(
                runnable
        );
        keepAliveGenerator.start();
        return keepAliveGenerator;
    }

}
