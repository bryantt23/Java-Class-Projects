
/////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2011 ODOH KENNETH EMEKA (TURKU UNIVERSITY OF APPLIED SCIENCES)

//This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

//This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
/////////////////////////////////////////////////////////////////////////////

You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses>.

The program was successfully tested on linux platform. We had to use bash script to run the program neatly.

Overview
=========
user can create a bank account by meeting an accountant in the bank.
Every user has two accounts which is saving and current account.
There are only deposit in all types of account. However, withdrawal in a negative deposit.
They are two kinds of users. They include normal user and administrator.


Description of key terms
==========================

A current account : This application implement a current account where a user can deposit and withdraw account at his or her own free. However, this has the feature of overdraft where the customer can borrow money when his account is zero. Any overdraft loan made has a specific rate that goes to the bank.This is how the bank makes profit. There is a limit on the overdraft that the customer can take in the account.


A savings account:This application implements savings account allows the customer to make withdrawal and deposit freely.There is not monthly interest rate available in this application.

Every user cannot make deposits independently.He has to meet an account in the bank who will see the cash in person before making the deposit.However,withdrawal can be made freely the customer at every time independently.

Database Design
=================
There are 4 tables in the BankDB database. They are: 
a)	CUSTOMER
b)	SAVINGACCOUNT
c)	CURRENTACCOUNT
d)	OVERDRAFTLOAN

The CUSTOMER has the following attributes.They include :
a) 	indentification number
	This is used to uniquely indentify an individual in the bank.This was made the primary key.

b)	social security number
	This is unique so the column was defined unique in the database and used for some restricted search.That is why it is indexed. We don't saved this value in clear text. This column values is hashed using md5 hashing algorithm to ensure that the social security cannot be compromised even if some one has access to the database.

c)	first name 
	The user's first name.

d)  last name
	The user's last name.

e)	address 
	The user's address.

f)	phone number
	The user's phone number.

g)	email address
	The user's email address.

h)	username 
	The user's username. This is supposed to used with a unique constraint. However,we used a createusername method in the customer class to form a username that consist of the first letter of the first named concatenated with the last name and 5 randomly generated character.This will give use unique usernames in most of the cases. However,if two persons have the same first name and last name. we would have about 10 * 10 * 10 * 10 * 10 possibilities.This is huge we can hardly chosen two number to be same from this possibility since we are permutating without replacement. However,the probability is equally very slim. This column is inherently unique by design.We created an index on the username so that it can be used for search during user log in.
i)	password 
	The user's password.This is also hashed giving us some security.Therefore we cannot know the user's password.Whenever the user forgets his password.We have to reset the password by updating the password field.Index is created on the password to enable quick search when authenticating users.
j)	status 
	The user can be enabled or disabled.
k)	type
	This determines if the user is an administrator or normal customer.
l)  update_at 
	The date the customer data were update.This is done automatically.
m)	create_at 
	The date the customer data created.This timestamp was passed by a method to fill the field.

Every field is inherently not null by design.You cannot created a user without providing all these details. Otherwise,we don't really know our customer.

The SAVINGACCOUNT has the following attributes. They include:

a)	accNo 
	The account number.This is formed from the concatenation of the user's id and "SAV" for saving account. 
b) 	custID 
This is the customer id. This is used to create a foreign key relation at application level.This decision was made because don't want the customer id to be unique in this table.This is used to track the amount deposited into the table.The balance will be calculated by the sum of all the deposited and withdrawn amount from the account. This gives us the advantage of being able to prevent the bank statement. We can easily track deposits and withdrawal with time stamps using the customer id as a filtering key. This is indeed a great advantage on which the entire application was based on.
c)	amount 
There are only deposits in the account. Withdrawal are in a real sense negative deposit. Withdrawal will show up in the bank statement as negative values.
d)	savingDate 
The time stamp for the amount deposited.


The CURRENTACCOUNT has the following attributes. They include:

a)	accNo 
	The account number.This is formed from the concatenation of the user's id and "CUR" for current account . 
b) 	custID 
This is the customer id. This is used to create a foreign key relation at application level.This decision was made because don't want the customer id to be unique in this table.This is used to track the amount deposited into the table.The balance will be calculated by the sum of all the deposited and withdrawn amount from the account. This gives us the advantage of being able to prevent the bank statement. We can easily track deposits and withdrawal with time stamps using the customer id as a filtering key. This is indeed a great advantage on which the entire application was based on.
c)	amount 
There are only deposits in the account. Withdrawal are in a real sense negative deposit. Withdrawal will show up in the bank statement as negative values.
d)	currentDate 
The time stamp for the amount deposited.

CREATE TABLE OVERDRAFTLOAN (
   	serialNo INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	curAccNo VARCHAR(20),
	amount DECIMAL(7,2),
	overdraftDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
The OVERDRAFTLOAN  has the following attributes. They include:

a) 	serialNo 
	This is the serial number and also a primary key for identifying each transaction.

b)	curAccNo
	This is the current account number which is used to create a foreign that references the current account.This is implemented in the application.
