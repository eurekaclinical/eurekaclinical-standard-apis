package org.eurekaclinical.standardapis.filter;

/*-
 * #%L
 * Eureka! Clinical Common
 * %%
 * Copyright (C) 2016 - 2017 Emory University
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
import java.io.IOException;
import java.security.Principal;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Base class for creating filters that add the user's roles to the request and
 * set a session attribute, <code>roles</code> which will contain an array of
 * role names.
 *
 * @author Andrew Post
 */
public abstract class AbstractRolesFilter implements RolesFilter {

    /**
     * Does nothing.
     * 
     * @param fc the filter configuration.
     */
    @Override
    public void init(FilterConfig fc) {
    }

    /**
     * Sets a <code>roles</code> session attribute containing an array of role
     * names for the current user principal. It fetches the roles array from the {@link #getRoles(java.security.Principal, javax.servlet.ServletRequest) }
     * call. If the session attribute is not null, it will not fetch the
     * user's roles again. If there is no session or if the user principal is
     * not set, this filter just passes the request and response onto the next
     * filter in the chain.
     *
     * @param inRequest the servlet request.
     * @param inResponse the servlet response.
     * @param inChain the filter chain.
     * @throws IOException if the exception is thrown from downstream in the
     * filter chain.
     * @throws ServletException if the {@link #getRoles(java.security.Principal, javax.servlet.ServletRequest) }
     * call fails or if the exception is thrown from downstream in the filter
     * chain.
     */
    @Override
    public void doFilter(ServletRequest inRequest, ServletResponse inResponse, FilterChain inChain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) inRequest;
        Principal principal = servletRequest.getUserPrincipal();
        HttpSession session = servletRequest.getSession(false);
        if (principal != null && session != null) {
            String[] roleNames = (String[]) session.getAttribute("roles");

            if (roleNames == null) {
                roleNames = getRoles(principal, inRequest);
                session.setAttribute("roles", roleNames);
            }

            HttpServletRequest wrappedRequest = new RolesRequestWrapper(
                    servletRequest, principal, roleNames);

            inChain.doFilter(wrappedRequest, inResponse);
        } else {
            inChain.doFilter(inRequest, inResponse);
        }
    }

    /**
     * Gets the user's roles. It is called by 
     * {@link #doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain) }.
     *
     * @param inPrincipal the user's principal. Cannot be <code>null</code>.
     * @param inRequest the servlet request.
     *
     * @return the role names. If the user has no roles, the array will be
     * empty. A return value of <code>null</code> should be used if an error
     * occurred retrieving role information.
     *
     * @throws javax.servlet.ServletException if the user's role information 
     * is not available due to a fatal error.
     *
     */
    protected abstract String[] getRoles(Principal inPrincipal, ServletRequest inRequest) throws ServletException;

    /**
     * Does nothing.
     */
    @Override
    public void destroy() {
    }
}
