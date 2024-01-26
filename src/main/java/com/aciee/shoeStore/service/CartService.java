package com.aciee.shoeStore.service;

import com.aciee.shoeStore.model.CartEntity;
import com.aciee.shoeStore.model.OrderDetailsEntity;
import com.aciee.shoeStore.model.OrdersEntity;
import com.aciee.shoeStore.model.ShoesEntity;
import com.aciee.shoeStore.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartService {

    private final CartRepository cartRepository;

    private final UsersService usersService;


    private final OrderDetailsService orderDetailsService;

    public CartService(CartRepository cartRepository, UsersService usersService, OrderDetailsService orderDetailsService) {
        this.cartRepository = cartRepository;
        this.usersService = usersService;
        this.orderDetailsService = orderDetailsService;
    }

    public List<CartEntity> getAllCartItems(String username) {
        return cartRepository.findAllByUsername(Optional.ofNullable(usersService.getUserByUsername(username)));
    }

    public Optional<CartEntity> getCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    public CartEntity createCart(CartEntity cart) {
        return cartRepository.save(cart);
    }


    public void updateCart(Long cartId, CartEntity updatedCart) {
        if (cartRepository.existsById(cartId)) {
            updatedCart.setCartId(cartId);
            cartRepository.save(updatedCart);
        }
    }

    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public void addToCart(String username, ShoesEntity shoe) {
        CartEntity cart = findUsernameShoe(username, shoe);

        if (cart != null) {
            cart.incrementQuantity();
        } else {
            cart = new CartEntity(usersService.getUserByUsername(username), shoe);
        }

        cartRepository.save(cart);
    }

    public CartEntity findUsernameShoe(String username, ShoesEntity shoe) {
        return cartRepository.findByUsernameAndShoeId(usersService.getUserByUsername(username), shoe);
    }

    public void removeFromCart(String username, ShoesEntity shoe) {
        CartEntity cart = findUsernameShoe(username, shoe);

        if (cart != null) {
            cart.decrementQuantity();

            // If quantity is 0, remove the item from the cart
            if (cart.getQuantity() <= 0) {
                deleteCart(cart.getCartId());
            } else {
                // Update the cart with the new quantity
                updateCart(cart.getCartId(), cart);
            }
        } else {
            log.warn("Item not found in the cart for user {}.", username);
        }
    }

    public void clearCarts(String username) {
        List<CartEntity> carts = cartRepository.findAllByUsername(Optional.ofNullable(usersService.getUserByUsername(username)));
        for (CartEntity cart : carts) {
            deleteCart(cart.getCartId());
        }
    }

    public void moveCartsToOrderDetails(OrdersEntity order) {
        List<CartEntity> carts = cartRepository.findAllByUsername(Optional.ofNullable(usersService.getUserByUsername(order.getUsername().getUsername())));
        for (CartEntity cart : carts) {
            OrderDetailsEntity orderDetails = new OrderDetailsEntity();
            orderDetails.setOrderId(order);
            orderDetails.setShoeId(cart.getShoeId());
            orderDetails.setQuantity(cart.getQuantity());
            orderDetailsService.createOrderDetail(orderDetails);
        }
    }

    public double calculateTotalAmount(String username) {
        List<CartEntity> carts = cartRepository.findAllByUsername(Optional.ofNullable(usersService.getUserByUsername(username)));
        double totalAmount = 0;

        for (CartEntity cart : carts) {
            ShoesEntity shoe = cart.getShoeId();
            totalAmount += (shoe.getPrice() * cart.getQuantity());
        }
        return totalAmount;
    }
}