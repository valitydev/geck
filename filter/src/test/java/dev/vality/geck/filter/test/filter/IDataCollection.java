package dev.vality.geck.filter.test.filter;

import org.apache.thrift.protocol.TProtocolException;

import java.util.*;

public class IDataCollection extends org.apache.thrift.TUnion<IDataCollection, IDataCollection._Fields> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("IDataCollection");
  private static final org.apache.thrift.protocol.TField DATA_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("data_list", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField DATA_SET_FIELD_DESC = new org.apache.thrift.protocol.TField("data_set", org.apache.thrift.protocol.TType.SET, (short)2);

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DATA_LIST((short)1, "data_list"),
    DATA_SET((short)2, "data_set");

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
        case 1: // DATA_LIST
          return DATA_LIST;
        case 2: // DATA_SET
          return DATA_SET;
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
    tmpMap.put(_Fields.DATA_LIST, new org.apache.thrift.meta_data.FieldMetaData("data_list", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, IData.class))));
    tmpMap.put(_Fields.DATA_SET, new org.apache.thrift.meta_data.FieldMetaData("data_set", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, IData.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(IDataCollection.class, metaDataMap);
  }

  public IDataCollection() {
    super();
  }

  public IDataCollection(_Fields setField, Object value) {
    super(setField, value);
  }

  public IDataCollection(IDataCollection other) {
    super(other);
  }
  public IDataCollection deepCopy() {
    return new IDataCollection(this);
  }

  public static IDataCollection data_list(List<IData> value) {
    IDataCollection x = new IDataCollection();
    x.setDataList(value);
    return x;
  }

  public static IDataCollection data_set(Set<IData> value) {
    IDataCollection x = new IDataCollection();
    x.setDataSet(value);
    return x;
  }


  @Override
  protected void checkType(_Fields setField, Object value) throws ClassCastException {
    switch (setField) {
      case DATA_LIST:
        if (value instanceof List) {
          break;
        }
        throw new ClassCastException("Was expecting value of type List<IData> for field 'data_list', but got " + value.getClass().getSimpleName());
      case DATA_SET:
        if (value instanceof Set) {
          break;
        }
        throw new ClassCastException("Was expecting value of type Set<IData> for field 'data_set', but got " + value.getClass().getSimpleName());
      default:
        throw new IllegalArgumentException("Unknown field id " + setField);
    }
  }

  @Override
  protected Object standardSchemeReadValue(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TField field) throws org.apache.thrift.TException {
    _Fields setField = _Fields.findByThriftId(field.id);
    if (setField != null) {
      switch (setField) {
        case DATA_LIST:
          if (field.type == DATA_LIST_FIELD_DESC.type) {
            List<IData> data_list;
            {
              org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
              data_list = new ArrayList<IData>(_list0.size);
              IData _elem1;
              for (int _i2 = 0; _i2 < _list0.size; ++_i2)
              {
                _elem1 = new IData();
                _elem1.read(iprot);
                data_list.add(_elem1);
              }
              iprot.readListEnd();
            }
            return data_list;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case DATA_SET:
          if (field.type == DATA_SET_FIELD_DESC.type) {
            Set<IData> data_set;
            {
              org.apache.thrift.protocol.TSet _set3 = iprot.readSetBegin();
              data_set = new HashSet<IData>(2*_set3.size);
              IData _elem4;
              for (int _i5 = 0; _i5 < _set3.size; ++_i5)
              {
                _elem4 = new IData();
                _elem4.read(iprot);
                data_set.add(_elem4);
              }
              iprot.readSetEnd();
            }
            return data_set;
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
      case DATA_LIST:
        List<IData> data_list = (List<IData>)value_;
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, data_list.size()));
          for (IData _iter6 : data_list)
          {
            _iter6.write(oprot);
          }
          oprot.writeListEnd();
        }
        return;
      case DATA_SET:
        Set<IData> data_set = (Set<IData>)value_;
        {
          oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRUCT, data_set.size()));
          for (IData _iter7 : data_set)
          {
            _iter7.write(oprot);
          }
          oprot.writeSetEnd();
        }
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
        case DATA_LIST:
          List<IData> data_list;
          {
            org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
            data_list = new ArrayList<IData>(_list8.size);
            IData _elem9;
            for (int _i10 = 0; _i10 < _list8.size; ++_i10)
            {
              _elem9 = new IData();
              _elem9.read(iprot);
              data_list.add(_elem9);
            }
            iprot.readListEnd();
          }
          return data_list;
        case DATA_SET:
          Set<IData> data_set;
          {
            org.apache.thrift.protocol.TSet _set11 = iprot.readSetBegin();
            data_set = new HashSet<IData>(2*_set11.size);
            IData _elem12;
            for (int _i13 = 0; _i13 < _set11.size; ++_i13)
            {
              _elem12 = new IData();
              _elem12.read(iprot);
              data_set.add(_elem12);
            }
            iprot.readSetEnd();
          }
          return data_set;
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
      case DATA_LIST:
        List<IData> data_list = (List<IData>)value_;
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, data_list.size()));
          for (IData _iter14 : data_list)
          {
            _iter14.write(oprot);
          }
          oprot.writeListEnd();
        }
        return;
      case DATA_SET:
        Set<IData> data_set = (Set<IData>)value_;
        {
          oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRUCT, data_set.size()));
          for (IData _iter15 : data_set)
          {
            _iter15.write(oprot);
          }
          oprot.writeSetEnd();
        }
        return;
      default:
        throw new IllegalStateException("Cannot write union with unknown field " + setField_);
    }
  }

  @Override
  protected org.apache.thrift.protocol.TField getFieldDesc(_Fields setField) {
    switch (setField) {
      case DATA_LIST:
        return DATA_LIST_FIELD_DESC;
      case DATA_SET:
        return DATA_SET_FIELD_DESC;
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


  public List<IData> getDataList() {
    if (getSetField() == _Fields.DATA_LIST) {
      return (List<IData>)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'data_list' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setDataList(List<IData> value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.DATA_LIST;
    value_ = value;
  }

  public Set<IData> getDataSet() {
    if (getSetField() == _Fields.DATA_SET) {
      return (Set<IData>)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'data_set' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setDataSet(Set<IData> value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.DATA_SET;
    value_ = value;
  }

  public boolean isSetDataList() {
    return setField_ == _Fields.DATA_LIST;
  }


  public boolean isSetDataSet() {
    return setField_ == _Fields.DATA_SET;
  }


  public boolean equals(Object other) {
    if (other instanceof IDataCollection) {
      return equals((IDataCollection)other);
    } else {
      return false;
    }
  }

  public boolean equals(IDataCollection other) {
    return other != null && getSetField() == other.getSetField() && getFieldValue().equals(other.getFieldValue());
  }

  @Override
  public int compareTo(IDataCollection other) {
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
