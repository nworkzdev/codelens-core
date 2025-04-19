package com.odyss.codelens.agent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InterceptorAdviceTest {

    private MockedStatic<DataCapture> dataCaptureMockedStatic;

    @BeforeEach
    void setUp() {
        dataCaptureMockedStatic = mockStatic(DataCapture.class);
    }

    @Test
    void onEnter_shouldCaptureEntry() throws NoSuchMethodException {
        // Arrange
        Method method = TestClass.class.getMethod("testMethod", String.class, int.class);
        Parameter[] parameters = method.getParameters();
        DataCapture dataCapture = mock(DataCapture.class);
        when(DataCapture.getInstance()).thenReturn(dataCapture);

        // Act
        InterceptorAdvice.onEnter(method);

        // Assert
        verify(dataCapture).captureEntry(eq("com.odyss.codelens.agent.InterceptorAdviceTest$TestClass"), eq("testMethod"), eq(parameters));

        dataCaptureMockedStatic.verify(() -> DataCapture.getInstance());
        dataCaptureMockedStatic.close();

    }

    @Test
    void onExit_withException_shouldCaptureExitWithException() throws NoSuchMethodException {
        // Arrange
        Method method = TestClass.class.getMethod("testMethod", String.class, int.class);
        Throwable thrown = new RuntimeException("Test Exception");
        DataCapture dataCapture = mock(DataCapture.class);
        when(DataCapture.getInstance()).thenReturn(dataCapture);

        // Act
        InterceptorAdvice.onExit(method, null, thrown);

        // Assert
        verify(dataCapture).captureExit(eq("com.odyss.codelens.agent.InterceptorAdviceTest$TestClass"), eq("testMethod"), eq("Exception"), eq(thrown));
        dataCaptureMockedStatic.verify(() -> DataCapture.getInstance());
        dataCaptureMockedStatic.close();
    }

    @Test
    void onExit_withReturn_shouldCaptureExitWithReturn() throws NoSuchMethodException {
        // Arrange
        Method method = TestClass.class.getMethod("testMethod", String.class, int.class);
        Object ret = "Test Return";
        DataCapture dataCapture = mock(DataCapture.class);
        when(DataCapture.getInstance()).thenReturn(dataCapture);

        // Act
        InterceptorAdvice.onExit(method, ret, null);

        // Assert
        verify(dataCapture).captureExit(eq("com.odyss.codelens.agent.InterceptorAdviceTest$TestClass"), eq("testMethod"), eq("Return"), eq(ret));
        dataCaptureMockedStatic.verify(() -> DataCapture.getInstance());
        dataCaptureMockedStatic.close();
    }

    static class TestClass {
        public void testMethod(String str, int num) {
            //test class
        }
    }
}