package com.jdagnogo.welovemarathon.run.data

import com.jdagnogo.welovemarathon.run.domain.Run
import javax.inject.Inject

class RunMapper @Inject constructor() {
    fun toRuns(runEntities: List<RunEntity>): List<Run> {
        return runEntities.map {
            it.toMarathonRun()
        }
    }

    fun toRunsEntities(runs: List<Run>): List<RunEntity> {
        return runs.map {
            it.toRunEntity()
        }
    }
}
