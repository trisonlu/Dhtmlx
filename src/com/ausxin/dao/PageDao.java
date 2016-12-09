package com.ausxin.dao;

import com.ausxin.dao.BaseJdbcDao;
import com.ausxin.domain.PageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageDao extends BaseJdbcDao {
	public List findPageListByJDBCSql(final String sql, Object[] args, PageInfo page) {
		page.setRowCount(this.getCountSql(sql, args));
		List list = this.findPageListByJDBCSql(sql, args, page.getRowStart(), page.getRowEnd());
		return list;
	}

	/**
	 * JDBC oracle分页查询
	 *
	 * @param sql
	 * @param args
	 * @param start
	 * @param end
	 * @return
	 */
	public List findPageListByJDBCSql(final String sql, Object[] args, final int start, final int end) {
		StringBuffer oracleQuery = new StringBuffer();
		oracleQuery.append("select * from (select t.*,rownum as rowno from (");
		oracleQuery.append(sql);
		oracleQuery.append(") t ) where rowno between ");
		oracleQuery.append(start);
		oracleQuery.append(" and ");
		oracleQuery.append(end);

		List list = new ArrayList();

		// 判断先是否有参数，否则可能会报错
		if (args == null) {
			list = getJdbcTemplate().queryForList(oracleQuery.toString());
		} else {
			list = getJdbcTemplate().queryForList(oracleQuery.toString(), args);
		}
		return list;
	}

	/**
	 * JDBC mysql分页查询
	 *
	 * @param sql
	 * @param args
	 * @param start
	 * @param end
	 * @return
	 */
	public List findPageListByJDBCSqlMy(final String sql, Object[] args, final int start, final int end) {
		StringBuffer mysqlQuery = new StringBuffer();
		mysqlQuery.append("select * from (");
		mysqlQuery.append(sql);
		mysqlQuery.append(") t  limit ");
		mysqlQuery.append(start<=0?0:start-1);
		mysqlQuery.append(" , ");
		mysqlQuery.append(end-1);

		List list = new ArrayList();

		// 判断先是否有参数，否则可能会报错
		if (args == null) {
			list = getJdbcTemplate().queryForList(mysqlQuery.toString());
		} else {
			list = getJdbcTemplate().queryForList(mysqlQuery.toString(), args);
		}
		return list;
	}

	/**
	 * 查询总条数

	 * 
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public int getCountSql(final String sql, Object[] args) {
		int rowCount = 0;
		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("select count(*) as rowCount from (");
		sqlCount.append(sql);
		sqlCount.append(") cst ");


		List list = getJdbcTemplate().queryForList(sqlCount.toString(), args);
		if (list != null && !list.isEmpty()) {
			Map mp = (Map) list.get(0);
			rowCount = Integer.parseInt(String.valueOf(mp.get("rowCount")));
		}
		return rowCount;
	}
	
	public int queryForInt(final String sql, Object[] args) {
		int result = 0;

		result = getJdbcTemplate().queryForInt(sql, args);
		
		return result;
	}
}
