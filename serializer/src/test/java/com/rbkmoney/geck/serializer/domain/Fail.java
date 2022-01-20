package com.rbkmoney.geck.serializer.domain;

import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import javax.annotation.Generated;
import java.util.*;

@Generated(value = "Autogenerated by Thrift Compiler (1.0.0-dev)", date = "2020-07-09")
public class Fail implements org.apache.thrift.TBase<Fail, Fail._Fields>, java.io.Serializable, Cloneable, Comparable<Fail> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Fail");

  private static final org.apache.thrift.protocol.TField REASONS_FIELD_DESC = new org.apache.thrift.protocol.TField("reasons", org.apache.thrift.protocol.TType.SET, (short)1);

  private static final SchemeFactory STANDARD_SCHEME_FACTORY = new FailStandardSchemeFactory();
  private static final SchemeFactory TUPLE_SCHEME_FACTORY = new FailTupleSchemeFactory();

  public Set<String> reasons; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    REASONS((short)1, "reasons");

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
        case 1: // REASONS
          return REASONS;
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
  private static final _Fields optionals[] = {_Fields.REASONS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.REASONS, new org.apache.thrift.meta_data.FieldMetaData("reasons", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Fail.class, metaDataMap);
  }

  public Fail() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Fail(Fail other) {
    if (other.isSetReasons()) {
      Set<String> __this__reasons = new HashSet<String>(other.reasons);
      this.reasons = __this__reasons;
    }
  }

  public Fail deepCopy() {
    return new Fail(this);
  }

  @Override
  public void clear() {
    this.reasons = null;
  }

  public int getReasonsSize() {
    return (this.reasons == null) ? 0 : this.reasons.size();
  }

  public java.util.Iterator<String> getReasonsIterator() {
    return (this.reasons == null) ? null : this.reasons.iterator();
  }

  public void addToReasons(String elem) {
    if (this.reasons == null) {
      this.reasons = new HashSet<String>();
    }
    this.reasons.add(elem);
  }

  public Set<String> getReasons() {
    return this.reasons;
  }

  public Fail setReasons(Set<String> reasons) {
    this.reasons = reasons;
    return this;
  }

  public void unsetReasons() {
    this.reasons = null;
  }

  /** Returns true if field reasons is set (has been assigned a value) and false otherwise */
  public boolean isSetReasons() {
    return this.reasons != null;
  }

  public void setReasonsIsSet(boolean value) {
    if (!value) {
      this.reasons = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case REASONS:
      if (value == null) {
        unsetReasons();
      } else {
        setReasons((Set<String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case REASONS:
      return getReasons();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case REASONS:
      return isSetReasons();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Fail)
      return this.equals((Fail)that);
    return false;
  }

  public boolean equals(Fail that) {
    if (that == null)
      return false;

    boolean this_present_reasons = true && this.isSetReasons();
    boolean that_present_reasons = true && that.isSetReasons();
    if (this_present_reasons || that_present_reasons) {
      if (!(this_present_reasons && that_present_reasons))
        return false;
      if (!this.reasons.equals(that.reasons))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetReasons()) ? 131071 : 524287);
    if (isSetReasons())
      hashCode = hashCode * 8191 + reasons.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(Fail other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetReasons()).compareTo(other.isSetReasons());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReasons()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.reasons, other.reasons);
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
    StringBuilder sb = new StringBuilder("Fail(");
    boolean first = true;

    if (isSetReasons()) {
      sb.append("reasons:");
      if (this.reasons == null) {
        sb.append("null");
      } else {
        sb.append(this.reasons);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
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

  private static class FailStandardSchemeFactory implements SchemeFactory {
    public FailStandardScheme getScheme() {
      return new FailStandardScheme();
    }
  }

  private static class FailStandardScheme extends StandardScheme<Fail> {

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
          case 1: // REASONS
            if (schemeField.type == org.apache.thrift.protocol.TType.SET) {
              {
                org.apache.thrift.protocol.TSet _set176 = iprot.readSetBegin();
                struct.reasons = new HashSet<String>(2*_set176.size);
                String _elem177;
                for (int _i178 = 0; _i178 < _set176.size; ++_i178)
                {
                  _elem177 = iprot.readString();
                  struct.reasons.add(_elem177);
                }
                iprot.readSetEnd();
              }
              struct.setReasonsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Fail struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.reasons != null) {
        if (struct.isSetReasons()) {
          oprot.writeFieldBegin(REASONS_FIELD_DESC);
          {
            oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRING, struct.reasons.size()));
            for (String _iter179 : struct.reasons)
            {
              oprot.writeString(_iter179);
            }
            oprot.writeSetEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FailTupleSchemeFactory implements SchemeFactory {
    public FailTupleScheme getScheme() {
      return new FailTupleScheme();
    }
  }

  private static class FailTupleScheme extends TupleScheme<Fail> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Fail struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetReasons()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetReasons()) {
        {
          oprot.writeI32(struct.reasons.size());
          for (String _iter180 : struct.reasons)
          {
            oprot.writeString(_iter180);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Fail struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TSet _set181 = new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.reasons = new HashSet<String>(2*_set181.size);
          String _elem182;
          for (int _i183 = 0; _i183 < _set181.size; ++_i183)
          {
            _elem182 = iprot.readString();
            struct.reasons.add(_elem182);
          }
        }
        struct.setReasonsIsSet(true);
      }
    }
  }

  private static <S extends IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
