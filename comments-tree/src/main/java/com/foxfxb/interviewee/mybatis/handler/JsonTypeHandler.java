package com.foxfxb.interviewee.mybatis.handler;

import com.foxfxb.interviewee.utils.JSONUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonTypeHandler extends BaseTypeHandler<Object> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object t, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSONUtils.toJson(t));
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return JSONUtils.toObject(resultSet.getString(columnName), Object.class);
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return JSONUtils.toObject(resultSet.getString(columnIndex), Object.class);
    }

    @Override
    public Object getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return JSONUtils.toObject(callableStatement.getString(columnIndex), Object.class);
    }
}
