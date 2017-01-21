package snp.spring.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * RFC3339 Handler for Mybatis
 *
 * @author lempel@gmail.com
 * @sinced 2016-12-13
 */
@MappedJdbcTypes(JdbcType.DATE)
public class DateHandler extends BaseTypeHandler {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setDate(i, fromString(parameter));
        } catch (ParseException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getDate(columnName);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getDate(columnIndex);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getDate(columnIndex);
    }

    // 2014-07-21T16:35:27.000Z
    private static final SimpleDateFormat format = new SimpleDateFormat();

    //private static final SimpleDateFormat yyyy_MM_dd_format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Convert {@link Date} to RFC3339 (GMT)
     *
     * @param date Java Date
     * @return RFC3339 (GMT)
     */
    public static String toString(Date date) {
        synchronized (format) {
            return format.format(date);
        }
    }

    /**
     * Convert RFC3339 (GMT) to {@link Date}
     *
     * @param date RFC3339 (GMT)
     * @return Java Date
     * @throws ParseException
     */
    public static Date fromString(final Object date) throws ParseException {
        Date result = null;

        if (date != null && !"".equals(date.toString().trim())){
            String strDate = String.valueOf(date).trim();
            int dateLength = strDate.length();

            String datePattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
            switch (dateLength){
                case 8:
                    datePattern = "yyyyMMdd";
                    break;
                case 10:
                    datePattern = "yyyy-MM-dd";
                    break;
                case 14:
                    datePattern = "yyyyMMddHHmmss";
                    break;
                default:
                    break;

            }
            format.applyPattern(datePattern);
            result = new Date(format.parse(strDate).getTime());
        }

        return result;
    }
}