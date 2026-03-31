package com.example.club

import com.example.club.app.USER
import org.junit.Test
import org.junit.Assert.*
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * 反射实际应用场景测试
 * 演示反射在真实开发中的使用场景
 */
class ReflectionPracticalTest {

    @Test
    fun testObjectToStringWithReflection() {
        // 场景 1: 使用反射实现通用的对象转字符串方法
        val user = USER()
        
        val result = objectToString(user)
        println("\n通用 toString 结果:\n$result")
        
        assertNotNull("应该返回字符串", result)
        assertTrue("应该包含类名", result.contains("USER"))
    }

    @Test
    fun testCopyPropertiesWithReflection() {
        // 场景 2: 使用反射复制对象属性
        val source = USER()
        val target = USER()
        
        copyProperties(source, target)
        
        println("\n✓ 属性复制完成")
    }

    @Test
    fun testValidateObjectWithReflection() {
        // 场景 3: 使用反射验证对象状态
        val user = USER()
        
        val isValid = validateObject(user)
        
        println("\n对象验证结果：${if (isValid) "有效" else "无效"}")
        assertTrue("对象应该通过验证", isValid)
    }

    @Test
    fun testGetObjectInfoWithReflection() {
        // 场景 4: 使用反射获取对象详细信息（用于日志/调试）
        val user = USER()
        
        val info = getObjectDebugInfo(user)
        println("\n$info")
        
        assertTrue("信息应该包含类名", info.contains("USER"))
    }

    @Test
    fun testCompareObjectsWithReflection() {
        // 场景 5: 使用反射比较两个对象是否相等
        val user1 = USER()
        val user2 = USER()
        
        val areEqual = compareObjects(user1, user2)
        
        println("\n对象比较结果：${if (areEqual) "相等" else "不相等"}")
        assertTrue("两个空对象应该相等", areEqual)
    }

    @Test
    fun testSerializeToJsonWithReflection() {
        // 场景 6: 使用反射将对象序列化为 JSON 格式
        val user = USER()
        
        val json = serializeToSimpleJson(user)
        println("\n序列化 JSON:\n$json")
        
        assertTrue("JSON 应该包含花括号", json.contains("{") && json.contains("}"))
    }

    @Test
    fun testFindMethodByName() {
        // 场景 7: 使用反射动态查找并调用方法
        val clazz = USER::class.java
        
        // 查找 toString 方法
        val method = findMethod(clazz, "toString")
        
        assertNotNull("应该找到 toString 方法", method)
        println("\n找到方法：${method?.name}")
    }

    @Test
    fun testGetAllFieldsIncludingSuperclass() {
        // 场景 8: 获取包括父类在内的所有字段
        val clazz = USER::class.java
        
        val allFields = getAllFields(clazz)
        
        println("\n所有字段（包括父类）：")
        allFields.forEach { field ->
            println("  - ${field.declaringClass.simpleName}.${field.name}")
        }
        
        // 这个测试只是演示功能，不强制要求有字段
        println("共找到 ${allFields.size} 个字段")
    }

    // ========== 工具方法实现 ==========

    /**
     * 通用的对象转字符串方法
     */
    private fun objectToString(obj: Any): String {
        val clazz = obj.javaClass
        val sb = StringBuilder("${clazz.simpleName}(\n")
        
        clazz.declaredFields.forEach { field ->
            field.isAccessible = true
            val value = try {
                field.get(obj) ?: "null"
            } catch (e: Exception) {
                "<error: ${e.message}>"
            }
            sb.append("  ${field.name} = $value\n")
        }
        
        sb.append(")")
        return sb.toString()
    }

    /**
     * 复制对象属性
     */
    private fun copyProperties(source: Any, target: Any) {
        val sourceClass = source.javaClass
        val targetClass = target.javaClass
        
        sourceClass.declaredFields.forEach { sourceField ->
            try {
                val targetField = targetClass.getDeclaredField(sourceField.name)
                if (targetField != null && sourceField.type == targetField.type) {
                    sourceField.isAccessible = true
                    targetField.isAccessible = true
                    val value = sourceField.get(source)
                    targetField.set(target, value)
                    println("  复制 ${sourceField.name}: $value")
                }
            } catch (e: NoSuchFieldException) {
                // 目标类没有这个字段，跳过
            }
        }
    }

    /**
     * 验证对象是否有效
     */
    private fun validateObject(obj: Any): Boolean {
        return try {
            obj.javaClass.declaredFields.all { field ->
                field.isAccessible = true
                val value = field.get(obj)
                
                // 检查必填字段（这里假设非 null 即为有效）
                value != null
            }
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 获取对象调试信息
     */
    private fun getObjectDebugInfo(obj: Any): String {
        val clazz = obj.javaClass
        val sb = StringBuilder()
        
        sb.appendLine("========== 对象调试信息 ==========")
        sb.appendLine("类名：${clazz.name}")
        sb.appendLine("包名：${clazz.`package`?.name ?: "default"}")
        sb.appendLine("修饰符：${java.lang.reflect.Modifier.toString(clazz.modifiers)}")
        sb.appendLine()
        sb.appendLine("字段信息:")
        
        clazz.declaredFields.forEach { field ->
            field.isAccessible = true
            val value = try {
                field.get(obj)
            } catch (e: Exception) {
                "无法访问"
            }
            sb.appendLine("  - ${field.name}: ${field.type.simpleName} = $value")
        }
        
        sb.appendLine("==============================")
        return sb.toString()
    }

    /**
     * 比较两个对象是否相等
     */
    private fun compareObjects(obj1: Any, obj2: Any): Boolean {
        if (obj1.javaClass != obj2.javaClass) {
            return false
        }
        
        return obj1.javaClass.declaredFields.all { field ->
            field.isAccessible = true
            val value1 = field.get(obj1)
            val value2 = field.get(obj2)
            
            if (value1 == null && value2 == null) {
                true
            } else {
                value1 == value2
            }
        }
    }

    /**
     * 简单的 JSON 序列化
     */
    private fun serializeToSimpleJson(obj: Any): String {
        val clazz = obj.javaClass
        val sb = StringBuilder("{\n")
        
        val fields = clazz.declaredFields
        fields.forEachIndexed { index, field ->
            field.isAccessible = true
            val value = try {
                field.get(obj)
            } catch (e: Exception) {
                null
            }
            
            val jsonValue = when (value) {
                null -> "null"
                is String -> "\"$value\""
                is Number -> value.toString()
                is Boolean -> value.toString()
                else -> "\"${value.toString()}\""
            }
            
            sb.append("  \"${field.name}\": $jsonValue")
            if (index < fields.size - 1) {
                sb.append(",")
            }
            sb.appendLine()
        }
        
        sb.append("}")
        return sb.toString()
    }

    /**
     * 查找方法
     */
    private fun findMethod(clazz: Class<*>, methodName: String): Method? {
        return try {
            clazz.getMethod(methodName)
        } catch (e: NoSuchMethodException) {
            try {
                clazz.getDeclaredMethod(methodName)
            } catch (e2: NoSuchMethodException) {
                null
            }
        }
    }

    /**
     * 获取所有字段（包括父类）
     */
    private fun getAllFields(clazz: Class<*>): List<Field> {
        val fields = mutableListOf<Field>()
        var currentClass: Class<*>? = clazz
        
        while (currentClass != null && currentClass != Any::class.java) {
            fields.addAll(currentClass.declaredFields)
            currentClass = currentClass.superclass
        }
        
        return fields
    }
}
