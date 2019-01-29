# springboot-security-oauth2
springboot(1&amp;2)整合security-oauth2并使用redis存储token例子
---  
数据库文件：test.sql  
需要redis，最低版本3.0，我调试的时候使用3.0版本，其他版本没测试过  

<pre>  
请求token(post请求):http://localhost:8080/oauth/token?username=admin&password=123456&grant_type=password&scope=all&client_id=clientId&client_secret=nianzx
用户自觉把上面的username换成user  

删掉token(delete请求，access_token自觉替换):http://localhost:8080/oauth/token?access_token=62c1030c-af9a-444e-b66a-b8c99bf5e5c2&client_id=clientId&client_secret=nianzx

访问资源(access_token自觉替换):  
http://localhost:8080/home?access_token=a32fab8d-d721-4ce1-9feb-7de359fca29c  
http://localhost:8080/userHome?access_token=a32fab8d-d721-4ce1-9feb-7de359fca29c  
http://localhost:8080/adminHome?access_token=a32fab8d-d721-4ce1-9feb-7de359fca29c 
</pre>  

代码中有改动的全局搜索springboot2.0，有改动的都有标注着