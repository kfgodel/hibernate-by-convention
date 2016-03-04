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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(getClass().getSimpleName());
    builder.append("{");
    builder.append(amount);
    builder.append(" ");
    builder.append(unit);
    builder.append("}");
    return builder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!TimeValue.class.isInstance(obj)) {
      return false;
    }
    TimeValue that = (TimeValue) obj;
    return this.toNanoSeconds() == that.toNanoSeconds();
  }

  @Override
  public int hashCode() {
    return (int) toNanoSeconds();
  }

  private long toNanoSeconds() {
    return unit.toNanos(amount);
  }
}
