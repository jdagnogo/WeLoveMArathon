package com.jdagnogo.welovemarathon.home.data.run

import com.jdagnogo.welovemarathon.home.domain.MarathonRun
import javax.inject.Inject

class RunMapper @Inject constructor() {
    fun toRuns(runEntities: List<RunEntity>): List<MarathonRun> {
        return runEntities.map {
            it.toMarathonRun()
        }
    }

    fun toRunsEntities(runs: List<MarathonRun>): List<RunEntity> {
        return runs.map {
            it.toRunEntity()
        }
    }
}
