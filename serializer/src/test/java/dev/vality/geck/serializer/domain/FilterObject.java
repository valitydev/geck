/**
 * Autogenerated by Thrift Compiler (0.20.0)
 * <p>
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *
 * @generated
 */
package dev.vality.geck.serializer.domain;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@jakarta.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.20.0)", date = "2024-06-24")
public class FilterObject implements org.apache.thrift.TBase<FilterObject, FilterObject._Fields>, java.io.Serializable, Cloneable, Comparable<FilterObject> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FilterObject");

  private static final org.apache.thrift.protocol.TField SNAME1_FIELD_DESC = new org.apache.thrift.protocol.TField("sname1", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField UNAME1_FIELD_DESC = new org.apache.thrift.protocol.TField("uname1", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField SNAME2_FIELD_DESC = new org.apache.thrift.protocol.TField("sname2", org.apache.thrift.protocol.TType.STRING, (short) 3);

    private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new FilterObjectStandardSchemeFactory();
    private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new FilterObjectTupleSchemeFactory();

    public @org.apache.thrift.annotation.Nullable java.lang.String sname1; // required
    public @org.apache.thrift.annotation.Nullable FilterUnion uname1; // required
    public @org.apache.thrift.annotation.Nullable java.lang.String sname2; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SNAME1((short)1, "sname1"),
    UNAME1((short)2, "uname1"),
    SNAME2((short)3, "sname2");

      private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
        for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // SNAME1
          return SNAME1;
        case 2: // UNAME1
          return UNAME1;
        case 3: // SNAME2
          return SNAME2;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
      private final java.lang.String _fieldName;

      _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
      }

      @Override
    public short getThriftFieldId() {
      return _thriftId;
      }

      @Override
      public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
      java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SNAME1, new org.apache.thrift.meta_data.FieldMetaData("sname1", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.UNAME1, new org.apache.thrift.meta_data.FieldMetaData("uname1", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, FilterUnion.class)));
    tmpMap.put(_Fields.SNAME2, new org.apache.thrift.meta_data.FieldMetaData("sname2", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
      metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FilterObject.class, metaDataMap);
  }

  public FilterObject() {
  }

  public FilterObject(
          java.lang.String sname1,
    FilterUnion uname1,
          java.lang.String sname2)
  {
    this();
    this.sname1 = sname1;
    this.uname1 = uname1;
    this.sname2 = sname2;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FilterObject(FilterObject other) {
    if (other.isSetSname1()) {
      this.sname1 = other.sname1;
    }
    if (other.isSetUname1()) {
      this.uname1 = new FilterUnion(other.uname1);
    }
    if (other.isSetSname2()) {
      this.sname2 = other.sname2;
    }
  }

    @Override
  public FilterObject deepCopy() {
    return new FilterObject(this);
  }

  @Override
  public void clear() {
    this.sname1 = null;
    this.uname1 = null;
    this.sname2 = null;
  }

    @org.apache.thrift.annotation.Nullable
    public java.lang.String getSname1() {
    return this.sname1;
    }

    public FilterObject setSname1(@org.apache.thrift.annotation.Nullable java.lang.String sname1) {
    this.sname1 = sname1;
    return this;
  }

  public void unsetSname1() {
    this.sname1 = null;
  }

  /** Returns true if field sname1 is set (has been assigned a value) and false otherwise */
  public boolean isSetSname1() {
    return this.sname1 != null;
  }

  public void setSname1IsSet(boolean value) {
    if (!value) {
      this.sname1 = null;
    }
  }

    @org.apache.thrift.annotation.Nullable
  public FilterUnion getUname1() {
    return this.uname1;
    }

    public FilterObject setUname1(@org.apache.thrift.annotation.Nullable FilterUnion uname1) {
    this.uname1 = uname1;
    return this;
  }

  public void unsetUname1() {
    this.uname1 = null;
  }

  /** Returns true if field uname1 is set (has been assigned a value) and false otherwise */
  public boolean isSetUname1() {
    return this.uname1 != null;
  }

  public void setUname1IsSet(boolean value) {
    if (!value) {
      this.uname1 = null;
    }
  }

    @org.apache.thrift.annotation.Nullable
    public java.lang.String getSname2() {
    return this.sname2;
    }

    public FilterObject setSname2(@org.apache.thrift.annotation.Nullable java.lang.String sname2) {
    this.sname2 = sname2;
    return this;
  }

  public void unsetSname2() {
    this.sname2 = null;
  }

  /** Returns true if field sname2 is set (has been assigned a value) and false otherwise */
  public boolean isSetSname2() {
    return this.sname2 != null;
  }

  public void setSname2IsSet(boolean value) {
    if (!value) {
      this.sname2 = null;
    }
  }

    @Override
    public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case SNAME1:
      if (value == null) {
        unsetSname1();
      } else {
          setSname1((java.lang.String) value);
      }
      break;

    case UNAME1:
      if (value == null) {
        unsetUname1();
      } else {
        setUname1((FilterUnion)value);
      }
      break;

    case SNAME2:
      if (value == null) {
        unsetSname2();
      } else {
          setSname2((java.lang.String) value);
      }
      break;

    }
    }

    @org.apache.thrift.annotation.Nullable
    @Override
    public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case SNAME1:
      return getSname1();

    case UNAME1:
      return getUname1();

    case SNAME2:
      return getSname2();

    }
        throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  @Override
  public boolean isSet(_Fields field) {
    if (field == null) {
        throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case SNAME1:
      return isSetSname1();
    case UNAME1:
      return isSetUname1();
    case SNAME2:
      return isSetSname2();
    }
      throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof FilterObject)
      return this.equals((FilterObject)that);
    return false;
  }

  public boolean equals(FilterObject that) {
    if (that == null)
      return false;
      if (this == that)
          return true;

    boolean this_present_sname1 = true && this.isSetSname1();
    boolean that_present_sname1 = true && that.isSetSname1();
    if (this_present_sname1 || that_present_sname1) {
      if (!(this_present_sname1 && that_present_sname1))
        return false;
      if (!this.sname1.equals(that.sname1))
        return false;
    }

    boolean this_present_uname1 = true && this.isSetUname1();
    boolean that_present_uname1 = true && that.isSetUname1();
    if (this_present_uname1 || that_present_uname1) {
      if (!(this_present_uname1 && that_present_uname1))
        return false;
      if (!this.uname1.equals(that.uname1))
        return false;
    }

    boolean this_present_sname2 = true && this.isSetSname2();
    boolean that_present_sname2 = true && that.isSetSname2();
    if (this_present_sname2 || that_present_sname2) {
      if (!(this_present_sname2 && that_present_sname2))
        return false;
      if (!this.sname2.equals(that.sname2))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetSname1()) ? 131071 : 524287);
    if (isSetSname1())
      hashCode = hashCode * 8191 + sname1.hashCode();

    hashCode = hashCode * 8191 + ((isSetUname1()) ? 131071 : 524287);
    if (isSetUname1())
      hashCode = hashCode * 8191 + uname1.hashCode();

    hashCode = hashCode * 8191 + ((isSetSname2()) ? 131071 : 524287);
    if (isSetSname2())
      hashCode = hashCode * 8191 + sname2.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(FilterObject other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

      lastComparison = java.lang.Boolean.compare(isSetSname1(), other.isSetSname1());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSname1()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sname1, other.sname1);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
      lastComparison = java.lang.Boolean.compare(isSetUname1(), other.isSetUname1());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUname1()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.uname1, other.uname1);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
      lastComparison = java.lang.Boolean.compare(isSetSname2(), other.isSetSname2());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSname2()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sname2, other.sname2);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

    @org.apache.thrift.annotation.Nullable
    @Override
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public _Fields[] getFields() {
    return _Fields.values();
  }

    public java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> getFieldMetaData() {
    return metaDataMap;
    }

    @Override
  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
    }

    @Override
  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder("FilterObject(");
    boolean first = true;

    sb.append("sname1:");
    if (this.sname1 == null) {
      sb.append("null");
    } else {
      sb.append(this.sname1);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("uname1:");
    if (this.uname1 == null) {
      sb.append("null");
    } else {
      sb.append(this.uname1);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("sname2:");
    if (this.sname2 == null) {
      sb.append("null");
    } else {
      sb.append(this.sname2);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (sname1 == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'sname1' was not present! Struct: " + toString());
    }
    if (sname2 == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'sname2' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
    }

    private static class FilterObjectStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
        @Override
    public FilterObjectStandardScheme getScheme() {
      return new FilterObjectStandardScheme();
    }
    }

    private static class FilterObjectStandardScheme extends org.apache.thrift.scheme.StandardScheme<FilterObject> {

        @Override
    public void read(org.apache.thrift.protocol.TProtocol iprot, FilterObject struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SNAME1
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.sname1 = iprot.readString();
              struct.setSname1IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // UNAME1
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.uname1 = new FilterUnion();
              struct.uname1.read(iprot);
              struct.setUname1IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SNAME2
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.sname2 = iprot.readString();
              struct.setSname2IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
        }

        @Override
    public void write(org.apache.thrift.protocol.TProtocol oprot, FilterObject struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.sname1 != null) {
        oprot.writeFieldBegin(SNAME1_FIELD_DESC);
        oprot.writeString(struct.sname1);
        oprot.writeFieldEnd();
      }
      if (struct.uname1 != null) {
        oprot.writeFieldBegin(UNAME1_FIELD_DESC);
        struct.uname1.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.sname2 != null) {
        oprot.writeFieldBegin(SNAME2_FIELD_DESC);
        oprot.writeString(struct.sname2);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

    }

    private static class FilterObjectTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
        @Override
    public FilterObjectTupleScheme getScheme() {
      return new FilterObjectTupleScheme();
    }
    }

    private static class FilterObjectTupleScheme extends org.apache.thrift.scheme.TupleScheme<FilterObject> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, FilterObject struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeString(struct.sname1);
      oprot.writeString(struct.sname2);
        java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetUname1()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetUname1()) {
        struct.uname1.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, FilterObject struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.sname1 = iprot.readString();
      struct.setSname1IsSet(true);
      struct.sname2 = iprot.readString();
      struct.setSname2IsSet(true);
        java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.uname1 = new FilterUnion();
        struct.uname1.read(iprot);
        struct.setUname1IsSet(true);
      }
    }
    }

    private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
        return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

