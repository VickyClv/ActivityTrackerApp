import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ActionsForApp extends Thread{
    ObjectInputStream in;
    ObjectOutputStream out;
    public static ArrayList<File> route_list = new ArrayList<>();


    public ActionsForApp(Socket connection) {
        try {
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            int choice = in.readInt();
            if (choice == 1) {
                receiveFile();
            }
            else if (choice == 2){
                String username = in.readUTF();
                String routeName = in.readUTF();
                sendRouteResults(username, routeName);
                sendUserResults(username);
            }
            else {
                String username = in.readUTF();
                sendUserResults(username);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /* Receive file from App */
    private void receiveFile() {
        try {
            String route_file_name = in.readUTF();

            String file_contents = in.readUTF();

            System.out.println("File received from App");

            File route_file = new File(route_file_name);

            try {
                FileWriter myWriter = new FileWriter(route_file_name);
                myWriter.write(file_contents);
                myWriter.close();
            } catch (IOException e) {
                System.err.println("An error occurred while writing on the file.");
                e.printStackTrace();
            }

            Scanner gpx = new Scanner(route_file);
            // Find user
            gpx.nextLine();
            String data = gpx.nextLine();
            int user_index = data.indexOf("creator");
            String username = data.substring(user_index + 9, data.length() - 2);

            gpx.close();

            out.writeUTF(username);
            out.flush();

            route_list.add(route_file);

            System.out.println("Sent file to Actions for Workers");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* Send the route's results to App */
    private void sendRouteResults(String username, String routeName) {
        try {
            // accept results from ActionsForWorkers
            for (User each_user : ActionsForWorkers.getUsersList()) {
                if (each_user.getUsername().equals(username)) {
                    for (Route route : each_user.getRoutes()) {
                        if ((route.getRoute_name() + ".gpx").equals(routeName)) {
                            String[] results = {String.valueOf(route.getTotal_distance()), String.valueOf(route.getAverage_speed()), String.valueOf(route.getTotal_ascent()), String.valueOf(route.getTotal_time())};
                            out.reset();
                            out.writeObject(results);
                            out.flush();
                            break;
                        }
                    }
                }
            }
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* Send the user's results to App */
    private void sendUserResults(String username) {
        try {
            // accept results from ActionsForWorkers
            for (User each_user : ActionsForWorkers.getUsersList()) {
                if (each_user.getUsername().equals(username)) {
                    String[] results = new String[]{String.valueOf(each_user.getTotal_time()), String.valueOf(each_user.getTotal_distance()), String.valueOf(each_user.getTotal_ascent()), String.valueOf(each_user.getAverage_time()), String.valueOf(each_user.getAverage_distance()), String.valueOf(each_user.getAverage_ascent()), String.valueOf(each_user.getPercentage_time()), String.valueOf(each_user.getPercentage_distance()), String.valueOf(each_user.getPercentage_ascent())};
                    out.reset();
                    out.writeObject(results);
                    out.flush();
                    break;
                }
            }

        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /* Get the first file on the list and remove it */
    public synchronized static File getFile() {
        if (!route_list.isEmpty()) {
            File file = route_list.get(0);
            route_list.remove(0);
            return file;
        }
        else {
            return null;
        }
    }
}
