package com.sist.temp;

import javax.servlet.http.*;

import com.sist.movie.model.MemberModel;
import com.sist.movie.model.MovieModel;
import com.sist.movie.model.ReserveModel;
//mvc�۾��ƴϴ�.
//main,detail.jsp�� �ִ°��� ������ ������ �����ϰ� �ϱ���ؼ� control�� (java��������) �����Ŵ�
public class MovieController {

	// ���尴ü�� �Ű������� ����ϴ� ���
	public void controller(HttpServletRequest request) {
		MovieModel mm = new MovieModel();
		MemberModel mb= new MemberModel();
		ReserveModel rm=new ReserveModel();
		String mode = request.getParameter("mode");
		// ��ɺи�
		if (mode == null)
			mode = "0";
		int index = Integer.parseInt(mode);
		// mode�϶��� if���� switch�� ��
		switch (index) {
		case 0: //movieDetaildata 
			mm.movieListData(request); 
			return; 
		case 1:
			mm.movieDetailData(request);
			break;
		case 2:
			mb.memberJoin(request);
			break;
		case 3:
			rm.reserveMain(request);
			break;
		}
	}
}
