package com.example.vkr.controllers;

import com.example.vkr.entities.*;
import com.example.vkr.services.*;
import lombok.RequiredArgsConstructor;
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
//        userService.findByLogin(principal.getName());
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

//    @PostMapping("/addOrder/product/{id}")
//    public String addOrder(Model model, @PathVariable("id") Long id){
//        model.addAttribute("order", new Order());
//        model.addAttribute("product", productService.findById(id));
//        System.out.println(productService.findById(id));
//        return "addOrder";
//    }

    @GetMapping("/orders")
    public String allOrders(Model model, Principal principal) {
        model.addAttribute("orders", orderService.findOrderByLogin(principal.getName()));
        return "orders";
    }
    @GetMapping("/orders/{id}")
    public String allOrders(@PathVariable("id") Long id,Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return "orderDetails";
    }
//    @GetMapping("/allUsers")
//    public String allUsers(Model model) {
//        User user = new User();
//        List<User> users  = userServiceImpl.findAll();
//        model.addAttribute("user", user);
//        model.addAttribute("roles", roleService.findAll());
//        model.addAttribute("users", users);

//        return "admin/users";

//    }

    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        Order order = orderService.findById(id);
        Product product = order.getProduct();
//        product.setInStock(product.getInStock() + order.getAmount());
        order.setProduct(product);
        orderService.deleteById(id);
        return "redirect:/main/allOrders";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/main/allUsers";
    }
//    @GetMapping("/editSupplier/{id}")
//    public String editSupplierById(Model model, @PathVariable("id") Long id) {
//        Supplier supplier = supplierService.findById(id);
//        model.addAttribute("supplier", supplier);
//        return "admin/editSupplier";
//    }
//
//    @PostMapping("/editSupplier")
//    public String editSupplier(@ModelAttribute("supplier") Supplier supplier) {
//        supplierService.save(supplier);
//        return "redirect:/main/allSuppliers";
//    }
//    @GetMapping("/editClient/{id}")
//    public String editClientById(Model model, @PathVariable("id") Long id) {
//        Client client = clientService.findById(id);
//        model.addAttribute("client", client);
//        return "admin/editClient";
//    }
//
//    @PostMapping("/editClient")
//    public String editClient(@ModelAttribute("client") Client client){
//        clientService.save(client);
//        return "redirect:/main/allClients";

//    }

    @GetMapping("/editOrder/{id}")
    public String editOrderById(Model model, @PathVariable("id")Long id){
        Order order = orderService.findById(id);
        List<Product> products = productService.findAll();

        model.addAttribute("order", order);
//        model.addAttribute("clients", clients);
//        model.addAttribute("employees", employees);
        model.addAttribute("products",  products);
        return "admin/editOrder";
    }

    @PostMapping("editOrder")
    @Transactional
    public String editOrder(@ModelAttribute("order")Order order){
        Order previousOrder = orderService.findById(order.getId());
//        int previousOrderAmount = previousOrder.getAmount();
//        int actualOrderAmount = order.getAmount();
        Product product = order.getProduct();
        order.setProduct(product);
        orderService.save(order);

        return "redirect:/main/allOrders";
    }


}
