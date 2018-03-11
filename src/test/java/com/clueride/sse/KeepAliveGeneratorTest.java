package com.clueride.sse;
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

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Exercises the KeepAliveGeneratorTest class.
 */
public class KeepAliveGeneratorTest {
    private KeepAliveGenerator toTest;
    private boolean hasRunAction = false;

    @BeforeMethod
    public void setUp() throws Exception {
        toTest = new KeepAliveGenerator(1);
        toTest.setAction( new Runnable() {
            @Override
            public void run() {
                hasRunAction = true;
            }
        });
    }

    @AfterMethod
    public void tearDown() throws Exception {
        toTest.release();
    }

    @Test
    public void testStart() throws Exception {
        toTest.start();
        assertTrue(toTest.getState() == KeepAliveGenerator.State.RUNNING, "Expected to be running");
        TimeUnit.SECONDS.sleep(2);
        assertTrue(hasRunAction, "Expected Action to have been run");
    }

    @Test
    public void testRelease() throws Exception {
        toTest.start();
        sendMessagesFasterThanTimeout();
        toTest.release();
        TimeUnit.SECONDS.sleep(2);
        assertFalse(hasRunAction, "Expected Action to never have been run");
    }

    @Test
    public void testReset() throws Exception {
        toTest.start();
        sendMessagesFasterThanTimeout();
        assertFalse(hasRunAction, "Expected Action to never have been run");
    }

    private void sendMessagesFasterThanTimeout() throws InterruptedException {
        for (int i=0; i<10; i++) {
            TimeUnit.MILLISECONDS.sleep(750);
            toTest.reset();
        }
    }

}