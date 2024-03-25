package com.example.lastproject.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.lastproject.data.local.entity.GitEntity
import com.example.lastproject.data.local.room.GitDao
import com.example.lastproject.data.local.room.GitDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.Result

class GitRepository (application: Application){

    private val mGitDao : GitDao
    private val executorsService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = GitDatabase.getDatabase(application)
        mGitDao = db.gitDao()
    }

    private val result = MediatorLiveData<Result<List<GitEntity>>>()

    fun getGitAll() : LiveData<List<GitEntity>> = mGitDao.getAll()

    fun insert(user: GitEntity) {
        executorsService.execute{mGitDao.insert(user)}
    }

    fun delete(user: GitEntity) {
        executorsService.execute{mGitDao.delete(user)}
    }

    fun getFavoriteUser(user: String): LiveData<GitEntity> {
        return mGitDao.getFavoriteUserByUsername(user)
    }
}