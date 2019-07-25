package com.maryang.fastrxjava.util

import com.maryang.fastrxjava.entity.GithubRepo

object Functional {
    fun sumImperative(arr: List<Int>): Int {
        var result = 0
        arr.forEach {
            result += it
        }
        return result
    }

    fun sumFunctional(arr: List<Int>): Int =
        arr.reduce { acc, i -> acc + arr[i] }

    //함수형
    fun starRepoNames(repos: List<GithubRepo>): List<String> =
        repos.filter { it.stargazersCount > 15 }
            .map { it.name }
    // tips : .run 하면 반환 타입을 알 수 있다

    //레포지토리들 중에서 스타ㅏ 많은 것 (15개ㅐ 이상) 들의 이름과 스타 개수를 가져와보자 by 명령형
    fun starRepoNamesByOrder(repos: List<GithubRepo>): List<Pair<String, Int>> {

        val starRepos: MutableList<GithubRepo> = mutableListOf()
        repos.forEach{
            if (it.stargazersCount >= 15) starRepos.add(it)
        }

        val pairs: MutableList<Pair<String, Int>> = mutableListOf()
        starRepos.forEach {
            pairs.add(Pair(it.name, it.stargazersCount))
        }

        return pairs

    }

    fun findRepo(repos: List<GithubRepo>, search: String): List<GithubRepo> =
        repos.filter {
            it.name.contains(search)
                || it.description?.contains(search) ?: false
                || it.owner.userName.contains(search)
        }
}
