package Reports;

import sqldb.ChocanConnection;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.Calendar.DAY_OF_WEEK;

//import java.sql.Timestamp;

/**
 * Created by The Borg on 11/25/2016.
 */
public class TimedServices {

    private ChocanConnection conn;
    private Calendar with;
    ScheduledExecutorService weeklyReportScheduler = Executors.newScheduledThreadPool(1);
    ScheduledExecutorService timeOfDayAdjustmentScheduler = Executors.newScheduledThreadPool(1);
    //private timer weeklySummaryReport;

    public TimedServices(ChocanConnection conn){
        this.conn = conn;
    }
    
    public void callWeeklySummary()
    {
        java.sql.Timestamp time = new java.sql.Timestamp(System.currentTimeMillis());
        conn.addFileWriteDate(time);
        Reports all_reports = new Reports(conn);
        all_reports.MemberSummaryReports(false);
        all_reports.ProviderSummaryReports(false);
        all_reports.SummarizeReports(false);

        //serviceTimer(false);
    }

    public void serviceTimer()
    {
        Calendar timeOfDayCal = Calendar.getInstance();

        timeOfDayCal.add(Calendar.DAY_OF_MONTH, 0);
        timeOfDayCal.set(Calendar.HOUR_OF_DAY, 23);
        timeOfDayCal.set(Calendar.MINUTE, 59);
        timeOfDayCal.set(Calendar.SECOND, 0);
        timeOfDayCal.set(Calendar.MILLISECOND, 0);

        long howLong = (timeOfDayCal.getTimeInMillis()-System.currentTimeMillis());

        timeOfDayAdjustmentScheduler.schedule(new Runnable() {
            public void run() {
                try {
                    weeklyServiceTimer();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, howLong, TimeUnit.MILLISECONDS);


    }

    /**
     * This should in theory, set a delay based on the current day of the week
     * and run on friday.
     *
     * Still gotta add time of day offset*
     */
    private void weeklyServiceTimer()
    {
        int delayInDays;
        int dayOfWeek;
        //if(reinitialize == false) {
            Map<Integer, Integer> dayOfWeekOffset = new HashMap<>();
            dayOfWeekOffset.put(Calendar.FRIDAY, 0);
            dayOfWeekOffset.put(Calendar.SATURDAY, 6);
            dayOfWeekOffset.put(Calendar.SUNDAY, 5);
            dayOfWeekOffset.put(Calendar.MONDAY, 4);
            dayOfWeekOffset.put(Calendar.TUESDAY, 3);
            dayOfWeekOffset.put(Calendar.WEDNESDAY, 2);
            dayOfWeekOffset.put(Calendar.THURSDAY, 1);
            dayOfWeek = with.get(DAY_OF_WEEK);
            delayInDays = dayOfWeekOffset.get(dayOfWeek);
        //}
        //else
        //{
            //delayInDays = 7;
        //}

        weeklyReportScheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    callWeeklySummary();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, delayInDays, 7, TimeUnit.DAYS);
    }
}
