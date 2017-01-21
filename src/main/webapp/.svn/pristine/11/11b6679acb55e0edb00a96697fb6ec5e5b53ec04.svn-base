package com.sck.util;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class InputStreamTypeHandler extends BaseTypeHandler<InputStream>
{
	
	public InputStreamTypeHandler()
	{
		
	}
	
	@Override
	public InputStream getNullableResult(ResultSet rs, String columnName) throws SQLException
	{
		return toInputStream(rs.getBlob(columnName));
	}

	@Override
	public InputStream getNullableResult(ResultSet rs, int columnIndex) throws SQLException
	{
		return toInputStream(rs.getBlob(columnIndex));
	}

	@Override
	public InputStream getNullableResult(CallableStatement cs, int columnIndex) throws SQLException
	{
		return toInputStream(cs.getBlob(columnIndex));
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,InputStream parameter, JdbcType jdbcType) throws SQLException
	{
		ps.setBlob(i, parameter);

	}
	
	private InputStream toInputStream(Blob blob) throws SQLException
	{
		return blob == null ? null : blob.getBinaryStream();
	}

}