we could calculate the total overdraft loan taken by simply summing all the amount.We could use the curacc number to identify the curent account that has taken the amount. 

c)	amount 
	The amount of overdraft loan made at an time.

d)	overdraftDate 
	The date overdraft was made.


Normal User
============
A normal user can do the following
The user chooses the account type at the start of operation. The below operations will be performed on the customer account of choice.
a) view current balance
	This balance can be readily seen in a form.
b) make withdrawal
	The user just have to enter the amount and press "commit".
c)view transaction or bank statement.
	The user see all the deposits and withdrawals on his bank account
d) change password
	The user needs the old password and type in the new password twice. The password is case-sensitive.

e) make transfer
	The user can transfer money from one account to another within the bank. This prompt the user to fill in a randomly generator code.This helps the user to know that he is making a post request.
	This is simply making a withdrawal from a sender account and making a deposit to the recipient account. This was easy because the account types have been properly abstracted in a class.

Administrator user
===================
a) create a new user
	The administrator can create both new normal user and administrator user. This automatically creates the user name and displays the password to the user.

b) delete a user
	The administrator can delete both new normal user and administrator user. He must first search for the user using his SSN. This is not a security issue as the real text is passed as argument into the hashing function. The user only know the password before entering the database. Once it is in the database. It is all hash. if the ssn hit a result.The name is displayed to the administrator. The administrator is asked if he wants to delete the person.Once he clicks yes.This action is irreversible as the user is out of the database.

c) disable a user / enable a user
	The administrator can disable both new normal user and administrator user. He must first search for the user using his SSN. if the ssn hit a result.The name is displayed to the administrator. The administrator is asked if he wants to disable the person.Once he clicks yes.This action is user is disabled and cannot log into the system.The user is advised to see the administrator.However,this action is reversible. The administrator searches again once he see the person.He click "no" and automatically the user is re-enabled again. He can use the system freely.

d) view bank transaction
	The administrator can see the sum of all the money in the savings,current and overdraft table. This can be used to make business decision.The transaction can be displayed in a tabular form to the user.
e) make deposit
	The administrator searches for the user using his SSN. if the ssn hit a result.The name is displayed to the administrator. The administrator receives physical cash from the user and enters the amount in the textbox. The balance is calculated automatically again. The customer is made away of his balance at that instant.

f) view customer
	The administrator can view all the customer details available in the table.

How to run the application
===========================
/////////
keys
-----
$  			stands for command line
sql> 		stands for mysql command line
////////


a) Setting up the database
			steps
			------
	1)Save the customer.sql to a convenient directory.
	2)Navigate to that directory.
	3)login into your mysql database as root.
	$ mysql -u root -p
	enter password when prompted

	Now your are in the root of your mysql database.The run the command below to log the .sql file into the database. This contains the database schema for the application.

	sql> source customer.sql

	This will create all the tables needed in the database.

	create a banking application
	sql>exit
	4)create new user and give access to all tables in your database. This program had made used this user.
		USERNAME = "jhtp7"
		PASSWORD = "jhtp7"
	The default database used in this application is BankDB.
	if you had created a different user. You have to modify the following files to change the user name, password and database name.They include:
			a) DB.java
			b) DisplayGenericTable.java

	5)To create the first administrator user.You need to navigate to the addon directory available within the program directory. You have to run the script.
		$ chmod u+x compile.sh 
		$ ./compile.sh 

The GUI is quite easy to follow.

	6) After this first administrator user has been created. Make sure that you write down your login details and all neccessay information. 

	7) You have need to use the programs in the addon directory once and keep it outside the system where the application is installed. This should not get into the wrong hands as they can create rogue administrator users that can compromise the system.

	8) Your application is ready to run

b) Running the application
	1) I have prepared a bash script that is very easy to use. This script has taken into account the set of path required by the jdbc connector. Here are the steps.
		a)Navigate to the BankingApp directory where the program is saved
		$ chmod u+x compile.sh 
		$ ./compile.sh 

		b) Your application is up and running.
		c) You can log in with the previously created administrator user.


Security
=============
Only hashed passwords are saved in the database.
SSN is also hashed.
The password field for login does not display the real alphabets type in the field.
The made use of view. Normal user and administrator view cannot conflict.

The validator has a canHappenSQLInject method. This help to avoid the come forms of sql injection.This will prevent any search that has an SQL keywords. These are potential sources of SQL injection. This program will refuse any query that has any of the specified keywords.

The code is properly documented.
Every user input is fully validated to ensure that the user has not input data in wrong form. The data was well validated.

References
=================
	*Mysql online documentation (http://www.mysql.com/) 
	*Java online documentation  (http://download.oracle.com/javase/tutorial/)
	*Deitel Java How to Program 7th edition by Paul J. Deitel - Deitel & Associates, Inc.; Harvey M. Deitel - Deitel & Associates, Inc.
	*Java for Programmers: Deitel Developer Series
by Paul J. Deitel - Deitel & Associates, Inc.; Harvey M. Deitel - Deitel & Associates, Inc.




