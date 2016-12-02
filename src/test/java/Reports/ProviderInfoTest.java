package Reports;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jeff on 12/1/16.
 */
public class ProviderInfoTest {
    private ProviderInfo myProviderTest;
    @Before
    public void setUp() throws Exception {
        myProviderTest = new ProviderInfo("name", "address", "city", "state", "zip");
    }

    @Test
    public void getName() throws Exception {
        assertNotNull("is null", myProviderTest.getName());
    }

    @Test
    public void getAddress() throws Exception {
        assertNotNull("is null", myProviderTest.getAddress());
    }

    @Test
    public void getCity() throws Exception {
        assertNotNull("is null", myProviderTest.getCity());
    }

    @Test
    public void getState() throws Exception {
        assertNotNull("is null", myProviderTest.getState());
    }

    @Test
    public void getZip() throws Exception {
        assertNotNull("is null", myProviderTest.getZip());
    }

}