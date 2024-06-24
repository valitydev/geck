/**
 * Autogenerated by Thrift Compiler (0.20.0)
 * <p>
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *
 * @generated
 */
package dev.vality.geck.serializer.domain;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@jakarta.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.20.0)", date = "2024-06-24")
public class MapTest implements org.apache.thrift.TBase<MapTest, MapTest._Fields>, java.io.Serializable, Cloneable, Comparable<MapTest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MapTest");

  private static final org.apache.thrift.protocol.TField ENUM_MAP_FIELD_DESC = new org.apache.thrift.protocol.TField("enumMap", org.apache.thrift.protocol.TType.MAP, (short)1);
  private static final org.apache.thrift.protocol.TField IDS_MAP_FIELD_DESC = new org.apache.thrift.protocol.TField("idsMap", org.apache.thrift.protocol.TType.MAP, (short)2);
  private static final org.apache.thrift.protocol.TField STATUS_MAP_FIELD_DESC = new org.apache.thrift.protocol.TField("statusMap", org.apache.thrift.protocol.TType.MAP, (short) 3);

    private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new MapTestStandardSchemeFactory();
    private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new MapTestTupleSchemeFactory();

    public @org.apache.thrift.annotation.Nullable java.util.Map<Enums, java.lang.String> enumMap; // required
    public @org.apache.thrift.annotation.Nullable java.util.Map<Ids, java.lang.String> idsMap; // optional
    public @org.apache.thrift.annotation.Nullable java.util.Map<Status, java.lang.String> statusMap; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ENUM_MAP((short)1, "enumMap"),
    IDS_MAP((short)2, "idsMap"),
    STATUS_MAP((short)3, "statusMap");

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
        case 1: // ENUM_MAP
          return ENUM_MAP;
        case 2: // IDS_MAP
          return IDS_MAP;
        case 3: // STATUS_MAP
          return STATUS_MAP;
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
  private static final _Fields optionals[] = {_Fields.IDS_MAP, _Fields.STATUS_MAP};
    public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
      java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ENUM_MAP, new org.apache.thrift.meta_data.FieldMetaData("enumMap", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, Enums.class), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.IDS_MAP, new org.apache.thrift.meta_data.FieldMetaData("idsMap", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Ids.class), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.STATUS_MAP, new org.apache.thrift.meta_data.FieldMetaData("statusMap", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Status.class), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
      metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MapTest.class, metaDataMap);
  }

  public MapTest() {
  }

  public MapTest(
          java.util.Map<Enums, java.lang.String> enumMap)
  {
    this();
    this.enumMap = enumMap;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MapTest(MapTest other) {
    if (other.isSetEnumMap()) {
        java.util.Map<Enums, java.lang.String> __this__enumMap = new java.util.EnumMap<Enums, java.lang.String>(Enums.class);
        for (java.util.Map.Entry<Enums, java.lang.String> other_element : other.enumMap.entrySet()) {

        Enums other_element_key = other_element.getKey();
            java.lang.String other_element_value = other_element.getValue();

        Enums __this__enumMap_copy_key = other_element_key;

            java.lang.String __this__enumMap_copy_value = other_element_value;

        __this__enumMap.put(__this__enumMap_copy_key, __this__enumMap_copy_value);
      }
      this.enumMap = __this__enumMap;
    }
    if (other.isSetIdsMap()) {
        java.util.Map<Ids, java.lang.String> __this__idsMap = new java.util.HashMap<Ids, java.lang.String>(other.idsMap.size());
        for (java.util.Map.Entry<Ids, java.lang.String> other_element : other.idsMap.entrySet()) {

        Ids other_element_key = other_element.getKey();
            java.lang.String other_element_value = other_element.getValue();

            Ids __this__idsMap_copy_key = new Ids(other_element_key);

            java.lang.String __this__idsMap_copy_value = other_element_value;

        __this__idsMap.put(__this__idsMap_copy_key, __this__idsMap_copy_value);
      }
      this.idsMap = __this__idsMap;
    }
    if (other.isSetStatusMap()) {
        java.util.Map<Status, java.lang.String> __this__statusMap = new java.util.HashMap<Status, java.lang.String>(other.statusMap.size());
        for (java.util.Map.Entry<Status, java.lang.String> other_element : other.statusMap.entrySet()) {

        Status other_element_key = other_element.getKey();
            java.lang.String other_element_value = other_element.getValue();

            Status __this__statusMap_copy_key = new Status(other_element_key);

            java.lang.String __this__statusMap_copy_value = other_element_value;

        __this__statusMap.put(__this__statusMap_copy_key, __this__statusMap_copy_value);
      }
      this.statusMap = __this__statusMap;
    }
  }

    @Override
  public MapTest deepCopy() {
    return new MapTest(this);
  }

  @Override
  public void clear() {
    this.enumMap = null;
    this.idsMap = null;
    this.statusMap = null;
  }

  public int getEnumMapSize() {
    return (this.enumMap == null) ? 0 : this.enumMap.size();
  }

    public void putToEnumMap(Enums key, java.lang.String val) {
    if (this.enumMap == null) {
        this.enumMap = new java.util.EnumMap<Enums, java.lang.String>(Enums.class);
    }
    this.enumMap.put(key, val);
    }

    @org.apache.thrift.annotation.Nullable
    public java.util.Map<Enums, java.lang.String> getEnumMap() {
    return this.enumMap;
    }

    public MapTest setEnumMap(@org.apache.thrift.annotation.Nullable java.util.Map<Enums, java.lang.String> enumMap) {
    this.enumMap = enumMap;
    return this;
  }

  public void unsetEnumMap() {
    this.enumMap = null;
  }

  /** Returns true if field enumMap is set (has been assigned a value) and false otherwise */
  public boolean isSetEnumMap() {
    return this.enumMap != null;
  }

  public void setEnumMapIsSet(boolean value) {
    if (!value) {
      this.enumMap = null;
    }
  }

  public int getIdsMapSize() {
    return (this.idsMap == null) ? 0 : this.idsMap.size();
  }

    public void putToIdsMap(Ids key, java.lang.String val) {
    if (this.idsMap == null) {
        this.idsMap = new java.util.HashMap<Ids, java.lang.String>();
    }
    this.idsMap.put(key, val);
    }

    @org.apache.thrift.annotation.Nullable
    public java.util.Map<Ids, java.lang.String> getIdsMap() {
    return this.idsMap;
    }

    public MapTest setIdsMap(@org.apache.thrift.annotation.Nullable java.util.Map<Ids, java.lang.String> idsMap) {
    this.idsMap = idsMap;
    return this;
  }

  public void unsetIdsMap() {
    this.idsMap = null;
  }

  /** Returns true if field idsMap is set (has been assigned a value) and false otherwise */
  public boolean isSetIdsMap() {
    return this.idsMap != null;
  }

  public void setIdsMapIsSet(boolean value) {
    if (!value) {
      this.idsMap = null;
    }
  }

  public int getStatusMapSize() {
    return (this.statusMap == null) ? 0 : this.statusMap.size();
  }

    public void putToStatusMap(Status key, java.lang.String val) {
    if (this.statusMap == null) {
        this.statusMap = new java.util.HashMap<Status, java.lang.String>();
    }
    this.statusMap.put(key, val);
    }

    @org.apache.thrift.annotation.Nullable
    public java.util.Map<Status, java.lang.String> getStatusMap() {
    return this.statusMap;
    }

    public MapTest setStatusMap(@org.apache.thrift.annotation.Nullable java.util.Map<Status, java.lang.String> statusMap) {
    this.statusMap = statusMap;
    return this;
  }

  public void unsetStatusMap() {
    this.statusMap = null;
  }

  /** Returns true if field statusMap is set (has been assigned a value) and false otherwise */
  public boolean isSetStatusMap() {
    return this.statusMap != null;
  }

  public void setStatusMapIsSet(boolean value) {
    if (!value) {
      this.statusMap = null;
    }
  }

    @Override
    public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case ENUM_MAP:
      if (value == null) {
        unsetEnumMap();
      } else {
          setEnumMap((java.util.Map<Enums, java.lang.String>) value);
      }
      break;

    case IDS_MAP:
      if (value == null) {
        unsetIdsMap();
      } else {
          setIdsMap((java.util.Map<Ids, java.lang.String>) value);
      }
      break;

    case STATUS_MAP:
      if (value == null) {
        unsetStatusMap();
      } else {
          setStatusMap((java.util.Map<Status, java.lang.String>) value);
      }
      break;

    }
    }

    @org.apache.thrift.annotation.Nullable
    @Override
    public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ENUM_MAP:
      return getEnumMap();

    case IDS_MAP:
      return getIdsMap();

    case STATUS_MAP:
      return getStatusMap();

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
    case ENUM_MAP:
      return isSetEnumMap();
    case IDS_MAP:
      return isSetIdsMap();
    case STATUS_MAP:
      return isSetStatusMap();
    }
      throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof MapTest)
      return this.equals((MapTest)that);
    return false;
  }

  public boolean equals(MapTest that) {
    if (that == null)
      return false;
      if (this == that)
          return true;

    boolean this_present_enumMap = true && this.isSetEnumMap();
    boolean that_present_enumMap = true && that.isSetEnumMap();
    if (this_present_enumMap || that_present_enumMap) {
      if (!(this_present_enumMap && that_present_enumMap))
        return false;
      if (!this.enumMap.equals(that.enumMap))
        return false;
    }

    boolean this_present_idsMap = true && this.isSetIdsMap();
    boolean that_present_idsMap = true && that.isSetIdsMap();
    if (this_present_idsMap || that_present_idsMap) {
      if (!(this_present_idsMap && that_present_idsMap))
        return false;
      if (!this.idsMap.equals(that.idsMap))
        return false;
    }

    boolean this_present_statusMap = true && this.isSetStatusMap();
    boolean that_present_statusMap = true && that.isSetStatusMap();
    if (this_present_statusMap || that_present_statusMap) {
      if (!(this_present_statusMap && that_present_statusMap))
        return false;
      if (!this.statusMap.equals(that.statusMap))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetEnumMap()) ? 131071 : 524287);
    if (isSetEnumMap())
      hashCode = hashCode * 8191 + enumMap.hashCode();

    hashCode = hashCode * 8191 + ((isSetIdsMap()) ? 131071 : 524287);
    if (isSetIdsMap())
      hashCode = hashCode * 8191 + idsMap.hashCode();

    hashCode = hashCode * 8191 + ((isSetStatusMap()) ? 131071 : 524287);
    if (isSetStatusMap())
      hashCode = hashCode * 8191 + statusMap.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(MapTest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

      lastComparison = java.lang.Boolean.compare(isSetEnumMap(), other.isSetEnumMap());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEnumMap()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.enumMap, other.enumMap);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
      lastComparison = java.lang.Boolean.compare(isSetIdsMap(), other.isSetIdsMap());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIdsMap()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.idsMap, other.idsMap);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
      lastComparison = java.lang.Boolean.compare(isSetStatusMap(), other.isSetStatusMap());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatusMap()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.statusMap, other.statusMap);
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
      java.lang.StringBuilder sb = new java.lang.StringBuilder("MapTest(");
    boolean first = true;

    sb.append("enumMap:");
    if (this.enumMap == null) {
      sb.append("null");
    } else {
      sb.append(this.enumMap);
    }
    first = false;
    if (isSetIdsMap()) {
      if (!first) sb.append(", ");
      sb.append("idsMap:");
      if (this.idsMap == null) {
        sb.append("null");
      } else {
        sb.append(this.idsMap);
      }
      first = false;
    }
    if (isSetStatusMap()) {
      if (!first) sb.append(", ");
      sb.append("statusMap:");
      if (this.statusMap == null) {
        sb.append("null");
      } else {
        sb.append(this.statusMap);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (enumMap == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'enumMap' was not present! Struct: " + toString());
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

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
    }

    private static class MapTestStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
        @Override
    public MapTestStandardScheme getScheme() {
      return new MapTestStandardScheme();
    }
    }

    private static class MapTestStandardScheme extends org.apache.thrift.scheme.StandardScheme<MapTest> {

        @Override
    public void read(org.apache.thrift.protocol.TProtocol iprot, MapTest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
          break;
        }
        switch (schemeField.id) {
          case 1: // ENUM_MAP
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map104 = iprot.readMapBegin();
                  struct.enumMap = new java.util.EnumMap<Enums, java.lang.String>(Enums.class);
                  @org.apache.thrift.annotation.Nullable Enums _key105;
                  @org.apache.thrift.annotation.Nullable java.lang.String _val106;
                for (int _i107 = 0; _i107 < _map104.size; ++_i107) {
                    _key105 = dev.vality.geck.serializer.domain.Enums.findByValue(iprot.readI32());
                  _val106 = iprot.readString();
                    if (_key105 != null) {
                        struct.enumMap.put(_key105, _val106);
                    }
                }
                iprot.readMapEnd();
              }
              struct.setEnumMapIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // IDS_MAP
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map108 = iprot.readMapBegin();
                  struct.idsMap = new java.util.HashMap<Ids, java.lang.String>(2 * _map108.size);
                  @org.apache.thrift.annotation.Nullable Ids _key109;
                  @org.apache.thrift.annotation.Nullable java.lang.String _val110;
                for (int _i111 = 0; _i111 < _map108.size; ++_i111)
                {
                  _key109 = new Ids();
                  _key109.read(iprot);
                  _val110 = iprot.readString();
                  struct.idsMap.put(_key109, _val110);
                }
                iprot.readMapEnd();
              }
              struct.setIdsMapIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // STATUS_MAP
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map112 = iprot.readMapBegin();
                  struct.statusMap = new java.util.HashMap<Status, java.lang.String>(2 * _map112.size);
                  @org.apache.thrift.annotation.Nullable Status _key113;
                  @org.apache.thrift.annotation.Nullable java.lang.String _val114;
                for (int _i115 = 0; _i115 < _map112.size; ++_i115)
                {
                  _key113 = new Status();
                  _key113.read(iprot);
                  _val114 = iprot.readString();
                  struct.statusMap.put(_key113, _val114);
                }
                iprot.readMapEnd();
              }
              struct.setStatusMapIsSet(true);
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
    public void write(org.apache.thrift.protocol.TProtocol oprot, MapTest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.enumMap != null) {
        oprot.writeFieldBegin(ENUM_MAP_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I32, org.apache.thrift.protocol.TType.STRING, struct.enumMap.size()));
            for (java.util.Map.Entry<Enums, java.lang.String> _iter116 : struct.enumMap.entrySet())
          {
            oprot.writeI32(_iter116.getKey().getValue());
            oprot.writeString(_iter116.getValue());
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.idsMap != null) {
        if (struct.isSetIdsMap()) {
          oprot.writeFieldBegin(IDS_MAP_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRUCT, org.apache.thrift.protocol.TType.STRING, struct.idsMap.size()));
              for (java.util.Map.Entry<Ids, java.lang.String> _iter117 : struct.idsMap.entrySet())
            {
              _iter117.getKey().write(oprot);
              oprot.writeString(_iter117.getValue());
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.statusMap != null) {
        if (struct.isSetStatusMap()) {
          oprot.writeFieldBegin(STATUS_MAP_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRUCT, org.apache.thrift.protocol.TType.STRING, struct.statusMap.size()));
              for (java.util.Map.Entry<Status, java.lang.String> _iter118 : struct.statusMap.entrySet())
            {
              _iter118.getKey().write(oprot);
              oprot.writeString(_iter118.getValue());
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

    }

    private static class MapTestTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
        @Override
    public MapTestTupleScheme getScheme() {
      return new MapTestTupleScheme();
    }
    }

    private static class MapTestTupleScheme extends org.apache.thrift.scheme.TupleScheme<MapTest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MapTest struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        oprot.writeI32(struct.enumMap.size());
          for (java.util.Map.Entry<Enums, java.lang.String> _iter119 : struct.enumMap.entrySet())
        {
          oprot.writeI32(_iter119.getKey().getValue());
          oprot.writeString(_iter119.getValue());
        }
      }
        java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetIdsMap()) {
        optionals.set(0);
      }
      if (struct.isSetStatusMap()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetIdsMap()) {
        {
          oprot.writeI32(struct.idsMap.size());
            for (java.util.Map.Entry<Ids, java.lang.String> _iter120 : struct.idsMap.entrySet())
          {
            _iter120.getKey().write(oprot);
            oprot.writeString(_iter120.getValue());
          }
        }
      }
      if (struct.isSetStatusMap()) {
        {
          oprot.writeI32(struct.statusMap.size());
            for (java.util.Map.Entry<Status, java.lang.String> _iter121 : struct.statusMap.entrySet())
          {
            _iter121.getKey().write(oprot);
            oprot.writeString(_iter121.getValue());
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MapTest struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
        {
            org.apache.thrift.protocol.TMap _map122 = iprot.readMapBegin(org.apache.thrift.protocol.TType.I32, org.apache.thrift.protocol.TType.STRING);
            struct.enumMap = new java.util.EnumMap<Enums, java.lang.String>(Enums.class);
            @org.apache.thrift.annotation.Nullable Enums _key123;
            @org.apache.thrift.annotation.Nullable java.lang.String _val124;
        for (int _i125 = 0; _i125 < _map122.size; ++_i125) {
            _key123 = dev.vality.geck.serializer.domain.Enums.findByValue(iprot.readI32());
          _val124 = iprot.readString();
            if (_key123 != null) {
                struct.enumMap.put(_key123, _val124);
            }
        }
      }
      struct.setEnumMapIsSet(true);
        java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
          {
              org.apache.thrift.protocol.TMap _map126 = iprot.readMapBegin(org.apache.thrift.protocol.TType.STRUCT, org.apache.thrift.protocol.TType.STRING);
              struct.idsMap = new java.util.HashMap<Ids, java.lang.String>(2 * _map126.size);
              @org.apache.thrift.annotation.Nullable Ids _key127;
              @org.apache.thrift.annotation.Nullable java.lang.String _val128;
          for (int _i129 = 0; _i129 < _map126.size; ++_i129)
          {
            _key127 = new Ids();
            _key127.read(iprot);
            _val128 = iprot.readString();
            struct.idsMap.put(_key127, _val128);
          }
        }
        struct.setIdsMapIsSet(true);
      }
      if (incoming.get(1)) {
          {
              org.apache.thrift.protocol.TMap _map130 = iprot.readMapBegin(org.apache.thrift.protocol.TType.STRUCT, org.apache.thrift.protocol.TType.STRING);
              struct.statusMap = new java.util.HashMap<Status, java.lang.String>(2 * _map130.size);
              @org.apache.thrift.annotation.Nullable Status _key131;
              @org.apache.thrift.annotation.Nullable java.lang.String _val132;
          for (int _i133 = 0; _i133 < _map130.size; ++_i133)
          {
            _key131 = new Status();
            _key131.read(iprot);
            _val132 = iprot.readString();
            struct.statusMap.put(_key131, _val132);
          }
        }
        struct.setStatusMapIsSet(true);
      }
    }
    }

    private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
        return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

