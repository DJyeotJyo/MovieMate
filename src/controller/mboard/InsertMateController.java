package controller.mboard;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.mboard.MboardDAO;
import model.mboard.MboardDTO;
/**
 * Servlet implementation class InsertController
 */
@WebServlet("/InsertMateController")
public class InsertMateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertMateController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/mboard/mate_insert.jsp");
		rd.forward(request, response);
	}

	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	        
	 	System.out.println("dopost");
	 	
	 	response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String nickName = (String) session.getAttribute("userNick");
        
        System.out.println(nickName);

        MboardDTO mboard = new MboardDTO();
        mboard.setBoardTitle(title);
        mboard.setBoardContents(content);
        mboard.setMem_nick(nickName);;
        
        if (title == null|| title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("제목과 내용은 반드시 입력되어야 합니다.");
            return;
            
        }

        MboardDAO mdao = new MboardDAO();
        int insertResult = 0;

        try {
        	insertResult = mdao.insert(mboard);
            System.out.println(insertResult);
            if(insertResult>0) {
            	
            	Gson gson = new Gson();
            	String jsonResponse = gson.toJson(insertResult);

                // Send the response JSON back to the client
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.print(jsonResponse);
                out.flush();
            }else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("입력실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 

	    }
}