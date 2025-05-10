package itemservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import Users.User;
import item.model.Item;

public class ItemUtilService {
	
	private DataSource dataSource;
	
	public ItemUtilService(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	public List<Item> getAllItem() throws SQLException{
		Connection connection = null;
		Statement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			String sql = "select * from item order by id";
			ResultSet resultSet = statement.executeQuery(sql);
			
			List<Item> items = new ArrayList();
			while (resultSet.next()) {
					Item item = new Item();
				
					item.setId(resultSet.getInt("id"));
					item.setName(resultSet.getString("name"));
					item.setPrice(resultSet.getDouble("price"));
					item.setTotalNumber(resultSet.getInt("total_number"));
					
					items.add(item);
			}
			
			return items;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
			statement.close();
		}
		return new ArrayList();
	}
	
	public List<ShowItemDetails> getAllItemDetails() throws SQLException{
		Connection connection = null;
		Statement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			String sql = "SELECT * FROM ITEM i INNER JOIN ITEM_DETAILS it ON (i.ID = it.ITEM_ID)";
			ResultSet resultSet = statement.executeQuery(sql);
			
			List<ShowItemDetails> items = new ArrayList();
			while (resultSet.next()) {
				ShowItemDetails itemDetails = new ShowItemDetails();
				
				itemDetails.setId(resultSet.getInt("id"));
				itemDetails.setName(resultSet.getString("name"));
				itemDetails.setPrice(resultSet.getDouble("price"));
				itemDetails.setTotalNumber(resultSet.getInt("total_number"));
				itemDetails.setDescription(resultSet.getString("DESCRIPTION"));
				itemDetails.setModel(resultSet.getString("MODEL"));
				itemDetails.setType(resultSet.getString("TYPE_MODEL"));
				items.add(itemDetails);
			}
			
			return items;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
			statement.close();
		}
		return new ArrayList();
	}
	public List<ShowItemDetails> getAllItemDetails2() throws SQLException{
		Connection connection = null;
		Statement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			String sql = "SELECT * FROM ITEM i Full JOIN ITEM_DETAILS it ON (i.ID = it.ITEM_ID) order by id";
			ResultSet resultSet = statement.executeQuery(sql);
			
			List<ShowItemDetails> items = new ArrayList();
			while (resultSet.next()) {
				ShowItemDetails itemDetails = new ShowItemDetails();
				
				itemDetails.setId(resultSet.getInt("id"));
				itemDetails.setName(resultSet.getString("name"));
				itemDetails.setPrice(resultSet.getDouble("price"));
				itemDetails.setTotalNumber(resultSet.getInt("total_number"));
				itemDetails.setDescription(resultSet.getString("DESCRIPTION"));
				itemDetails.setModel(resultSet.getString("MODEL"));
				itemDetails.setType(resultSet.getString("TYPE_MODEL"));
				items.add(itemDetails);
			}
			
			return items;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
			statement.close();
		}
		return new ArrayList();
	}
	public List<ItemDetails> getItemDetails() throws SQLException{
		Connection connection = null;
		Statement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			String sql = "SELECT * FROM  ITEM_DETAILS order by ITEM_ID)";
			ResultSet resultSet = statement.executeQuery(sql);
			List<ItemDetails> items = new ArrayList();
			while (resultSet.next()) {
				ItemDetails item = new ItemDetails();
				
				item.setItemId(resultSet.getInt("ITEM_ID"));
				item.setDescription(resultSet.getString("DESCRIPTION"));
				item.setType(resultSet.getString("TYPE_MODEL"));
				item.setModel(resultSet.getString("MODEL"));
					
					items.add(item);
			}
			
			return items;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
			statement.close();
		}
		return new ArrayList();
	}
	 
	public void saveItem(Item item) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			
			String sql = "INSERT INTO item (NAME,PRICE,TOTAL_NUMBER)"
						+ " VALUES (?, ?, ?)";
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, item.getName());
			statement.setDouble(2, item.getPrice());
			statement.setInt(3, item.getTotalNumber());
			
			statement.execute();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
			statement.close();
		}
	}
	
	
	
	public void saveUser(User user) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			
			String sql = "INSERT INTO USERS  (Username,Password)"
						+ " VALUES (?, ?)";
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			
			
			statement.execute();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
			statement.close();
		}
	}
	
	public boolean getConnection(User user) throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    boolean flag = false;
	    try {
	        connection = dataSource.getConnection();

	        // Prepare SQL with placeholders for username and password
	        String sql = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
	        statement = connection.prepareStatement(sql);

	        // Set values for placeholders
	        statement.setString(1, user.getUsername());
	        statement.setString(2, user.getPassword());

	        resultSet = statement.executeQuery();

	        // Check if a result is returned (i.e., a matching user is found)
	        flag = resultSet.next();

	        return flag;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (resultSet != null) {
	            resultSet.close();
	        }
	        if (statement != null) {
	            statement.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
	    }
	    return flag;
	}
	
	
	
	
	public void saveItemDetails(ItemDetails itemDetails) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			
			String sql = "INSERT INTO item_details (model, type_model, description, item_id) VALUES "
					+ "(?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, itemDetails.getModel());
			statement.setString(2, itemDetails.getType());
			statement.setString(3, itemDetails.getDescription());
			statement.setInt(4, itemDetails.getItemId());
			
			statement.execute();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
			statement.close();
		}
	}
	
	
	public Item findItemById(int id) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			
			String sql = "select * from item where id = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			
			ResultSet resultSet = statement.executeQuery();
			
			Item item = new Item();
			if (resultSet.next()) {
					item.setId(resultSet.getInt("id"));
					item.setName(resultSet.getString("name"));
					item.setPrice(resultSet.getDouble("price"));
					item.setTotalNumber(resultSet.getInt("total_number"));
			}
			
			return item;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
			statement.close();
		}
		return null;
	}
	
	public ItemDetails findItemDetailsById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			
			String sql = "select * from ITEM_DETAILS where ITEM_ID = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			
			ResultSet resultSet = statement.executeQuery();
			
			ItemDetails item = new ItemDetails();
			if (resultSet.next()) {
					item.setItemId(resultSet.getInt("ITEM_ID"));
					item.setDescription(resultSet.getString("DESCRIPTION"));
					item.setType(resultSet.getString("TYPE_MODEL"));
					item.setModel(resultSet.getString("MODEL"));
			}
			
			return item;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	
	public void deleteItemD(int id) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			
			String sql = "DELETE from ITEM_DETAILS where ITEM_ID = ?";
			statement = connection.prepareStatement(sql);
			
			statement.setInt(1, id);
			
			statement.execute();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
			statement.close();
		}
	}
	
	public void deleteItem(int id) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			deleteItemD(id);
			String sql = "DELETE from Item where id = ?";
			statement = connection.prepareStatement(sql);
			
			statement.setInt(1, id);
			
			statement.execute();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
			statement.close();
		}
	}
	

	public void updateItem(Item item) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			
			String sql = "UPDATE ITEM SET NAME =?, PRICE=?, TOTAL_NUMBER=? WHERE id = ?";
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, item.getName());
			statement.setDouble(2, item.getPrice());
			statement.setInt(3, item.getTotalNumber());
			statement.setInt(4, item.getId());
			statement.execute();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
			statement.close();
		}
	}
	public void updateItemD(ItemDetails item) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			
			String sql = "UPDATE item_details SET DESCRIPTION =?, MODEL=?, TYPE_MODEL=? WHERE ITEM_ID = ?";
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, item.getDescription());
			statement.setString(2, item.getModel());
			statement.setString(3, item.getType());
			statement.setInt(4, item.getItemId());
			statement.execute();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
			statement.close();
		}
	
}
}
