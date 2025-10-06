package productDao;

import dao.DBConnection;
import static dao.DBConnection.getConnection;
import model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao implements IProductDao {

    private static final String INSERT_SQL = "INSERT INTO Product (name, price, description, stock, status) VALUES (?,?,?,?,?)";
    private static final String SELECT_BY_ID = "SELECT * FROM Product WHERE id=?";
    private static final String SELECT_ACTIVE = "SELECT * FROM Product WHERE status=1";
    private static final String UPDATE_SQL = "UPDATE Product SET name=?, price=?, description=?, stock=?, status=? WHERE id=?";
    private static final String DEACTIVATE_SQL = "UPDATE Product SET status=0 WHERE id=?";

    @Override
    public void insertProduct(Product pro) throws SQLException {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setString(1, pro.getName());
            ps.setDouble(2, pro.getPrice());
            ps.setString(3, pro.getDescription());
            ps.setInt(4, pro.getStock());
            ps.setBoolean(5, pro.isStatus());
            ps.executeUpdate();
        }
    }

    @Override
    public Product selectProduct(int id) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> selectAllActiveProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE status = 1 ORDER BY id";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setDescription(rs.getString("description"));
                p.setStock(rs.getInt("stock"));
                p.setStatus(rs.getBoolean("status"));
                products.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public boolean updateProduct(Product pro) throws SQLException {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, pro.getName());
            ps.setDouble(2, pro.getPrice());
            ps.setString(3, pro.getDescription());
            ps.setInt(4, pro.getStock());
            ps.setBoolean(5, pro.isStatus());
            ps.setInt(6, pro.getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deactivateProduct(int id) throws SQLException {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(DEACTIVATE_SQL)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getDouble("price"));
        p.setDescription(rs.getString("description"));
        p.setStock(rs.getInt("stock"));
        p.setImportDate(rs.getTimestamp("import_date"));
        p.setStatus(rs.getBoolean("status"));
        return p;
    }
}
