package Reports;

/**
 * Created by Michael Cohoe on 11/27/2016.
 */
public class ServiceInfo {
    private String serve_date = "01/02/16";
    private String prov_name = "test name";
    private String service = "test service";

    public ServiceInfo(String serve_date, String prov_name, String service) {
        this.serve_date = serve_date;
        this.prov_name = prov_name;
        this.service = service;
    }

    public String getServe_date() {
        return serve_date;
    }

    public String getProv_name() {
        return prov_name;
    }

    public String getService() {
        return service;
    }
}
