package edu.hw2.Task3.connectionmanagers;

import edu.hw2.Task3.Chance;
import edu.hw2.Task3.connections.Connection;
import edu.hw2.Task3.connections.FaultyConnection;
import edu.hw2.Task3.connections.StableConnection;

public class DefaultConnectionManager implements ConnectionManager{
    @Override
    public Connection getConnection() {
        if(Chance.getChance()){
            return new StableConnection();
        } else {
            return new FaultyConnection();
        }
    }
}
