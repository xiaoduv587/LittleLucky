package com.xiaodu.littlelucky

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import com.blankj.utilcode.util.LogUtils
import org.hamcrest.CoreMatchers.`is`
import org.junit.Test
import org.junit.runner.RunWith
import cn.bmob.v3.listener.UpdateListener
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull


/**
 * author : 杜志斌
 * date : 2019/9/24 9:53
 * verson :
 * description :
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class PersonTest {

    private var mObjectId = ""

    @Test
    fun should_add() {
        val person = Person()
        person.name = "lucky"
        person.address = "北京海淀"
        person.save(object : SaveListener<String>() {
            override fun done(objectId: String?, e: BmobException?) {
                assertNull(e)
                assertNotNull(objectId)
                if (e == null) {
                    LogUtils.e("添加数据成功，返回objectId为：$objectId")
                    this@PersonTest.mObjectId = objectId!!
                } else {
                    LogUtils.e("创建数据失败：" + e.message)
                }
            }
        })
    }

    @Test
    fun should_select() {
        //查找Person表里面id为6b6c11c537的数据
        val bmobQuery = BmobQuery<Person>()
        bmobQuery.getObject(mObjectId, object : QueryListener<Person>() {
            override fun done(p0: Person?, p1: BmobException?) {
                if (p1 == null) {
                    LogUtils.e("查询成功")
                    assertThat(p0?.name, `is`("lucky"))
                } else {
                    LogUtils.e("查询失败：" + p1?.message)
                }
            }

        })
    }

    @Test
    fun should_update() {
        //更新Person表里面id为c64e0ef7ad的数据，address内容更新为“北京朝阳”
        val p2 = Person()
        p2.address = "北京朝阳"
        p2.update(mObjectId, object : UpdateListener() {
            override fun done(e: BmobException?) {
                assertThat(p2.address, `is`("北京朝阳"))
                if (e == null) {
                    LogUtils.e("更新成功:" + p2.updatedAt)
                } else {
                    LogUtils.e("更新失败:" + e.message)
                }
            }

        })
    }



    @Test
    fun should_delect() {
        val p2 = Person()
        p2.objectId = mObjectId
        p2.delete(object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    LogUtils.e("删除成功:" + p2.updatedAt)
                } else {
                    LogUtils.e("删除失败：" + e.message)
                }
            }

        })
    }

}