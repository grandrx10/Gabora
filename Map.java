import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class Map {
    private ArrayList<Room> rooms;
    private int mapSize;
    private int numberOfCustomRooms;

    Map(int x, int y, ArrayList<Entity> entities, int mapSize) {
        rooms = new ArrayList<Room>();

        loadMapFile(entities, x, y, mapSize);
    }

    public void loadMapFile(ArrayList<Entity> entities, int x, int y, int mapSize) {
        File mapFile = new File("");
        Scanner mapScanner = new Scanner("");
        try {
            mapFile = new File("rooms.txt");
            mapScanner = new Scanner(mapFile);
        } catch (Exception e) {
            System.out.println("File could not be found");
        }

        numberOfCustomRooms = mapScanner.nextInt();

        int xPos = x;
        int yPos = y;
        int lineNum = 0;
        int startX = -1;
        int startY = -1;
        int exitX = -1;
        int exitY = -1;
        int roomLength = 0;
        int roomWidth = 0;
        int roomsSpawned = 0;

        while (roomsSpawned < mapSize) {
            roomsSpawned++;

            int roomToSpawn = randint(0, numberOfCustomRooms - 1);
            // Set the map scanner to the position where the room is in the file
            mapScanner = loadRoom("rooms.txt", roomToSpawn);
            boolean loadRoom = true;
            while (loadRoom) {
                String text = mapScanner.nextLine();
                if (text.equals("---")) {
                    loadRoom = false;
                } else {
                    roomLength = text.length() * 50;
                    System.out.println(text);
                    for (int i = 0; i < text.length(); i++) {
                        String subsection = text.substring(i, i + 1);
                        if (subsection.equals("S")) {
                            startX = xPos + i * 50;
                            startY = lineNum * 50;
                        }
                    }
                    yPos += 50;
                    lineNum++;
                }
                roomWidth = yPos;
                // rooms.add(new Room(0, x, y, roomLength, roomWidth));
            }

            mapScanner = loadRoom("rooms.txt", roomToSpawn);
            loadRoom = true;
            lineNum = 0;
            System.out.println(roomLength);
            if (exitX != -1 && exitY != -1) {
                yPos = exitY - startY;
            } else {
                xPos = x;
                yPos = y;
            }

            while (loadRoom) {
                String text = mapScanner.nextLine();
                if (text.equals("---")) {
                    loadRoom = false;
                } else {
                    for (int i = 0; i < text.length(); i++) {
                        String subsection = text.substring(i, i + 1);
                        if (subsection.equals("#")) {
                            entities.add(new Wall(xPos + i * 50, yPos, 50, 50, ""));
                        } else if (subsection.equals("|")) {
                            entities.add(new Door(xPos + i * 50 + 10, yPos, 30, 50, ""));
                        } else if (subsection.equals("S")) {
                            entities.add(new Door(xPos + i * 50 + 10, yPos, 30, 50, ""));
                        } else if (subsection.equals("E")) {
                            entities.add(new Door(xPos + i * 50 + 10, yPos, 30, 50, ""));
                            exitX = xPos + i * 50;
                            exitY = yPos;
                        }
                    }
                    yPos += 50;
                }
            }
            xPos += roomLength;
        }

        mapScanner.close();
    }

    public Scanner loadRoom(String fileName, int roomNumber) {
        Scanner mapScanner = new Scanner("");
        File mapFile;
        try {
            mapFile = new File(fileName);
            mapScanner = new Scanner(mapFile);
        } catch (Exception e) {
            System.out.println("File could not be found");
        }
        // This is here to get rid of the line at the beginning of the file
        // where the number of rooms is
        mapScanner.nextLine();

        int roomsPassed = 0;
        while (roomsPassed < roomNumber) {
            String text = mapScanner.nextLine();
            if (text.equals("---")) {
                roomsPassed++;
            }
        }
        return mapScanner;
    }

    public static int randint(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }

}
