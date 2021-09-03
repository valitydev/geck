package com.rbkmoney.geck.serializer.domain;


import org.apache.thrift.TEnum;

public enum Enums implements TEnum {
  TEST1(0),
  TEST2(1),
  TEST3(2);

  private final int value;

  private Enums(int value) {
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
  public static Enums findByValue(int value) { 
    switch (value) {
      case 0:
        return TEST1;
      case 1:
        return TEST2;
      case 2:
        return TEST3;
      default:
        return null;
    }
  }
}
