class exp {
    public static void main(String[] args) {
        BookingID f = new BookingID(2);
 
        Thread t1 = new Thread(f);
        t1.setName("Drake");
        Thread t2 = new Thread(f); 
        t2.setName("Ravi");
        Thread t3 = new Thread(f);
        t3.setName("Anjana");
        System.out.println("Welcome to my bus service!");
        System.out.println("We have 5 tickets available");

        t1.start();
        t2.start();
        t3.start();
    }
}

class BookingID extends Thread {
    static int vacant = 5;
    int required;

    BookingID(int required) {
        this.required = required;
    }

    public synchronized void run() {
       
        if (vacant >= required) {
            System.out.println(required + " Tickets Have Been Booked For: " + Thread.currentThread().getName() + "!");
            try {
                Thread.sleep(200);
            } catch (Exception e) {
            }

            vacant -= required;
        } else {
            System.out.println("Sorry " + Thread.currentThread().getName() + ", But The BUS is Booked! (Only "
                    + vacant + " Seat(s) Available)");
        }
    }

}