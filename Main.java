import java.util.Scanner;

import DbModels.taxi;

class Main {
  public static void main(String[] args) {
    System.out.println("Hello, World");

    // key file

      new taxi(100, -1, 0,  'a', 'a').createTaxi();
      new taxi(101, -1, 0,  'a', 'a').createTaxi();
      new taxi(102, -1, 0,  'a', 'a').createTaxi();
      new taxi(103, -1, 0,  'a', 'a').createTaxi();

      //testing
      System.out.println(new taxi(1001,9,'a','d').searchTaxi());
      System.out.println(new taxi(1002,9,'a','d').searchTaxi());
      System.out.println(new taxi(1003,9,'a','d').searchTaxi());
      System.out.println(new taxi(1004,9,'a','d').searchTaxi());
      System.out.println(new taxi(1006,9,'a','d').searchTaxi());
      System.out.println(new taxi(1007,9,'a','d').searchTaxi());
      System.out.println(new taxi(1008,1,'a','c').searchTaxi());
      System.out.println(new taxi(1009,9,'b','f').searchTaxi());
      System.out.println(new taxi(1010,9,'d','f').searchTaxi());
      System.out.println(new taxi(1011,40,'f','a').searchTaxi());
      System.out.println(new taxi(1012,23,'f','a').searchTaxi());
      System.out.println(new taxi(1013,50,'f','a').searchTaxi());

      System.out.println("\t*********** Welcome to my Taxi Booking **************");
      int choice  = 0;
      Scanner sc = new Scanner(System.in);
      do{
        System.out.println("\n1)Book a call Taxi ");
        System.out.println("2)Detail of taxis ");
        System.out.println("3)Exit ");
        System.out.print("Enter your choice : ");
        
        choice = sc.nextInt();

        switch(choice){
          case 1:
            System.out.print("Enter the customerId : ");
            int customerId = sc.nextInt();
            if(customerId > 9999 || customerId < 999){
              System.out.println("Custome Id should be four digit number");
              break;
            }
            System.out.print("Enter the PickupPoint : ");
            char source = sc.next().charAt(0);
            if(source < 'a' || source > 'f'){
              System.out.println("There is not place like that please choose between a - f and lowercase");
              break;
            }

            System.out.print("Enter the DropPoint : ");
            char destination = sc.next().charAt(0);
            if(destination < 'a' || destination > 'f'){
              System.out.println("There is not place like that please choose between a - f and lowercase");
              break;
            }else if(destination == source){
              System.out.println("From that we know you are slightly stupid BTW In case you are wondering "+
                                "why? you are already in the location you are trying to go.");
            }

            System.out.print("Enter the PickupTime : ");
            int bookedOn = sc.nextInt();
            if(bookedOn > 24 || bookedOn < 0){
              System.out.println("You are obviously lack common sense and brain cells. In futher please sure "+
                                "know that is only 24 hrs in a day.");
              break;
            }
            System.out.println(new taxi(customerId, bookedOn, source, destination).searchTaxi());
            break;
          case 2:
            taxi.printStatus();
            break;
          case 3:
            System.out.println("Good bye !!")
            break;
          default:
            System.out.println("Now Now don't be stupid ");
        }

      }while(choice != 3);

      sc.close();
    
  }
}