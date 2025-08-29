Own test framework

Write your own test framework.

Support your own annotations @Test, @Before, @After.

Launch by calling a static method with the name of the class with tests.

That is, you need to do:

1. Create three annotations: @Test, @Before, @After.

2. Create a test class that will contain methods marked with annotations.

3. Create a "test launcher". As input, it should receive the name of the class with tests, in which you should find and launch the methods marked with annotations and point 1.

4. The launch algorithm should be as follows:
- method(s) Before
- current method Test
- method(s) After

For each such "troika", you need to create YOUR OWN test class object.

- An exception in one test should not interrupt the entire testing process.

- Based on the exceptions that occurred during testing, display test execution statistics (how many passed successfully, how many failed, how many in total)
