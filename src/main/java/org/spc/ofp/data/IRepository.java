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

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author Corey Cole <coreyc@spc.int>
 *
 */
public interface IRepository<T> {

	T find(final String query, final RowMapper<T> mapper, final Object... args);	
	List<T> list(final String query, final RowMapper<T> mapper, final Object... args);
}
