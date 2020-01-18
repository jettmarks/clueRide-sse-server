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
 * Created by jett on 3/10/18.
 */
package com.clueride.sse.keepalive;

import java.util.concurrent.TimeUnit;

/**
 * Generates Keep Alive events whenever no other message has been sent
 * for a certain period of time.
 */
public class KeepAliveGenerator {
    public static Integer MAX_INACTIVE_PERIOD_SECONDS = 30;

    private final Thread thread;
    private Runnable action;
    private State state;

    /**
     * Constructor using the standard max inactive period.
     */
    public KeepAliveGenerator() {
       this(MAX_INACTIVE_PERIOD_SECONDS);
    }

    /**
     * Constructor accepting a non-standard max inactive period.
     */
    public KeepAliveGenerator(final Integer maxInactivePeriodInSeconds) {
        state = State.INITIALIZING;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                state = State.RUNNING;
                while (state == State.RUNNING) {
                    try {
                        action.run();
                        TimeUnit.SECONDS.sleep(maxInactivePeriodInSeconds);
                    } catch (InterruptedException e) {
                        switch (state) {
                            case RUNNING:
                                break;
                            case RESET_REQUESTED:
                                state = State.RUNNING;
                                break;
                            case RELEASE_REQUESTED:
                                break;
                        }
                    }
                }
            }
        });
    }

    /**
     * Provide the thread with the action to be invoked upon the timer firing.
     * @param action Runnable to be executed upon firing.
     */
    public void setAction(Runnable action) {
        this.action = action;
    }

    /**
     * Begins running the timer loop.
     */
    public void start() {
        state = State.RUNNING;
        thread.start();
    }

    /**
     * Resets the counter so another 30 seconds will have to elapse before the action is run.
     */
    public void reset() {
        state = State.RESET_REQUESTED;
        thread.interrupt();
    }

    /**
     * Shuts down the loop and closes the thread.
     */
    public void release() {
        state = State.RELEASE_REQUESTED;
        thread.interrupt();
    }

    public State getState() {
        return this.state;
    }

    /**
     * When coming out of the sleep, the state is checked against these values and the logic is steered on the state.
     */
    public enum State {
        INITIALIZING,
        RUNNING,
        RESET_REQUESTED,
        RELEASE_REQUESTED
    }

}
