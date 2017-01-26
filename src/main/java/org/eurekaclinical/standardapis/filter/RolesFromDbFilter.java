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
import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.eurekaclinical.standardapis.dao.UserDao;
import org.eurekaclinical.standardapis.entity.RoleEntity;
import org.eurekaclinical.standardapis.entity.UserEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A filter to fetch roles from a database, and assign them to the current
 * principal.
 *
 * @author hrathod
 *
 */
@Singleton
public class RolesFromDbFilter implements RolesFilter {

    private final UserDao<? extends UserEntity<? extends RoleEntity>> userDao;

    @Inject
    public RolesFromDbFilter(UserDao<? extends UserEntity<? extends RoleEntity>> inUserDao) {
        this.userDao = inUserDao;
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest inRequest, ServletResponse inResponse,
            FilterChain inChain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) inRequest;
        Principal principal = servletRequest.getUserPrincipal();
        if (principal != null) {
            UserEntity<? extends RoleEntity> user = this.userDao.getByPrincipal(principal);
            Set<String> roles = new HashSet<>();
            if (user != null) {
                List<? extends RoleEntity> roleEntities = user.getRoles();
                for (RoleEntity roleEntity : roleEntities) {
                    roles.add(roleEntity.getName());
                }
            }
            HttpServletRequest wrappedRequest = new RolesRequestWrapper(
                    servletRequest, principal, roles);
            inChain.doFilter(wrappedRequest, inResponse);
        } else {
            inChain.doFilter(inRequest, inResponse);
        }
    }

    @Override
    public void destroy() {
    }

}
