/**
 * Autogenerated by Thrift Compiler (0.20.0)
 * <p>
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *
 * @generated
 */
package dev.vality.geck.filter.test;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@jakarta.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.20.0)", date = "2024-06-24")
public class Fail implements org.apache.thrift.TBase<Fail, Fail._Fields>, java.io.Serializable, Cloneable, Comparable<Fail> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Fail");

  private static final org.apache.thrift.protocol.TField CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("code", org.apache.thrift.protocol.TType.BYTE, (short)1);
  private static final org.apache.thrift.protocol.TField DESCRIPTION_FIELD_DESC = new org.apache.thrift.protocol.TField("description", org.apache.thrift.protocol.TType.STRING, (short) 2);

    private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new FailStandardSchemeFactory();
    private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new FailTupleSchemeFactory();

  public byte code; // required
    public @org.apache.thrift.annotation.Nullable java.lang.String description; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CODE((short)1, "code"),
    DESCRIPTION((short)2, "description");

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
        case 1: // CODE
          return CODE;
        case 2: // DESCRIPTION
          return DESCRIPTION;
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
  private static final int __CODE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.DESCRIPTION};
    public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
      java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CODE, new org.apache.thrift.meta_data.FieldMetaData("code", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    tmpMap.put(_Fields.DESCRIPTION, new org.apache.thrift.meta_data.FieldMetaData("description", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
      metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Fail.class, metaDataMap);
  }

  public Fail() {
  }

  public Fail(
    byte code)
  {
    this();
    this.code = code;
    setCodeIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Fail(Fail other) {
    __isset_bitfield = other.__isset_bitfield;
    this.code = other.code;
    if (other.isSetDescription()) {
      this.description = other.description;
    }
  }

    @Override
  public Fail deepCopy() {
    return new Fail(this);
  }

  @Override
  public void clear() {
    setCodeIsSet(false);
    this.code = 0;
    this.description = null;
  }

  public byte getCode() {
    return this.code;
  }

  public Fail setCode(byte code) {
    this.code = code;
    setCodeIsSet(true);
    return this;
  }

  public void unsetCode() {
      __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CODE_ISSET_ID);
  }

  /** Returns true if field code is set (has been assigned a value) and false otherwise */
  public boolean isSetCode() {
      return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CODE_ISSET_ID);
  }

  public void setCodeIsSet(boolean value) {
      __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CODE_ISSET_ID, value);
  }

    @org.apache.thrift.annotation.Nullable
    public java.lang.String getDescription() {
    return this.description;
    }

    public Fail setDescription(@org.apache.thrift.annotation.Nullable java.lang.String description) {
    this.description = description;
    return this;
  }

  public void unsetDescription() {
    this.description = null;
  }

  /** Returns true if field description is set (has been assigned a value) and false otherwise */
  public boolean isSetDescription() {
    return this.description != null;
  }

  public void setDescriptionIsSet(boolean value) {
    if (!value) {
      this.description = null;
    }
  }

    @Override
    public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case CODE:
      if (value == null) {
        unsetCode();
      } else {
          setCode((java.lang.Byte) value);
      }
      break;

    case DESCRIPTION:
      if (value == null) {
        unsetDescription();
      } else {
          setDescription((java.lang.String) value);
      }
      break;

    }
    }

    @org.apache.thrift.annotation.Nullable
    @Override
    public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case CODE:
      return getCode();

    case DESCRIPTION:
      return getDescription();

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
    case CODE:
      return isSetCode();
    case DESCRIPTION:
      return isSetDescription();
    }
      throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof Fail)
      return this.equals((Fail)that);
    return false;
  }

  public boolean equals(Fail that) {
    if (that == null)
      return false;
      if (this == that)
          return true;

    boolean this_present_code = true;
    boolean that_present_code = true;
    if (this_present_code || that_present_code) {
      if (!(this_present_code && that_present_code))
        return false;
      if (this.code != that.code)
        return false;
    }

    boolean this_present_description = true && this.isSetDescription();
    boolean that_present_description = true && that.isSetDescription();
    if (this_present_description || that_present_description) {
      if (!(this_present_description && that_present_description))
        return false;
      if (!this.description.equals(that.description))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + (int) (code);

    hashCode = hashCode * 8191 + ((isSetDescription()) ? 131071 : 524287);
    if (isSetDescription())
      hashCode = hashCode * 8191 + description.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(Fail other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

      lastComparison = java.lang.Boolean.compare(isSetCode(), other.isSetCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.code, other.code);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
      lastComparison = java.lang.Boolean.compare(isSetDescription(), other.isSetDescription());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDescription()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.description, other.description);
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
      java.lang.StringBuilder sb = new java.lang.StringBuilder("Fail(");
    boolean first = true;

    sb.append("code:");
    sb.append(this.code);
    first = false;
    if (isSetDescription()) {
      if (!first) sb.append(", ");
      sb.append("description:");
      if (this.description == null) {
        sb.append("null");
      } else {
        sb.append(this.description);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'code' because it's a primitive and you chose the non-beans generator.
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
    }

    private static class FailStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
        @Override
    public FailStandardScheme getScheme() {
      return new FailStandardScheme();
    }
    }

    private static class FailStandardScheme extends org.apache.thrift.scheme.StandardScheme<Fail> {

        @Override
    public void read(org.apache.thrift.protocol.TProtocol iprot, Fail struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.BYTE) {
              struct.code = iprot.readByte();
              struct.setCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DESCRIPTION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.description = iprot.readString();
              struct.setDescriptionIsSet(true);
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
      if (!struct.isSetCode()) {
          throw new org.apache.thrift.protocol.TProtocolException("Required field 'code' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
        }

        @Override
    public void write(org.apache.thrift.protocol.TProtocol oprot, Fail struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(CODE_FIELD_DESC);
      oprot.writeByte(struct.code);
      oprot.writeFieldEnd();
      if (struct.description != null) {
        if (struct.isSetDescription()) {
          oprot.writeFieldBegin(DESCRIPTION_FIELD_DESC);
          oprot.writeString(struct.description);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

    }

    private static class FailTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
        @Override
    public FailTupleScheme getScheme() {
      return new FailTupleScheme();
    }
    }

    private static class FailTupleScheme extends org.apache.thrift.scheme.TupleScheme<Fail> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Fail struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeByte(struct.code);
        java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetDescription()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetDescription()) {
        oprot.writeString(struct.description);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Fail struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.code = iprot.readByte();
      struct.setCodeIsSet(true);
        java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.description = iprot.readString();
        struct.setDescriptionIsSet(true);
      }
    }
    }

    private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
        return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

