# 团队工作管理工具

BAICAI  一个用于团队管理的web应用


## 使用到的技术 ##
&emsp;&emsp;1.[springboot框架](https://spring.io/projects/spring-boot)  
&emsp;&emsp;2.[swagger在线文档](https://github.com/battcn/swagger-spring-boot)  
&emsp;&emsp;3.[JavaMail邮件系统](http://www.runoob.com/java/java-sending-email.html)  
&emsp;&emsp;4.[mybaties数据库访问框架](http://www.mybatis.org/mybatis-3/)  
&emsp;&emsp;5.[springsecurity权限管理](https://spring.io/guides/gs/securing-web/)  
&emsp;&emsp;6.[druid 线程池](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter)  
&emsp;&emsp;7.[jgit Git代码统计](https://www.eclipse.org/jgit/)  
&emsp;&emsp;8.[svnkit svn代码统计](https://svnkit.com/)  
&emsp;&emsp;9.[mysql数据库](https://www.mysql.com/)  
&emsp;&emsp;10.[Vuejs前端框架](https://vuejs.org/)  
&emsp;&emsp;11.[elementUI框架](http://element-cn.eleme.io/#/zh-CN)  
&emsp;&emsp;12.[echarts图表](https://echarts.baidu.com/)
## 模块 ##
0.登录
![登录.png](https://upload-images.jianshu.io/upload_images/8494967-f064cdd4325efd98.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1.控制台  
&emsp;这部分主要包括：  
&emsp;&emsp;各种常用网址的导航  
&emsp;&emsp;团队成员以周为单位的代码提交行数、删除行数、提交次数统计  
&emsp;&emsp;每个成员在每个项目中的贡献比  
&emsp;&emsp;本周周报的提交情况统计  

![常用网站导航.png](https://upload-images.jianshu.io/upload_images/8494967-75c58e5a38d913f5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![代码提交量.png](https://upload-images.jianshu.io/upload_images/8494967-761d0cc188bc4006.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![贡献率.png](https://upload-images.jianshu.io/upload_images/8494967-4eccd235157def4b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


2.周报管理  
&emsp;这部分主要包括：  
&emsp;&emsp;每周周报的写作  
&emsp;&emsp;支持导入git提交记录  
&emsp;&emsp;成员每周周报的展示
![写周报.png](https://upload-images.jianshu.io/upload_images/8494967-15804e26072bebe2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![周报查看.png](https://upload-images.jianshu.io/upload_images/8494967-5ce1eef1bffe6040.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

3.应用管理  
&emsp;这部分主要包括：  
&emsp;&emsp;团队开发中的应该管理，据此来统计代码贡献率  
&emsp;&emsp;团队负责项目的更新与维护  
![应用管理.png](https://upload-images.jianshu.io/upload_images/8494967-07112e6e91ecc7e9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![详情展示.png](https://upload-images.jianshu.io/upload_images/8494967-072f0ef670665beb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


4.项目排期管理  
&emsp;这部分主要包括：  
&emsp;&emsp;团队项目排期的增加修改  
&emsp;&emsp;每个项目每周工作的展示  
&emsp;&emsp;排期延期的说明管理  
![展示.png](https://upload-images.jianshu.io/upload_images/8494967-21f6e70fc3a812b1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![延期.png](https://upload-images.jianshu.io/upload_images/8494967-f13cba682da97595.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



5.成员管理  
&emsp;这部分主要包括：  
&emsp;&emsp;新成员账号的开通  
&emsp;&emsp;团队成员信息的查询  
![成员列表.png](https://upload-images.jianshu.io/upload_images/8494967-9d7a3c37128d97db.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![成员信息.png](https://upload-images.jianshu.io/upload_images/8494967-931133a59dd012d7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

6.个人中心  
&emsp;这部分主要包括：  
&emsp;&emsp;个人信息的修改  
&emsp;&emsp;密码修改  
![个人中心.png](https://upload-images.jianshu.io/upload_images/8494967-6ceff205449675b0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 分支说明
master  主分支，当前稳定版  
dep  开发分支  
test 测试分支

## 数据库文件
数据库使用mysql ,  
sql文件位置  src/resource/static/sqlfile/team-job.sql

## 在线访问地址
http://7r6y89.natappfree.cc/team-job/
## 默认用户名密码
username ：111@qq.com  
password ：123456

## swagger文档地址
http://7r6y89.natappfree.cc/team-job/swagger-ui.html
