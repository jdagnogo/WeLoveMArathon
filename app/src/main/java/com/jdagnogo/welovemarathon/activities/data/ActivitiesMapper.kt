package com.jdagnogo.welovemarathon.activities.data

import com.jdagnogo.welovemarathon.activities.domain.Activities
import com.jdagnogo.welovemarathon.activities.domain.ActivitiesCategory
import com.jdagnogo.welovemarathon.activities.domain.ActivitiesTag
import javax.inject.Inject

class ActivitiesMapper @Inject constructor() {
    fun toDomain(activitiesEntities: List<ActivitiesEntity>): List<Activities> {
        return activitiesEntities.map {
            it.toActivities()
        }
    }

    fun toEntities(activitiess: List<Activities>): List<ActivitiesEntity> {
        return activitiess.map {
            it.toActivitiesEntity()
        }
    }

    fun toDomainCategories(entities: List<ActivitiesCategoryEntity>): List<ActivitiesCategory> {
        return entities.map {
            it.toDomainCategory()
        }
    }

    fun toEntitiesCategories(categories: List<ActivitiesCategory>): List<ActivitiesCategoryEntity> {
        return categories.map {
            it.toCategoryEntity()
        }
    }

    fun toDomainTag(entities: List<ActivitiesTagEntity>): List<ActivitiesTag> {
        return entities.map {
            it.toDomainCategory()
        }
    }

    fun toEntitiesTag(tag: List<ActivitiesTag>): List<ActivitiesTagEntity> {
        return tag.map {
            it.toEntity()
        }
    }
}
