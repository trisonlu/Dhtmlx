package com.ausxin.dao;

import com.ausxin.util.AusxinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class BaseJdbcDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;


    public final JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public final SimpleJdbcTemplate getSimpleJdbcTemplate() {
        return this.simpleJdbcTemplate;
    }

    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    /**
     * 查询单个对象的值，由于直接调用spring的queryForObject，如果记录不存在的话，会抛出异常，为了避免异常出现，因此在基类加了该方法
     *
     * @param <T>
     * @param sql
     * @param rm
     * @param args
     * @return
     */
    public <T> T queryForObject(String sql, ParameterizedRowMapper<T> rm, Object... args) {

        List<T> queryList = getSimpleJdbcTemplate().query(sql, rm, args);

        if (queryList == null || queryList.size() < 1)
            return null;

        return queryList.get(0);
    }

    /**
     * 判断列名是否存在
     *
     * @param rs
     * @param columnName
     * @return
     * @throws SQLException
     */
    public boolean isExsitedColumn(ResultSet rs, String columnName) throws SQLException {

        if (rs == null || AusxinUtil.isEmpty(columnName))
            return false;

        int columnCount = rs.getMetaData().getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            if (columnName.equalsIgnoreCase(rs.getMetaData().getColumnName(i)))
                return true;
        }
        return false;
    }
    
    /**
	 * 关闭连接
	 * @param conn
	 * @param st
	 * @param rs
	 */
	protected void closeConn(Connection conn, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected String convertString(String dbString)
	{
		return dbString==null?"":dbString;
	}
	
	public int[] batchUpdate(String sql,BatchPreparedStatementSetter setter){
		return this.getJdbcTemplate().batchUpdate(sql, setter);
	}
	
}
