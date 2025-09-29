package productDao;

import model.Product;
import java.sql.SQLException;
import java.util.List;

public interface IProductDao {
    void insertProduct(Product pro) throws SQLException;
    Product selectProduct(int id);
    List<Product> selectAllActiveProducts();
    boolean updateProduct(Product pro) throws SQLException;
    boolean deactivateProduct(int id) throws SQLException; // soft delete
}
