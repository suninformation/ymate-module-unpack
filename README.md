# YMATE-MODULE-UNPACK

[![Maven Central status](https://img.shields.io/maven-central/v/net.ymate.module/ymate-module-unpack.svg)](https://search.maven.org/artifact/net.ymate.module/ymate-module-unpack)
[![LICENSE](https://img.shields.io/github/license/suninformation/ymate-module-unpack.svg)](https://gitee.com/suninformation/ymate-module-unpack/blob/master/LICENSE)

基于 YMP 框架实现的文件解包器模块封装，用于自动执行文件解压。

#### Maven包依赖

```xml
<dependency>
    <groupId>net.ymate.module</groupId>
    <artifactId>ymate-module-unpack</artifactId>
    <version>1.0.0</version>
</dependency>
```

#### 模块配置参数说明

```properties
#-------------------------------------
# module.unpack 解包器模块初始化参数
#-------------------------------------

# 解包器模块是否已启用, 默认值: true
#ymp.configs.module.unpack.enabled=

# 禁止解包列表, 多个包名称之间使用'|'分隔, 默认值：空
#ymp.configs.module.unpack.disabled_unpack_list=
```

## One More Thing

YMP 不仅提供便捷的 Web 及其它 Java 项目的快速开发体验，也将不断提供更多丰富的项目实践经验。

感兴趣的小伙伴儿们可以加入官方 QQ 群：[480374360](https://qm.qq.com/cgi-bin/qm/qr?k=3KSXbRoridGeFxTVA8HZzyhwU_btZQJ2)，一起交流学习，帮助 YMP 成长！

如果喜欢 YMP，希望得到你的支持和鼓励！

![Donation Code](https://ymate.net/img/donation_code.png)

了解更多有关 YMP 框架的内容，请访问官网：[https://ymate.net](https://ymate.net)