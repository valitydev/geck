namespace java com.rbkmoney.geck.serializer.test

struct TestObject {
    1: required Ids ids
    2: required double value
    3: optional string description
    4: optional binary data
    5: required list<i32> numbers
    6: required Status status
    7: required list<set<string>> fuck
    8: optional map<string, i32> maps
    9: optional list<Status> statuses
    10: required bool active
    11: required map<map<set<Kek>, Status>, map<Status, set<Ids>>> kebabMap
}

struct SetTest {
    1: required set<Ids> idsSet
    2: required set<Status> statusSet
}

struct MapTest {
    1: required map<Kek, string> enumMap
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

enum Kek {
    TEST1, TEST2, TEST3
}

struct Ids {
    1: required i8 micro_id
    2: required i16 mini_id
    3: required i32 id
    4: required i64 big_id
}