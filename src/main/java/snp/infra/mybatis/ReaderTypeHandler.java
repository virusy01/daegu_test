package snp.infra.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.lob.LobHandler;

import java.io.Reader;
import java.sql.*;

/**
 * TODO 설명 작성
 *
 * <br/>개정이력<br/>
 * 2016-11-25 kirious@xenoimpact.com mybatis type handler 추가<br/>
 *
 * @author kirious@xenoimpact.com
 * @since 2016-11-25
 */
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
