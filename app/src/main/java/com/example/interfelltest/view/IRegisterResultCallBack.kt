package com.example.interfelltest.view

import com.example.interfelltest.dblogic.entity.Contravention

interface IRegisterResultCallBack {
    fun onRegister()

    fun LoadDetail(items: List<Contravention>, item: Contravention)
}