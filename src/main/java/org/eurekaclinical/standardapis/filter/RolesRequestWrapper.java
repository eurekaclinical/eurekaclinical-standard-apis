package org.eurekaclinical.standardapis.filter;

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

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wraps an HttpServletRequest object, so that roles for the principal can be
 * implemented.
 *
 * @author hrathod
 *
 */
public class RolesRequestWrapper extends HttpServletRequestWrapper {

    /**
     * The original principal.
     */
    private final Principal principal;
    /**
     * The roles assigned to the principal;
     */
    private final Set<String> roles;

    /**
     * The original request.
     */
    private final HttpServletRequest request;

    /**
     * Create a wrapper with the given principal, role assigned to that
     * principal, and the original request.
     *
     * @param inRequest The original request.
     * @param inPrincipal The request principal.
     * @param inRoles The roles assigned to the principal.
     */
    public RolesRequestWrapper(HttpServletRequest inRequest,
            Principal inPrincipal, String[] inRoles) {
        super(inRequest);
        this.request = inRequest;
        this.principal = inPrincipal;
        this.roles = new HashSet<>();
        for (String role : inRoles) {
            this.roles.add(role);
        }
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletRequestWrapper#isUserInRole(java.lang.String
	 * )
     */
    @Override
    public boolean isUserInRole(String inRole) {
        boolean result;
        if (this.roles == null) {
            result = this.request.isUserInRole(inRole);
        } else {
            result = this.roles.contains(inRole);
        }
        return result;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletRequestWrapper#getUserPrincipal()
     */
    @Override
    public Principal getUserPrincipal() {
        Principal result;
        if (this.principal == null) {
            result = this.request.getUserPrincipal();
        } else {
            result = this.principal;
        }
        return result;
    }

    public Set<String> getRoles() {
        return roles;
    }

}
