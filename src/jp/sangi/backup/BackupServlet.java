package jp.sangi.backup;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class BackupServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//共通パラメータ受け取り
		req.setCharacterEncoding("UTF-8");
		int type = Integer.parseInt(req.getParameter("opttype"));	//Geometric Distribution
		int s = Integer.parseInt(req.getParameter("s"));		    //System backup costs : S
		double h = Double.parseDouble(req.getParameter("h"));		//System loss cost : h 
		double p = Double.parseDouble(req.getParameter("p"));		//System loss probability 
		int t = Integer.parseInt(req.getParameter("t"));		    //Backup interval : T
		if(type == 1){
			Backup backup = new Backup(s, h, p, t);
			backup.computeBackup();
			backup.computeBackupAll();
			req.setAttribute("res",backup);
			this.getServletContext().getRequestDispatcher("/Backup_Geo.jsp").forward(req,resp);
		}
		else if(type == 2){
			double alpha = Double.parseDouble(req.getParameter("alpha"));
			double beta = Double.parseDouble(req.getParameter("beta"));
			Backup backup = new Backup(s, h, p, t, alpha, beta);
			backup.computeBackup_Weibull();
			backup.computeBackup_Weibull_All();
			req.setAttribute("res",backup);
			this.getServletContext().getRequestDispatcher("/Backup_Weibull.jsp").forward(req,resp);
		}
		else if(type == 3){
			int n = Integer.parseInt(req.getParameter("n"));
			int typemethod = Integer.parseInt(req.getParameter("min-max"));
			Backup backup = new Backup(s, h, p, t, n);
			if(typemethod == 0){
				backup.computeBackup_multi_min();
				backup.computeBackup_multi_minAll();
			}else{
				backup.computeBackup_multi_max();
				backup.computeBackup_multi_maxAll();
			}
			req.setAttribute("res",backup);
			this.getServletContext().getRequestDispatcher("/Backup_Geo.jsp").forward(req,resp);
		}		
	}
}
