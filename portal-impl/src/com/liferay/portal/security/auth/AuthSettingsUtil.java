/*
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

/**
 * <a href="AuthSettingsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class AuthSettingsUtil {
	public static boolean isLDAPAuthEnabled(long companyId) throws SystemException {
		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_AUTH_ENABLED,
				PropsValues.LDAP_AUTH_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isNtlmEnabled(long companyId)
		throws SystemException {

		if (PrefsPropsUtil.getBoolean(
			companyId, PropsKeys.NTLM_AUTH_ENABLED,
			PropsValues.NTLM_AUTH_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isSiteMinderEnabled(long companyId)
		throws SystemException {

		if (PrefsPropsUtil.getBoolean(
			companyId, PropsKeys.SITEMINDER_AUTH_ENABLED,
			PropsValues.SITEMINDER_AUTH_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

}
