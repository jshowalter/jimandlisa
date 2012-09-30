To install the schema:

1) Copy invoiceDbSchema.sql to your MySqlServer data folder (for example, C:\ProgramData\MySQL\MySQL Server 5.5\data).
2) Open a command window.
3) Navigate to the data directory.
4) Assuming your user is "root" and your password is 'a', type: mysql -u root -pa
5) At the mysql> prompt, type: create database invoicedb; (be sure to include the ending ';').
6) Verify that you got a response like "Query OK, 1 row affected" and a new mysql> prompt.
7) Type: exit, and verify that you are back at a normal command prompt.
8) Assuming your user is "root" and your password is 'a', type: mysql –u root –pa invoicedb < invoiceDbSchema.sql

If step #8 does not work for you:

1) Launch MySQL Workbench.
2) Right-click on invoiced.
3) Select Set as Default Schema.
4) Select File -> Open SQL Script...
5) Navigate to the invoiceDbSchema.sql file and select it.
6) Click the leftmost lightning bold in the SQL window.