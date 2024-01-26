package com.aciee.shoeStore.controller;

import com.aciee.shoeStore.model.CartEntity;
import com.aciee.shoeStore.model.OrdersEntity;
import com.aciee.shoeStore.model.ShoesEntity;
import com.aciee.shoeStore.model.UsersEntity;
import com.aciee.shoeStore.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/clients")
public class ClientController {

    private final ShoesService shoesService;
    private final CartService cartService;
    private final UsersService usersService;

    private final OrdersService ordersService;



    public ClientController(ShoesService shoesService, CartService cartService, UsersService usersService, OrdersService ordersService) {
        this.shoesService = shoesService;
        this.cartService = cartService;
        this.usersService = usersService;
        this.ordersService = ordersService;
    }

    @GetMapping("/shoesList")
    public String getFilteredShoes(Model model,
                                   @RequestParam(value = "minPrice", required = false) Double minPrice,
                                   @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                   @RequestParam(value = "sortBy", required = false) String sortBy,
                                   @RequestParam(value = "size", required = false) Float size,
                                   @RequestParam(value = "search", required = false) String search,
                                   WebRequest webRequest) {

        // Store filter values in the session
        webRequest.setAttribute("minPrice", minPrice, WebRequest.SCOPE_SESSION);
        webRequest.setAttribute("maxPrice", maxPrice, WebRequest.SCOPE_SESSION);
        webRequest.setAttribute("sortBy", sortBy, WebRequest.SCOPE_SESSION);
        webRequest.setAttribute("size", size, WebRequest.SCOPE_SESSION);
        webRequest.setAttribute("search", search, WebRequest.SCOPE_SESSION);

        List<ShoesEntity> filteredShoes = shoesService.getFilteredShoes(minPrice, maxPrice, sortBy, size, search);
        model.addAttribute("shoes", filteredShoes);

        return "fragments/shoesListFragment :: shoesListFragment";
    }

    @GetMapping("")
    public String getShoes(Model model,
                           @RequestParam(value = "minPrice", required = false) Double minPrice,
                           @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                           @RequestParam(value = "sortBy", required = false) String sortBy,
                           @RequestParam(value = "size", required = false) Float size,
                           @RequestParam(value = "search", required = false) String search) {

        List<ShoesEntity> filteredShoes = shoesService.getFilteredShoes(minPrice, maxPrice, sortBy, size, search);

        model.addAttribute("shoes", filteredShoes);
        model.addAttribute("sizes", shoesService.getDistinctSizes()); // Add sizes to the model

        return "client";
    }

    @GetMapping("/sizes")
    @ResponseBody
    public List<Float> getDistinctSizes() {
        return shoesService.getDistinctSizes();
    }


    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public UsersEntity getUser() {
        return usersService.getUserByUsername(getUsername());
    }

    @GetMapping("/cart")
    public String getCart(Model model) {
        List<CartEntity> cartItems = cartService.getAllCartItems(getUsername());
        model.addAttribute("cartItems", cartItems); // Use the correct attribute name

        return "cart";
    }

    @PostMapping("/addToCart")
    @ResponseBody
    public ResponseEntity<String> addToCart(@RequestParam Long shoeId) {
        Optional<ShoesEntity> optionalShoe = shoesService.getShoeById(shoeId);

        if (optionalShoe.isPresent()) {
            ShoesEntity shoe = optionalShoe.get();
            cartService.addToCart(getUsername(), shoe);

            log.info("Added shoe with ID {} to the cart for user {}.", shoeId, getUsername());
            return ResponseEntity.ok("Shoe added to cart successfully.");
        } else {
            log.warn("Shoe with ID {} not found.", shoeId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shoe not found.");
        }
    }


    @PostMapping("/removeFromCart")
    public String removeFromCart(@RequestParam Long shoeId) {
        Optional<ShoesEntity> optionalShoe = shoesService.getShoeById(shoeId);

        if (optionalShoe.isPresent()) {
            ShoesEntity shoe = optionalShoe.get();
            cartService.removeFromCart(getUsername(), shoe);

            log.info("Removed shoe with ID {} from the cart for user {}.", shoeId, getUsername());
        } else {
            log.warn("Shoe with ID {} not found.", shoeId);
        }

        return "redirect:/clients/cart";
    }

    @PostMapping("/cart/clearCart")
    public String clearCart() {
        cartService.clearCarts(getUsername());
        return "redirect:/clients/cart";
    }

    @PostMapping("/cart/buyAll")
    public String buyAll(Model model) {
        List<CartEntity> carts = cartService.getAllCartItems(getUsername());
        if (carts.isEmpty()) {
            // Cart is empty, set a message and redirect to products page
            model.addAttribute("cartEmptyMessage", "Nothing to buy, cart is empty. Redirecting to products page...");
            return "redirect:/clients";
        }

        OrdersEntity order = new OrdersEntity(usersService.getUserByUsername(getUsername()), cartService.calculateTotalAmount(getUsername()));

        OrdersEntity savedOrder = ordersService.createOrder(order);

        cartService.moveCartsToOrderDetails(savedOrder);

        cartService.clearCarts(getUsername());

        return "redirect:/clients";
    }


}
