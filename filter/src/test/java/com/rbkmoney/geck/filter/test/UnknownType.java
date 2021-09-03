package com.rbkmoney.geck.filter.test;

import org.apache.thrift.protocol.TProtocolException;

import java.util.*;

public class UnknownType extends org.apache.thrift.TUnion<UnknownType, UnknownType._Fields> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UnknownType");
  private static final org.apache.thrift.protocol.TField RESULT_FIELD_DESC = new org.apache.thrift.protocol.TField("result", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField RESULT_TYPES_FIELD_DESC = new org.apache.thrift.protocol.TField("resultTypes", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("value", org.apache.thrift.protocol.TType.DOUBLE, (short)3);

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    RESULT((short)1, "result"),
    RESULT_TYPES((short)2, "resultTypes"),
    VALUE((short)3, "value");

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
        case 1: // RESULT
          return RESULT;
        case 2: // RESULT_TYPES
          return RESULT_TYPES;
        case 3: // VALUE
          return VALUE;
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
    tmpMap.put(_Fields.RESULT, new org.apache.thrift.meta_data.FieldMetaData("result", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.RESULT_TYPES, new org.apache.thrift.meta_data.FieldMetaData("resultTypes", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, Type.class))));
    tmpMap.put(_Fields.VALUE, new org.apache.thrift.meta_data.FieldMetaData("value", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UnknownType.class, metaDataMap);
  }

  public UnknownType() {
    super();
  }

  public UnknownType(_Fields setField, Object value) {
    super(setField, value);
  }

  public UnknownType(UnknownType other) {
    super(other);
  }
  public UnknownType deepCopy() {
    return new UnknownType(this);
  }

  public static UnknownType result(String value) {
    UnknownType x = new UnknownType();
    x.setResult(value);
    return x;
  }

  public static UnknownType resultTypes(List<Type> value) {
    UnknownType x = new UnknownType();
    x.setResultTypes(value);
    return x;
  }

  public static UnknownType value(double value) {
    UnknownType x = new UnknownType();
    x.setValue(value);
    return x;
  }


  @Override
  protected void checkType(_Fields setField, Object value) throws ClassCastException {
    switch (setField) {
      case RESULT:
        if (value instanceof String) {
          break;
        }
        throw new ClassCastException("Was expecting value of type String for field 'result', but got " + value.getClass().getSimpleName());
      case RESULT_TYPES:
        if (value instanceof List) {
          break;
        }
        throw new ClassCastException("Was expecting value of type List<Type> for field 'resultTypes', but got " + value.getClass().getSimpleName());
      case VALUE:
        if (value instanceof Double) {
          break;
        }
        throw new ClassCastException("Was expecting value of type Double for field 'value', but got " + value.getClass().getSimpleName());
      default:
        throw new IllegalArgumentException("Unknown field id " + setField);
    }
  }

  @Override
  protected Object standardSchemeReadValue(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TField field) throws org.apache.thrift.TException {
    _Fields setField = _Fields.findByThriftId(field.id);
    if (setField != null) {
      switch (setField) {
        case RESULT:
          if (field.type == RESULT_FIELD_DESC.type) {
            String result;
            result = iprot.readString();
            return result;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case RESULT_TYPES:
          if (field.type == RESULT_TYPES_FIELD_DESC.type) {
            List<Type> resultTypes;
            {
              org.apache.thrift.protocol.TList _list32 = iprot.readListBegin();
              resultTypes = new ArrayList<Type>(_list32.size);
              Type _elem33;
              for (int _i34 = 0; _i34 < _list32.size; ++_i34)
              {
                _elem33 = Type.findByValue(iprot.readI32());
                resultTypes.add(_elem33);
              }
              iprot.readListEnd();
            }
            return resultTypes;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case VALUE:
          if (field.type == VALUE_FIELD_DESC.type) {
            Double value;
            value = iprot.readDouble();
            return value;
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
      case RESULT:
        String result = (String)value_;
        oprot.writeString(result);
        return;
      case RESULT_TYPES:
        List<Type> resultTypes = (List<Type>)value_;
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, resultTypes.size()));
          for (Type _iter35 : resultTypes)
          {
            oprot.writeI32(_iter35.getValue());
          }
          oprot.writeListEnd();
        }
        return;
      case VALUE:
        Double value = (Double)value_;
        oprot.writeDouble(value);
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
        case RESULT:
          String result;
          result = iprot.readString();
          return result;
        case RESULT_TYPES:
          List<Type> resultTypes;
          {
            org.apache.thrift.protocol.TList _list36 = iprot.readListBegin();
            resultTypes = new ArrayList<Type>(_list36.size);
            Type _elem37;
            for (int _i38 = 0; _i38 < _list36.size; ++_i38)
            {
              _elem37 = Type.findByValue(iprot.readI32());
              resultTypes.add(_elem37);
            }
            iprot.readListEnd();
          }
          return resultTypes;
        case VALUE:
          Double value;
          value = iprot.readDouble();
          return value;
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
      case RESULT:
        String result = (String)value_;
        oprot.writeString(result);
        return;
      case RESULT_TYPES:
        List<Type> resultTypes = (List<Type>)value_;
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, resultTypes.size()));
          for (Type _iter39 : resultTypes)
          {
            oprot.writeI32(_iter39.getValue());
          }
          oprot.writeListEnd();
        }
        return;
      case VALUE:
        Double value = (Double)value_;
        oprot.writeDouble(value);
        return;
      default:
        throw new IllegalStateException("Cannot write union with unknown field " + setField_);
    }
  }

  @Override
  protected org.apache.thrift.protocol.TField getFieldDesc(_Fields setField) {
    switch (setField) {
      case RESULT:
        return RESULT_FIELD_DESC;
      case RESULT_TYPES:
        return RESULT_TYPES_FIELD_DESC;
      case VALUE:
        return VALUE_FIELD_DESC;
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


  public String getResult() {
    if (getSetField() == _Fields.RESULT) {
      return (String)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'result' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setResult(String value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.RESULT;
    value_ = value;
  }

  public List<Type> getResultTypes() {
    if (getSetField() == _Fields.RESULT_TYPES) {
      return (List<Type>)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'resultTypes' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setResultTypes(List<Type> value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.RESULT_TYPES;
    value_ = value;
  }

  public double getValue() {
    if (getSetField() == _Fields.VALUE) {
      return (Double)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'value' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setValue(double value) {
    setField_ = _Fields.VALUE;
    value_ = value;
  }

  public boolean isSetResult() {
    return setField_ == _Fields.RESULT;
  }


  public boolean isSetResultTypes() {
    return setField_ == _Fields.RESULT_TYPES;
  }


  public boolean isSetValue() {
    return setField_ == _Fields.VALUE;
  }


  public boolean equals(Object other) {
    if (other instanceof UnknownType) {
      return equals((UnknownType)other);
    } else {
      return false;
    }
  }

  public boolean equals(UnknownType other) {
    return other != null && getSetField() == other.getSetField() && getFieldValue().equals(other.getFieldValue());
  }

  @Override
  public int compareTo(UnknownType other) {
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
