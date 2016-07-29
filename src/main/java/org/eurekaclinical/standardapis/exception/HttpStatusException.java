package org.eurekaclinical.standardapis.exception;

/*-
 * #%L
 * Eureka! Clinical Standard APIs
 * %%
 * Copyright (C) 2016 Emory University
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Andrew Post
 */
public class HttpStatusException extends WebApplicationException {

    private static final long serialVersionUID = 1L;

    private final Status status;
    
    public HttpStatusException(Status status) {
        super(buildResponse(status));
        this.status = status;
    }

    public HttpStatusException(Status status, String message) {
        super(buildResponse(status, message));
        this.status = status;
    }

    public HttpStatusException(Status status, Throwable cause) {
        super(cause, buildResponse(status, cause));
        this.status = status;
    }

    public HttpStatusException(Status status, String message, Throwable cause) {
        super(cause,
                message != null ? buildResponse(status, message) : buildResponse(status, cause));
        this.status = status;
    }

    public Status getStatus() {
        return this.status;
    }
    
    private static Response buildResponse(Status status, Throwable cause) {
        if (cause != null) {
            return Response.status(status).entity(cause.getMessage()).type(MediaType.TEXT_PLAIN).build();
        } else {
            return buildResponse(status);
        }
    }

    private static Response buildResponse(Status status, String message) {
        if (message != null) {
            return Response.status(status).entity(message).type(MediaType.TEXT_PLAIN).build();
        } else {
            return buildResponse(status);
        }
    }

    private static Response buildResponse(Status status) {
        return Response.status(status).entity(status.getReasonPhrase()).type(MediaType.TEXT_PLAIN).build();
    }

}
