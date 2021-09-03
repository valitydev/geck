package com.rbkmoney.geck.filter.test;

import org.apache.thrift.EncodingUtils;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import javax.annotation.Generated;
import java.util.*;

@Generated(value = "Autogenerated by Thrift Compiler (1.0.0-dev)", date = "2020-07-09")
public class Ids implements org.apache.thrift.TBase<Ids, Ids._Fields>, java.io.Serializable, Cloneable, Comparable<Ids> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Ids");

  private static final org.apache.thrift.protocol.TField MICRO_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("micro_id", org.apache.thrift.protocol.TType.BYTE, (short)1);
  private static final org.apache.thrift.protocol.TField MINI_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("mini_id", org.apache.thrift.protocol.TType.I16, (short)2);
  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final SchemeFactory STANDARD_SCHEME_FACTORY = new IdsStandardSchemeFactory();
  private static final SchemeFactory TUPLE_SCHEME_FACTORY = new IdsTupleSchemeFactory();

  public byte micro_id; // required
  public short mini_id; // required
  public int id; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MICRO_ID((short)1, "micro_id"),
    MINI_ID((short)2, "mini_id"),
    ID((short)3, "id");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // MICRO_ID
          return MICRO_ID;
        case 2: // MINI_ID
          return MINI_ID;
        case 3: // ID
          return ID;
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
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __MICRO_ID_ISSET_ID = 0;
  private static final int __MINI_ID_ISSET_ID = 1;
  private static final int __ID_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.MICRO_ID, new org.apache.thrift.meta_data.FieldMetaData("micro_id", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    tmpMap.put(_Fields.MINI_ID, new org.apache.thrift.meta_data.FieldMetaData("mini_id", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Ids.class, metaDataMap);
  }

  public Ids() {
  }

  public Ids(
    byte micro_id,
    short mini_id,
    int id)
  {
    this();
    this.micro_id = micro_id;
    setMicroIdIsSet(true);
    this.mini_id = mini_id;
    setMiniIdIsSet(true);
    this.id = id;
    setIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Ids(Ids other) {
    __isset_bitfield = other.__isset_bitfield;
    this.micro_id = other.micro_id;
    this.mini_id = other.mini_id;
    this.id = other.id;
  }

  public Ids deepCopy() {
    return new Ids(this);
  }

  @Override
  public void clear() {
    setMicroIdIsSet(false);
    this.micro_id = 0;
    setMiniIdIsSet(false);
    this.mini_id = 0;
    setIdIsSet(false);
    this.id = 0;
  }

  public byte getMicroId() {
    return this.micro_id;
  }

  public Ids setMicroId(byte micro_id) {
    this.micro_id = micro_id;
    setMicroIdIsSet(true);
    return this;
  }

  public void unsetMicroId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MICRO_ID_ISSET_ID);
  }

  /** Returns true if field micro_id is set (has been assigned a value) and false otherwise */
  public boolean isSetMicroId() {
    return EncodingUtils.testBit(__isset_bitfield, __MICRO_ID_ISSET_ID);
  }

  public void setMicroIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MICRO_ID_ISSET_ID, value);
  }

  public short getMiniId() {
    return this.mini_id;
  }

  public Ids setMiniId(short mini_id) {
    this.mini_id = mini_id;
    setMiniIdIsSet(true);
    return this;
  }

  public void unsetMiniId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MINI_ID_ISSET_ID);
  }

  /** Returns true if field mini_id is set (has been assigned a value) and false otherwise */
  public boolean isSetMiniId() {
    return EncodingUtils.testBit(__isset_bitfield, __MINI_ID_ISSET_ID);
  }

  public void setMiniIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MINI_ID_ISSET_ID, value);
  }

  public int getId() {
    return this.id;
  }

  public Ids setId(int id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }

  public void unsetId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case MICRO_ID:
      if (value == null) {
        unsetMicroId();
      } else {
        setMicroId((Byte)value);
      }
      break;

    case MINI_ID:
      if (value == null) {
        unsetMiniId();
      } else {
        setMiniId((Short)value);
      }
      break;

    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case MICRO_ID:
      return getMicroId();

    case MINI_ID:
      return getMiniId();

    case ID:
      return getId();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case MICRO_ID:
      return isSetMicroId();
    case MINI_ID:
      return isSetMiniId();
    case ID:
      return isSetId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Ids)
      return this.equals((Ids)that);
    return false;
  }

  public boolean equals(Ids that) {
    if (that == null)
      return false;

    boolean this_present_micro_id = true;
    boolean that_present_micro_id = true;
    if (this_present_micro_id || that_present_micro_id) {
      if (!(this_present_micro_id && that_present_micro_id))
        return false;
      if (this.micro_id != that.micro_id)
        return false;
    }

    boolean this_present_mini_id = true;
    boolean that_present_mini_id = true;
    if (this_present_mini_id || that_present_mini_id) {
      if (!(this_present_mini_id && that_present_mini_id))
        return false;
      if (this.mini_id != that.mini_id)
        return false;
    }

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + (int) (micro_id);

    hashCode = hashCode * 8191 + mini_id;

    hashCode = hashCode * 8191 + id;

    return hashCode;
  }

  @Override
  public int compareTo(Ids other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetMicroId()).compareTo(other.isSetMicroId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMicroId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.micro_id, other.micro_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMiniId()).compareTo(other.isSetMiniId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMiniId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.mini_id, other.mini_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public _Fields[] getFields() {
    return _Fields.values();
  }

  public Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> getFieldMetaData() {
    return metaDataMap;
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Ids(");
    boolean first = true;

    sb.append("micro_id:");
    sb.append(this.micro_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("mini_id:");
    sb.append(this.mini_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("id:");
    sb.append(this.id);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'micro_id' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'mini_id' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'id' because it's a primitive and you chose the non-beans generator.
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class IdsStandardSchemeFactory implements SchemeFactory {
    public IdsStandardScheme getScheme() {
      return new IdsStandardScheme();
    }
  }

  private static class IdsStandardScheme extends StandardScheme<Ids> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Ids struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MICRO_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.BYTE) {
              struct.micro_id = iprot.readByte();
              struct.setMicroIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // MINI_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.mini_id = iprot.readI16();
              struct.setMiniIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.id = iprot.readI32();
              struct.setIdIsSet(true);
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
      if (!struct.isSetMicroId()) {
        throw new TProtocolException("Required field 'micro_id' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetMiniId()) {
        throw new TProtocolException("Required field 'mini_id' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetId()) {
        throw new TProtocolException("Required field 'id' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Ids struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(MICRO_ID_FIELD_DESC);
      oprot.writeByte(struct.micro_id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(MINI_ID_FIELD_DESC);
      oprot.writeI16(struct.mini_id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI32(struct.id);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class IdsTupleSchemeFactory implements SchemeFactory {
    public IdsTupleScheme getScheme() {
      return new IdsTupleScheme();
    }
  }

  private static class IdsTupleScheme extends TupleScheme<Ids> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Ids struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeByte(struct.micro_id);
      oprot.writeI16(struct.mini_id);
      oprot.writeI32(struct.id);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Ids struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.micro_id = iprot.readByte();
      struct.setMicroIdIsSet(true);
      struct.mini_id = iprot.readI16();
      struct.setMiniIdIsSet(true);
      struct.id = iprot.readI32();
      struct.setIdIsSet(true);
    }
  }

  private static <S extends IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

