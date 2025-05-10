package itemservice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import Users.User;
import item.model.Item;

/**
 * Servlet implementation class ItemController
 */
@WebServlet("/ItemController")
public class ItemController extends HttpServlet {
	
	@Resource(name = "jdbc/web_item")
	private DataSource dataSource;
    
	private ItemUtilService itemUtilService;

    
	@Override
	public void init() throws ServletException {
		itemUtilService = new ItemUtilService(dataSource);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String finalAction = request.getParameter("action");
		
		if (finalAction == null) {
			finalAction = "Sign";
		}
		
		switch(finalAction) {
			case "ADD":
				addItem(request, response);
				break;
			case "LOAD_ITEMS":
				loadItems(request, response);
				break;
			case "LOAD_ITEM":
				loadItem(request, response);
				break;
			case "DELETE":
				deleteItem(request, response);
				break;
			case "UPDATE":
				updateItem(request, response);
				break;
			case "ADD_ITEM_DETAILS":
				addItemDetails(request, response);
				break;
			case "LOAD_ITEM_DETAILS":
				loadItemDetails(request, response);
				break;
			case "Update_ITEM_DETAILS":
				UpdateITEMDETAILS(request, response);
				break;
			case "LOAD_ITEMS_DETAILS":
				lOADITEMSDETAILS(request, response);
				break;
			case "Sign":
				sign(request, response);
				break;
			case "Sign_In":
				signIn(request, response);
				break;
			case "Sign Up":
				signUp(request, response);
				break;	
			case "SIGN_OUT":
	            signOut(request, response);
				
			default:
				loadItems(request, response);
		}
	}
	
	
	
	

	private void signOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		  HttpSession session = request.getSession(false);
		    if (session != null) {
		        session.invalidate();
		    }

		    // Delete the "usernamee" cookie
		    Cookie cookie = new Cookie("usernamee", "");
		    cookie.setMaxAge(0);  // Expire the cookie immediately
		    cookie.setPath("/");  // Ensure the cookie is valid across the entire domain
		    response.addCookie(cookie);

		    // Redirect to login page
		    response.sendRedirect("login.jsp");
	}
    // Additional methods like loading item details, etc.
		
	

	private void sign(HttpServletRequest request, HttpServletResponse response) {
		
		try {	
			
			
			// Forward the wrapped request
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void signUp(HttpServletRequest request, HttpServletResponse response) {
		try {
			String username = request.getParameter("usernameup");
			String password = (request.getParameter("passwordup"));
			
		
			User user = new User(username,password);
		
			itemUtilService.saveUser(user);
			
			 HttpSession session = request.getSession();
		        session.setAttribute("username", username);
		        session.setAttribute("isAuthenticated", true);

		        // Optionally, create a cookie to remember the user
		        Cookie usernameCookie = new Cookie("usernamee", username);
		        usernameCookie.setMaxAge(50); // 30 days
		        usernameCookie.setPath(request.getContextPath());
		        response.addCookie(usernameCookie);

		        // Redirect the user to the items page or appropriate page after signup
		        response.sendRedirect("ItemController?action=LOAD_ITEMS");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private boolean isAuthenticated(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return session != null && session.getAttribute("isAuthenticated") != null
		&& (Boolean) session.getAttribute("isAuthenticated");
		}

	private void signIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
	        String username = request.getParameter("usernamein");
	        String password = request.getParameter("passwordin");
	        User user = new User(username,password);
	        boolean isAuthenticated = itemUtilService.getConnection(user);
	        
	        if (isAuthenticated) {
	            // Create a new session or get existing one
	            HttpSession session = request.getSession();
	         
	            
	            // Store user information in session
	            session.setAttribute("username", username);
	            session.setAttribute("isAuthenticated", true);
	            
	            //session.setMaxInactiveInterval(10);
	            
	            Cookie authCookie = new Cookie("usernamee", username);
	            authCookie.setMaxAge(30);  // Set cookie expiration (e.g., 30 days)
	          // authCookie.setPath("/");  // Make the cookie available throughout the app
	            response.addCookie(authCookie);
	            loadItems(request, response);
	        } else {
	            // Handle failed login
	            request.setAttribute("errorMessage", "Invalid username or password");
	            request.getRequestDispatcher("/login.jsp").forward(request, response);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle error appropriately
	        request.setAttribute("errorMessage", "An error occurred during sign in");
	        request.getRequestDispatcher("/login.jsp").forward(request, response);
	    }
		
		
		
	}

	private void lOADITEMSDETAILS(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
		
			ItemDetails item = itemUtilService.findItemDetailsById(id);
			
			request.setAttribute("existedItem", item);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/Update-Item-D.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void UpdateITEMDETAILS(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String type = request.getParameter("type");
			String model = request.getParameter("model");
			String description = request.getParameter("description");
			
			ItemDetails item = new ItemDetails(type, model, description, id);
			itemUtilService.updateItemD(item);
			
			
			loadItems(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	


	void addItem(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			String name = request.getParameter("nameItem");
			double price = Double.parseDouble(request.getParameter("price"));
			int totalNumber = Integer.parseInt(request.getParameter("totalNumber"));
			
			Item item = new Item(name, price, totalNumber);
			itemUtilService.saveItem(item);
			
			loadItems(request, response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	void addItemDetails(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			String type = request.getParameter("type");
			String model = request.getParameter("model");
			String description = request.getParameter("Description");
			int item_id = Integer.parseInt(request.getParameter("id"));
			
			ItemDetails itemDetails = new ItemDetails(type, model, description, item_id);
			itemUtilService.saveItemDetails(itemDetails);
			
			loadItems(request, response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	

	void loadItems(HttpServletRequest request, HttpServletResponse response) {
			
		try {	
			List<Item> items = itemUtilService.getAllItem();
			List<ShowItemDetails> it = itemUtilService.getAllItemDetails2();
			request.setAttribute("allItems", items);
			request.setAttribute("allIt", it);
			
			// Forward the wrapped request
			RequestDispatcher dispatcher = request.getRequestDispatcher("/show-items.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	void loadItem(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			int id = Integer.parseInt(request.getParameter("id"));
		
			Item item = itemUtilService.findItemById(id);
			
			request.setAttribute("existedItem", item);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/update-item.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	void loadItemDetails(HttpServletRequest request, HttpServletResponse response) {
		
		try {	
			List<ShowItemDetails> items = itemUtilService.getAllItemDetails();
			
			request.setAttribute("allItemsDetails", items);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/show-items-details.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void deleteItem(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			int id = Integer.parseInt(request.getParameter("id"));
		
			// TODO   delete child item details if exist
			itemUtilService.deleteItem(id);
			
			loadItems(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void updateItem(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("nameItem");
			double price = Double.parseDouble(request.getParameter("price"));
			int totalNumber = Integer.parseInt(request.getParameter("totalNumber"));
			
			Item item = new Item(id, name, price, totalNumber);
			itemUtilService.updateItem(item);
			
			
			loadItems(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
