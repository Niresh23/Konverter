package com.nik.konverter.model

open class SingletoneHolder<out T: Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val checkInstace = instance
        if(checkInstace != null) {
            return checkInstace
        }

        return synchronized(this) {
            val checkInstaceAgain = instance
            if(checkInstaceAgain != null) {
                checkInstaceAgain
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}