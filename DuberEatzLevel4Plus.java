/**
 * Duber Eatz Level 4+
 * ICS3U6
 * Kyle Zhou
 * Nov 6 2019
 * 
 * Solves map.
 * Solves number of steps to optimize tips
 * With multiple destinations.
 * Saves to ppm file.
 * 
*/ 

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

public class DuberEatzLevel4Plus{
    
  static int steps = -1;
  static char[][] solutionMap;
  static int maxTips = Integer.MIN_VALUE;
  
  public static void main(String[] args) throws Exception{
  
    String maze = "";
    int posX = 0;
    int posY = 0;
    int counter = 0;
    int tips = 0;
    
    Scanner input = new Scanner(System.in);
    System.out.println("Enter the name of your maze file: ");
    String enteredFile = input.nextLine();
    String dotTxt = enteredFile.substring(enteredFile.length() - 4); //grabs the last 4 letters of the file 
    if (!(dotTxt.equals(".txt"))){ 
      enteredFile = enteredFile + ".txt";
    }
    File file = new File(enteredFile);
    
    Scanner input2 = new Scanner(file);
    int numOfRows = input2.nextInt();
    int numOfColumns = input2.nextInt();
    
    solutionMap = new char[numOfRows][numOfColumns];
    char[][] map = new char[numOfRows][numOfColumns];
    
    maze = input2.nextLine(); // Accounts for the empty line left by input.nextInt();
    
    while (input2.hasNext()){
      for (int i = 0; i < numOfRows; i++){
        maze = input2.nextLine();
        for (int j = 0; j < numOfColumns; j++){
          //map[i][j] = maze.substring(j,j+1);
          map[i][j] = maze.charAt(j);
          if (map[i][j] == 'S'){ // establishes starting co-ordinates x and y
           posX = j;
           posY = i;
          } 
        }
      }
    }
    input2.close();
    
    //calls findPath method
    int findingPath = findPath(map, posY, posX, counter, tips);   

    System.out.println("Solution: " + steps);
    System.out.println("Tips: " + maxTips + "\n");
    for (int k = 0; k < numOfRows; k++){
      for (int l = 0; l < numOfColumns; l++){
        if (solutionMap[k][l] == '.'){
          solutionMap[k][l] = 'x';
        } 
      }
    }
    
    drawBoard(solutionMap, numOfRows, numOfColumns);
    ppmBoard(solutionMap, numOfRows, numOfColumns);
    
  }
  
  /**
  * findPath 
  * This method accepts the map, starting y positon, starting x position, counter and tips as parameters.
  * It will return a 0 if a deadend is found or recursively call itself to continue searching the maze if paths are available
  * Once it reaches a destination it will save the steps and the map before checking
  * if there are more solutions and repeating until no solutions are left
  * It will pick the solution with the greatest maxTip and save the optimal map and tip value to static variables
  * @Param map1, a 2D array which stores the current map   
  * @Param y, an int which stores the current y co-ordinate of Duber
  * @Param x, an int which stores the current x co-ordinate of Duber
  * @Param counter, an int which stores the amount of steps Duber has taken
  * @Param tips, an int which stores the amount of tip Duber has recieved
  * @Return 0 if no moves are left, return recursively otherwise
  */
  
