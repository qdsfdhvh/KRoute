# KRoute

## 前言

`Navigation`绑定一个路由比较麻烦，随着界面的增多后会变得比较难维护，所以我们就尝试把这部分硬编码改成动态生成的方式。

## 内容

### 1.绑定路由

在`Compose`中，`Navigation`绑定一个路由的方式大致为：

```kotlin
composable(
    route = "/labs/detail/{id}/{name}?desc={desc}",
    arguments = listOf(
        navArgument("id") { type = NavType.LongType },
        navArgument("name") { type = NavType.StringType },
        navArgument("desc") { type = NavType.StringType; nullable = true },
    )
) {
    val id = it.arguments!!.get("id") as Long
    val name = it.arguments!!.get("name") as String
    val desc = it.arguments?.get("desc") as? String
    LabsDetailScene(
        navController = navController,
        id = id,
        name = name,
        desc = desc
    )
}

navController.navigate("/labs/detail/10/balala?desc=xmx")
```

`route`和`arguments`配置起来都很麻烦，所以我们就尝试通过ksp去动态生成。

### 2.动态生成路由

我们把项目配置成了`kotlin("multiplatform")`，以此来使用`expect/actual`关键字；

定义一个`@Route`注解，通过下面的方式来配置路由：

```kotlin
@Route
expect object LabsRoute {
    val Tab: String
    object Detail {
        operator fun invoke(id: String, name: String, detail: String?): String
    }
}
```

使用ksp来生成`actual`实现：

```kotlin
actual object LabsRoute {
    actual val Tab = "LabsRoute/Tab"
    actual object Detail {
        const val path = "LabsRoute/Detail/{id}/{name}?detail={detail}"
        actual operator fun invoke(id: String, name: String, detail: String?): String {
            return "LabsRoute/Detail/$id/$name?detail=$detail"
        }
    }
}
```

这样，我们的使用就变成了下面这样，不用再硬编码`route`了。

```kotlin
composable(
    route = LabsRoute.Detail.path,
    ...
) {
    ...
}

navController.navigate(LabsRoute.Detail(10, "balala", "xmx"))
```

### 3.将路由改为常量

处理`arguments`的思路也差不多，但是注解只支持常量，所以我们需要把`route`的参数改为`const`；

直接添加`const`会提示`Const 'val' should have an initializer`导致无法编译，不过`expect/actual`是支持常量的，这个警告更像是个bug，通过`@Suppress`来忽略;

```kotlin
@Suppress("CONST_VAL_WITHOUT_INITIALIZER")
@Route
expect object LabsRoute {
    const val Tab: String
    object Detail {
        operator fun invoke(id: String, name: String, detail: String?): String
    }
}
```

### 4.动态注册路由

定义一个`@NavGraphDestination`注解，像常规的路由框架那样：

```kotlin
@NavGraphDestination(
    route = LabsRoute.Detail.path,
)
fun LabsDetailScene(
    navController: NavController,
    @Path("id") id: Long,
    @Path("name") name: String,
    @Query("desc") desc: String?
) {
    ...
}
```

依靠辅助的`@Path`和`@Query`注解，生成和上面差不多的代码；

不过因为我们的`route`是动态的，ksp在编译的时候可能会碰到`java.util.NoSuchElementException: Collection contains no element matching the predicate.`就也就是路由还没生成好的情况；

毕竟是一波套娃的操作，遇到这个错误也是预料之中，我们这里现在的处理是，先检查一遍`route`，错误的时候随便返回一个list触发ksp的重试：

```kotlin
override fun process(resolver: Resolver): List<KSAnnotated> {
    val symbols = resolver...
    val generatedFunctionSymbols = resolver...

    fun checkValidRoute(symbol: KSFunctionDeclaration): Boolean {
        return try {
            symbol.getAnnotationsByType(NavGraphDestination::class).first().route
            true
        } catch (e: Throwable) {
            false
        }
    }

    if (symbols.any { !checkValidRoute(it) }) {
        return (symbols + generatedFunctionSymbols).toList()
    }

    ...
}
```

### 5.收集路由

同样定义一个`@GeneratedFunction`注解，写个空壳方法：

```kotlin
@GeneratedFunction
expect fun NavGraphBuilder.generatedLabsRoute(
    navController: NavController
)
```

ksp会把当前 module 中的`@NavGraphDestination`函数到放入这个入口：

```kotlin
actual fun NavGraphBuilder.generatedLabsRoute(navController: NavController) {
    composable(
        route = "/labs/detail/{id}/{name}?desc={desc}",
        arguments = listOf(
            navArgument("id") { type = NavType.LongType },
            navArgument("name") { type = NavType.StringType },
            navArgument("desc") { type = NavType.StringType; nullable = true },
        )
    ) {
        val id = it.arguments!!.get("id") as Long
        val name = it.arguments!!.get("name") as String
        val desc = it.arguments?.get("desc") as? String
        LabsDetailScene(
            navController = navController,
            id = id,
            name = name,
            desc = desc
        )
    }
}
```

由于`expect`函数外部 module 是可以引用的，可以直接在 app 中导入或者通过 di 注入：

```kotlin
@Composable
fun Route() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = ...) {
        ...
        generatedLabsRoute(navController)
    }
}
```

## 结语

文章并没有多少内容（有点水），很多项目估计也不方便切换到kotlin多平台，所以不太实用；

这里主要还是想分享下`expect/actual`+`ksp`组合，动态生成的代码可以直接和外部建立联系，这个我觉得可玩性还是很大的，期待大佬们后面能玩出一些**大格局**的操作。