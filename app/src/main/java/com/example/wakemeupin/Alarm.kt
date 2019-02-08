package com.example.wakemeupin

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Alarm (@PrimaryKey val id: int)