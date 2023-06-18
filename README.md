# ActivityTrackerApp

A data analysis system (Distributed System) from activity tracking implimenting MapReduce.

-----

In the mobile app the user chooses and uploads a GPX file containing a sequence of GPX waypoints (A waypoint consists of coordinates (latitude, longitude), elevation and a timestamp). The app sends the contents of the file to the server through a socket.

The server upon recieving the contents, creates a new GPX file, places the contents in it and saves it locally. Afterwards, it cuts the file in chunks (each chunk contains 4 waypoints) and sends them using RoundRobin to the workers through sockets.

Each worker, after recieving a chunk, calculates the intermediate results from the contents of the chunk (total distance, average speed, total ascent and total time) using the Map function. After the function completes the culculations, the worker returns the results to the server.

After the server recieves all the intermediate results of the file from the workers, it calculates the total statistics for the entire file (total distance, average speed, total ascent and total time) using the Redue function. The server also calculates the user's personal statistics (total time, total distance, total ascent, average time, average distance and average ascent) on all routes as well as the user's statistics compared to the other users.

Finally, the user through the app can choose to see the route's statistics, upload a new file or see their general statistics.

-----

The App folder contains the files needed for the android app. 

The Server folder contains all the files needed for the server to run. The main function requires as argumetns the number of workers that will be connected to the server.

The Worker folder contains all the files needed for each worker to run. Next to the source folder you need to create a worker_directory folder for the program to run properly. Each worker's main function requires as arguments the IP address of the server and the worker ID (the first worker to connect has the ID = 0. Afterwards, next worker has the ID = 1, and so on).

-----

I do not own any of the assets.
