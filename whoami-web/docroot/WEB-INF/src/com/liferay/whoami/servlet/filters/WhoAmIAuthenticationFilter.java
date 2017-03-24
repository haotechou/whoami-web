/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.whoami.servlet.filters;

import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortletClassInvoker;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Ryan Park
 */
public class WhoAmIAuthenticationFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain)
		throws IOException, ServletException {

		try {
			PortletClassInvoker.invoke(
				true, "1_WAR_oauthportlet",
				new MethodKey(
					"com.liferay.oauth.hook.filter.OAuthFilter", "doFilter",
					ServletRequest.class, ServletResponse.class,
					FilterChain.class),
				servletRequest, servletResponse, filterChain);
		}
		catch (IOException ioe) {
			throw ioe;
		}
		catch (ServletException se) {
			throw se;
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

}