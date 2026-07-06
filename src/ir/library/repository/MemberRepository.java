package ir.library.repository;

import ir.library.exception.MemberNotFoundException;
import ir.library.exception.DatabaseRepositoryException;
import ir.library.model.Member;
import ir.library.util.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public Member read(Long id) throws DatabaseRepositoryException {
        Connection connection = DatabaseConfig.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM members WHERE id = ?"
        )) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new MemberNotFoundException("Member Not Found!");
            }

            return new Member(
                    rs.getLong("id"),
                    rs.getString("full_name"),
                    rs.getString("phone_number")
            );
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("PostgreSQL Syntax Incorrect!");
        }
    }

    public Member update(Member member) throws DatabaseRepositoryException {
        Connection connection = DatabaseConfig.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE members SET full_name = ?, phone_number = ? WHERE id = ?"
        )) {
            ps.setString(1, member.getFullName());
            ps.setString(2, member.getPhoneNumber());
            ps.setLong(3, member.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) throw new MemberNotFoundException("Member Not Found!");

            System.out.println("Member Updated Successfully!");
            return new Member(member.getId(), member.getFullName(), member.getPhoneNumber());
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("PostgreSQL Syntax Incorrect!");
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
