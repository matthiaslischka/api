package io.nobt.rest.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nobt.application.BodyParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import spark.Request;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BodyParserTest {

    @Mock
    private ObjectMapper objectMapperMock;

    @Mock
    private Request requestMock;

    private BodyParser sut;

    @Before
    public void setUp() throws Exception {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        sut = new BodyParser(objectMapperMock, validator);
    }

    @Test
    public void shouldUseBodyAsSource() throws Exception {
        when(requestMock.body()).thenReturn("Sample body");
        when(objectMapperMock.readValue(anyString(), eq(Sample.class))).thenReturn(new Sample());

        sut.parseBodyAs(requestMock, Sample.class);

        verify(objectMapperMock).readValue("Sample body", Sample.class);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldValidateMappedObject() throws Exception {

        when(objectMapperMock.readValue(anyString(), eq(ValidationSample.class))).thenReturn(new ValidationSample(null));

        sut.parseBodyAs(requestMock, ValidationSample.class);
    }

    public static class Sample {

    }

    public static class ValidationSample {

        @NotNull
        private String test;

        public ValidationSample(String test) {
            this.test = test;
        }
    }
}