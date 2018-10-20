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


/**
 * Wraps an HttpServletRequest object, so that roles for the principal can be
 * implemented if none are populated in the request instance.
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
     * @param inRequest The original request. Cannot be null.
     * @param inPrincipal The request principal. Cannot be null.
     * @param inRoles The roles assigned to the principal.
     */
    public RolesRequestWrapper(HttpServletRequest inRequest,
            Principal inPrincipal, String[] inRoles) {
        super(inRequest);
        this.request = inRequest;
        this.principal = inPrincipal;
        if (inRoles != null) {
            this.roles = new HashSet<>();
            for (String role : inRoles) {
                this.roles.add(role);
            }
        } else {
            this.roles = null;
        }
    }

    /**
     * Checks if the given role is one of those that were passed into the
     * constructor. If <code>null</code> passed in for roles, it checks 
     * the request that was passed into the constructor instead.
     * 
     * @param inRole a role.
     * @return <code>true</code> or <code>false</code>.
     */
    @Override
    public boolean isUserInRole(String inRole) {
        if (this.roles == null) {
            return this.request.isUserInRole(inRole);
        } else {
            return this.roles.contains(inRole);
        }
    }

    /**
     * Returns the {@link Principal} that was passed into the constructor. If
     * <code>null</code> was passed into the constructor, it tries getting
     * the Principal from the request that was passed into the constructor.
     * 
     * @return a user principal.
     */
    @Override
    public Principal getUserPrincipal() {
        if (this.principal == null) {
            return this.request.getUserPrincipal();
        } else {
            return this.principal;
        }
    }

    public Set<String> getRoles() {
        return roles;
    }

}
