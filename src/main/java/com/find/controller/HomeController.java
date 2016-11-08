package com.find.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.find.mapper.DealInfoDao;

/**
 * Created by buri on 2016. 10. 24..
 */
@Controller
public class HomeController {

	private static final int LIST_PAGE_SIZE = 25;
	@Autowired
	DealInfoDao placeDao;

	@RequestMapping(value = "/places", method = RequestMethod.GET)
	public String showHouses( HttpServletRequest request ){
		request.getSession().setAttribute("placeList", null);
		return "redirect:places/page/1";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/places/page/{pageNumber}", method = RequestMethod.GET)
	public String showHouses( HttpServletRequest request, @PathVariable Integer pageNumber, Model model ){

		PagedListHolder<?> pagedListHolder = (PagedListHolder<?>) request.getSession().getAttribute("placeList");
		if( pagedListHolder == null ) {
			pagedListHolder = new PagedListHolder(placeDao.getPlaces());
			pagedListHolder.setPageSize(LIST_PAGE_SIZE);
		}
		else {
			final int goToPage = pageNumber - 1;
			if( goToPage <= pagedListHolder.getPageCount() && goToPage >= 0 ) {
				pagedListHolder.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("placeList", pagedListHolder);

		int current = pagedListHolder.getPage() + 1;
		int begin = Math.max(1, current - LIST_PAGE_SIZE);
		int end = Math.min(begin + 5, pagedListHolder.getPageCount());
		int totalPageCount = pagedListHolder.getPageCount();
		String baseUrl = "/places/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("totalItems", pagedListHolder.getNrOfElements());
		model.addAttribute("placeList", pagedListHolder);

		return "places";
	}
}
