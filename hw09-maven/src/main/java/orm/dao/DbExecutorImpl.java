package orm.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import orm.entities.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public class DbExecutorImpl<T> {
    private final Logger log;
    private static final String URL = "jdbc:h2:mem:";
    private final Connection connection;

    private LinkedHashMap<String, Class<?>> fieldNames;
    private String idField;

    public DbExecutorImpl() throws SQLException {
        log = LoggerFactory.getLogger(DbExecutorImpl.class);
        this.connection = DriverManager.getConnection(URL);
    }

    public void createTable(Class<T> cls) throws NoSuchFieldException {
        fieldNames = getFieldNames(cls);
        idField = getIdFieldName(cls);

        try (PreparedStatement ps = connection.prepareStatement(sqlCreateTable(cls))) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    public void create(T objectData) {
        Savepoint savepoint;
        Class<T> aClass = (Class<T>) objectData.getClass();
        StringBuilder sql = sqlCreateEntity(aClass);

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            fillSqlWithValues(objectData, ps, sql);
            System.out.println(sql);
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

    }

    public void createOrUpdate(T objectData) {

    }

    public T load(long id, Class<T> cls) throws SQLException {
        StringBuilder sql = sqlSelectEntity(cls);

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = "";
                    int age = 0;

                    for (Map.Entry<String, Class<?>> pair : fieldNames.entrySet()) {
                        String fieldName = pair.getKey();
                        Class<?> fieldType = pair.getValue();

                        if (fieldType.getSimpleName().equals("String")) {
                            name = rs.getString(fieldName);
                            continue;
                        }

                        if (fieldType.getSimpleName().equals("int")) {
                            age = rs.getInt(fieldName);
                        }
                    }

                    sql.append(", Params: [").append(id).append("]");
                    System.out.println(sql);

                    return cls.getConstructor(long.class, String.class, int.class).newInstance(id, name, age);
                }
            } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | InvocationTargetException ex) {

                ex.printStackTrace();
            }
        }
        throw new SQLException("Entity with id " + id + " does not exist");
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

    private StringBuilder sqlSelectEntity(Class<T> cls) {
        StringBuilder sb = new StringBuilder("SELECT ");
        fieldNames.forEach((fieldName, fieldType) -> sb.append(fieldName).append(","));
        sb.deleteCharAt(sb.length() - 1)
                .append(" FROM ")
                .append(cls.getSimpleName().toLowerCase(Locale.ROOT))
                .append(" WHERE ")
                .append(idField)
                .append(" = ?");

        return sb;
    }

    private String getIdFieldName(Class<T> cls) throws NoSuchFieldException {
        for (Field fld : cls.getDeclaredFields()) {
            if (fld.isAnnotationPresent(Id.class)) {
                return fld.getName();
            }
        }
        throw new NoSuchFieldException("Class " + cls.getSimpleName() + " has no field with @Id annotation");
    }

    private String sqlCreateTable(Class<T> cls) {
        StringBuilder sql = new StringBuilder("create table ");
        sql.append(cls.getSimpleName().toLowerCase(Locale.ROOT)).append("(");
        constructColumns(sql);

        System.out.println(sql);
        return sql.toString();
    }

    private void constructColumns(StringBuilder sql) {
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

    private StringBuilder sqlCreateEntity(Class<T> cls) {
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(cls.getSimpleName().toLowerCase(Locale.ROOT)).append("(");
        fieldNames.forEach((fieldName, fieldType) -> sb.append(fieldName).append(","));
        sb.deleteCharAt(sb.length() - 1).append(") VALUES (");
        fieldNames.forEach((fieldName, fieldType) -> sb.append("?,"));
        sb.deleteCharAt(sb.length() - 1).append(")");
        return sb;
    }

    private LinkedHashMap<String, Class<?>> getFieldNames(Class<T> cls) {
        LinkedHashMap<String, Class<?>> fields = new LinkedHashMap<>();

        try {
            for (Field fld : cls.getConstructor().newInstance().getClass().getDeclaredFields()) {
                fld.setAccessible(true);
                fields.put(fld.getName(), fld.getType());
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            log.error(ex.getMessage());
        }

        return fields;
    }

    private void fillSqlWithValues(T objectData, PreparedStatement ps, StringBuilder sql) throws SQLException {
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
