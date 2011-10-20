/*
 * Copyright (C) 2011 Secretariat of the Pacific Community
 *
 * This file is part of TUBS.
 *
 * TUBS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TUBS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with TUBS.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.spc.ofp.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author Corey Cole <coreyc@spc.int>
 *
 */
public class Repository<T> implements IRepository<T> {

	@Autowired
	protected RepositoryImpl repository;
	
	private static final SimpleDateFormat SQLITE_DATE_FORMAT =
	    new SimpleDateFormat("yyyy-MM-dd", Locale.US);
	
	private static final SimpleDateFormat SQLITE_TIMESTAMP_FORMAT =
		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
	
	public void setRepository(final RepositoryImpl repo) {
		this.repository = repo;
	}
	
	@Override
	public T find(final String query, final RowMapper<T> mapper, final Object... args) {
		return repository.find(query, mapper, args);
	}

	@Override
	public List<T> list(final String query, final RowMapper<T> mapper, final Object... args) {
		return repository.list(query, mapper, args);
	}
	
	public static Date readTimestamp(final ResultSet rs, final String columnName) throws SQLException {
		final String dateValue = rs.getString(columnName);
		if (null == dateValue || "".equalsIgnoreCase(dateValue.trim())) { return null ; }
		if (dateValue.trim().startsWith("-4")) { return null; } // Some of the data has timestamps that go back to 4712 BC
		Date dt = null;
		try {
			synchronized(SQLITE_TIMESTAMP_FORMAT) {
				dt = SQLITE_TIMESTAMP_FORMAT.parse(dateValue);
			}
		} catch (ParseException ignoreMe) { } // NOPMD
		return dt;
	}
	
	public static Date readDate(final ResultSet rs, final String columnName) throws SQLException {
		final String dateValue = rs.getString(columnName);
		if (null == dateValue || "".equalsIgnoreCase(dateValue.trim())) { return null ; }
		Date dt = null;
		try {
			synchronized(SQLITE_DATE_FORMAT) {
				dt = SQLITE_DATE_FORMAT.parse(dateValue);
			}
		} catch (ParseException ignoreMe) { } // NOPMD
		return dt;
	}
	
	public static Long readLong(final ResultSet rs, final String columnName) throws SQLException {
		final long value = rs.getLong(columnName);
		return rs.wasNull() ? null : Long.valueOf(value);
	}
	
	public static Integer readInteger(final ResultSet rs, final String columnName) throws SQLException {
		final int value = rs.getInt(columnName);
		return rs.wasNull() ? null : Integer.valueOf(value);
	}
	
	public static Double readDouble(final ResultSet rs, final String columnName) throws SQLException {
		final double value = rs.getDouble(columnName);
		return rs.wasNull() ? null : Double.valueOf(value);
	}

}
