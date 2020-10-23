package com.test.linetwdemo.model


class DataResource<T>(val status: Status, val data: T?, val message: String) {
    companion object {
        fun <T> success(data: T): DataResource<T> {
            return DataResource(Status.SUCCESS, data, "")
        }

        fun <T> error(data: T, msg: String): DataResource<T> {
            return DataResource(Status.ERROR, data, msg)
        }
        fun <T> loading(data: T): DataResource<T> {
            return DataResource(Status.LOADING, data, "")
        }
    }
}
