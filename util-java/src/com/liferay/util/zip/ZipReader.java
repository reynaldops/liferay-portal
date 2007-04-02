/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.util.zip;

import com.liferay.portal.kernel.util.ByteArrayMaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ZipReader.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 *
 */
public class ZipReader implements Serializable {

	public ZipReader(File file) throws Exception {
		_zis = new ZipInputStream(new FileInputStream(file));
	}

	public ZipReader(InputStream stream) {
		try {
			_zis = new ZipInputStream(stream);

			while (true) {
				ZipEntry entry = _zis.getNextEntry();

				if (entry == null) {
					break;
				}

				String currentName = entry.getName();

				ByteArrayMaker bam = new ByteArrayMaker();

				while (true) {
					int count = _zis.read(_data, 0, _BUFFER);

					if (count == -1) {
						break;
					}

					bam.write(_data, 0, count);
				}

				byte[] byteArray = bam.toByteArray();

				_entries.put(currentName, byteArray);
			}
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
		finally {
			try {
				_zis.close();
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}
		}
	}

	public Map getEntries() {
		return _entries;
	}

	public String getEntryAsString(String name) {
		byte[] byteArray = getEntryAsByteArray(name);

		if (byteArray != null) {
			return new String(byteArray);
		}

		return null;
	}

	public byte[] getEntryAsByteArray(String name) {
		return (byte[])_entries.get(name);
	}

	private static final int _BUFFER = 2048;

	private static Log _log = LogFactory.getLog(ZipReader.class);

	private ZipInputStream _zis;
	private Map _entries = new LinkedHashMap();
	private byte[] _data = new byte[_BUFFER];

}