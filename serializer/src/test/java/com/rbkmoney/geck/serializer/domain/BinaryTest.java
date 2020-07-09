/**
 * Autogenerated by Thrift Compiler (1.0.0-dev)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.rbkmoney.geck.serializer.domain;

import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import javax.annotation.Generated;
import java.nio.ByteBuffer;
import java.util.*;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@Generated(value = "Autogenerated by Thrift Compiler (1.0.0-dev)", date = "2020-07-09")
public class BinaryTest implements org.apache.thrift.TBase<BinaryTest, BinaryTest._Fields>, java.io.Serializable, Cloneable, Comparable<BinaryTest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BinaryTest");

  private static final org.apache.thrift.protocol.TField DATA_FIELD_DESC = new org.apache.thrift.protocol.TField("data", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField DATA_IN_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("dataInList", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField DATA_IN_SET_FIELD_DESC = new org.apache.thrift.protocol.TField("dataInSet", org.apache.thrift.protocol.TType.SET, (short)3);
  private static final org.apache.thrift.protocol.TField DATA_IN_MAP_FIELD_DESC = new org.apache.thrift.protocol.TField("dataInMap", org.apache.thrift.protocol.TType.MAP, (short)4);

  private static final SchemeFactory STANDARD_SCHEME_FACTORY = new BinaryTestStandardSchemeFactory();
  private static final SchemeFactory TUPLE_SCHEME_FACTORY = new BinaryTestTupleSchemeFactory();

  public ByteBuffer data; // required
  public List<ByteBuffer> dataInList; // required
  public Set<ByteBuffer> dataInSet; // required
  public Map<ByteBuffer,ByteBuffer> dataInMap; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DATA((short)1, "data"),
    DATA_IN_LIST((short)2, "dataInList"),
    DATA_IN_SET((short)3, "dataInSet"),
    DATA_IN_MAP((short)4, "dataInMap");

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
        case 1: // DATA
          return DATA;
        case 2: // DATA_IN_LIST
          return DATA_IN_LIST;
        case 3: // DATA_IN_SET
          return DATA_IN_SET;
        case 4: // DATA_IN_MAP
          return DATA_IN_MAP;
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DATA, new org.apache.thrift.meta_data.FieldMetaData("data", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    tmpMap.put(_Fields.DATA_IN_LIST, new org.apache.thrift.meta_data.FieldMetaData("dataInList", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING            , true))));
    tmpMap.put(_Fields.DATA_IN_SET, new org.apache.thrift.meta_data.FieldMetaData("dataInSet", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING            , true))));
    tmpMap.put(_Fields.DATA_IN_MAP, new org.apache.thrift.meta_data.FieldMetaData("dataInMap", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING            , true), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING            , true))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BinaryTest.class, metaDataMap);
  }

  public BinaryTest() {
  }

  public BinaryTest(
    ByteBuffer data,
    List<ByteBuffer> dataInList,
    Set<ByteBuffer> dataInSet,
    Map<ByteBuffer,ByteBuffer> dataInMap)
  {
    this();
    this.data = org.apache.thrift.TBaseHelper.copyBinary(data);
    this.dataInList = dataInList;
    this.dataInSet = dataInSet;
    this.dataInMap = dataInMap;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BinaryTest(BinaryTest other) {
    if (other.isSetData()) {
      this.data = org.apache.thrift.TBaseHelper.copyBinary(other.data);
    }
    if (other.isSetDataInList()) {
      List<ByteBuffer> __this__dataInList = new ArrayList<ByteBuffer>(other.dataInList);
      this.dataInList = __this__dataInList;
    }
    if (other.isSetDataInSet()) {
      Set<ByteBuffer> __this__dataInSet = new HashSet<ByteBuffer>(other.dataInSet);
      this.dataInSet = __this__dataInSet;
    }
    if (other.isSetDataInMap()) {
      Map<ByteBuffer,ByteBuffer> __this__dataInMap = new HashMap<ByteBuffer,ByteBuffer>(other.dataInMap);
      this.dataInMap = __this__dataInMap;
    }
  }

  public BinaryTest deepCopy() {
    return new BinaryTest(this);
  }

  @Override
  public void clear() {
    this.data = null;
    this.dataInList = null;
    this.dataInSet = null;
    this.dataInMap = null;
  }

  public byte[] getData() {
    setData(org.apache.thrift.TBaseHelper.rightSize(data));
    return data == null ? null : data.array();
  }

  public ByteBuffer bufferForData() {
    return org.apache.thrift.TBaseHelper.copyBinary(data);
  }

  public BinaryTest setData(byte[] data) {
    this.data = data == null ? (ByteBuffer)null : ByteBuffer.wrap(Arrays.copyOf(data, data.length));
    return this;
  }

  public BinaryTest setData(ByteBuffer data) {
    this.data = org.apache.thrift.TBaseHelper.copyBinary(data);
    return this;
  }

  public void unsetData() {
    this.data = null;
  }

  /** Returns true if field data is set (has been assigned a value) and false otherwise */
  public boolean isSetData() {
    return this.data != null;
  }

  public void setDataIsSet(boolean value) {
    if (!value) {
      this.data = null;
    }
  }

  public int getDataInListSize() {
    return (this.dataInList == null) ? 0 : this.dataInList.size();
  }

  public java.util.Iterator<ByteBuffer> getDataInListIterator() {
    return (this.dataInList == null) ? null : this.dataInList.iterator();
  }

  public void addToDataInList(ByteBuffer elem) {
    if (this.dataInList == null) {
      this.dataInList = new ArrayList<ByteBuffer>();
    }
    this.dataInList.add(elem);
  }

  public List<ByteBuffer> getDataInList() {
    return this.dataInList;
  }

  public BinaryTest setDataInList(List<ByteBuffer> dataInList) {
    this.dataInList = dataInList;
    return this;
  }

  public void unsetDataInList() {
    this.dataInList = null;
  }

  /** Returns true if field dataInList is set (has been assigned a value) and false otherwise */
  public boolean isSetDataInList() {
    return this.dataInList != null;
  }

  public void setDataInListIsSet(boolean value) {
    if (!value) {
      this.dataInList = null;
    }
  }

  public int getDataInSetSize() {
    return (this.dataInSet == null) ? 0 : this.dataInSet.size();
  }

  public java.util.Iterator<ByteBuffer> getDataInSetIterator() {
    return (this.dataInSet == null) ? null : this.dataInSet.iterator();
  }

  public void addToDataInSet(ByteBuffer elem) {
    if (this.dataInSet == null) {
      this.dataInSet = new HashSet<ByteBuffer>();
    }
    this.dataInSet.add(elem);
  }

  public Set<ByteBuffer> getDataInSet() {
    return this.dataInSet;
  }

  public BinaryTest setDataInSet(Set<ByteBuffer> dataInSet) {
    this.dataInSet = dataInSet;
    return this;
  }

  public void unsetDataInSet() {
    this.dataInSet = null;
  }

  /** Returns true if field dataInSet is set (has been assigned a value) and false otherwise */
  public boolean isSetDataInSet() {
    return this.dataInSet != null;
  }

  public void setDataInSetIsSet(boolean value) {
    if (!value) {
      this.dataInSet = null;
    }
  }

  public int getDataInMapSize() {
    return (this.dataInMap == null) ? 0 : this.dataInMap.size();
  }

  public void putToDataInMap(ByteBuffer key, ByteBuffer val) {
    if (this.dataInMap == null) {
      this.dataInMap = new HashMap<ByteBuffer,ByteBuffer>();
    }
    this.dataInMap.put(key, val);
  }

  public Map<ByteBuffer,ByteBuffer> getDataInMap() {
    return this.dataInMap;
  }

  public BinaryTest setDataInMap(Map<ByteBuffer,ByteBuffer> dataInMap) {
    this.dataInMap = dataInMap;
    return this;
  }

  public void unsetDataInMap() {
    this.dataInMap = null;
  }

  /** Returns true if field dataInMap is set (has been assigned a value) and false otherwise */
  public boolean isSetDataInMap() {
    return this.dataInMap != null;
  }

  public void setDataInMapIsSet(boolean value) {
    if (!value) {
      this.dataInMap = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case DATA:
      if (value == null) {
        unsetData();
      } else {
        if (value instanceof byte[]) {
          setData((byte[])value);
        } else {
          setData((ByteBuffer)value);
        }
      }
      break;

    case DATA_IN_LIST:
      if (value == null) {
        unsetDataInList();
      } else {
        setDataInList((List<ByteBuffer>)value);
      }
      break;

    case DATA_IN_SET:
      if (value == null) {
        unsetDataInSet();
      } else {
        setDataInSet((Set<ByteBuffer>)value);
      }
      break;

    case DATA_IN_MAP:
      if (value == null) {
        unsetDataInMap();
      } else {
        setDataInMap((Map<ByteBuffer,ByteBuffer>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case DATA:
      return getData();

    case DATA_IN_LIST:
      return getDataInList();

    case DATA_IN_SET:
      return getDataInSet();

    case DATA_IN_MAP:
      return getDataInMap();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case DATA:
      return isSetData();
    case DATA_IN_LIST:
      return isSetDataInList();
    case DATA_IN_SET:
      return isSetDataInSet();
    case DATA_IN_MAP:
      return isSetDataInMap();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BinaryTest)
      return this.equals((BinaryTest)that);
    return false;
  }

  public boolean equals(BinaryTest that) {
    if (that == null)
      return false;

    boolean this_present_data = true && this.isSetData();
    boolean that_present_data = true && that.isSetData();
    if (this_present_data || that_present_data) {
      if (!(this_present_data && that_present_data))
        return false;
      if (!this.data.equals(that.data))
        return false;
    }

    boolean this_present_dataInList = true && this.isSetDataInList();
    boolean that_present_dataInList = true && that.isSetDataInList();
    if (this_present_dataInList || that_present_dataInList) {
      if (!(this_present_dataInList && that_present_dataInList))
        return false;
      if (!this.dataInList.equals(that.dataInList))
        return false;
    }

    boolean this_present_dataInSet = true && this.isSetDataInSet();
    boolean that_present_dataInSet = true && that.isSetDataInSet();
    if (this_present_dataInSet || that_present_dataInSet) {
      if (!(this_present_dataInSet && that_present_dataInSet))
        return false;
      if (!this.dataInSet.equals(that.dataInSet))
        return false;
    }

    boolean this_present_dataInMap = true && this.isSetDataInMap();
    boolean that_present_dataInMap = true && that.isSetDataInMap();
    if (this_present_dataInMap || that_present_dataInMap) {
      if (!(this_present_dataInMap && that_present_dataInMap))
        return false;
      if (!this.dataInMap.equals(that.dataInMap))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetData()) ? 131071 : 524287);
    if (isSetData())
      hashCode = hashCode * 8191 + data.hashCode();

    hashCode = hashCode * 8191 + ((isSetDataInList()) ? 131071 : 524287);
    if (isSetDataInList())
      hashCode = hashCode * 8191 + dataInList.hashCode();

    hashCode = hashCode * 8191 + ((isSetDataInSet()) ? 131071 : 524287);
    if (isSetDataInSet())
      hashCode = hashCode * 8191 + dataInSet.hashCode();

    hashCode = hashCode * 8191 + ((isSetDataInMap()) ? 131071 : 524287);
    if (isSetDataInMap())
      hashCode = hashCode * 8191 + dataInMap.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(BinaryTest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetData()).compareTo(other.isSetData());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetData()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.data, other.data);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDataInList()).compareTo(other.isSetDataInList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDataInList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dataInList, other.dataInList);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDataInSet()).compareTo(other.isSetDataInSet());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDataInSet()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dataInSet, other.dataInSet);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDataInMap()).compareTo(other.isSetDataInMap());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDataInMap()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dataInMap, other.dataInMap);
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
    StringBuilder sb = new StringBuilder("BinaryTest(");
    boolean first = true;

    sb.append("data:");
    if (this.data == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.data, sb);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("dataInList:");
    if (this.dataInList == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.dataInList, sb);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("dataInSet:");
    if (this.dataInSet == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.dataInSet, sb);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("dataInMap:");
    if (this.dataInMap == null) {
      sb.append("null");
    } else {
      sb.append(this.dataInMap);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (data == null) {
      throw new TProtocolException("Required field 'data' was not present! Struct: " + toString());
    }
    if (dataInList == null) {
      throw new TProtocolException("Required field 'dataInList' was not present! Struct: " + toString());
    }
    if (dataInSet == null) {
      throw new TProtocolException("Required field 'dataInSet' was not present! Struct: " + toString());
    }
    if (dataInMap == null) {
      throw new TProtocolException("Required field 'dataInMap' was not present! Struct: " + toString());
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

  private static class BinaryTestStandardSchemeFactory implements SchemeFactory {
    public BinaryTestStandardScheme getScheme() {
      return new BinaryTestStandardScheme();
    }
  }

  private static class BinaryTestStandardScheme extends StandardScheme<BinaryTest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BinaryTest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DATA
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.data = iprot.readBinary();
              struct.setDataIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DATA_IN_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list142 = iprot.readListBegin();
                struct.dataInList = new ArrayList<ByteBuffer>(_list142.size);
                ByteBuffer _elem143;
                for (int _i144 = 0; _i144 < _list142.size; ++_i144)
                {
                  _elem143 = iprot.readBinary();
                  struct.dataInList.add(_elem143);
                }
                iprot.readListEnd();
              }
              struct.setDataInListIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // DATA_IN_SET
            if (schemeField.type == org.apache.thrift.protocol.TType.SET) {
              {
                org.apache.thrift.protocol.TSet _set145 = iprot.readSetBegin();
                struct.dataInSet = new HashSet<ByteBuffer>(2*_set145.size);
                ByteBuffer _elem146;
                for (int _i147 = 0; _i147 < _set145.size; ++_i147)
                {
                  _elem146 = iprot.readBinary();
                  struct.dataInSet.add(_elem146);
                }
                iprot.readSetEnd();
              }
              struct.setDataInSetIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // DATA_IN_MAP
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map148 = iprot.readMapBegin();
                struct.dataInMap = new HashMap<ByteBuffer,ByteBuffer>(2*_map148.size);
                ByteBuffer _key149;
                ByteBuffer _val150;
                for (int _i151 = 0; _i151 < _map148.size; ++_i151)
                {
                  _key149 = iprot.readBinary();
                  _val150 = iprot.readBinary();
                  struct.dataInMap.put(_key149, _val150);
                }
                iprot.readMapEnd();
              }
              struct.setDataInMapIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BinaryTest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.data != null) {
        oprot.writeFieldBegin(DATA_FIELD_DESC);
        oprot.writeBinary(struct.data);
        oprot.writeFieldEnd();
      }
      if (struct.dataInList != null) {
        oprot.writeFieldBegin(DATA_IN_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.dataInList.size()));
          for (ByteBuffer _iter152 : struct.dataInList)
          {
            oprot.writeBinary(_iter152);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.dataInSet != null) {
        oprot.writeFieldBegin(DATA_IN_SET_FIELD_DESC);
        {
          oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRING, struct.dataInSet.size()));
          for (ByteBuffer _iter153 : struct.dataInSet)
          {
            oprot.writeBinary(_iter153);
          }
          oprot.writeSetEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.dataInMap != null) {
        oprot.writeFieldBegin(DATA_IN_MAP_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, struct.dataInMap.size()));
          for (Map.Entry<ByteBuffer, ByteBuffer> _iter154 : struct.dataInMap.entrySet())
          {
            oprot.writeBinary(_iter154.getKey());
            oprot.writeBinary(_iter154.getValue());
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BinaryTestTupleSchemeFactory implements SchemeFactory {
    public BinaryTestTupleScheme getScheme() {
      return new BinaryTestTupleScheme();
    }
  }

  private static class BinaryTestTupleScheme extends TupleScheme<BinaryTest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BinaryTest struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeBinary(struct.data);
      {
        oprot.writeI32(struct.dataInList.size());
        for (ByteBuffer _iter155 : struct.dataInList)
        {
          oprot.writeBinary(_iter155);
        }
      }
      {
        oprot.writeI32(struct.dataInSet.size());
        for (ByteBuffer _iter156 : struct.dataInSet)
        {
          oprot.writeBinary(_iter156);
        }
      }
      {
        oprot.writeI32(struct.dataInMap.size());
        for (Map.Entry<ByteBuffer, ByteBuffer> _iter157 : struct.dataInMap.entrySet())
        {
          oprot.writeBinary(_iter157.getKey());
          oprot.writeBinary(_iter157.getValue());
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BinaryTest struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.data = iprot.readBinary();
      struct.setDataIsSet(true);
      {
        org.apache.thrift.protocol.TList _list158 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
        struct.dataInList = new ArrayList<ByteBuffer>(_list158.size);
        ByteBuffer _elem159;
        for (int _i160 = 0; _i160 < _list158.size; ++_i160)
        {
          _elem159 = iprot.readBinary();
          struct.dataInList.add(_elem159);
        }
      }
      struct.setDataInListIsSet(true);
      {
        org.apache.thrift.protocol.TSet _set161 = new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
        struct.dataInSet = new HashSet<ByteBuffer>(2*_set161.size);
        ByteBuffer _elem162;
        for (int _i163 = 0; _i163 < _set161.size; ++_i163)
        {
          _elem162 = iprot.readBinary();
          struct.dataInSet.add(_elem162);
        }
      }
      struct.setDataInSetIsSet(true);
      {
        org.apache.thrift.protocol.TMap _map164 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, iprot.readI32());
        struct.dataInMap = new HashMap<ByteBuffer,ByteBuffer>(2*_map164.size);
        ByteBuffer _key165;
        ByteBuffer _val166;
        for (int _i167 = 0; _i167 < _map164.size; ++_i167)
        {
          _key165 = iprot.readBinary();
          _val166 = iprot.readBinary();
          struct.dataInMap.put(_key165, _val166);
        }
      }
      struct.setDataInMapIsSet(true);
    }
  }

  private static <S extends IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

