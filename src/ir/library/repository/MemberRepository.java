package ir.library.repository;

import ir.library.exception.MemberNotFoundException;
import ir.library.exception.DatabaseRepositoryException;
import ir.library.model.Member;
import ir.library.util.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MemberRepository {
    public Long insert(Member member) throws DatabaseRepositoryException {
        Connection connection = DatabaseConfig.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO members (full_name, phone_number) VALUES (?, ?) RETURNING id"
        )) {
            ps.setString(1, member.getFullName());
            ps.setString(2, member.getPhoneNumber());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong("id");

            throw new DatabaseRepositoryException("Member ID Not Returned!");
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Member Insertion to Database Failed!");
        }
    }

    public Optional<Member> findById(Long id) throws DatabaseRepositoryException {
        Connection connection = DatabaseConfig.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM members WHERE id = ?"
        )) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Member member = new Member(rs.getString("full_name"), rs.getString("phone_number"));
                member.setId(id);
                return Optional.of(member);
            }

            return Optional.empty();
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Member Find By ID From Database Failed!");
        }
    }

    public boolean updateFullName(Long id, String newFullName) throws DatabaseRepositoryException {
        Connection connection = DatabaseConfig.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE members SET full_name = ? WHERE id = ?"
        )) {
            ps.setString(1, newFullName);
            ps.setLong(2, id);

            int rowsAffected = ps.executeUpdate();

            return !(rowsAffected == 0);
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Update Member Full Name From Database Failed!");
        }
    }

    public Long delete(Long id) throws DatabaseRepositoryException {
        Connection connection = DatabaseConfig.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM members WHERE id = ?"
        )) {
            ps.setLong(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new MemberNotFoundException("Member Not Found!");
            }

            System.out.println("Member Deleted Successfully!");
            return id;
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("PostgreSQL Syntax Incorrect!");
        }
    }

    public boolean existByPhoneNumber(String phoneNumber) throws DatabaseRepositoryException {
        Connection connection = DatabaseConfig.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM members WHERE phone_number = ?"
        )) {
            ps.setString(1, phoneNumber);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("PostgreSQL Syntax Incorrect!");
        }
    }
}
