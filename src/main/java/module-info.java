module cz.krokviak.donkeykong {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    opens cz.krokviak.donkeykong to javafx.fxml;
    exports cz.krokviak.donkeykong.main;
}
