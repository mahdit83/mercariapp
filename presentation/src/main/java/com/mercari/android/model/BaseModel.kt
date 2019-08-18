package com.mercari.android.model

import java.io.Serializable

abstract class BaseModel: Serializable {

    abstract fun getViewType(): Int
}