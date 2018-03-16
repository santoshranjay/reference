#install
install Microsoft Visual C++ 2013 Redistributable Package
> https://www.microsoft.com/en-in/download/details.aspx?id=40784

#start server
  1. create a data folder under MYSQL_HOME
  2. initialize
    ```$ mysqld --initialize //this create generated password printed in .err file inside data folder```
    
  3. start server
    ```$mysqld --console```
  4. connect client and change root password
   ``` 
    $mysql -u root -p
    $ALTER USER USER() IDENTIFIED BY 'password' 
    ```


#References:-
 https://dev.mysql.com/doc/refman/5.7/en/
 http://www.mysqltutorial.org/mysql-cheat-sheet.aspx 
