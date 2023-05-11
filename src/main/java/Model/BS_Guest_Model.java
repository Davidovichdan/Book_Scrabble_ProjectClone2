package Model;

import Model.GameData.*;
import Model.GameLogic.ClientCommunicationHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;
import java.util.regex.Pattern;

public class BS_Guest_Model extends Observable implements BS_Model {
    private static BS_Guest_Model model_instance = null;
    public String[] playersScores;
    Socket socket;
    Tile[][] tileBoard;
    Player player; // TODO: 04/05/2023 implement player class and send it to the host
    ClientCommunicationHandler communicationHandler = new ClientCommunicationHandler();

    private BS_Guest_Model() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the ip address of the server");
        String ip = scanner.nextLine();
        System.out.println("Please enter the port of the server");
        int port = scanner.nextInt();
        scanner.close();
        player = new Player();
        playersScores = new String[0];
    }

    public static BS_Guest_Model getModel() {
        return BS_Guest_ModelHolder.BSGuestModelInstance;
    }

    public void setPlayerProperties(String name) {
        this.player.set_name(name);
    }

    public ClientCommunicationHandler getCommunicationHandler() {
        return communicationHandler;
    }

    public void openSocket(String ip, int port) { //button start in the view
        //if (validateIpPort(ip, port)) {
        try {
            socket = new Socket(ip, port);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        communicationHandler.setCom();
        //communicationHandler.outMessages("addPlayer:" + player.get_name()); // FIXME: 10/05/2023 need to be changed
//        } else {
//            throw new RuntimeException("Invalid ip or port");
//        }
    }

    private boolean validateIpPort(String ip, int port) {
        // Regular expression for IPv4 address
        String ipv4Regex = "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$";
        // Regular expression for port number (1-65535)
        String portRegex = "^([1-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$";
        // Validate IP address
        if (!Pattern.matches(ipv4Regex, ip)) {
            return false;
        }
        // Validate port number
        return Pattern.matches(portRegex, Integer.toString(port));
    }

    public Socket getSocket() {
        return socket;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void passTurn(int id) {
        communicationHandler.outMessages("passTurn:" + id);
    }

    public void tryPlaceWord(String word, int x, int y, boolean isVertical) {
        String message = word + ":" + x + ":" + y + ":" + isVertical;
        String id = String.valueOf(player.get_id());
        communicationHandler.outMessages("tryPlaceWord:" + id + ":" + message);
    }

    public void challengeWord(String word) {
        String id = String.valueOf(player.get_id());
        communicationHandler.outMessages("challengeWord:" + id + ":" + word);
        //send challengeWord:id:word (word is the word that the player wants to challenge)
    }

    @Override
    public void setNextPlayerIndex(int index) {

    }

    @Override
    public int getCurrentPlayerScore() {
        return 0;
    }

    @Override
    public List<Tile> getCurrentPlayerHand() {
        return null;
    }

    @Override
    public Tile[][] getBoardState() {
        return tileBoard;
    }

    @Override
    public int[] getBagState() {
        return new int[0];
    }

    @Override
    public String getWinner() {
        return null;
    }

    @Override
    public boolean isHost() {
        return false;
    }

    @Override
    public boolean isGameOver() {
        communicationHandler.outMessages("isGameOver:");
        // TODO get the answer from the server and return it
        return false;
    }

    @Override
    public void setGameOver(boolean isGameOver) {
        communicationHandler.outMessages("setGameOver:" + isGameOver);
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    public void setBoard(Tile[][] boardTiles) {
        this.tileBoard = boardTiles;
        setChanged();
        notifyObservers("board:");
    }

    public void setPlayersScores(String id, String score) {
        playersScores[Integer.parseInt(id)] += score;
        setChanged();
        notifyObservers("tryPlaceWord:" + id + ":" + score);
    }

    private static class BS_Guest_ModelHolder {
        private static final BS_Guest_Model BSGuestModelInstance = new BS_Guest_Model();
    }
}
