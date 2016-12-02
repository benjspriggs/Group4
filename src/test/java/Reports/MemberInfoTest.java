package Reports;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jeff on 12/1/16.
 */
public class MemberInfoTest {
    private MemberInfo myMemberInfo;
    @Before
    public void setUp() throws Exception {
        myMemberInfo = new MemberInfo("name", "address", "city", "state", "zip");
    }

    @Test
    public void getName() throws Exception {
        assertNotNull("is null", myMemberInfo.getName());
    }

    @Test
    public void getAddress() throws Exception {
        assertNotNull("is null", myMemberInfo.getAddress());
    }

    @Test
    public void getCity() throws Exception {
        assertNotNull("is null", myMemberInfo.getCity());
    }

    @Test
    public void getState() throws Exception {
        assertNotNull("is null", myMemberInfo.getState());
    }

    @Test
    public void getZip() throws Exception {
        assertNotNull("is null", myMemberInfo.getZip());
    }

}