  public static int findPath(char[][] map1, int y, int x, int counter, int tips) {
    
    char[][] map = duplicateArray(map1); //call duplicateArray method to duplicate map and track all possibilitie
    int up = 0, down = 0, right = 0, left = 0;
    
    //convert the char value of y and x to an int
    int intValues = Character.getNumericValue(map[y][x]); 
    int destination = -1;
    
    for (int m = 0; m < 10; m++) {
      if (intValues == m) { //checks if the int value is equal to an integer from 0-9 
        destination = m;  
      } 
    }
    
    //Base case; when the y and x co-ordinates reach a number
    if (map[y][x] == Integer.toString(destination).charAt(0)){ //Convert destination integer to a string and then convert string to char
      
      boolean finished = true;
      map[y][x] = 'X';
      
      //tip calculations
      if (destination - counter <= 0) { 
          tips += destination - counter;
      } else {
          tips += (destination - counter) * 10;
      }

      //searches for anymore integers (possible solutions) 
      for (int n = 0; n < map.length; n++){
        for (int o = 0; o < map[n].length; o++){
          for (int p = 0; p < 10; p++){
            if (map[n][o] == Integer.toString(p).charAt(0)){
              finished = false;
            }
          }
        }    
      }
                  
      if (finished == false){ //if possible solutions still remain
       for (int q = 0; q < map.length; q++){
         for (int r = 0; r < map[q].length; r++){
           if (map[q][r] == ('x')){
             map[q][r] = '.';   //backtrack so that any value of 'x' is now '.' and can be travelled on
           }
         }
       }
       return findPath(map, y, x, counter, tips); //return findPath at the location of the destination
       
      } else { //if no more possible solutions remain
                
        if (maxTips == Integer.MIN_VALUE){ //checks if maxTips is equal to the intialized minimum value
          maxTips = tips;
          steps = counter;
          for (int s = 0; s < map.length; s++) {
            for (int t = 0; t < map[s].length; t++) {
              solutionMap[s][t] = map[s][t];
            }
          }
                 
        } else { //if more than one destination exists
          
          if (tips > maxTips){
            maxTips = tips;
            steps = counter;
            for (int u = 0; u < map.length; u++){
              for (int v = 0; v < map[u].length; v++){
                solutionMap[u][v] = map[u][v];
              }
            }
          }
        }
      }
    }
    
    if (!((map[y][x] == ('X')) || (map[y][x] == ('S')))){
      map[y][x] = 'x';
    }
     
    if (!((map[y + 1][x] == ('#')) || (map[y + 1][x] == ('x')) || (map[y + 1][x] == ('S')) || (map[y + 1][x] == ('X')))) {  
      down = findPath(map, y + 1, x, counter + 1, tips);
    }
    if (!((map[y][x + 1] == ('#')) || (map[y][x + 1] == ('x')) || (map[y][x + 1] == ('S')) || (map[y][x + 1] == ('X')))) {
      right = findPath(map, y, x + 1, counter + 1, tips);
    }
    if (!((map[y][x - 1] == ('#')) || (map[y][x - 1] == ('x')) || (map[y][x - 1] == ('S')) || (map[y][x - 1] == ('X')))) {
      left = findPath(map, y, x - 1, counter + 1, tips);
    }
    if (!((map[y - 1][x] == ('#')) || (map[y - 1][x] == ('x')) || (map[y - 1][x] == ('S')) || (map[y - 1][x] == ('X')))) {
      up = findPath(map, y - 1, x, counter + 1, tips);
    }

    return 0;
  }


  
  /**
  * duplicateArray 
  * This method accepts the map and duplicates it
  * returns the copy of the map
  * @Param map, a 2D array which stores the current map
  * @Return copy - the copy made of the map
  */
  
  public static char[][] duplicateArray(char[][] map){
    char[][]copy = new char[map.length][map[0].length];
    for (int w = 0;  w < map.length; w++){
      for (int x = 0; x < map[w].length; x++){
        copy[w][x] = map[w][x];
      }
    }
    return copy;
  }
  
  /**
  * drawBoard 
  * This method accepts the map, the numOfRows, and numOfColumns
  * Prints the solved map to the console
  * @Param map, a 2D array which stores the current map
  * @Param numOfRows, an int which stores the number of rows of the map
  * @Param numOfColumns, an int which stores the number of columns of the map
  */
  
  public static void drawBoard(char[][] map, int numOfRows, int numOfColumns) {
    for (int y = 0; y < numOfRows; y++) {
      System.out.print("\n");
      for (int z = 0; z < numOfColumns; z++) {
        System.out.print(map[y][z]);
      }
    }
  }
  
  /**
  * ppmBoard 
  * This method accepts the map, the numOfRows, and numOfColumns
  * Saves the map to a ppm file
  * @Param map, a 2D array which stores the current map
  * @Param numOfRows, an int which stores the number of rows of the map
  * @Param numOfColumns, an int which stores the number of columns of the map
  */
  
  public static void ppmBoard(char[][] map, int numOfRows, int numOfColumns) throws Exception {
    
    File ppm = new File("maze.ppm");
    PrintWriter output = new PrintWriter(ppm);
    output.println("P3");
    output.println(numOfColumns * 20 + " " + numOfRows * 20);
    output.println("255");
    
    for (int a = 0; a < numOfRows; a++) {  
      for (int b = 0; b < 20; b++){  //magnify each pixels length by 20
        for (int c = 0; c < numOfColumns; c++) {
          for (int d = 0; d < 20; d++){ //magnify each pixels width by 20
            if (map[a][c] == '#'){
              output.println("0 0 0 ");  //black
            } else if (map[a][c] == 'x'){
              output.println("255 50 50 ");  //red
            } else if (map[a][c] == 'S'){
              output.println("90 255 150 ");  //green
            } else if (map[a][c] == 'X'){
              output.println("90 200 250 "); //blue
            } else {
              output.println("255 255 255 ");  //white
            }
          }
        }
      }
    }
    output.close();
  } 
    

}