package life.genny.gennyproxy.repository.entity.timezone;

import java.io.Serializable;

public class GoogleTimezone implements Serializable {

    private long dstOffset;
    private long rawOffset;
    private String status;
    private String timeZoneId;
    private String timeZoneName;
    private String errorMessage;

    public GoogleTimezone() {
    }

    public long getDstOffset() {
        return dstOffset;
    }

    public void setDstOffset(long dstOffset) {
        this.dstOffset = dstOffset;
    }

    public long getRawOffset() {
        return rawOffset;
    }

    public void setRawOffset(long rawOffset) {
        this.rawOffset = rawOffset;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getTimeZoneName() {
        return timeZoneName;
    }

    public void setTimeZoneName(String timeZoneName) {
        this.timeZoneName = timeZoneName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String toString() {
        return String.format("dstOffset:%d, rawOffset:%d, status:%s, timeZoneId:%s, timeZoneName:%s, errorMessage:%s.",
                            dstOffset, rawOffset, status,timeZoneId, timeZoneName, errorMessage) ;
    }
}
