package ro.sopa.statistifier.web.model;

public class TimelinePoint {

    private String time;
    private Long listens;

    public TimelinePoint(String time, Long listens) {
        this.time = time;
        this.listens = listens;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getListens() {
        return listens;
    }

    public void setListens(Long listens) {
        this.listens = listens;
    }
}
