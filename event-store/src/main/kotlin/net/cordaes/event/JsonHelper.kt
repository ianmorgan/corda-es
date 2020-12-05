package net.cordaes.eventStore


import net.cordaes.json.JSONArray
import net.cordaes.json.JSONObject
import java.util.ArrayList
import java.util.HashMap


object JsonHelper {

    /**
     * Take a Json string and convert to normal Java Map by
     * examining each key in the corresponding JSONObject
     *
     * @param json The json string
     * @return a Java Map with same content as the original JSON
     */

    fun jsonToMap(json: String): Map<String, Any> {
        return jsonToMap(JSONObject(json))
    }

    /**
     * Take a JSONObject and convert to normal Java Map by
     * examining each key.
     *
     * @param json The JSONObject
     * @return a Java Map with same content as the original JSONObject
     */
    fun jsonToMap(json: JSONObject): Map<String, Any> {
        val result = HashMap<String,Any>(json.keySet().size)

        for (key in json.keySet()) {
            val o = json.get(key)
            if (o is JSONObject) {
                result.put(key, jsonToMap(o))
            } else if (o is JSONArray) {
                result.put(key, jsonToList(o))
            } else {
                result.put(key, o)
            }
        }
        return result
    }

    /**
     * Take a String holding an array an convert to a normal Java List , examining each
     * element in the corresponding JSONArray individually
     *
     * @param jsonArray The string holding an array in JSON syntax
     * @return A Java List with the same content as the original array
     */
    fun jsonToList(jsonArray: String): List<Any> {
        return jsonToList(JSONArray(jsonArray))
    }

    /**
     * Take a JSONArray an convert to a normal Java List , examining each
     * element in the array individually
     *
     * @param array The JSONArray
     * @return A Java List with the same content as the original JSONArray
     */
    fun jsonToList(array: JSONArray): List<Any> {
        val result = ArrayList<Any>(array.length())

        for (i in 0 until array.length()) {
            val o = array.get(i)
            if (o is JSONObject) {
                result.add(i, jsonToMap(o))
            } else if (o is JSONArray) {
                result.add(i, jsonToList(o))
            } else {
                result.add(i, o)
            }
        }
        return result
    }
}