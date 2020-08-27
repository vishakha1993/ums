# ums
user management system assignment

# Getting Started

### Reference Documentation
1)After cloning repo

- create schema using dbDump.sql
- change db url at application.properties
- run ./mvnw spring-boot:run

sample api curls are as follows 
1) Login of admin user

vishakha@l-pc02xsxg ~ % curl --location --request GET 'http://127.0.0.1:8080/login?username=admin&password=Admin@123'                                                                
{"code":"SUCCESS","message":"Login Successfully","token":"49fedbe6-b06e-4420-bc34-900898d98f21","admin":true}%         

2)Creating user with invalid admin token

vishakha@l-pc02xsxg ~ % curl --location --request POST 'http://127.0.0.1:8080/admin/createUser' \                                                                                   
--header 'Content-Type: application/json' \
--data-raw '{
 "userName":"suchu98"  ,
 "firstName":"Suchu",
 "lastName":"Gaur",
 "dob":1598552145453,
 "email":"suchita98@gmail.com",
 "token":"91a2af64-ac7f-44d8-b348-6d42ad9da0a2"
}'
{"code":"Unauthorized Access","message":"You are not authorized for this api","userName":null,"password":null}% 

3)Creating user

vishakha@l-pc02xsxg ~ % curl --location --request POST 'http://127.0.0.1:8080/admin/createUser' \
--header 'Content-Type: application/json' \
--data-raw '{
 "userName":"swass8"  ,
 "firstName":"Swati",
 "lastName":"Singh",
 "dob":1598552145453,
 "email":"xyz@gmail.com",
 "token":"49fedbe6-b06e-4420-bc34-900898d98f21" 
}'
{"code":"Success","message":"User created successfully","userName":"swass8","password":"J$8igUZj"}%  


4)Login Using new user

vishakha@l-pc02xsxg ~ % curl --location --request GET 'http://127.0.0.1:8080/login?username=swass8&password=J$8igUZj' 
{"code":"SUCCESS","message":"Login Successfully","token":"c2a79132-ca8a-4c06-9862-8989fade5024","admin":false}%                                                                                           

5)change password
vishakha@l-pc02xsxg ~ % curl --location --request GET 'http://127.0.0.1:8080/user/changePassword?oldPassword=d$5JVQHV&newPassword=h1$ghkju&token=c2a79132-ca8a-4c06-9862-8989fade5024'
{"code":"Error","message":"Old Password is incorrect"}%                                                                                                                                          

vishakha@l-pc02xsxg ~ % curl --location --request GET 'http://127.0.0.1:8080/user/changePassword?oldPassword=J$8igUZj&newPassword=h1$ghkju&token=c2a79132-ca8a-4c06-9862-8989fade5024'
{"code":"SUCESS","message":"Password updated successfully"}%    

6)edit details
curl --location --request POST 'http://127.0.0.1:8080/user/updateDetails' \
--header 'Content-Type: application/json' \
--data-raw '{
 "firstName":"Swati",
 "email":"xyz22@gmail.com",
 "token":"ccb3635d-4797-445f-8508-5fd2e6fbfeea"
}'
{"code":"SUCESS","message":"details updated successfully"}%   


7) admin -get all users list
vishakha@l-pc02xsxg ~ % curl --location --request GET 'http://127.0.0.1:8080/admin/getAllUsers?token=7542adf8-06bd-469b-9563-509a30c448b2'
{"code":null,"message":null,"users":["admin","swass8"]}%                                                                                                                                             

8)get user detail
vishakha@l-pc02xsxg ~ % curl --location --request GET 'http://127.0.0.1:8080/admin/getUserDetail?username=swass8&token=7542adf8-06bd-469b-9563-509a30c448b2'
{"code":"SUCCESS","message":null,"email":"xyz22@gmail.com","firstName":"Swati","lastName":"Singh","dob":"2020-08-27T18:15:45.000+00:00"}%                                                   



All activities are persited in user_activity_log table.
