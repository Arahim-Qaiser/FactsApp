package com.example.funfacts

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp // triggers Hilt's code generation (hook)
class FunFactsApplication : Application()
