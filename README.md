# BiMeiLingCalculator

四川师范大学 Android 开发上机测试项目。

- 姓名：毕美灵
- 学号：2024110101
- 学号末位为 1，因此主要文字采用红色
- 技术：Kotlin、XML Views、两个 Activity、RecyclerView、SharedPreferences

## 功能

1. 第一个 Activity 输入两个数，并用单选按钮选择加、减、乘、除。
2. `Calculator` 业务类独立完成计算，Activity 只负责输入和显示。
3. 点击工具栏右上角“历史”，进入第二个 Activity。
4. 第二个 Activity 使用 RecyclerView 按最新记录优先显示历史。
5. 历史记录保存到 SharedPreferences，旋转屏幕或重启应用后仍能读取。
6. 页面放在滚动容器中，横竖屏均可正常查看；计算结果也会在旋转后保留。

## 运行方法

1. 用 Android Studio 打开 `D:\Android_Projects\BiMeiLingCalculator`。
2. 等待 Gradle Sync 完成。
3. 选择模拟器或 Android 真机，点击 Run。
4. 在模拟器中计算几次，点击右上角“历史”检查 RecyclerView。
5. 使用模拟器旋转按钮查看横屏效果。

也可以在项目目录运行：

```powershell
$env:JAVA_HOME='D:\App\Android Studio\jbr'
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug --no-daemon
```

APK 生成在 `app\build\outputs\apk\debug\app-debug.apk`。

## Gitee 提交建议

先在 Gitee 创建一个空仓库，然后在本项目目录执行：

```powershell
git init
git add .gitignore README.md settings.gradle.kts build.gradle.kts gradle.properties gradlew gradlew.bat gradle app/build.gradle.kts
git commit -m "初始化计算器项目"
git add app
git commit -m "完成四则运算和历史记录功能"
git branch -M main
git remote add origin <你的Gitee仓库地址>
git push -u origin main
```

提交前确认 `local.properties`、`.gradle`、`.idea` 和 `build` 没有被加入版本库。
