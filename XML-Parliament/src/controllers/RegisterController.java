package controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import session.KorisnikDaoLocal;



public class RegisterController extends HttpServlet {

	private static final long serialVersionUID = -6820366488786163882L;
	
	private static Logger log = Logger.getLogger(RegisterController.class);
	
	@EJB
	private KorisnikDaoLocal korisnikDao;
	
	@PersistenceContext(unitName = "XML-Parlieament")
	EntityManager em;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
		
	}

	protected void doPost(HttpServletRequest request, 	HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}
}