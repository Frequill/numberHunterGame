public class Main {
    public static void main(String[] args) {
        System.out.println("Player is not meant to see this developer console...");
        System.out.println("Current amount of active threads: " + Thread.activeCount()); // INFO (There are 4 of these)

        GameUI game = new GameUI();


        // TO-DO:

        // 1. Hur får jag programmet att loopa utan en loop? Det skall gå att "resetta" GUI:n och återgå till spelet

        // 2. Exakt hur loopar jag ett object?
    }
}
