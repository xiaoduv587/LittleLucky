package com.xiaodu.littlelucky.algorithm

import java.lang.IllegalArgumentException

/**
 * 简单-两数之和
 *  Given nums = [2, 7, 11, 15], target = 9,
 *   Because nums[0] + nums[1] = 2 + 7 = 9,
 *   return [0, 1].
 */
class `01_TwoSum` {

    /**
     * 思路
     * 两层for循环
     */
    fun test(nums: IntArray, target: Int): IntArray {
        for (i in nums.indices) {
            for (j in i+1 until  nums.size ) {
                if (nums[j] == target - nums[i]) {
                   return intArrayOf(i,j)
                }
            }
        }
        throw  IllegalArgumentException("no two sum solution")
    }


}

fun main() {
    val sum = `01_TwoSum`()
    println(sum.test(intArrayOf(3,2,4), 6).toString())
}