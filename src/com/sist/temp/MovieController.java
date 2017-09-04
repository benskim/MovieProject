package com.sist.temp;

import javax.servlet.http.*;

import com.sist.movie.model.MemberModel;
import com.sist.movie.model.MovieModel;
import com.sist.movie.model.ReserveModel;
//mvc작업아니다.
//main,detail.jsp에 넣는것이 맞지만 재사용을 수월하게 하기우해서 control로 (java형식으로) 빼낸거다
public class MovieController {

	// 내장객체를 매개변수로 사용하는 경우
	public void controller(HttpServletRequest request) {
		MovieModel mm = new MovieModel();
		MemberModel mb= new MemberModel();
		ReserveModel rm=new ReserveModel();
		String mode = request.getParameter("mode");
		// 기능분리
		if (mode == null)
			mode = "0";
		int index = Integer.parseInt(mode);
		// mode일때는 if보다 switch가 편리
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
