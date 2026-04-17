package com.moviemanagerexam.ticketgui.dal.database;

import com.moviemanagerexam.ticketgui.be.Event;
import com.moviemanagerexam.ticketgui.dal.DBConnector;
import com.moviemanagerexam.ticketgui.dal.IEventDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventDAO implements IEventDAO {
    private final DBConnector dbConnector;

    public EventDAO(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public List<Event> getAllEvents() throws Exception {
        String sql = """
                SELECT id, [name], start_date_time, end_date_time, [location], location_guidance, notes
                FROM dbo.events
                ORDER BY start_date_time
                """;
        List<Event> events = new ArrayList<>();

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                events.add(mapEvent(rs));
            }
        }

        return events;
    }

    @Override
    public Event createEvent(Event event) throws Exception {
        String sql = """
                INSERT INTO dbo.events ([name], start_date_time, end_date_time, [location], location_guidance, notes)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            fillEventStatement(statement, event);
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    event.setId(keys.getInt(1));
                    return event;
                }
            }
        }

        throw new SQLException("Failed to create event.");
    }

    @Override
    public void updateEvent(Event event) throws Exception {
        String sql = """
                UPDATE dbo.events
                SET [name] = ?, start_date_time = ?, end_date_time = ?, [location] = ?, location_guidance = ?, notes = ?
                WHERE id = ?
                """;

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            fillEventStatement(statement, event);
            statement.setInt(7, event.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteEvent(Event event) throws Exception {
        String sql = "DELETE FROM dbo.events WHERE id = ?";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, event.getId());
            statement.executeUpdate();
        }
    }

    private void fillEventStatement(PreparedStatement statement, Event event) throws SQLException {
        statement.setString(1, event.getName());
        statement.setTimestamp(2, toTimestamp(event.getStartDateTime()));
        statement.setTimestamp(3, toTimestamp(event.getEndDateTime()));
        statement.setString(4, event.getLocation());
        statement.setString(5, event.getLocationGuidance());
        statement.setString(6, event.getNotes());
    }

    private Event mapEvent(ResultSet rs) throws SQLException {
        Event event = new Event(
                rs.getInt("id"),
                rs.getString("name"),
                toLocalDateTime(rs.getTimestamp("start_date_time")),
                rs.getString("location")
        );
        event.setEndDateTime(toLocalDateTime(rs.getTimestamp("end_date_time")));
        event.setLocationGuidance(rs.getString("location_guidance"));
        event.setNotes(rs.getString("notes"));
        return event;
    }

    private Timestamp toTimestamp(LocalDateTime dateTime) {
        return dateTime == null ? null : Timestamp.valueOf(dateTime);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
