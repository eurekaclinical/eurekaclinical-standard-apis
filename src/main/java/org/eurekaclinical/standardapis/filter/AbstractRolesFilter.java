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

    @Override
    public final void doFilter(ServletRequest inRequest, ServletResponse inResponse, FilterChain inChain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) inRequest;
        String username = servletRequest.getRemoteUser();
        if (username != null) {
            HttpSession session = servletRequest.getSession(false);
            assert session != null : "session should not be null";

            Principal principal = servletRequest.getUserPrincipal();
            assert principal != null : "principal should not be null";

            String[] roleNames = (String[]) session.getAttribute("roles");

            if (roleNames == null) {
                roleNames = getRoles(principal);
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
     * Gets the user's roles.
     * 
     * @param principal the user's principal. Guaranteed not <code>null</code>.
     * 
     * @return the role names.
     * 
     * @throws ServletException if an error occurred getting role information.
     */
    protected abstract String[] getRoles(Principal principal) throws ServletException;

    /**
     * Does nothing.
     */
    @Override
    public void destroy() {
    }
}
