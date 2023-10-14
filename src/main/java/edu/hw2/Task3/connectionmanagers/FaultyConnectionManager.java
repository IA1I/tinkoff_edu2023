package edu.hw2.Task3.connectionmanagers;

import edu.hw2.Task3.connections.Connection;
import edu.hw2.Task3.connections.FaultyConnection;

public class FaultyConnectionManager implements ConnectionManager{
    @Override
    public Connection getConnection() {
        return new FaultyConnection();
    }
}
