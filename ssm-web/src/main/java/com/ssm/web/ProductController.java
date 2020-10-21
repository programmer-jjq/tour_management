package com.ssm.web;

import com.github.pagehelper.PageInfo;
import com.ssm.domain.Product;
import com.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    // 查询所有产品信息 --未分页
    /*@RequestMapping("/findAll")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Product> products = productService.findAll();
        mv.addObject("productList",products);
        mv.setViewName("product-list");
        return mv;
    }*/

    // 查询所有产品信息 --分页
    @RequestMapping("/findAll")
    public ModelAndView findAll(
            @RequestParam(name = "page",required =true,defaultValue = "1") Integer page,
            @RequestParam(name = "size",required = true,defaultValue = "4") Integer size
            ) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Product> products = productService.findAll(page,size);
        PageInfo pageInfo = new PageInfo(products);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("product-list");
        return mv;
    }

    // 添加产品
    @RequestMapping("/save")
    public String save(Product product) throws Exception{
        productService.save(product);
        return "redirect:findAll";
    }

    // 删除产品
    @RequestMapping("/deleteProduct")
    public String deleteProduct(Integer id) throws Exception{
        productService.deleteById(id);
        return "redirect:findAll";
    }
}
