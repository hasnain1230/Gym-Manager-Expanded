# Gym-Manager
This is a project that emulates a chain of gyms. This project establishes a 
database of users and allows a gym manager to enter commands that will manipulate
and display the state of the database of members. Managers can add and delete members
from the database and can also manager virtual fitness classes offered by the gym chain.

This is a versatile and easy-to-use Gym Manager designed with those in charge of running a gym
in mind.

## How To Run:
```
git clone https://github.com/hasnain1230/Gym-Manager.git
cd Gym\ Manager/src
javac RunProject1.java
java RunProject1
```
If all has gone well, you should see the following in `stdout`:
```
Gym Manager Running...
```
If you see this, that means the Gym Manager interface is ready to start accepting commands. 

## Commands
**Please Note: Commands are case-sensitive!**
- Add member to database
  - ```text
    A <First Name> <Last Name> <Date of Birth> <Expiration Date> <Primary Gym Location>
    ```
- Remove member from database
  - ```text
    R <First Name> <Last Name> <Date of Birth>
    ```
- Print the current database to `stdout` with no sorting.
  - ```text
    P
    ```
- Print the current database to `stdout` sorted by county.
    - ```text
      PC
      ```
- Print the current database to `stdout` sorted by Last Name, then First Name.
    - ```text
      PN
      ```
- Print the current database to `stdout` sorted by expiration dates (closest to expire to the furthest away from expiring).
    - ```text
      PD
      ```
- Print the current fitness class database to `stdout`. This will print the class name, time, instructor name, and the participants, if any.
    - ```text
      S
      ```
- Check a member into a fitness class.
    - ```text
      C <Class Name> <First Name> <Last Name> <Date of Birth>
      ```
- Remove a member from a certain class.
    - ```text
      D <Class Name> <First Name> <Last Name> <Date of Birth>
      ```
- Terminate application..
    - ```text
      Q
      ```

## Documentation
To read in-detail, and in-depth documentation, view `javadocs/index.html` in the web browser
of your choice. There, you can view full documentation for this entire application and further understand
the inner workings of `Gym Manager`.

# Authors
This program was written by my friend and I.
- **Hasnain Ali (Me)**
- **Carolette Saguil**

Please Note: Since my friend wished to avoid common problems with Git (merge conflicts and other issues), and since 
this application was developed in a pair programming environment on my machine, my friend did not push commits to Git. **However**
**this application was completed by the two of us with equal contributions from both of us.**

