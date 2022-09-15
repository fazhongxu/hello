# framework 层级关系说明

## 框架核心层，可以抽取到私服，别的项目可以用
### module_kit 工具类
### module_core 核心类 依赖module_kit工具类

## 项目业务层
### module_resources  xml,drawable相关，不涉及具体代码
### module_router路由模块，主要作用为路由，路径，要跨模块用到的实体信息，依赖module_core、module_kit、module_resources
### module_widget模块 自定义view/跨模块要用到的页面
### module_common模块 配置信息相关/module_kit没有的的常用工具类
### module_service模块 数据库、sp抽象类，下载、上传，队列，api请求，响应数据等；依赖module_router，module_kit，module_core，module_common



