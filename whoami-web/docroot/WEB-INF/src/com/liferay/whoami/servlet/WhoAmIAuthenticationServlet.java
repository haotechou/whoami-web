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

package com.liferay.whoami.servlet;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.ProtectedServletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * @author Haote Chou
 */
public class WhoAmIAuthenticationServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

			try {
				getUser(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	protected void getUser(
			HttpServletRequest request, HttpServletResponse response) 
		throws Exception {
		
		ProtectedServletRequest protectedServletRequest = 
			(ProtectedServletRequest) request;
		
		long userId = GetterUtil.getLong(
			protectedServletRequest.getRemoteUser());
		
		User user = UserLocalServiceUtil.getUser(userId);
		
		JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject();
		
		responseJSONObject.put("uuid", user.getUserUuid());
		responseJSONObject.put("fullName", user.getFullName());
		responseJSONObject.put("emailAddress", user.getEmailAddress());
		responseJSONObject.put("avatarURL", StringPool.BLANK);
		
		String responseJSON = responseJSONObject.toString();
		
		response.setContentType(ContentTypes.APPLICATION_JSON);
		
		ServletOutputStream servletOutputStream = response.getOutputStream();
		
		servletOutputStream.write(responseJSON.getBytes(StringPool.UTF8));
	}

}