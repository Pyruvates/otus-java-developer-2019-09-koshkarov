package orm.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import orm.entities.Id;

import java.beans.Statement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public class DbExecutorImpl<T> {
    private final Logger log;
    private static final String URL = "jdbc:h2:mem:";
    private final Connection connection;

    private Map<String, Class<?>> fieldNames;

    public DbExecutorImpl() throws SQLException {
        log = LoggerFactory.getLogger(DbExecutorImpl.class);
        this.connection = DriverManager.getConnection(URL);
    }

    public void createTable(Class<T> cls) {
        saveFieldNames(cls);

        try (PreparedStatement ps = connection.prepareStatement(generateCreateTableQuery(cls))) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    public void create(T objectData) {
        Savepoint savepoint;
        @SuppressWarnings("unchecked") Class<T> aClass = (Class<T>) objectData.getClass();
        StringBuilder sql = generateInsertQuery(aClass);

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            fillInsertQueryWithValues(sql, objectData, ps);

            savepoint = connection.setSavepoint("insert_savepoint");

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
        Savepoint savepoint;
        @SuppressWarnings("unchecked") Class<T> aClass = (Class<T>) objectData.getClass();
        StringBuilder sql = generateUpdateQuery(aClass);

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            fillUpdateQueryWithValues(sql, objectData, ps);

            savepoint = connection.setSavepoint("update_savepoint");

            try {
                ps.executeUpdate();
            } catch (SQLException ex) {
                rollbackToSavepoint(connection, savepoint);
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    public void createOrUpdate(T objectData) {

    }

    public T load(long id, Class<T> cls) {
        StringBuilder sql = generateSelectQuery(cls);

        T instance = null;
        try {
            instance = cls.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    T finalInstance = instance;

                    fieldNames.forEach((fieldName, fieldType) -> {
                        try {
                            Object fieldValue = getRSValue(rs, fieldName, fieldType);
                            Statement st = new Statement(finalInstance, "set" + firstCharToUpperCase(fieldName), new Object[]{fieldValue});
                            st.execute();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });

                    sql.append(", Params: [").append(id).append("]");
                    System.out.println(sql);
                    return finalInstance;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return instance;
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

    private String generateCreateTableQuery(Class<T> cls) {
        StringBuilder sql = new StringBuilder();
        sql.append("create table ")
                .append(cls.getSimpleName().toLowerCase(Locale.ROOT))
                .append("(");
        toListColumns(sql);

        System.out.println(sql);
        return sql.toString();
    }

    private void toListColumns(StringBuilder sql) {
        for (Map.Entry<String, Class<?>> pair : fieldNames.entrySet()) {
            String fieldName = pair.getKey();
            Class<?> fieldType = pair.getValue();

            if (fieldType.getSimpleName().equals("long")) {
                sql.append(fieldName).append(" bigint(20) not null,");
                continue;
            }

            if (fieldType.getSimpleName().equals("String")) {
                sql.append(fieldName).append(" varchar(255),");
                continue;
            }

            if (fieldType.getSimpleName().equals("int")) {
                sql.append(fieldName).append(" int(3),");
            }
        }

        sql.deleteCharAt(sql.length() - 1).append(")");
    }

    private StringBuilder generateSelectQuery(Class<T> cls) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        fieldNames.forEach((fieldName, fieldType) -> sb.append(fieldName).append(","));
        sb.deleteCharAt(sb.length() - 1)
                .append(" FROM ")
                .append(cls.getSimpleName().toLowerCase(Locale.ROOT))
                .append(" WHERE ")
                .append(fieldNames.keySet().iterator().next())
                .append(" = ?");
        System.out.println(sb);
        return sb;
    }

    private StringBuilder generateUpdateQuery(Class<T> cls) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ")
                .append(cls.getSimpleName().toLowerCase(Locale.ROOT))
                .append(" SET ");
        try {
            for (Field fld : cls.getConstructor().newInstance().getClass().getDeclaredFields()) {
                if (fld.isAnnotationPresent(Id.class)) {
                    continue;
                }

                fld.setAccessible(true);
                sb.append(fld.getName()).append(" = ?,");
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            log.error(ex.getMessage());
        }
        sb.deleteCharAt(sb.length() - 1)
                .append(" WHERE ")
                .append(fieldNames.keySet().iterator().next())
                .append(" = ?");

        System.out.println(sb);
        return sb;
    }

    private StringBuilder generateInsertQuery(Class<T> cls) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ")
                .append(cls.getSimpleName().toLowerCase(Locale.ROOT))
                .append("(");
        fieldNames.forEach((fieldName, fieldType) -> sb.append(fieldName).append(","));
        sb.deleteCharAt(sb.length() - 1).append(") VALUES (");
        fieldNames.forEach((fieldName, fieldType) -> sb.append("?,"));
        sb.deleteCharAt(sb.length() - 1).append(")");
        return sb;
    }

    private void saveFieldNames(Class<T> cls) {
        fieldNames = new LinkedHashMap<>();

        try {
            for (Field fld : cls.getConstructor().newInstance().getClass().getDeclaredFields()) {
                fld.setAccessible(true);
                fieldNames.put(fld.getName(), fld.getType());
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            log.error(ex.getMessage());
        }
    }

    private void fillInsertQueryWithValues(StringBuilder sql, T objectData, PreparedStatement ps) throws SQLException {
        sql.append(", Params: [");

        List<Object> fieldValues = getFieldValues(objectData);

        for (Object o : fieldValues) {
            if (o instanceof Long) {
                long value = (long) o;
                ps.setLong(1, value);
                sql.append(value).append(",");
                continue;
            }

            if (o instanceof String) {
                String value = (String) o;
                ps.setString(2, value);
                sql.append(value).append(",");
                continue;
            }

            if (o instanceof Integer) {
                int value = (int) o;
                ps.setInt(3, value);
                sql.append(value).append(",");
            }

            sql.deleteCharAt(sql.length() -1).append("]");
        }
        System.out.println(sql);
    }

    private void fillUpdateQueryWithValues(StringBuilder sql, T objectData, PreparedStatement ps) throws SQLException {
        sql.append(", Params: [");

        List<Object> fieldValues = getFieldValues(objectData);

        for (Object o : fieldValues) {
            if (o instanceof String) {
                String value = (String) o;
                ps.setString(1, value);
                sql.append(value).append(",");
                continue;
            }

            if (o instanceof Integer) {
                int value = (int) o;
                ps.setInt(2, value);
                sql.append(value).append(",");
            }

            if (o instanceof Long) {
                long value = (long) o;
                ps.setLong(3, value);
                sql.append(value).append(",");
                continue;
            }

            sql.deleteCharAt(sql.length() -1).append("]");
        }
        System.out.println(sql);
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

    private Object getRSValue(ResultSet rs, String fieldName, Class<?> fieldType) throws SQLException {
        if (fieldType.getSimpleName().equals("long")) {
            return rs.getLong(fieldName);
        }

        if (fieldType.getSimpleName().equals("String")) {
            return rs.getString(fieldName);
        }

        if (fieldType.getSimpleName().equals("int")) {
            return rs.getInt(fieldName);
        }

        return new Object();
    }

    private String firstCharToUpperCase(String value) {
        return value.substring(0, 1).toUpperCase(Locale.ROOT) + value.substring(1);
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
