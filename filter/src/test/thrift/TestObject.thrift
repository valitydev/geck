namespace java com.rbkmoney.geck.filter.test

struct TestObject {
    1: required i64 id
    2: required Ids other_ids
    3: required Status status
    4: required Type type
    5: optional slist words
    6: optional list<i64> numbers
    7: optional list<Ids> list_ids
    8: optional list<list<Status>> statuses
}

struct Ids {
    1: required i8 micro_id
    2: required i16 mini_id
    3: required i32 id
}

union Status {

    1: Ok ok_status
    2: Fail fail_status
    3: Unknown unknown_status

}

struct Ok {

}

struct Fail {
    1: required byte code
    2: optional string description
}

struct Unknown {
    1: required UnknownType unknown
    2: optional string description
}

union UnknownType {
    1: slist result
    2: list<Type> resultTypes
    3: double value
}

enum Type {
    BLACK = 1,
    RED = 2,
    GREEN = 3
}
