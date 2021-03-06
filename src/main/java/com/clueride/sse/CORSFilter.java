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
 * Created by jett on 3/4/18.
 */
package com.clueride.sse;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

/**
 * Handles the CORS responses for the Jersey requests.
 */
@Provider
public class CORSFilter implements ContainerResponseFilter {
    private static Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void filter(
            ContainerRequestContext request,
            ContainerResponseContext response
    ) throws IOException {
        LOGGER.debug("Adding Headers");
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization, headers, lazyinit, lazyupdate, normalizednames"
        );
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
        response.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD"
        );
    }

}
