package dev.vality.geck.filter.test;

import org.apache.thrift.TEnum;

public enum Type implements TEnum {
  BLACK(1),
  RED(2),
  GREEN(3);

  private final int value;

  private Type(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static Type findByValue(int value) { 
    switch (value) {
      case 1:
        return BLACK;
      case 2:
        return RED;
      case 3:
        return GREEN;
      default:
        return null;
    }
  }
}
