# spring_task_manager
Web app for interaction between managers and employees

# Database
App uses Postgres database, schema shown below:
![image](https://user-images.githubusercontent.com/52649614/176594597-3b8aa02a-5aa1-4e97-992f-7ed5d1ba58f9.png)


# How it works
The application uses Spring Boot (Web, Mybatis).

- Use Spring Security for auth
- Use Soring Validation for check forms
- Use Yandex API to display data on map

Example:
![image](https://user-images.githubusercontent.com/52649614/176599854-f9d4bbe8-bc3c-4083-a3e6-648e5e62dd7a.png)


# Working script
The manager receives an appeal from the client and enters it into the system, if the contract exists, "address", "client", "appart" fields will be autofilled, otherwise the "contract" field will be declared as a "new contract"
Example:
![image](https://user-images.githubusercontent.com/52649614/176599936-45cdc91c-60c2-4f62-be1d-d7d3b3b0e134.png)

Further, after the delegation of the appeal to the employee, only the employee can give the appeal the status "completed"
![image](https://user-images.githubusercontent.com/52649614/176600025-9270c40e-011b-42de-a880-fe2b52fd2c05.png)

On the employee page, a mobiscroll calendar is connected to sort all appeals by the days of the current sprint
![image](https://user-images.githubusercontent.com/52649614/176600102-aaade45b-11bf-4101-bf02-a474f8a10b74.png)


# Future
Many forms are redundant as the data must come from DWH, however this option is not implemented at this stage
