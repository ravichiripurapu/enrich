package data.pipeline.api.error;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by ravi on 8/20/17.
 */
public class FlowExceptionTest {

    @Test
    public void testWithException() {
        Exception e = new Exception("Test Exception Message");
        FlowException exception =  FlowException.build(TestErrorCode.TestError, e)
                .set("ERR1","Custom Error Message 1")
                .set("ERR2","Custom Error Message 2");
        System.out.println(exception.toString());
        assertEquals (32655,exception.getErrorCode().getCode());
    }

    @Test
    public void testSansException() {

        FlowException exception =  FlowException.build(TestErrorCode.TestError)
                .set("ERR3","Custom Error Message 3")
                .set("ERR4","Custom Error Message 4");
        System.out.println(exception.toString());
        assertEquals (32655,exception.getErrorCode().getCode());
    }
}
