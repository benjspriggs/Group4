package Reports;

/**
 * Created by Michael Cohoe on 11/27/2016.
 */
public class ProviderInfo {
    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;

    public ProviderInfo(String name, String address, String city, String state, String zip) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }
}
