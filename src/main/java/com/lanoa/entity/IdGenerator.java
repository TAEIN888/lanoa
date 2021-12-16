package com.lanoa.entity;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class IdGenerator implements IdentifierGenerator, Configurable {

    public static final String METHOD = "tableName";
    private String tableName;

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
        tableName = ConfigurationHelper.getString(METHOD, properties);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        String sql = null;
        String nextId = null;

        switch (tableName) {
            case "GOODS":
                sql = "SELECT LPAD(IFNULL(MAX(GOODS_CODE), '1'), 8, '0') FROM TGOODS";
                break;
            default:
                break;
        }

        // JDBC Connection
        Connection con = null;
        try {
            con = sharedSessionContractImplementor.getJdbcConnectionAccess().obtainConnection();  // 공유세션으로부터 jdbc connection을 얻는다
            CallableStatement callStatement = con.prepareCall(sql);
            callStatement.executeQuery(); // SQL를 실행
            ResultSet rs = callStatement.getResultSet();

            if (rs.next()) {
                nextId = rs.getString(1); // 결과값 추출
            }
        } catch (SQLException sqlException) {
            throw new HibernateException(sqlException);
        } finally {
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return nextId;
    }
}
