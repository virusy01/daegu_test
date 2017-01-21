package com.sck.util;

import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.lob.LobHandler;

public class ReaderTypeHandler extends BaseTypeHandler<Reader>
{
	@Autowired
	private LobHandler lobHandler;

	@Override
	public Reader getNullableResult(ResultSet rs, String columnName) throws SQLException
	{
		return lobHandler.getClobAsCharacterStream(rs, columnName);
	}

	@Override
	public Reader getNullableResult(ResultSet rs, int columnIndex) throws SQLException
	{
		return lobHandler.getClobAsCharacterStream(rs, columnIndex);
	}

	@Override
	public Reader getNullableResult(CallableStatement cs, int columnIndex) throws SQLException
	{
		 return toReader(cs.getClob(columnIndex));
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Reader parameter, JdbcType jdbcType) throws SQLException
	{
		if(ps.getConnection().getMetaData().getDatabaseProductName().toLowerCase().contains("oracle"))
		{
			ps.setClob(i, parameter);
		}
		else
		{
			lobHandler.getLobCreator().setClobAsCharacterStream(ps, i, parameter, Integer.MAX_VALUE);
		}
	}
	
	private Reader toReader(Clob clob) throws SQLException
	{
		return clob == null ? null : clob.getCharacterStream();
	}

}
