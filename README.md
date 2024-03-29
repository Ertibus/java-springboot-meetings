# Java Developer Task 
Create a web application to manage Visma’s internal meetings using Java Spring Boot. Requirements: 

- [X] Endpoints:
	- `/api/meetings` - find meetings (With optional parameters)
	- `/api/meetings` - new meeting (RequestBody: Meeting)
	- `/api/{userId}/meetings/{meetingId}` - delete meeting
	- `/api/meetings/{meetingId}` add attendee to the meeting (RequestBody: Attendee)
	- `/api/meetings/{meetingId}/{userId}` remove attendee from the meeting
	- `/api/meetings` remove 


- [X] Rest API endpoint to create a new meeting. All the meeting data should be stored in a JSON file. Application should retain data between restarts. Meeting model should contain the following properties: 
	- Name 
	- ResponsiblePerson 
	- Description 
	- Category (Fixed values - CodeMonkey / Hub / Short / TeamBuilding) ○ Type (Fixed values - Live / InPerson) 
	- StartDate 
	- EndDate 

- [X] Rest API endpoint to delete a meeting. Only the person responsible can delete the meeting. 

- [X] Rest API endpoint to add a person to the meeting. 
	- Command should specify who is being added and at what time. 
	- If a person is already in a meeting which intersects with the one being added, a warning message should be given. 
	- Prevent the same person from being added twice. 

- [X] Rest API endpoint to remove a person from the meeting. 
	- If a person is responsible for the meeting, he can not be removed. 

- [X] Rest API endpoint to list all the meetings. Add the following parameters to filter the data (can be used in combinations. Example: `/api/meetings?desc=java&category=CODE_MONKEY`): 
	- `?desc` Filter by description (if the description is “Jono Java meetas”, searching for java should return this entry) 
	- `?responsible` Filter by responsible person (ID)
	- `?category` Filter by category
	- `?type` Filter by type 
	- `?fromDate` and `?toDate` Filter by dates (e.g meetings that will happen starting from 2022-01-01 / meetings that will happen between 2022-01-01 and 2022-02-01) 
	- `?attendees` Filter by the number of attendees (e.g show meetings that have over 10 people attending) 

Focus on good code design and good practices. Besides the completion of functional requirements, we require: OOP design, unit tests and code should adhere to Java coding conventions. 

Push your code to Git (e.g. GitHub, GitLab, Bitbucket) repository and share the link with us. Good luck!
