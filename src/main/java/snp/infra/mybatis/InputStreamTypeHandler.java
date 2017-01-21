package snp.infra.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.InputStream;
import java.sql.*;

/**
 * TODO 설명 작성
 *
 * <br/>개정이력<br/>
 * 2016-11-25 kirious@xenoimpact.com mybatis type handler 추가<br/>
 * 2016-11-28 lempel@gmail.com code review<br/>
 *
 * @author kirious@xenoimpact.com
 * @since 2016-11-25
 */
public class InputStreamTypeHandler extends BaseTypeHandler<InputStream> {
    @Override
    public InputStream getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
        return toInputStream(rs.getBlob(columnName));
    }

    @Override
    public InputStream getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
        return toInputStream(rs.getBlob(columnIndex));
    }

    @Override
    public InputStream getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
        return toInputStream(cs.getBlob(columnIndex));
    }

    @Override
    public void setNonNullParameter(final PreparedStatement ps, final int i, final InputStream parameter,
                                    final JdbcType jdbcType) throws SQLException {
        ps.setBlob(i, parameter);
    }

    private InputStream toInputStream(final Blob blob) throws SQLException {
        return blob == null ? null : blob.getBinaryStream();
    }
}
