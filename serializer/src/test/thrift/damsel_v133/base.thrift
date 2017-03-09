/*
 * Базовые, наиболее общие определения
 */

namespace java com.rbkmoney.damsel_v133.base

/** Идентификатор */
typedef string ID

/** Пространство имён */
typedef string Namespace

/** Идентификатор некоторого события */
typedef i64 EventID

/** Непрозрачный для участника общения набор данных */
typedef binary Opaque

/** Набор данных, подлежащий интерпретации согласно типу содержимого. */
struct Content {
    /** Тип содержимого, согласно [RFC2046](https://www.ietf.org/rfc/rfc2046) */
    1: required string type
    2: required binary data
}

/**
 * Отметка во времени согласно RFC 3339.
 *
 * Строка должна содержать дату и время в UTC в следующем формате:
 * `2016-03-22T06:12:27Z`.
 */
typedef string Timestamp

/** Отображение из строки в строку */
typedef map<string, string> StringMap

/** Рациональное число. */
struct Rational {
    1: required i64 p
    2: required i64 q
}

/** Отрезок времени в секундах */
typedef i32 Timeout

/** Значение ассоциации */
typedef string Tag

/** Критерий остановки таймера */
union Timer {
    /** Отрезок времени, после истечения которого таймер остановится */
    1: Timeout timeout
    /** Отметка во времени, при пересечении которой таймер остановится */
    2: Timestamp deadline
}

/** Общий успех */
struct Ok {
}

/** Общая ошибка */
struct Error {
    /** Уникальный признак ошибки, пригодный для обработки машиной */
    1: required string code
    /** Описание ошибки, пригодное для восприятия человеком */
    2: optional string description
}

/** Общее исключение */
exception Failure {
    /** Ошибка, которая привела к возникновению исключения */
    //Нельзя назвать поле `error` из-за особенностей генерации thrift Go - приводит к одинаковым именам для функции и поля структуры
    1: required Error e
}

/**
 * Исключение, сигнализирующее о возникновение транзиентной проблемы, которая с высокой
 * вероятностью не повторится при последующих попытках
 */
exception TryLater {
    /** Транзиентная ошибка, которая привела к возникновению исключения */
    1: required Error e
}

/**
 * Исключение, сигнализирующее о непригодных с точки зрения бизнес-логики входных данных
 */
exception InvalidRequest {
    /** Список пригодных для восприятия человеком ошибок во входных данных */
    1: required list<string> errors
}

/** Исключение, сигнализирующее об отсутствии объекта или процесса */
exception NotFound {}
