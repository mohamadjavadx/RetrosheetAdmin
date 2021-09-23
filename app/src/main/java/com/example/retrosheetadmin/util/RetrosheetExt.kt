package com.example.retrosheetadmin.util

import com.example.retrosheetadmin.model.CommonItem

fun <T : CommonItem> List<T>.removeInvalidItems(): List<T> {
    val set = HashSet<String>()
    val list = ArrayList<T>()
    for (e in this.asReversed()) {
        if (set.add(e.id) && !e.isArchived)
            list.add(e)
    }
    return list.reversed()
}