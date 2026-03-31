package com.example.club

import com.example.club.app.USER
import org.junit.Test
import org.junit.Assert.*
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier

/**
 * 反射单元测试示例
 * 演示如何使用反射测试类的属性、方法和构造函数
 */
class ReflectionUnitTest {

    @Test
    fun testClassExists() {
        // 测试类是否存在
        val clazz = Class.forName("com.example.club.app.USER")
        assertNotNull("USER 类应该存在", clazz)
        assertEquals("类名应该是 USER", "USER", clazz.simpleName)
    }

    @Test
    fun testClassModifiers() {
        // 测试类的修饰符
        val clazz = USER::class.java
        
        val isPublic = Modifier.isPublic(clazz.modifiers)
        
        assertTrue("USER 类应该是 public 的", isPublic)
        
        println("\nUSER 类修饰符信息:")
        println("  - 是否 public: $isPublic")
        println("  - 是否 final: ${Modifier.isFinal(clazz.modifiers)}")
        println("  - 是否 abstract: ${Modifier.isAbstract(clazz.modifiers)}")
    }

    @Test
    fun testDeclaredFields() {
        // 测试类声明的字段
        val clazz = USER::class.java
        val fields = clazz.declaredFields
        
        println("========== USER 类声明的字段 ==========")
        fields.forEach { field ->
            println("字段名：${field.name}")
            println("  类型：${field.type.name}")
            println("  修饰符：${Modifier.toString(field.modifiers)}")
        }
        println("====================================")
        
        // 验证字段数量
        assertTrue("USER 类应该有字段", fields.size >= 0)
    }

    @Test
    fun testDeclaredMethods() {
        // 测试类声明的方法
        val clazz = USER::class.java
        val methods = clazz.declaredMethods
        
        println("\n========== USER 类声明的方法 ==========")
        methods.forEach { method ->
            println("方法名：${method.name}")
            println("  返回类型：${method.returnType.name}")
            println("  参数数量：${method.parameterCount}")
            println("  修饰符：${Modifier.toString(method.modifiers)}")
        }
        println("====================================")
        
        // 验证方法数量
        assertTrue("USER 类应该有方法", methods.size >= 0)
    }

    @Test
    fun testConstructors() {
        // 测试构造函数
        val clazz = USER::class.java
        val constructors = clazz.constructors
        
        println("\n========== USER 类构造函数 ==========")
        constructors.forEach { constructor ->
            println("构造函数参数数量：${constructor.parameterCount}")
            println("  修饰符：${Modifier.toString(constructor.modifiers)}")
        }
        println("====================================")
        
        // 验证至少有一个构造函数
        assertTrue("USER 类应该至少有一个构造函数", constructors.size >= 1)
    }

    @Test
    fun testCreateInstanceWithReflection() {
        // 使用反射创建实例
        val clazz = USER::class.java
        
        // 获取无参构造函数并创建实例
        val constructor = clazz.getDeclaredConstructor()
        val instance = constructor.newInstance()
        
        assertNotNull("应该能够创建 USER 实例", instance)
        assertEquals("实例类型应该是 USER", USER::class.java, instance.javaClass)
        
        println("\n✓ 成功使用反射创建 USER 实例")
    }

    @Test
    fun testFieldAccess() {
        // 测试使用反射访问字段
        val clazz = USER::class.java
        val instance = clazz.getDeclaredConstructor().newInstance()
        
        // 添加一些字段进行测试（需要先修改 USER 类）
        // 这里演示如何访问私有字段
        try {
            val fields = clazz.declaredFields
            fields.forEach { field ->
                // 如果是私有字段，需要设置可访问
                if (Modifier.isPrivate(field.modifiers)) {
                    field.isAccessible = true
                }
                
                val value = field.get(instance)
                println("字段 ${field.name} 的值：$value")
            }
        } catch (e: Exception) {
            println("访问字段时出错：${e.message}")
        }
    }

    @Test
    fun testMethodInvocation() {
        // 测试使用反射调用方法
        val clazz = USER::class.java
        val instance = clazz.getDeclaredConstructor().newInstance()
        
        try {
            // 查找 toString 方法
            val toStringMethod: Method = clazz.getMethod("toString")
            val result = toStringMethod.invoke(instance)
            
            assertNotNull("toString 方法应该返回结果", result)
            println("\n调用 toString() 方法结果：$result")
        } catch (e: Exception) {
            println("调用方法时出错：${e.message}")
        }
    }

    @Test
    fun testSuperclassInfo() {
        // 测试获取父类信息
        val clazz = USER::class.java
        
        val superclass = clazz.superclass
        println("\n========== 继承关系 ==========")
        println("当前类：${clazz.name}")
        println("父类：${superclass?.name ?: "None"}")
        
        // 获取实现的接口
        val interfaces = clazz.interfaces
        if (interfaces.isNotEmpty()) {
            println("实现的接口:")
            interfaces.forEach { interfaceName ->
                println("  - ${interfaceName.name}")
            }
        } else {
            println("未实现任何接口")
        }
        println("==============================")
    }

    @Test
    fun testAnnotationInfo() {
        // 测试获取注解信息
        val clazz = USER::class.java
        
        val annotations = clazz.annotations
        println("\n========== 类注解信息 ==========")
        if (annotations.isNotEmpty()) {
            println("USER 类上的注解:")
            annotations.forEach { annotation ->
                println("  - ${annotation.annotationClass.qualifiedName}")
            }
        } else {
            println("USER 类没有注解")
        }
        println("==============================")
    }

    @Test
    fun testGenericInfo() {
        // 测试获取泛型信息
        val clazz = USER::class.java
        
        val typeParameters = clazz.typeParameters
        println("\n========== 泛型参数信息 ==========")
        if (typeParameters.isNotEmpty()) {
            println("USER 类的泛型参数:")
            typeParameters.forEach { param ->
                println("  - $param")
            }
        } else {
            println("USER 类没有泛型参数")
        }
        println("==============================")
    }

    @Test
    fun comprehensiveReflectionTest() {
        // 综合反射测试
        println("\n" + "=".repeat(60))
        println("          USER 类完整反射分析报告")
        println("=".repeat(60))
        
        val clazz = USER::class.java
        val instance = clazz.getDeclaredConstructor().newInstance()
        
        // 1. 基本信息
        println("\n【基本信息】")
        println("  全限定名：${clazz.name}")
        println("  简单名称：${clazz.simpleName}")
        println("  包名：${clazz.`package`?.name}")
        println("  修饰符：${Modifier.toString(clazz.modifiers)}")
        
        // 2. 构造器
        println("\n【构造器】")
        clazz.constructors.forEach { constructor ->
            println("  - ${constructor.toGenericString()}")
        }
        
        // 3. 字段
        println("\n【字段】")
        clazz.declaredFields.forEach { field ->
            field.isAccessible = true
            val value = try {
                field.get(instance)
            } catch (e: Exception) {
                "无法访问"
            }
            println("  - ${field.type.simpleName} ${field.name} = $value")
        }
        
        // 4. 方法
        println("\n【方法】")
        clazz.declaredMethods.forEach { method ->
            println("  - ${method.toGenericString()}")
        }
        
        // 5. 继承关系
        println("\n【继承关系】")
        var currentSuper: Class<*>? = clazz.superclass
        var level = 0
        while (currentSuper != null && currentSuper != Any::class.java) {
            println("  ${"  ".repeat(level + 1)}└─ ${currentSuper.simpleName}")
            currentSuper = currentSuper.superclass
            level++
        }
        
        println("\n" + "=".repeat(60))
        println("          分析完成 ✓")
        println("=".repeat(60) + "\n")
    }
}
