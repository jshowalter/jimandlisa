To install the schema:

1) Copy invoiceDbSchema.sql to your MySqlServer data folder (for example, C:\ProgramData\MySQL\MySQL Server 5.5\data).
2) Open a command window.
3) Navigate to the directory you copied the .sql file into.
4) Assuming your user is "root" and your password is 'a', type: mysql -u root -pa
5) At the mysql> prompt, type: create database invoicedb; (be sure to include the ending ';').
6) Verify that you got a response like "Query OK, 1 row affected" and a new mysql> prompt.
7) Type: exit, and verify that you are back at a normal command prompt.
8) Assuming your user is "root" and your password is 'a', type: mysql –u root –pa invoicedb < invoiceDbSchema.sql