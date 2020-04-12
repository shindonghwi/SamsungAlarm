package dh.shin.samsungalarm.Util

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context, prefName : String) {

    private var prefs : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    private var mContext : Context = context
    private var mPrefName: String = prefName


    // 쉐어드 프리퍼런스 초기화
    init {
        prefs = getPreference(mContext, mPrefName)
    }

    fun getPreference(context: Context, prefName: String): SharedPreferences{
        prefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        return prefs
    }

    // 데이터 넣기
    fun putData(pair: Pair<String, Any>) {

        // 키값
        val key = pair.first

        // 들어오는 타입에 맞춰 value값이 저장된다.
        when (val value = pair.second) {
            is String -> editor.putString(key,value)
            is Int -> editor.putInt(key,value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            else -> error("data save fail !!") // 데이터 저장 실패
        }
        editor.commit()
    }

    // 데이터 가져오기
    fun getData(key: String) : String{
        return prefs.getString(key,"").toString()
    }

}