package Reports;

/**
 * Created by Michael Cohoe on 11/27/2016.
 */
public class ServiceInfo {
    private String serve_date;
    private String timestamp;
    private String prov_name;
    private String service;
    private int service_id;
    private int mem_id;
    private Double fee;

    public ServiceInfo(String serve_date, String prov_name, String service) {
        this.serve_date = serve_date;
        this.timestamp = "";
        this.prov_name = prov_name;
        this.service = service;
        this.service_id = 0;
        this.mem_id = 0;
        this.fee = 0.0;
    }

    public ServiceInfo(String serve_date, String timestamp, String prov_name, String service, int service_id, int mem_id, Double fee) {
        this.serve_date = serve_date;
        this.timestamp = timestamp;
        this.prov_name = prov_name;
        this.service = service;
        this.service_id = service_id;
        this.mem_id = mem_id;
        this.fee = fee;
    }

    public String getServe_date() {
        return serve_date;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getProv_name() {
        return prov_name;
    }

    public String getService() {
        return service;
    }

    public int getService_id() {
        return service_id;
    }

    public int getMem_id() {
        return mem_id;
    }

    public Double getFee() {
        return fee;
    }
}
