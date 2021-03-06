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
 * Created by jett on 3/6/19.
 */
package com.clueride.sse.event.answer;

/**
 * Defines operations on Puzzle State event subscriptions.
 */
public interface AnswerSummaryService {

    /**
     * Sends the given message (assumed to be an AnswerSummary in JSON) to
     * all subscribers listening for updates to the identified answer and outing.
     *
     * @param outingId unique identifier for the Outing.
     * @param message JSON-formatted Answer Summary for the Puzzle on the given Outing.
     */
    String broadcastMessage(Integer outingId, String message);

}
