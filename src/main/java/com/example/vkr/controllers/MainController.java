package com.example.vkr.controllers;

import com.example.vkr.entities.*;
import com.example.vkr.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;
    private final RoleService roleService;


    @GetMapping
    public String homeMain(Model model) {
        model.addAttribute("title", "Home Page");
        return "HomePage";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("registration", "Registration");
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String user(Principal principal, Model model){
        model.addAttribute("user", userService.findByLogin(principal.getName()));
        return "user";
    }
    @GetMapping("/editUser/{id}")
    public String editUserById(Model model, @PathVariable("id")Long id){
        User user = userService.findById(id);

        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", user);
        return "editUser";
    }
    @PostMapping("/editUser")
    public  String editUser(@ModelAttribute("user")User user){
        userService.save(user);
        return "redirect:/main/user";
    }

    @GetMapping("/products")
    public String allProducts(Model model) {
        Product product = new Product();
        List<Product> products = productService.findAll();
        List<Order> orders = orderService.findAll();
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        model.addAttribute("orders", orders);
        return "products";
    }

    @GetMapping("/addProduct")
    public String addProducts(Model model){
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("product") Product product, Principal principal) {
        product.setUser(userService.findByLogin(principal.getName()));
        productService.save(product);
        return "redirect:/main/products";
    }
    @GetMapping("/products/{id}")
    public String productDetails(@PathVariable("id") Long id, Model model){
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "productDetails";
    }

    @GetMapping("/editProduct/{id}")
    public String editProductById(Model model, @PathVariable("id") Long id) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/editProduct")
    public String editProduct(@ModelAttribute("product") Product product){
        productService.save(product);
        return "redirect:/main/products";
    }
    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/main/products";
    }

    @PostMapping("/addOrder")
    public String addGroup(@ModelAttribute("order") Order order){
        orderService.save(order);
        return "redirect:/main/orders";
    }

    @GetMapping("/addOrder/product/{id}")
    public String addOrder(Model model, @PathVariable("id") Long id, Principal principal){
        System.out.println(productService.findById(id));
        model.addAttribute("order", Order.builder()
                .product(productService.findById(id))
                .user(userService.findByLogin(principal.getName()))
                .build());
        return "addOrder";
    }

    @GetMapping("/orders")
    public String allOrders(Model model, Principal principal) {
        if (userService.findByLogin(principal.getName()).getRoles().contains(roleService.findById(2L))){
            model.addAttribute("orders", orderService.findOrderByProductUserLogin(principal.getName()));
        }else {
            model.addAttribute("orders", orderService.findOrderByLogin(principal.getName()));
        }
        return "orders";
    }
    @GetMapping("/orders/{id}")
    public String allOrders(@PathVariable("id") Long id,Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return "orderDetails";
    }

    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        Order order = orderService.findById(id);
        Product product = order.getProduct();
        order.setProduct(product);
        orderService.deleteById(id);
        return "redirect:/main/orders";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/main/allUsers";
    }

    @GetMapping("/editOrder/{id}")
    public String editOrderById(Model model, @PathVariable("id")Long id){
        Order order = orderService.findById(id);
        Product product = order.getProduct();
        order.setProduct(product);
        model.addAttribute("order", order);
        model.addAttribute("product", product);
        return "editOrder";
    }

    @PostMapping("/editOrder")
    public String editOrder(@ModelAttribute("order")Order order){
        orderService.save(order);
        return "redirect:/main/orders";
    }


}
