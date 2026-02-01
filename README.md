# Game Tester App 🎮
**Developer Details**
* Name & Surname: Nhlanhla Cindi

* Student Number: ST10470261

* Group: 01

* GitHub Repository: [<https://github.com/ST10470261/Game-Tester-App.git>]
---
## About the Project :
The Game Tester App is a specialized Android-based utility designed for quality assurance testers and developers to track game performance metrics. This tool streamlines the process of logging testing sessions, providing a high-contrast, professional interface to monitor gameplay stability and user experience.
---
## Purpose :
The purpose of this app is to provide a structured way to store and analyze game testing data. It allows users to:

* Input and store up to 7 unique testing sessions.

* Log precise Minutes Played and Session Dates (via a DatePicker).

* Categorize sessions by Game Genre (Action, RPG, Strategy, etc.).

* Rate the stability or quality on a 5-star scale using a RatingBar.

* View a Results Summary calculating average playtime and the highest-rated genre.
---
## App Structure :
The app utilizes a robust multi-screen architecture combined with a Parcelable data processor:

1. Splash Screen: A 4-second branded introduction featuring the app logo and a smooth transition to the dashboard.

2. Main Dashboard: The landing page providing entry points to start testing or exit the application safely.

3. Data Entry Screen: The core interface where users log session details, featuring input validation and a 7-entry restriction logic.

4. Analytical Detail View: A summary screen that "unpacks" the testing data to show averages and top-performing categories.
---
## How to Use the App :
1. Launch: Open the app; the Splash Screen will display for 4 seconds.

2. Start Testing: On the Main Screen, tap Start to open the Data Entry terminal.

3. Log a Session:

 * Tap the Date field to select a date from the calendar.

 * Enter the Minutes Played.

* Select the Game Genre from the dropdown.

* Touch the Stars to set a rating (1-5).

* Tap Add Entry.
  
4. View Analytics: Once sessions are added, tap View Details to see your calculated averages and totals.

5. Manage/Exit: Use the Clear button to reset inputs or the Exit button to close the app.
---
## Screenshots & Visuals :
(Note: Replace these placeholders with your actual screenshot files once uploaded to GitHub)

1. Branding & Navigation
2. Data Entry & Feedback
3. Results & Analysis
---
## Error Handling & Design Logic :
Data Validation: The app triggers Toast messages if date or minutes are missing to ensure no "null" data enters the system.

Memory Management: To prevent storage overflow, the app utilizes a GameSessionProcessor that strictly enforces a 7-session limit.

UX Accessibility: All buttons and interactive elements follow the 48dp touch target standard for high precision.

Data Persistence: Uses Parcelable implementation to pass complex data arrays between activities without losing information.
---
## Design Considerations :
Theme: High-contrast dark theme for reduced eye strain during long testing sessions.

Architecture: Implements parallel 1D arrays to synchronize session data efficiently.

Navigation: Linear, stacked button placement to facilitate easy one-handed operation.
