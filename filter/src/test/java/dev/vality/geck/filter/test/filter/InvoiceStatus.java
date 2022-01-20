package dev.vality.geck.filter.test.filter;

import org.apache.thrift.protocol.TProtocolException;

import java.util.*;

public class InvoiceStatus extends org.apache.thrift.TUnion<InvoiceStatus, InvoiceStatus._Fields> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("InvoiceStatus");
  private static final org.apache.thrift.protocol.TField PAID_FIELD_DESC = new org.apache.thrift.protocol.TField("paid", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField CANCELED_FIELD_DESC = new org.apache.thrift.protocol.TField("canceled", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAID((short)1, "paid"),
    CANCELED((short)2, "canceled");

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
        case 1: // PAID
          return PAID;
        case 2: // CANCELED
          return CANCELED;
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
    tmpMap.put(_Fields.PAID, new org.apache.thrift.meta_data.FieldMetaData("paid", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, IStatusPaid.class)));
    tmpMap.put(_Fields.CANCELED, new org.apache.thrift.meta_data.FieldMetaData("canceled", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, IStatusCanceled.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(InvoiceStatus.class, metaDataMap);
  }

  public InvoiceStatus() {
    super();
  }

  public InvoiceStatus(_Fields setField, Object value) {
    super(setField, value);
  }

  public InvoiceStatus(InvoiceStatus other) {
    super(other);
  }
  public InvoiceStatus deepCopy() {
    return new InvoiceStatus(this);
  }

  public static InvoiceStatus paid(IStatusPaid value) {
    InvoiceStatus x = new InvoiceStatus();
    x.setPaid(value);
    return x;
  }

  public static InvoiceStatus canceled(IStatusCanceled value) {
    InvoiceStatus x = new InvoiceStatus();
    x.setCanceled(value);
    return x;
  }


  @Override
  protected void checkType(_Fields setField, Object value) throws ClassCastException {
    switch (setField) {
      case PAID:
        if (value instanceof IStatusPaid) {
          break;
        }
        throw new ClassCastException("Was expecting value of type IStatusPaid for field 'paid', but got " + value.getClass().getSimpleName());
      case CANCELED:
        if (value instanceof IStatusCanceled) {
          break;
        }
        throw new ClassCastException("Was expecting value of type IStatusCanceled for field 'canceled', but got " + value.getClass().getSimpleName());
      default:
        throw new IllegalArgumentException("Unknown field id " + setField);
    }
  }

  @Override
  protected Object standardSchemeReadValue(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TField field) throws org.apache.thrift.TException {
    _Fields setField = _Fields.findByThriftId(field.id);
    if (setField != null) {
      switch (setField) {
        case PAID:
          if (field.type == PAID_FIELD_DESC.type) {
            IStatusPaid paid;
            paid = new IStatusPaid();
            paid.read(iprot);
            return paid;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case CANCELED:
          if (field.type == CANCELED_FIELD_DESC.type) {
            IStatusCanceled canceled;
            canceled = new IStatusCanceled();
            canceled.read(iprot);
            return canceled;
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
      case PAID:
        IStatusPaid paid = (IStatusPaid)value_;
        paid.write(oprot);
        return;
      case CANCELED:
        IStatusCanceled canceled = (IStatusCanceled)value_;
        canceled.write(oprot);
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
        case PAID:
          IStatusPaid paid;
          paid = new IStatusPaid();
          paid.read(iprot);
          return paid;
        case CANCELED:
          IStatusCanceled canceled;
          canceled = new IStatusCanceled();
          canceled.read(iprot);
          return canceled;
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
      case PAID:
        IStatusPaid paid = (IStatusPaid)value_;
        paid.write(oprot);
        return;
      case CANCELED:
        IStatusCanceled canceled = (IStatusCanceled)value_;
        canceled.write(oprot);
        return;
      default:
        throw new IllegalStateException("Cannot write union with unknown field " + setField_);
    }
  }

  @Override
  protected org.apache.thrift.protocol.TField getFieldDesc(_Fields setField) {
    switch (setField) {
      case PAID:
        return PAID_FIELD_DESC;
      case CANCELED:
        return CANCELED_FIELD_DESC;
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


  public IStatusPaid getPaid() {
    if (getSetField() == _Fields.PAID) {
      return (IStatusPaid)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'paid' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setPaid(IStatusPaid value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.PAID;
    value_ = value;
  }

  public IStatusCanceled getCanceled() {
    if (getSetField() == _Fields.CANCELED) {
      return (IStatusCanceled)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'canceled' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setCanceled(IStatusCanceled value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.CANCELED;
    value_ = value;
  }

  public boolean isSetPaid() {
    return setField_ == _Fields.PAID;
  }


  public boolean isSetCanceled() {
    return setField_ == _Fields.CANCELED;
  }


  public boolean equals(Object other) {
    if (other instanceof InvoiceStatus) {
      return equals((InvoiceStatus)other);
    } else {
      return false;
    }
  }

  public boolean equals(InvoiceStatus other) {
    return other != null && getSetField() == other.getSetField() && getFieldValue().equals(other.getFieldValue());
  }

  @Override
  public int compareTo(InvoiceStatus other) {
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
