package lt.telesoftas.lab;

import java.awt.*;
import java.util.Random;

public class maze {

 int SizeX = 16;
 int SizeY = 16;
 private int[][] map;
 
 public int Wall = 1;
 public int Flr = 0;
 public int Start = 3;
 public int Finish = 2;
 
 Random r = new Random();

 public int [][] getMap() {
	 return map;
 }
 
 public void init(int sizeX, int sizeY) {
  //generatemaze(r.nextInt(70)+30);
	 this.SizeX = sizeX;
	 this.SizeY = sizeY;
	 map = new int[ SizeX ][ SizeY ];
  generatemaze(SizeX, SizeY, 20); 
  printMaze();
 }

// public void paint(Graphics g) {
//
//  g.drawString("Press mouse in Applet to create new maze", 0, getSize().height-12 );
//  for(int x=0;x<SizeX;x++)
//  {
//   for(int y=0;y<SizeY;y++)
//   {
//    if(map[x][y]==4)
//    {
//     g.fillRect(x*10,y*7,10,7);
//    }
//   }
//  }
// }

 public void generatemaze(int sizeX, int SizeY, int straightness)
 {
  // Maze Generator original by John Chase

  int NotChecked    = 0;
  int Checked    = 1;
  int Open          = 2;
  int NotOpen       = 3;
  int AllChecked    = SizeX * SizeY;
  int LastDirection   = r.nextInt(4);
  int North         = Open;
  int South         = Open;
  int East          = Open;
  int West          = Open;
  boolean TimeUp     = false;
  boolean Moved   = false;
  int NumFailedMoves   = 0;
  boolean ChangeDirection = false;
  boolean tocheckdirection= false;
  int CurrentX   = 0;
  int CurrentY   = 0;
  int Dir     = 0;
  long LastDrawTimer  = 0;
  boolean Done   = false;
  //make map array fill with walls
  for(int x=0;x < SizeX - 1 ; x++ )
  {
   for(int y=0;y < SizeY - 1 ; y++ )
   {
    map[x][y] = Wall;
   }
  }

  //pick a random cell and mark it as Flr hold in 1 cell from the edge
  CurrentX = r.nextInt( SizeX - 3 ) + 2;
  CurrentY = r.nextInt( SizeY - 3 ) + 2;

  while(TimeUp != true)
  {
   if(tocheckdirection!=true)
   {

    //pick a direction
    Moved = false;
    NumFailedMoves = 0;
    ChangeDirection = true;

    //check strightness factor
    //otherwise random percent chance
    if(r.nextInt(100) < straightness)
    {
     ChangeDirection = false;
     Dir = LastDirection;
    }

    //keep trying till you find a direction open
    while(Moved != true)//Repeat
    {
     //pick a direction to move at random
     if(ChangeDirection == true)
     {
      Dir = r.nextInt(4);//see here for bugs
      LastDirection = Dir;
     }

     ChangeDirection = true;

     switch(Dir)
     {
     //north
     case 0:
      if(North == Open)
      {
       Moved = true;
       CurrentY--;
      }
      break;
     //south
     case 1:
      if(South == Open)
      {
       Moved = true;
       CurrentY++;
      }
      break;
     //east
     case 2:
      if(East == Open)
      {
       Moved = true;
       CurrentX++;
      }
      break;
     //West
     case 3:
      if(West == Open)
      {
       Moved = true;
       CurrentX--;
      }
      break;
     }

    }//Until Moved = True
    //mark the map
    map[CurrentX][CurrentY] = Flr;
    LastDrawTimer = System.currentTimeMillis();
   }

   tocheckdirection = false;
   //step 3 from current cell check N,S,E,W in a random style
   //first set all direction Direction checked
   North = NotOpen;
   South = NotOpen;
   East  = NotOpen;
   West  = NotOpen;

   //check all 4 directions
   //;north
   //;out of bounds?
   if(CurrentY-2 < 0)
   {
    North = NotOpen;
   }
   else if(map[CurrentX][CurrentY-1] == Wall && map[CurrentX][CurrentY-2] == Wall)
   {
    if(map[CurrentX-1][CurrentY-1] == Wall && map[CurrentX+1][CurrentY-1] == Wall)
    {
     North = Open;
    }
    else
    {
    North = NotOpen;
    }
   }

   //south
   //out of bounds?
   if(CurrentY+2 > SizeY - 1 )
   {
    South = NotOpen;
   }
   else if(map[CurrentX][CurrentY+1] == Wall && map[CurrentX][CurrentY+2] == Wall)
   {
    if(map[CurrentX-1][CurrentY+1] == Wall && map[CurrentX+1][CurrentY+1] == Wall)
    {
     South = Open;
    }
    else
    {
     South = NotOpen;
    }
   }
   //;east
   if(CurrentX+2 > SizeX - 1 )
   {
    East = NotOpen;
   }
   else if(map[CurrentX+1][CurrentY] == Wall && map[CurrentX+2][CurrentY] == Wall )
   {
    if(map[CurrentX+1][CurrentY-1] == Wall && map[CurrentX+1][CurrentY+1] == Wall)
    {
     East = Open;
    }
    else
    {
     East = NotOpen;
    }
   }
   //west
   //out of bounds?
   if(CurrentX-2 < 0)
   {
    West = NotOpen;
   }
   else if(map[CurrentX-1][CurrentY] == Wall && map[CurrentX-2][CurrentY] == Wall)
   {
    if(map[CurrentX-1][CurrentY-1] == Wall && map[CurrentX-1][CurrentY+1]== Wall)
    {
     West = Open;
    }
    else
    {
     West = NotOpen;
    }
   }

   //;if time passes without finding anything we are done
   if( System.currentTimeMillis() - LastDrawTimer > 100 ) TimeUp = true;
   //now what happens if all directions are not open
   if(North == NotOpen && South == NotOpen && East == NotOpen && West == NotOpen && TimeUp == false)
   {
    Done = false;
    //pick a random already floored location and try again
    while(Done !=true)
    {
     CurrentX = r.nextInt( SizeX - 2 ) + 1;
     CurrentY = r.nextInt( SizeY - 2 ) + 1;
     if(map[CurrentX][CurrentY] == Flr) Done = true;
    }
    //goto checkdirection
    tocheckdirection=true;
   }

  }
  
  for (int i = 0; i < SizeX; i++) {
	  map[i][0] = Wall;
	  map[i][SizeX - 1] = Wall;
  }
  for (int i = 0; i < SizeY; i++) {
	  map[0][i] = Wall;
	  map[SizeY - 1][i] = Wall;
  }
  
  
  map[0][1] = Start;
  map[1][1] = Flr;
  map[2][1] = Flr;
  map[1][2] = Flr;
  map[2][2] = Flr;
  map[SizeX - 2][SizeY - 2] = Flr;
  map[SizeX - 2][SizeY - 3] = Flr;
  map[SizeX - 3][SizeY - 2] = Flr;
  map[SizeX - 3][SizeY - 3] = Flr;
  map[SizeX - 1][SizeY - 2] = Finish;
  

 }

 public void printMaze() {
	 for (int i = 0; i < SizeX; i++){
		 for (int j = 0; j < SizeY; j++)
		 {
			 if (map[j][i] == Start) {
				 System.out.print('S');
			 }
			 else if (map[j][i] == Finish) {
				 System.out.print('F');
			 }
			 else if (map[j][i] == Wall) {
				 System.out.print('X');
			 }
			 else {
				 System.out.print(' ');
			 }
		 }
		 System.out.println();
	 }
	 System.out.println("-----------");
 }

}