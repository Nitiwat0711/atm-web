package th.ac.ku.atm.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import th.ac.ku.atm.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerRepository {

    private JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Customer> findAll() {
        String query = "SELECT * FROM customer";
        List<Customer> customers =
                jdbcTemplate.query(query, new CustomerRowMapper());
        return customers;
    }

    public Customer findById(String id) {
        String query = "SELECT * FROM customer WHERE id = " + id;
        Customer customer = jdbcTemplate.queryForObject(query, new CustomerRowMapper());
        return customer;
    }

    public void save(Customer customer) {
        String query = "INSERT INTO customer (id,name,pin) VALUES (?,?,?);";
        Object[] data =
                { customer.getId(), customer.getName(), customer.getPin()};
        jdbcTemplate.update(query, data);
    }

    public void deleteById(String id) {
        String query = "DELETE FROM customer WHERE id = " + id;
        jdbcTemplate.update(query);
    }

    class CustomerRowMapper implements RowMapper<Customer> {

        @Override
        public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            String pin = resultSet.getString("pin");

            Customer customer = new Customer(id, name, pin);
            return customer;

        }
    }
}
