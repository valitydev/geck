/**
 * Autogenerated by Thrift Compiler (1.0.0-dev)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.rbkmoney.geck.filter.test;

import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import javax.annotation.Generated;
import java.util.*;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@Generated(value = "Autogenerated by Thrift Compiler (1.0.0-dev)", date = "2020-07-09")
public class Unknown implements org.apache.thrift.TBase<Unknown, Unknown._Fields>, java.io.Serializable, Cloneable, Comparable<Unknown> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Unknown");

  private static final org.apache.thrift.protocol.TField UNKNOWN_FIELD_DESC = new org.apache.thrift.protocol.TField("unknown", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField DESCRIPTION_FIELD_DESC = new org.apache.thrift.protocol.TField("description", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final SchemeFactory STANDARD_SCHEME_FACTORY = new UnknownStandardSchemeFactory();
  private static final SchemeFactory TUPLE_SCHEME_FACTORY = new UnknownTupleSchemeFactory();

  public UnknownType unknown; // required
  public String description; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    UNKNOWN((short)1, "unknown"),
    DESCRIPTION((short)2, "description");

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
        case 1: // UNKNOWN
          return UNKNOWN;
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
  private static final _Fields optionals[] = {_Fields.DESCRIPTION};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.UNKNOWN, new org.apache.thrift.meta_data.FieldMetaData("unknown", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, UnknownType.class)));
    tmpMap.put(_Fields.DESCRIPTION, new org.apache.thrift.meta_data.FieldMetaData("description", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Unknown.class, metaDataMap);
  }

  public Unknown() {
  }

  public Unknown(
    UnknownType unknown)
  {
    this();
    this.unknown = unknown;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Unknown(Unknown other) {
    if (other.isSetUnknown()) {
      this.unknown = new UnknownType(other.unknown);
    }
    if (other.isSetDescription()) {
      this.description = other.description;
    }
  }

  public Unknown deepCopy() {
    return new Unknown(this);
  }

  @Override
  public void clear() {
    this.unknown = null;
    this.description = null;
  }

  public UnknownType getUnknown() {
    return this.unknown;
  }

  public Unknown setUnknown(UnknownType unknown) {
    this.unknown = unknown;
    return this;
  }

  public void unsetUnknown() {
    this.unknown = null;
  }

  /** Returns true if field unknown is set (has been assigned a value) and false otherwise */
  public boolean isSetUnknown() {
    return this.unknown != null;
  }

  public void setUnknownIsSet(boolean value) {
    if (!value) {
      this.unknown = null;
    }
  }

  public String getDescription() {
    return this.description;
  }

  public Unknown setDescription(String description) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case UNKNOWN:
      if (value == null) {
        unsetUnknown();
      } else {
        setUnknown((UnknownType)value);
      }
      break;

    case DESCRIPTION:
      if (value == null) {
        unsetDescription();
      } else {
        setDescription((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case UNKNOWN:
      return getUnknown();

    case DESCRIPTION:
      return getDescription();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case UNKNOWN:
      return isSetUnknown();
    case DESCRIPTION:
      return isSetDescription();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Unknown)
      return this.equals((Unknown)that);
    return false;
  }

  public boolean equals(Unknown that) {
    if (that == null)
      return false;

    boolean this_present_unknown = true && this.isSetUnknown();
    boolean that_present_unknown = true && that.isSetUnknown();
    if (this_present_unknown || that_present_unknown) {
      if (!(this_present_unknown && that_present_unknown))
        return false;
      if (!this.unknown.equals(that.unknown))
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

    hashCode = hashCode * 8191 + ((isSetUnknown()) ? 131071 : 524287);
    if (isSetUnknown())
      hashCode = hashCode * 8191 + unknown.hashCode();

    hashCode = hashCode * 8191 + ((isSetDescription()) ? 131071 : 524287);
    if (isSetDescription())
      hashCode = hashCode * 8191 + description.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(Unknown other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetUnknown()).compareTo(other.isSetUnknown());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUnknown()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.unknown, other.unknown);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDescription()).compareTo(other.isSetDescription());
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
    StringBuilder sb = new StringBuilder("Unknown(");
    boolean first = true;

    sb.append("unknown:");
    if (this.unknown == null) {
      sb.append("null");
    } else {
      sb.append(this.unknown);
    }
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
    if (unknown == null) {
      throw new TProtocolException("Required field 'unknown' was not present! Struct: " + toString());
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

  private static class UnknownStandardSchemeFactory implements SchemeFactory {
    public UnknownStandardScheme getScheme() {
      return new UnknownStandardScheme();
    }
  }

  private static class UnknownStandardScheme extends StandardScheme<Unknown> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Unknown struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // UNKNOWN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.unknown = new UnknownType();
              struct.unknown.read(iprot);
              struct.setUnknownIsSet(true);
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
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Unknown struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.unknown != null) {
        oprot.writeFieldBegin(UNKNOWN_FIELD_DESC);
        struct.unknown.write(oprot);
        oprot.writeFieldEnd();
      }
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

  private static class UnknownTupleSchemeFactory implements SchemeFactory {
    public UnknownTupleScheme getScheme() {
      return new UnknownTupleScheme();
    }
  }

  private static class UnknownTupleScheme extends TupleScheme<Unknown> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Unknown struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      struct.unknown.write(oprot);
      BitSet optionals = new BitSet();
      if (struct.isSetDescription()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetDescription()) {
        oprot.writeString(struct.description);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Unknown struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.unknown = new UnknownType();
      struct.unknown.read(iprot);
      struct.setUnknownIsSet(true);
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.description = iprot.readString();
        struct.setDescriptionIsSet(true);
      }
    }
  }

  private static <S extends IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

