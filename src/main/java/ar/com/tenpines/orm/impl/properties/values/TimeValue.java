package ar.com.tenpines.orm.impl.properties.values;

import java.util.concurrent.TimeUnit;

/**
 * This type represents a time magnitude reference
 * Created by kfgodel on 21/03/15.
 */
public class TimeValue {

    private long amount;
    private TimeUnit unit;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public static TimeValue create(long amount, TimeUnit unit) {
        TimeValue timeValue = new TimeValue();
        timeValue.amount = amount;
        timeValue.unit = unit;
        return timeValue;
    }

}
