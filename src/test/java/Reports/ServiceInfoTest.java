package Reports;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jeff on 12/1/16.
 */
public class ServiceInfoTest {
    private ServiceInfo myServiceInfo;
    @Before
    public void setUp() throws Exception {
        myServiceInfo = new ServiceInfo("date", "prov name", "service");
    }

    @Test
    public void getMem_name() throws Exception {
        assertNotNull("is null", myServiceInfo.getMem_name());
    }

    @Test
    public void getServe_date() throws Exception {
        assertNotNull("is null", myServiceInfo.getServe_date());
    }

    @Test
    public void getTimestamp() throws Exception {
        assertNotNull("is null", myServiceInfo.getTimestamp());
    }

    @Test
    public void getProv_name() throws Exception {
        assertNotNull("is null", myServiceInfo.getProv_name());
    }

    @Test
    public void getService() throws Exception {
        assertNotNull("is null", myServiceInfo.getService());
    }

    @Test
    public void getService_id() throws Exception {
        assertNotNull("is null", myServiceInfo.getService_id());
    }

    @Test
    public void getMem_id() throws Exception {
        assertNotNull("is null", myServiceInfo.getMem_id());
    }

    @Test
    public void getFee() throws Exception {
        assertNotNull("is null", myServiceInfo.getFee());
    }

}