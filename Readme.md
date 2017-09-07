# <p align="center">zhw-util</p>
<p align="center">WEB开发工具集合</p>
<p align="center">
包含类操作、数据库操作、任务计划、通用日志输出、WEBGIS、文件上传下载、文件操作<br>
短信验证码、图形验证码、发送邮件、本机信息获取、系统资源SNMP监控、EXCEL和WORD与HTML转换<br>
加解密、签名验证、字符编码、线程超时、LUCENSE关键字、正则表达式<br>
SPRING事件监听、SHIRO自定义鉴权和动态权限、JDBC封装<br>
</p>  
# com.zhw.core.classhotloader  
类加载  
## com.zhw.core.db
* mysql、oracle数据库连接、操作、防止sql注入，转换为bean，数据库监控
* oracle连接,操作
* redis缓存操作、get、set、定时、是否存在、订阅发布
## com.zhw.core.jobplan
* 任务计划制定、配置、动态修改任务配置、任务执行
## com.zhw.core.log
* 通用日志输出
* System.out,System.err输出到文件
## com.zhw.core.map
* 百度、高德地图操作
## com.zhw.core.net
* ftp连接、上传、下载、列出文件等操作
* http请求处理，下载图片文件、上传
* 获取本机信息
* 邮件发送
* 发送短信、短信验证码生成、验证、超时、缓存
* snmp协议获取网络主机监控信息
## com.zhw.core.poi
* excel转换html，生成excel
* word转换html，生成word
## com.zhw.core.util
* AES加解密、签名验证
* DES加解密、签名验证
* MD5加密、SHA256加密、uuid生成
* 文件操作：文件读写、复制移动删除、列出、文件夹操作、压缩解压
* 查找关键字、搜索
* map与obj互相转换
* json与obj互相转换
* 日期与字符串转换
* decode、encode、去bom、Unicode转ascii
* double计算、保留小数位
* property获取，参数解析
* 字符串操作、判空、trim、lpad、大小写、过滤、分隔、替换、半全角
* 线程池、线程超时
* 密码登录验证、签名验证、加盐
* 正则表达式匹配
* 序列化
* sql语句字符串拼接转换
* xml解析、生成
## com.zhw.web.dao
* jdbc封装、映射、定义表名、全列名、update列、orderby、where、primary，limit
## com.zhw.web.filter
* 过滤、拦截、
* com.zhw.web.httpapi
* SERVLET请求映射到接口
## com.zhw.web.listener
* bean加载完成监听事件、session绑定事件、session创建销毁事件、servlet事件
## com.zhw.web.security
* shiro自定义验证
* quartz2余shiro结合
## com.zhw.web.util
* ApplicationContext获取
* cookie设定和获取、超时
* 文件上传
* 证书信任管理
* 分页查询
* 验证码生成器
## com.zhw.web.util.websocket
* websocket打开、广播、响应、推送、销毁


#to do list
* js常用库

#CHANGELOG
## v0.1
* v0.1

#contact
<zhw513029@163.com>