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
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * @author Corey Cole <coreyc@spc.int>
 *
 */
public class JdbcRepository extends RepositoryImpl {

	// Named to distinguish it from the TUBS datasource 
	@Resource(name = "jdbcDataSource")
	protected DataSource dataSource;
	
	public void setDataSource(final DataSource ds) {
		this.dataSource = ds;
	}
	
	/** 
	 * @return 
	 * @see org.spc.ofp.observer.domain.RepositoryImpl#find(java.lang.String, java.util.Map)
	 */
	@Override
	public <T> T find(final String query, final RowMapper<T> mapper, final Object... args) {		
		final SimpleJdbcTemplate template = new SimpleJdbcTemplate(dataSource);
		// The query should find a unique record, so the only overhead is messing around with a list
		// that contains either zero or one item.
		final List<T> temp = template.query(query, mapper, args);
		return temp.isEmpty() ? null : temp.get(0);
	}

	/**
	 * @see org.spc.ofp.observer.domain.RepositoryImpl#list(java.lang.String, java.util.Map)
	 */
	@Override
	public <T> List<T> list(final String query, final RowMapper<T> mapper, final Object... args) {
		return new SimpleJdbcTemplate(dataSource).query(query, mapper, args);
	}

	@Override
	public Map<String,Object> rawExecute(String query, final Object... args) {
		return new SimpleJdbcTemplate(dataSource).queryForMap(query, args);
	}
}
