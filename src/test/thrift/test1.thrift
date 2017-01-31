namespace java com.rbkmoney.kebab.test

struct TestObject {
    1: required Ids ids
    2: required double value
    3: optional string description
    4: optional binary data
    5: required list<i32> numbers
    6: required Status status
    7: required list<set<string>> fuck
    8: optional map<string, i32> maps
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

struct Ids {
    1: required i8 micro_id
    2: required i16 mini_id
    3: required i32 id
    4: required i64 big_id
}