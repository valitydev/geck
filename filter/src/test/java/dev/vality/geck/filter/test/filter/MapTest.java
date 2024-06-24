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
public class MapTest implements org.apache.thrift.TBase<MapTest, MapTest._Fields>, java.io.Serializable, Cloneable, Comparable<MapTest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MapTest");

  private static final org.apache.thrift.protocol.TField LST_KEY_MAP_FIELD_DESC = new org.apache.thrift.protocol.TField("lst_key_map", org.apache.thrift.protocol.TType.MAP, (short) 1);

    private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new MapTestStandardSchemeFactory();
    private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new MapTestTupleSchemeFactory();

    public @org.apache.thrift.annotation.Nullable java.util.Map<java.util.List<IData>, Integer> lst_key_map; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    LST_KEY_MAP((short)1, "lst_key_map");

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
        case 1: // LST_KEY_MAP
          return LST_KEY_MAP;
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
  private static final _Fields optionals[] = {_Fields.LST_KEY_MAP};
    public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
      java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.LST_KEY_MAP, new org.apache.thrift.meta_data.FieldMetaData("lst_key_map", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
                new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, IData.class)), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32))));
      metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MapTest.class, metaDataMap);
  }

  public MapTest() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MapTest(MapTest other) {
    if (other.isSetLstKeyMap()) {
        java.util.Map<java.util.List<IData>, Integer> __this__lst_key_map = new java.util.HashMap<java.util.List<IData>, Integer>(other.lst_key_map.size());
        for (java.util.Map.Entry<java.util.List<IData>, Integer> other_element : other.lst_key_map.entrySet()) {

            java.util.List<IData> other_element_key = other_element.getKey();
        Integer other_element_value = other_element.getValue();

            java.util.List<IData> __this__lst_key_map_copy_key = new java.util.ArrayList<IData>(other_element_key.size());
        for (IData other_element_key_element : other_element_key) {
          __this__lst_key_map_copy_key.add(new IData(other_element_key_element));
        }

        Integer __this__lst_key_map_copy_value = other_element_value;

        __this__lst_key_map.put(__this__lst_key_map_copy_key, __this__lst_key_map_copy_value);
      }
      this.lst_key_map = __this__lst_key_map;
    }
  }

    @Override
  public MapTest deepCopy() {
    return new MapTest(this);
  }

  @Override
  public void clear() {
    this.lst_key_map = null;
  }

  public int getLstKeyMapSize() {
    return (this.lst_key_map == null) ? 0 : this.lst_key_map.size();
  }

    public void putToLstKeyMap(java.util.List<IData> key, int val) {
    if (this.lst_key_map == null) {
        this.lst_key_map = new java.util.HashMap<java.util.List<IData>, Integer>();
    }
    this.lst_key_map.put(key, val);
    }

    @org.apache.thrift.annotation.Nullable
    public java.util.Map<java.util.List<IData>, Integer> getLstKeyMap() {
    return this.lst_key_map;
    }

    public MapTest setLstKeyMap(@org.apache.thrift.annotation.Nullable java.util.Map<java.util.List<IData>, Integer> lst_key_map) {
    this.lst_key_map = lst_key_map;
    return this;
  }

  public void unsetLstKeyMap() {
    this.lst_key_map = null;
  }

  /** Returns true if field lst_key_map is set (has been assigned a value) and false otherwise */
  public boolean isSetLstKeyMap() {
    return this.lst_key_map != null;
  }

  public void setLstKeyMapIsSet(boolean value) {
    if (!value) {
      this.lst_key_map = null;
    }
  }

    @Override
    public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable Object value) {
    switch (field) {
    case LST_KEY_MAP:
      if (value == null) {
        unsetLstKeyMap();
      } else {
          setLstKeyMap((java.util.Map<java.util.List<IData>, Integer>) value);
      }
      break;

    }
    }

    @org.apache.thrift.annotation.Nullable
    @Override
  public Object getFieldValue(_Fields field) {
    switch (field) {
    case LST_KEY_MAP:
      return getLstKeyMap();

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
    case LST_KEY_MAP:
      return isSetLstKeyMap();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that instanceof MapTest)
      return this.equals((MapTest)that);
    return false;
  }

  public boolean equals(MapTest that) {
    if (that == null)
      return false;
      if (this == that)
          return true;

    boolean this_present_lst_key_map = true && this.isSetLstKeyMap();
    boolean that_present_lst_key_map = true && that.isSetLstKeyMap();
    if (this_present_lst_key_map || that_present_lst_key_map) {
      if (!(this_present_lst_key_map && that_present_lst_key_map))
        return false;
      if (!this.lst_key_map.equals(that.lst_key_map))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetLstKeyMap()) ? 131071 : 524287);
    if (isSetLstKeyMap())
      hashCode = hashCode * 8191 + lst_key_map.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(MapTest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

      lastComparison = Boolean.compare(isSetLstKeyMap(), other.isSetLstKeyMap());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLstKeyMap()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lst_key_map, other.lst_key_map);
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
    StringBuilder sb = new StringBuilder("MapTest(");
    boolean first = true;

    if (isSetLstKeyMap()) {
      sb.append("lst_key_map:");
      if (this.lst_key_map == null) {
        sb.append("null");
      } else {
        sb.append(this.lst_key_map);
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
          case 1: // LST_KEY_MAP
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map44 = iprot.readMapBegin();
                  struct.lst_key_map = new java.util.HashMap<java.util.List<IData>, Integer>(2 * _map44.size);
                  @org.apache.thrift.annotation.Nullable java.util.List<IData> _key45;
                int _val46;
                for (int _i47 = 0; _i47 < _map44.size; ++_i47)
                {
                  {
                    org.apache.thrift.protocol.TList _list48 = iprot.readListBegin();
                      _key45 = new java.util.ArrayList<IData>(_list48.size);
                      @org.apache.thrift.annotation.Nullable IData _elem49;
                    for (int _i50 = 0; _i50 < _list48.size; ++_i50)
                    {
                      _elem49 = new IData();
                      _elem49.read(iprot);
                      _key45.add(_elem49);
                    }
                    iprot.readListEnd();
                  }
                  _val46 = iprot.readI32();
                  struct.lst_key_map.put(_key45, _val46);
                }
                iprot.readMapEnd();
              }
              struct.setLstKeyMapIsSet(true);
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
      if (struct.lst_key_map != null) {
        if (struct.isSetLstKeyMap()) {
          oprot.writeFieldBegin(LST_KEY_MAP_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.LIST, org.apache.thrift.protocol.TType.I32, struct.lst_key_map.size()));
              for (java.util.Map.Entry<java.util.List<IData>, Integer> _iter51 : struct.lst_key_map.entrySet())
            {
              {
                oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, _iter51.getKey().size()));
                for (IData _iter52 : _iter51.getKey())
                {
                  _iter52.write(oprot);
                }
                oprot.writeListEnd();
              }
              oprot.writeI32(_iter51.getValue());
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
        java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetLstKeyMap()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetLstKeyMap()) {
        {
          oprot.writeI32(struct.lst_key_map.size());
            for (java.util.Map.Entry<java.util.List<IData>, Integer> _iter53 : struct.lst_key_map.entrySet())
          {
            {
              oprot.writeI32(_iter53.getKey().size());
              for (IData _iter54 : _iter53.getKey())
              {
                _iter54.write(oprot);
              }
            }
            oprot.writeI32(_iter53.getValue());
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MapTest struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
          {
              org.apache.thrift.protocol.TMap _map55 = iprot.readMapBegin(org.apache.thrift.protocol.TType.LIST, org.apache.thrift.protocol.TType.I32);
              struct.lst_key_map = new java.util.HashMap<java.util.List<IData>, Integer>(2 * _map55.size);
              @org.apache.thrift.annotation.Nullable java.util.List<IData> _key56;
          int _val57;
          for (int _i58 = 0; _i58 < _map55.size; ++_i58)
          {
              {
                  org.apache.thrift.protocol.TList _list59 = iprot.readListBegin(org.apache.thrift.protocol.TType.STRUCT);
                  _key56 = new java.util.ArrayList<IData>(_list59.size);
                  @org.apache.thrift.annotation.Nullable IData _elem60;
              for (int _i61 = 0; _i61 < _list59.size; ++_i61)
              {
                _elem60 = new IData();
                _elem60.read(iprot);
                _key56.add(_elem60);
              }
            }
            _val57 = iprot.readI32();
            struct.lst_key_map.put(_key56, _val57);
          }
        }
        struct.setLstKeyMapIsSet(true);
      }
    }
    }

    private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
        return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

