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

@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final ProductService productService;
    private final OrderService orderService;
    private final UserServiceImpl userServiceImpl;
    private final RoleService roleService;


    @GetMapping
    public String homeMain(Model model) {
        List<User> users = userServiceImpl.findAll();
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
        userServiceImpl.save(user);
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String user(Principal principal, Model model){
        model.addAttribute("user", userServiceImpl.findByLogin(principal.getName()));
        return "user";
    }
    @GetMapping("/editUser/{id}")
    public String editUserById(Model model, @PathVariable("id")Long id){
        User user = userServiceImpl.findById(id);

        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", user);
        return "editUser";
    }
    @PostMapping("/editUser")
    public  String editUser(@ModelAttribute("user")User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userServiceImpl.save(user);
        return "redirect:/main/user";
    }

    @PostMapping("/addOrder")
    public String addGroup(@ModelAttribute("order") Order order) {
        Product product = order.getProduct();
//        product.setInStock(product.getInStock() - order.getAmount());
        order.setProduct(product);
        orderService.save(order);
        return "redirect:/main/allOrders";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/main/allProducts";
    }

    @GetMapping("/allOrders")
    public String allOrders(Model model) {
        Order order = new Order();
        List<User> users = userServiceImpl.findAll();
        List<Product> products = productService.findAll();

        model.addAttribute("order", order);
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("users", users);

        model.addAttribute("products", products);
        return "admin/orders";
    }
//
//    @GetMapping("/allClients")
//    public String allClients(Model model) {
//        Client client = new Client();
//
//        model.addAttribute("client", client);
//        model.addAttribute("clients", clientService.findAll());
//
//        return "admin/clients";
//    }
//
//
    @GetMapping("/allProducts")
    public String allProducts(Model model) {
        Product product = new Product();
        List<Product> products = productService.findAll();
//        List<Supplier> suppliers = supplierService.findAll();
//        List<Type> types = typeService.findAll();
        List<Order> orders = orderService.findAll();


        model.addAttribute("product", product);
        model.addAttribute("products", products);
//        model.addAttribute("suppliers", suppliers);
//        model.addAttribute("types", types);
        model.addAttribute("orders", orders);

        return "admin/products";
    }
//
//    @GetMapping("/allSuppliers")
//    public String allSuppliers(Model model) {
//        Supplier supplier = new Supplier();
//        List<Supplier> suppliers = supplierService.findAll();
//        model.addAttribute("supplier", supplier);
//        model.addAttribute("suppliers", suppliers);
//        return "admin/suppliers";
//    }
//
//    @GetMapping("/allTypes")
//    public String allTypes(Model model) {
//        Type type = new Type();
//        List<Type> types = typeService.findAll();
//        model.addAttribute("type", type);
//        model.addAttribute("types", types);
//        return "admin/types";
//    }
//    @GetMapping("/allUsers")
//    public String allUsers(Model model) {
//        User user = new User();
//        List<User> users  = userServiceImpl.findAll();
//        model.addAttribute("user", user);
//        model.addAttribute("roles", roleService.findAll());
//        model.addAttribute("users", users);
//        return "admin/users";
//    }


//    // удаление
//    @GetMapping("/deleteClient/{id}")
//    public String deleteClient(@PathVariable("id") Long id) {
//        clientService.deleteById(id);
//        return "redirect:/main/allClients";
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

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/main/allProducts";
    }

//    @GetMapping("/deleteSupplier/{id}")
//    public String deleteSupplier(@PathVariable("id") Long id) {
//        supplierService.deleteById(id);
//        return "redirect:/main/allSuppliers";
//    }
//
//    @GetMapping("/deleteType/{id}")
//    public String deleteType(@PathVariable("id") Long id) {
//        typeService.deleteById(id);
//        return "redirect:/main/allTypes";
//    }
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userServiceImpl.deleteById(id);
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

    @GetMapping("/editProduct/{id}")
    public String editProductById(Model model, @PathVariable("id") Long id) {
        Product product = productService.findById(id);
//        List<Supplier> suppliers = supplierService.findAll();
//        List<Type> types = typeService.findAll();

        model.addAttribute("product", product);
//        model.addAttribute("suppliers", suppliers);
//        model.addAttribute("types", types);
        return "admin/editProduct";
    }

    @PostMapping("editProduct")
    public String editProduct(@ModelAttribute("product") Product product){
        productService.save(product);
        return "redirect:/main/allProducts";
    }

    @GetMapping("/editOrder/{id}")
    public String editOrderById(Model model, @PathVariable("id")Long id){
        Order order = orderService.findById(id);
//        List<Employee> employees = employeeService.findAll();
//        List<Client> clients = clientService.findAll();
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
        int previousOrderAmount = previousOrder.getAmount();
        int actualOrderAmount = order.getAmount();
        Product product = order.getProduct();
//        product.setInStock(product.getInStock() + previousOrderAmount - actualOrderAmount);
        order.setProduct(product);
        orderService.save(order);

        return "redirect:/main/allOrders";
    }
//    @GetMapping("/editType/{id}")
//    public String editTypeById(Model model, @PathVariable("id") Long id) {
//        Type type = typeService.findById(id);
//        model.addAttribute("type", type);
//        return "admin/editType";
//    }
//
//    @PostMapping("editType")
//    public  String editType(@ModelAttribute("type")Type type){
//        typeService.save(type);
//        return "redirect:/main/allTypes";
//    }



    @GetMapping("/addProduct")
    public void createProduct(){
        Product product = Product.builder().name("Шлёпка").build();
        productService.save(product);
    }


}
