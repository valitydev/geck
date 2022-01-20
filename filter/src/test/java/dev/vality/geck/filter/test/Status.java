package dev.vality.geck.filter.test;

import org.apache.thrift.protocol.TProtocolException;

import java.util.*;

public class Status extends org.apache.thrift.TUnion<Status, Status._Fields> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Status");
  private static final org.apache.thrift.protocol.TField OK_STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("ok_status", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField FAIL_STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("fail_status", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField UNKNOWN_STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("unknown_status", org.apache.thrift.protocol.TType.STRUCT, (short)3);

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    OK_STATUS((short)1, "ok_status"),
    FAIL_STATUS((short)2, "fail_status"),
    UNKNOWN_STATUS((short)3, "unknown_status");

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
        case 1: // OK_STATUS
          return OK_STATUS;
        case 2: // FAIL_STATUS
          return FAIL_STATUS;
        case 3: // UNKNOWN_STATUS
          return UNKNOWN_STATUS;
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

  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.OK_STATUS, new org.apache.thrift.meta_data.FieldMetaData("ok_status", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Ok.class)));
    tmpMap.put(_Fields.FAIL_STATUS, new org.apache.thrift.meta_data.FieldMetaData("fail_status", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Fail.class)));
    tmpMap.put(_Fields.UNKNOWN_STATUS, new org.apache.thrift.meta_data.FieldMetaData("unknown_status", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Unknown.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Status.class, metaDataMap);
  }

  public Status() {
    super();
  }

  public Status(_Fields setField, Object value) {
    super(setField, value);
  }

  public Status(Status other) {
    super(other);
  }
  public Status deepCopy() {
    return new Status(this);
  }

  public static Status ok_status(Ok value) {
    Status x = new Status();
    x.setOkStatus(value);
    return x;
  }

  public static Status fail_status(Fail value) {
    Status x = new Status();
    x.setFailStatus(value);
    return x;
  }

  public static Status unknown_status(Unknown value) {
    Status x = new Status();
    x.setUnknownStatus(value);
    return x;
  }


  @Override
  protected void checkType(_Fields setField, Object value) throws ClassCastException {
    switch (setField) {
      case OK_STATUS:
        if (value instanceof Ok) {
          break;
        }
        throw new ClassCastException("Was expecting value of type Ok for field 'ok_status', but got " + value.getClass().getSimpleName());
      case FAIL_STATUS:
        if (value instanceof Fail) {
          break;
        }
        throw new ClassCastException("Was expecting value of type Fail for field 'fail_status', but got " + value.getClass().getSimpleName());
      case UNKNOWN_STATUS:
        if (value instanceof Unknown) {
          break;
        }
        throw new ClassCastException("Was expecting value of type Unknown for field 'unknown_status', but got " + value.getClass().getSimpleName());
      default:
        throw new IllegalArgumentException("Unknown field id " + setField);
    }
  }

  @Override
  protected Object standardSchemeReadValue(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TField field) throws org.apache.thrift.TException {
    _Fields setField = _Fields.findByThriftId(field.id);
    if (setField != null) {
      switch (setField) {
        case OK_STATUS:
          if (field.type == OK_STATUS_FIELD_DESC.type) {
            Ok ok_status;
            ok_status = new Ok();
            ok_status.read(iprot);
            return ok_status;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case FAIL_STATUS:
          if (field.type == FAIL_STATUS_FIELD_DESC.type) {
            Fail fail_status;
            fail_status = new Fail();
            fail_status.read(iprot);
            return fail_status;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case UNKNOWN_STATUS:
          if (field.type == UNKNOWN_STATUS_FIELD_DESC.type) {
            Unknown unknown_status;
            unknown_status = new Unknown();
            unknown_status.read(iprot);
            return unknown_status;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        default:
          throw new IllegalStateException("setField wasn't null, but didn't match any of the case statements!");
      }
    } else {
      org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
      return null;
    }
  }

  @Override
  protected void standardSchemeWriteValue(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    switch (setField_) {
      case OK_STATUS:
        Ok ok_status = (Ok)value_;
        ok_status.write(oprot);
        return;
      case FAIL_STATUS:
        Fail fail_status = (Fail)value_;
        fail_status.write(oprot);
        return;
      case UNKNOWN_STATUS:
        Unknown unknown_status = (Unknown)value_;
        unknown_status.write(oprot);
        return;
      default:
        throw new IllegalStateException("Cannot write union with unknown field " + setField_);
    }
  }

  @Override
  protected Object tupleSchemeReadValue(org.apache.thrift.protocol.TProtocol iprot, short fieldID) throws org.apache.thrift.TException {
    _Fields setField = _Fields.findByThriftId(fieldID);
    if (setField != null) {
      switch (setField) {
        case OK_STATUS:
          Ok ok_status;
          ok_status = new Ok();
          ok_status.read(iprot);
          return ok_status;
        case FAIL_STATUS:
          Fail fail_status;
          fail_status = new Fail();
          fail_status.read(iprot);
          return fail_status;
        case UNKNOWN_STATUS:
          Unknown unknown_status;
          unknown_status = new Unknown();
          unknown_status.read(iprot);
          return unknown_status;
        default:
          throw new IllegalStateException("setField wasn't null, but didn't match any of the case statements!");
      }
    } else {
      throw new TProtocolException("Couldn't find a field with field id " + fieldID);
    }
  }

  @Override
  protected void tupleSchemeWriteValue(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    switch (setField_) {
      case OK_STATUS:
        Ok ok_status = (Ok)value_;
        ok_status.write(oprot);
        return;
      case FAIL_STATUS:
        Fail fail_status = (Fail)value_;
        fail_status.write(oprot);
        return;
      case UNKNOWN_STATUS:
        Unknown unknown_status = (Unknown)value_;
        unknown_status.write(oprot);
        return;
      default:
        throw new IllegalStateException("Cannot write union with unknown field " + setField_);
    }
  }

  @Override
  protected org.apache.thrift.protocol.TField getFieldDesc(_Fields setField) {
    switch (setField) {
      case OK_STATUS:
        return OK_STATUS_FIELD_DESC;
      case FAIL_STATUS:
        return FAIL_STATUS_FIELD_DESC;
      case UNKNOWN_STATUS:
        return UNKNOWN_STATUS_FIELD_DESC;
      default:
        throw new IllegalArgumentException("Unknown field id " + setField);
    }
  }

  @Override
  protected org.apache.thrift.protocol.TStruct getStructDesc() {
    return STRUCT_DESC;
  }

  @Override
  protected _Fields enumForId(short id) {
    return _Fields.findByThriftIdOrThrow(id);
  }

  public _Fields[] getFields() {
    return _Fields.values();
  }

  public Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> getFieldMetaData() {
    return metaDataMap;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }


  public Ok getOkStatus() {
    if (getSetField() == _Fields.OK_STATUS) {
      return (Ok)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'ok_status' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setOkStatus(Ok value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.OK_STATUS;
    value_ = value;
  }

  public Fail getFailStatus() {
    if (getSetField() == _Fields.FAIL_STATUS) {
      return (Fail)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'fail_status' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setFailStatus(Fail value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.FAIL_STATUS;
    value_ = value;
  }

  public Unknown getUnknownStatus() {
    if (getSetField() == _Fields.UNKNOWN_STATUS) {
      return (Unknown)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'unknown_status' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setUnknownStatus(Unknown value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.UNKNOWN_STATUS;
    value_ = value;
  }

  public boolean isSetOkStatus() {
    return setField_ == _Fields.OK_STATUS;
  }


  public boolean isSetFailStatus() {
    return setField_ == _Fields.FAIL_STATUS;
  }


  public boolean isSetUnknownStatus() {
    return setField_ == _Fields.UNKNOWN_STATUS;
  }


  public boolean equals(Object other) {
    if (other instanceof Status) {
      return equals((Status)other);
    } else {
      return false;
    }
  }

  public boolean equals(Status other) {
    return other != null && getSetField() == other.getSetField() && getFieldValue().equals(other.getFieldValue());
  }

  @Override
  public int compareTo(Status other) {
    int lastComparison = org.apache.thrift.TBaseHelper.compareTo(getSetField(), other.getSetField());
    if (lastComparison == 0) {
      return org.apache.thrift.TBaseHelper.compareTo(getFieldValue(), other.getFieldValue());
    }
    return lastComparison;
  }


  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();
    list.add(this.getClass().getName());
    org.apache.thrift.TFieldIdEnum setField = getSetField();
    if (setField != null) {
      list.add(setField.getThriftFieldId());
      Object value = getFieldValue();
      if (value instanceof org.apache.thrift.TEnum) {
        list.add(((org.apache.thrift.TEnum)getFieldValue()).getValue());
      } else {
        list.add(value);
      }
    }
    return list.hashCode();
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


}
