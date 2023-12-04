module cz.krokviak.donkeykong {
    requires javafx.controls;
    requires javafx.fxml;
    opens cz.krokviak.donkeykong to javafx.fxml;
    exports cz.krokviak.donkeykong.main;
    exports cz.krokviak.donkeykong.gamestate;
    exports cz.krokviak.donkeykong.input;
    exports cz.krokviak.donkeykong.drawable;
    exports cz.krokviak.donkeykong.objects;
    opens cz.krokviak.donkeykong.main to javafx.fxml;
    exports cz.krokviak.donkeykong.items;
    exports cz.krokviak.donkeykong.objects.barrels;
    exports cz.krokviak.donkeykong.objects.ladder;
    exports cz.krokviak.donkeykong.objects.climb;
    exports cz.krokviak.donkeykong.objects.player;
    exports cz.krokviak.donkeykong.objects.princess;
}