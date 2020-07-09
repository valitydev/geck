namespace java com.rbkmoney.geck.serializer.domain

struct TestObject {
    1: required Ids ids
    2: required double value
    3: optional string description
    4: optional binary data
    5: required list<i32> numbers
    6: required Status status
    7: required list<set<string>> set_in_list
    8: optional map<string, i32> maps
    9: optional list<Status> statuses
    10: required bool active
    11: required map<map<set<Enums>, Status>, map<Status, set<Ids>>> kebabMap
    12: required string another_string
}

struct SetTest {
    1: required set<Ids> idsSet
    2: optional set<Status> statusSet
}

struct MapTest {
    1: required map<Enums, string> enumMap
    2: optional map<Ids, string> idsMap
    3: optional map<Status, string> statusMap
}

struct HandlerTest {
    1: required string one
    2: required i32 two
    3: required list<string> three
}

struct TUnionTest {
    1: required Status status
}

struct BinaryTest {
    1: required binary data
    2: required list<binary> dataInList
    3: required set<binary> dataInSet
    4: required map<binary, binary> dataInMap
}

struct MapListTest {
    1: list<string> listMapString
}

union Status {
    1: Ok ok
    2: Fail fail
    3: Unknown unknown
}

struct Unknown {
    1: required string description
}

struct Fail {
   1: optional set<string> reasons
}

struct Ok {

}

enum Enums {
    TEST1, TEST2, TEST3
}

struct Ids {
    1: required i8 micro_id
    2: required i16 mini_id
    3: required i32 id
    4: required i64 big_id
}

struct Ids2 {
    1: required i8 another_micro_id
    2: required i16 another_mini_id
    3: required i32 another_id
    4: required i64 another_big_id
}

struct Ids3 {
    4: required i8 another_micro_id
    3: required i16 another_mini_id
    2: required i32 another_id
    1: required i64 another_big_id
}

struct FilterObject {
    1: required string sname1
    2: FilterUnion uname1;
    3: required string sname2;
}

union FilterUnion {
    1: FilterListObject list_object_1
}

struct FilterListObject {
    1: list<string> strings
}

struct FilterMapObject {
    1: required map<string, Unknown> map1;
}
