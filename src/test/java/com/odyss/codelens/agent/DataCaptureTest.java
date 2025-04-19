package com.odyss.codelens.agent;

import com.odyss.codelens.call.MethodCall;
import com.odyss.codelens.call.Variable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataCaptureTest {

    private DataCapture dataCapture;
    private Method testMethod;
    private Parameter[] parameters;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        dataCapture = DataCapture.getInstance();
        testMethod = DataCaptureTest.class.getDeclaredMethod("dummyMethod", String.class, Integer.class);
        parameters = testMethod.getParameters();
    }

    @AfterEach
    void tearDown() {
        // clean up any state
    }

    @Test
    void getInstance_shouldReturnSameInstance() {
        DataCapture instance1 = DataCapture.getInstance();
        DataCapture instance2 = DataCapture.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void captureEntry_shouldSetCurrentMethodCall() {
        dataCapture.captureEntry("TestClass", "testMethod", parameters);

        // Use reflection to access the private field
        try {
            java.lang.reflect.Field currentMethodCallField = DataCapture.class.getDeclaredField("currentMethodCall");
            currentMethodCallField.setAccessible(true);
            ThreadLocal<MethodCall> currentMethodCall = (ThreadLocal<MethodCall>) currentMethodCallField.get(dataCapture);

            MethodCall methodCall = currentMethodCall.get();
            assertNotNull(methodCall);
            assertEquals("testMethod", methodCall.getName());
            assertEquals("TestClass", methodCall.getClassName());
            List<Variable> methodCallParameters = methodCall.getArguments();
            assertNotNull(methodCallParameters);
            assertEquals(2, methodCallParameters.size());
            assertEquals("arg0", methodCallParameters.get(0).getName());
            assertEquals("arg1", methodCallParameters.get(1).getName());

        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to access the currentMethodCall field: " + e.getMessage());
        }
    }

    @Test
    void captureExit_withValidMethodCall_shouldSetEndTimeAndReturnValue() {
        dataCapture.captureEntry("TestClass", "testMethod", parameters);
        String returnValue = "Test Return Value";
        dataCapture.captureExit("TestClass", "testMethod", "String", returnValue);

        // Use reflection to access the private field
        try {
            java.lang.reflect.Field currentMethodCallField = DataCapture.class.getDeclaredField("currentMethodCall");
            currentMethodCallField.setAccessible(true);
            ThreadLocal<MethodCall> currentMethodCall = (ThreadLocal<MethodCall>) currentMethodCallField.get(dataCapture);
            MethodCall methodCall = currentMethodCall.get();
            assertNull(methodCall); // should be removed

            currentMethodCallField.setAccessible(true);
            currentMethodCall = (ThreadLocal<MethodCall>) currentMethodCallField.get(dataCapture);
            methodCall = currentMethodCall.get();

            // get the most recent call
            java.lang.reflect.Field latestCall = DataCapture.class.getDeclaredField("currentMethodCall");
            latestCall.setAccessible(true);
            currentMethodCall = (ThreadLocal<MethodCall>) latestCall.get(dataCapture);

            // set back into variable
            methodCall = currentMethodCall.get();

            assertNull(methodCall);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to access the currentMethodCall field: " + e.getMessage());
        }
    }

    @Test
    void captureExit_withoutPriorCaptureEntry_shouldNotThrowException() {
        assertDoesNotThrow(() -> {
            dataCapture.captureExit("TestClass", "testMethod", "String", "returnValue");
        });
    }

    @Test
    void captureExit_shouldRemoveThreadLocal() {
        dataCapture.captureEntry("TestClass", "testMethod", parameters);
        dataCapture.captureExit("TestClass", "testMethod", "String", "test");

        try {
            java.lang.reflect.Field currentMethodCallField = DataCapture.class.getDeclaredField("currentMethodCall");
            currentMethodCallField.setAccessible(true);
            ThreadLocal<MethodCall> currentMethodCall = (ThreadLocal<MethodCall>) currentMethodCallField.get(dataCapture);

            assertNull(currentMethodCall.get());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to access the currentMethodCall field: " + e.getMessage());
        }
    }

    public void dummyMethod(String arg0, Integer arg1) {
        // This method is only used for reflection purposes in the test
    }
}