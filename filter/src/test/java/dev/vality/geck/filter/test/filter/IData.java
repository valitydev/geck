/**
 * Autogenerated by Thrift Compiler (0.20.0)
 * <p>
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *
 * @generated
 */
package dev.vality.geck.filter.test.filter;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@jakarta.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.20.0)", date = "2024-06-24")
public class IData implements org.apache.thrift.TBase<IData, IData._Fields>, java.io.Serializable, Cloneable, Comparable<IData> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("IData");

  private static final org.apache.thrift.protocol.TField DATA_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("data_val", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField DATA_OPT_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("data_opt_val", org.apache.thrift.protocol.TType.STRING, (short) 2);

    private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new IDataStandardSchemeFactory();
    private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new IDataTupleSchemeFactory();

    public @org.apache.thrift.annotation.Nullable String data_val; // required
    public @org.apache.thrift.annotation.Nullable String data_opt_val; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DATA_VAL((short)1, "data_val"),
    DATA_OPT_VAL((short)2, "data_opt_val");

      private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

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
        case 1: // DATA_VAL
          return DATA_VAL;
        case 2: // DATA_OPT_VAL
          return DATA_OPT_VAL;
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
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

      @Override
    public short getThriftFieldId() {
      return _thriftId;
      }

      @Override
    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final _Fields optionals[] = {_Fields.DATA_OPT_VAL};
    public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
      java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DATA_VAL, new org.apache.thrift.meta_data.FieldMetaData("data_val", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DATA_OPT_VAL, new org.apache.thrift.meta_data.FieldMetaData("data_opt_val", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
      metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(IData.class, metaDataMap);
  }

  public IData() {
  }

  public IData(
    String data_val)
  {
    this();
    this.data_val = data_val;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public IData(IData other) {
    if (other.isSetDataVal()) {
      this.data_val = other.data_val;
    }
    if (other.isSetDataOptVal()) {
      this.data_opt_val = other.data_opt_val;
    }
  }

    @Override
  public IData deepCopy() {
    return new IData(this);
  }

  @Override
  public void clear() {
    this.data_val = null;
    this.data_opt_val = null;
  }

    @org.apache.thrift.annotation.Nullable
  public String getDataVal() {
    return this.data_val;
    }

    public IData setDataVal(@org.apache.thrift.annotation.Nullable String data_val) {
    this.data_val = data_val;
    return this;
  }

  public void unsetDataVal() {
    this.data_val = null;
  }

  /** Returns true if field data_val is set (has been assigned a value) and false otherwise */
  public boolean isSetDataVal() {
    return this.data_val != null;
  }

  public void setDataValIsSet(boolean value) {
    if (!value) {
      this.data_val = null;
    }
  }

    @org.apache.thrift.annotation.Nullable
  public String getDataOptVal() {
    return this.data_opt_val;
    }

    public IData setDataOptVal(@org.apache.thrift.annotation.Nullable String data_opt_val) {
    this.data_opt_val = data_opt_val;
    return this;
  }

  public void unsetDataOptVal() {
    this.data_opt_val = null;
  }

  /** Returns true if field data_opt_val is set (has been assigned a value) and false otherwise */
  public boolean isSetDataOptVal() {
    return this.data_opt_val != null;
  }

  public void setDataOptValIsSet(boolean value) {
    if (!value) {
      this.data_opt_val = null;
    }
  }

    @Override
    public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable Object value) {
    switch (field) {
    case DATA_VAL:
      if (value == null) {
        unsetDataVal();
      } else {
        setDataVal((String)value);
      }
      break;

    case DATA_OPT_VAL:
      if (value == null) {
        unsetDataOptVal();
      } else {
        setDataOptVal((String)value);
      }
      break;

    }
    }

    @org.apache.thrift.annotation.Nullable
    @Override
  public Object getFieldValue(_Fields field) {
    switch (field) {
    case DATA_VAL:
      return getDataVal();

    case DATA_OPT_VAL:
      return getDataOptVal();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  @Override
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case DATA_VAL:
      return isSetDataVal();
    case DATA_OPT_VAL:
      return isSetDataOptVal();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that instanceof IData)
      return this.equals((IData)that);
    return false;
  }

  public boolean equals(IData that) {
    if (that == null)
      return false;
      if (this == that)
          return true;

    boolean this_present_data_val = true && this.isSetDataVal();
    boolean that_present_data_val = true && that.isSetDataVal();
    if (this_present_data_val || that_present_data_val) {
      if (!(this_present_data_val && that_present_data_val))
        return false;
      if (!this.data_val.equals(that.data_val))
        return false;
    }

    boolean this_present_data_opt_val = true && this.isSetDataOptVal();
    boolean that_present_data_opt_val = true && that.isSetDataOptVal();
    if (this_present_data_opt_val || that_present_data_opt_val) {
      if (!(this_present_data_opt_val && that_present_data_opt_val))
        return false;
      if (!this.data_opt_val.equals(that.data_opt_val))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetDataVal()) ? 131071 : 524287);
    if (isSetDataVal())
      hashCode = hashCode * 8191 + data_val.hashCode();

    hashCode = hashCode * 8191 + ((isSetDataOptVal()) ? 131071 : 524287);
    if (isSetDataOptVal())
      hashCode = hashCode * 8191 + data_opt_val.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(IData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

      lastComparison = Boolean.compare(isSetDataVal(), other.isSetDataVal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDataVal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.data_val, other.data_val);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
      lastComparison = Boolean.compare(isSetDataOptVal(), other.isSetDataOptVal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDataOptVal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.data_opt_val, other.data_opt_val);
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
  public String toString() {
    StringBuilder sb = new StringBuilder("IData(");
    boolean first = true;

    sb.append("data_val:");
    if (this.data_val == null) {
      sb.append("null");
    } else {
      sb.append(this.data_val);
    }
    first = false;
    if (isSetDataOptVal()) {
      if (!first) sb.append(", ");
      sb.append("data_opt_val:");
      if (this.data_opt_val == null) {
        sb.append("null");
      } else {
        sb.append(this.data_opt_val);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (data_val == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'data_val' was not present! Struct: " + toString());
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

    private static class IDataStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
        @Override
    public IDataStandardScheme getScheme() {
      return new IDataStandardScheme();
    }
    }

    private static class IDataStandardScheme extends org.apache.thrift.scheme.StandardScheme<IData> {

        @Override
    public void read(org.apache.thrift.protocol.TProtocol iprot, IData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DATA_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.data_val = iprot.readString();
              struct.setDataValIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DATA_OPT_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.data_opt_val = iprot.readString();
              struct.setDataOptValIsSet(true);
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
    public void write(org.apache.thrift.protocol.TProtocol oprot, IData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.data_val != null) {
        oprot.writeFieldBegin(DATA_VAL_FIELD_DESC);
        oprot.writeString(struct.data_val);
        oprot.writeFieldEnd();
      }
      if (struct.data_opt_val != null) {
        if (struct.isSetDataOptVal()) {
          oprot.writeFieldBegin(DATA_OPT_VAL_FIELD_DESC);
          oprot.writeString(struct.data_opt_val);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

    }

    private static class IDataTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
        @Override
    public IDataTupleScheme getScheme() {
      return new IDataTupleScheme();
    }
    }

    private static class IDataTupleScheme extends org.apache.thrift.scheme.TupleScheme<IData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, IData struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeString(struct.data_val);
        java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetDataOptVal()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetDataOptVal()) {
        oprot.writeString(struct.data_opt_val);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, IData struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.data_val = iprot.readString();
      struct.setDataValIsSet(true);
        java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.data_opt_val = iprot.readString();
        struct.setDataOptValIsSet(true);
      }
    }
    }

    private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
        return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

