package orm.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import orm.entities.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbExecutorImpl<T> {
    private final Logger log;
    private static final String URL = "jdbc:h2:mem:";

    private final Connection connection;

    public DbExecutorImpl() throws SQLException {
        log = LoggerFactory.getLogger(DbExecutorImpl.class);
        this.connection = DriverManager.getConnection(URL);
    }

    public void createTable(Class<T> cls) {
        List<String> fieldNames = getFieldNames(cls);

        try (PreparedStatement ps = connection.prepareStatement(sqlCreateTable(cls, fieldNames))) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    public void create(T objectData) {
        Savepoint savepoint;
        Class<T> aClass = (Class<T>) objectData.getClass();

        try (PreparedStatement ps = connection.prepareStatement(sqlCreateEntity(aClass))) {
            fillSqlWithValues(objectData, ps);
            savepoint = connection.setSavepoint("savepoint_on_insert");

            try {
                ps.executeUpdate();
            } catch (SQLException ex) {
                rollbackToSavepoint(connection, savepoint);
                log.error(ex.getMessage());
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    public void update(T objectData) {

    }

    public void createOrUpdate(T objectData) {

    }

    public T load(long id, Class<T> cls) throws SQLException {
        List<String> fieldNames = getFieldNames(cls);

        try (PreparedStatement ps = connection.prepareStatement(sqlSelectEntity(cls))) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    long objectId = rs.getLong(getIdFieldName(cls));
                    String second = rs.getString(fieldNames.get(1));
                    int third = rs.getInt(fieldNames.get(2));

                    return cls.getConstructor(long.class, String.class, int.class)
                            .newInstance(objectId, second, third);
                }
            } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | InvocationTargetException ex) {

                ex.printStackTrace();
            }
        }
        return null;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                log.error(ex.getMessage());
            }
        }
    }

    private String sqlSelectEntity(Class<T> cls) {
        List<String> fieldNames = getFieldNames(cls);

        StringBuilder sb = new StringBuilder("SELECT ");

        for (String fieldName : fieldNames) {
            sb.append(fieldName).append(",");
        }

        sb.deleteCharAt(sb.length() - 1)
                .append(" FROM ")
                .append(cls.getSimpleName())
                .append(" WHERE ")
                .append(getIdFieldName(cls))
                .append(" = ?");

        System.out.println(sb);

        return sb.toString();
    }

    private String getIdFieldName(Class<T> cls) {
        for (Field fld : cls.getDeclaredFields()) {
            if (fld.isAnnotationPresent(Id.class)) {
                return fld.getName();
            }
        }
        return "";
    }

    private String sqlCreateTable(Class<T> cls, List<String> fieldNames) {
        StringBuilder sql = new StringBuilder("create table ");
        sql.append(cls.getSimpleName()).append("(");

        for (int i = 0; i < fieldNames.size(); i++) {
            if (i == 0) {
                sql.append(fieldNames.get(i)).append(" bigint(20) not null, ");
            }
            if (i == 1) {
                sql.append(fieldNames.get(i)).append(" varchar(255), ");
            }
            if (i == 2) {
                sql.append(fieldNames.get(i)).append(" int(3))");
            }
        }

        System.out.println(sql);
        return sql.toString();
    }

    private String sqlCreateEntity(Class<T> cls) {
        StringBuilder sb = new StringBuilder("INSERT INTO ");

        try {
            sb.append(cls.getConstructor().newInstance().getClass().getSimpleName()).append("(");
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
            log.error(ex.getMessage());
        }

        getFieldNames(cls).forEach(elem -> sb.append(elem).append(","));

        sb.deleteCharAt(sb.length() - 1).append(") VALUES (");

        getFieldNames(cls).forEach(elem -> sb.append("?,"));

        sb.deleteCharAt(sb.length() - 1).append(")");

        System.out.println(sb);
        return sb.toString();
    }

    private List<String> getFieldNames(Class<T> cls) {
        List<String> fieldNames = new ArrayList<>();

        try {
            for (Field fld : cls.getConstructor().newInstance().getClass().getDeclaredFields()) {
                fld.setAccessible(true);
                fieldNames.add(fld.getName());
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            log.error(ex.getMessage());
        }

        return fieldNames;
    }

    private void fillSqlWithValues(T objectData, PreparedStatement ps) throws SQLException {
        List<Object> fieldValues = getFieldValues(objectData);

        for (Object o : fieldValues) {
            if (o instanceof Long) {
                ps.setLong(1, (long) o);
            }
            if (o instanceof String) {
                ps.setString(2, (String) o);
            }
            if (o instanceof Integer) {
                ps.setInt(3, (int) o);
            }
        }
    }

    private List<Object> getFieldValues(T objectData) {
        List<Object> values = new ArrayList<>();

        try {
            for (Field fld : objectData.getClass().getDeclaredFields()) {
                fld.setAccessible(true);
                values.add(fld.get(objectData));
            }
        } catch (IllegalAccessException ex) {
            log.error(ex.getMessage());
        }

        return values;
    }

    private void rollbackToSavepoint(Connection con, Savepoint savepoint) {
        if (savepoint != null) {
            try {
                con.rollback(savepoint);
            } catch (SQLException ex) {
                log.error(ex.getMessage());
            }
        }
    }
}
