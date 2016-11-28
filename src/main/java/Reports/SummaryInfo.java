package Reports;

/**
 * Created by Michael Cohoe on 11/27/2016.
 */
public class SummaryInfo {
    private String prov_name;
    private int consult_num;
    private double total_fee;

    public SummaryInfo(String prov_name, int consult_num, double total_fee) {
        this.prov_name = prov_name;
        this.consult_num = consult_num;
        this.total_fee = total_fee;
    }

    public String getProv_name() {
        return prov_name;
    }

    public int getConsult_num() {
        return consult_num;
    }

    public double getTotal_fee() {
        return total_fee;
    }
}
