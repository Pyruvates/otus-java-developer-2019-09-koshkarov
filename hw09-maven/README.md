Homemade ORM

The job should use the H2 database.

Create a User table in the database with the following fields:

* id bigint(20) NOT NULL auto_increment
* name varchar(255)
* age int(3)

Create your own @Id annotation

Create a User class (with fields that match the table, mark the id field with annotation).

Write a DBExecutor that can work with classes that have a field with the @Id annotation.

DBExecutor should save the object to the database, and read the object from the database.

The table name should match the class name, and the class fields are the columns in the table.

DBExecutor methods:

* void create(T objectData);

* void update(T objectData);

* void createOrUpdate(T objectData); // optional.
* T load(long id, Class clazz);

In fact, you need to create a "query generator". The generated queries should be executed in the already existing DbExecutor.

Test it on the User class.

The createOrUpdate method is optional. It should "check" for the presence of an object in the table and create a new one or update an existing one.

Create another Account table:
* no bigint(20) NOT NULL auto_increment
* type varchar(255)
* rest int(3)

Create an Account class for this table and test the DBExecutor on this class.
