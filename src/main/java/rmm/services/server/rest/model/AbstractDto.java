package rmm.services.server.rest.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * The Class AbstractDto abstract class with common java methods overriden to be used for logging 
 */
public abstract class AbstractDto implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 8679373024312940523L;

  @Override
  public String toString() {
    final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(this) {
      @Override
      protected boolean accept(final Field field) {
        return super.accept(field) && !(field.getType() == byte[].class);
      }
    };
    return reflectionToStringBuilder.toString();
  }

  @Override
  public boolean equals(final Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

}
