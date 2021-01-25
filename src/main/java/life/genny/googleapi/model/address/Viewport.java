
package life.genny.googleapi.model.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import life.genny.googleapi.model.address.Northeast;
import life.genny.googleapi.model.address.Southwest;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class Viewport implements Serializable {

    @SerializedName("northeast")
    @Expose
    private Northeast northeast;
    @SerializedName("southwest")
    @Expose
    private Southwest southwest;

    public Northeast getNortheast() {
        return northeast;
    }

    public void setNortheast(Northeast northeast) {
        this.northeast = northeast;
    }

    public Southwest getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Southwest southwest) {
        this.southwest = southwest;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("northeast", northeast).append("southwest", southwest).toString();
    }

}
